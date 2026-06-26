/**********************************
 * IFPB - Bacharelado em Eng. de Software
 * Prof. Fausto Maranhão Ayres
 **********************************/
package iu_swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import modelo_negocio.ContatoComercial;
import servico.Servico;
import servico.ServicoContato;
import servico.ServicoContatoComercial;

public class TelaContatoComercial {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel label_empresa;
	private JLabel label_resultado;
	private JLabel label_mensagem;
	private JLabel label_id;
	private JLabel label_nome;
	private JLabel label_cidade;
	private JLabel label_telefone;
	private JLabel label_idCidade;
	private JTextField textField_id;
	private JTextField textField_nome;
	private JTextField textField_cidade;
	private JTextField textField_empresa;
	private JTextField textField_telefone;
	private JTextField textField_idCidade;
	private JButton button_criar;
	private JButton button_atualizar;
	private JButton button_apagar;
	private JButton button_limpar;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// TelaReuniao window = new TelaReuniao();
	// window.frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the application.
	 */
	public TelaContatoComercial() {
		initialize();
		frame.setVisible(true); 
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setResizable(false);
		frame.setModal(true);
		frame.setTitle("Agenda de Contatos Comerciais");
		frame.setBounds(100, 100, 813, 401);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				try {
					Servico.conectar();
					listagem();
				} catch (Exception e) {
					label_mensagem.setText(e.getMessage());
				}
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.out.println("Fechando tela de Contato Pessoal");
				try {
					Servico.desconectar();
				} catch (Exception e) {
					label_mensagem.setText(e.getMessage());
				}
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 39, 751, 147);
		frame.getContentPane().add(scrollPane);
		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nome", "Empresa", "Cidade" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		table.setGridColor(Color.BLACK);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					label_mensagem.setText("");
					if (table.getSelectedRow() >= 0) {
						int id = (int) table.getValueAt(table.getSelectedRow(), 0);
						ContatoComercial p = ServicoContatoComercial.localizarContatoComercial(id);
						textField_id.setText(id + "");
						textField_nome.setText(p.getNome());
						textField_telefone.setText("");
					}
				} catch (Exception erro) {
					label_mensagem.setText(erro.getMessage());
				}
			}
		});

		label_mensagem = new JLabel("");
		label_mensagem.setForeground(Color.RED);
		label_mensagem.setBounds(21, 326, 751, 14);
		frame.getContentPane().add(label_mensagem);

		label_resultado = new JLabel("Selecione uma linha para editar");
		label_resultado.setBounds(21, 187, 394, 14);
		frame.getContentPane().add(label_resultado);

		label_nome = new JLabel("Nome:");
		label_nome.setHorizontalAlignment(SwingConstants.LEFT);
		label_nome.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_nome.setBounds(180, 215, 50, 14);
		frame.getContentPane().add(label_nome);

		label_cidade = new JLabel("Cidade:");
		label_cidade.setHorizontalAlignment(SwingConstants.LEFT);
		label_cidade.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_cidade.setBounds(180, 269, 50, 14);
		frame.getContentPane().add(label_cidade);

		label_empresa = new JLabel("Empresa:");
		label_empresa.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_empresa.setHorizontalAlignment(SwingConstants.LEFT);
		label_empresa.setBounds(180, 241, 50, 14);
		frame.getContentPane().add(label_empresa);
		
		label_telefone = new JLabel("N\u00FAmero:");
		label_telefone.setHorizontalAlignment(SwingConstants.LEFT);
		label_telefone.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_telefone.setBounds(301, 297, 50, 14);
		frame.getContentPane().add(label_telefone);

		button_criar = new JButton("Criar");
		button_criar.setToolTipText("Cadastrar novo contato");
		button_criar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botaoCriar();
			}
		});
		button_criar.setBounds(528, 211, 95, 23);
		frame.getContentPane().add(button_criar);

		button_atualizar = new JButton("Atualizar");
		button_atualizar.setToolTipText("Atualizar contato");
		button_atualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botaoAtualizar();
			}
		});
		button_atualizar.setBounds(528, 237, 95, 23);
		frame.getContentPane().add(button_atualizar);

		button_apagar = new JButton("Apagar");
		button_apagar.setToolTipText("Apagar contato e seus dados");
		button_apagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botaoApagar();
			}
		});
		button_apagar.setBounds(633, 211, 89, 23);
		frame.getContentPane().add(button_apagar);

		button_limpar = new JButton("Limpar");
		button_limpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_id.setText("");
				textField_nome.setText("");
				textField_empresa.setText("");
				textField_telefone.setText("");
			}
		});
		button_limpar.setBounds(633, 237, 89, 23);
		frame.getContentPane().add(button_limpar);
		
		textField_nome = new JTextField();
		textField_nome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_nome.setColumns(10);
		textField_nome.setBackground(Color.WHITE);
		textField_nome.setBounds(230, 211, 238, 20);
		frame.getContentPane().add(textField_nome);

		textField_empresa = new JTextField();
		textField_empresa.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_empresa.setColumns(10);
		textField_empresa.setBounds(230, 237, 238, 20);
		frame.getContentPane().add(textField_empresa);

		label_id = new JLabel("ID:");
		label_id.setHorizontalAlignment(SwingConstants.LEFT);
		label_id.setBounds(21, 215, 21, 14);
		frame.getContentPane().add(label_id);

		textField_id = new JTextField();
		textField_id.setFocusable(false);
		textField_id.setEditable(false);
		textField_id.setBounds(41, 211, 28, 20);
		frame.getContentPane().add(textField_id);
		textField_id.setColumns(10);
		
		label_idCidade = new JLabel("ID da Cidade:");
		label_idCidade.setHorizontalAlignment(SwingConstants.LEFT);
		label_idCidade.setBounds(21, 255, 21, 14);
		frame.getContentPane().add(label_idCidade);

		textField_idCidade = new JTextField();
		textField_idCidade.setFocusable(false);
		textField_idCidade.setEditable(false);
		textField_idCidade.setBounds(41, 811, 28, 20);
		frame.getContentPane().add(textField_idCidade);
		textField_idCidade.setColumns(10);
		
		textField_telefone = new JTextField();
		textField_telefone.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_telefone.setColumns(10);
		textField_telefone.setBounds(353, 293, 115, 20);
		frame.getContentPane().add(textField_telefone);

		try {
			
			textField_cidade = new JTextField();
			textField_cidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
			textField_cidade.setColumns(10);
			textField_cidade.setBounds(230, 265, 238, 20);
			frame.getContentPane().add(textField_cidade);

		} catch (Exception e1) {
		}
	}

	public void listagem() {
		try {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setRowCount(0);
			List<ContatoComercial> lista = ServicoContatoComercial.listarContatosEmpresa();
			for (ContatoComercial p : lista)
				model.addRow(new Object[] { p.getId(), p.getNome(), p.getCidade() });

			label_resultado.setText("Resultados: " + lista.size() + " contatos - selecione uma linha para editar");
		} catch (Exception erro) {
			label_mensagem.setText(erro.getMessage());
		}
	}

	public void botaoApagar() {
		try {
			if (textField_id.getText().isEmpty()) {
				label_mensagem.setText("Selecione um contato");
				return;
			}

			label_mensagem.setText("");
			textField_telefone.setText("");
			int id = Integer.parseInt(textField_id.getText());

			Object[] options = { "Confirmar", "Cancelar" };
			int escolha = JOptionPane.showOptionDialog(null, "Esta operação apagará o contato " + id, "Alerta",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
			if (escolha == JOptionPane.OK_OPTION) {
				ServicoContato.apagarContato(id);
				label_mensagem.setText("Contato excluído id: " + id);
				listagem();
			} else
				label_mensagem.setText("Exclusão cancelada id: " + id);

		} catch (Exception erro) {
			label_mensagem.setText(erro.getMessage());
		}
	}

	public void botaoCriar() {
		try {
			label_mensagem.setText("");
			String nome = textField_nome.getText().trim();
			String empresa = textField_empresa.getText().trim();
			int idC = Integer.parseInt(textField_idCidade.getText().trim());

			ServicoContatoComercial.criarContatoComercial(nome, empresa, idC);

			label_mensagem.setText("Contato criada");
			listagem();

		} catch (Exception ex) {
			label_mensagem.setText(ex.getMessage());
		}
	}

	public void botaoAtualizar() {
		try {
			if (textField_id.getText().isEmpty()) {
				label_mensagem.setText("Selecione um contato");
				return;
			}

			label_mensagem.setText("");
			int id = Integer.parseInt(textField_id.getText().trim());
			String nome = textField_nome.getText().trim();
			String empresa = textField_empresa.getText().trim();
			int idC = Integer.parseInt(textField_idCidade.getText().trim());

			ServicoContatoComercial.alterarContatoComercial(id, nome, empresa, idC);

			label_mensagem.setText("Pessoa atualizada id: " + id);
			listagem();
		} catch (Exception ex2) {
			label_mensagem.setText(ex2.getMessage());
		}
	}
}

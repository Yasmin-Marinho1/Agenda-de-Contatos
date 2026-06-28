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
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

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

import modelo_negocio.Cidade;
import modelo_negocio.ContatoPessoal;
import servico.Servico;
import servico.ServicoCidade;
import servico.ServicoContato;
import servico.ServicoContatoPessoal;

public class TelaContatoPessoal {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel label_grauProximidade;
	private JLabel label_resultado;
	private JLabel label_mensagem;
	private JLabel label_id;
	private JLabel label_nome;
	private JLabel label_cidade;
	private JLabel label_telefone;
	private JTextField textField_id;
	private JTextField textField_nome;
	private JTextField textField_cidade;
	private JTextField textField_grauProximidade;
	private JTextField textField_telefone;
	private JButton button_criar;
	private JButton button_atualizar;
	private JButton button_apagar;
	private JButton button_limpar;
	private JButton button_listarTelefones;
	private JButton button_criarTelefones;
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
	public TelaContatoPessoal() {
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
		frame.setTitle("Agenda de Contatos Pessoais");
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
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nome", "Cidade", "Grau de Proximidade", "Telefones Cadastrados" }) {
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
		// evento click na linha da tabela
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					label_mensagem.setText("");
					if (table.getSelectedRow() >= 0) {
						int id = (int) table.getValueAt(table.getSelectedRow(), 0);
						ContatoPessoal c = ServicoContatoPessoal.localizarContatoPessoal(id);
						textField_id.setText(id + "");
						textField_nome.setText(c.getNome());
						textField_cidade.setText(c.getCidade().getNome());
						textField_grauProximidade.setText(c.getGrauProximidade() + "");
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

		label_resultado = new JLabel("Selecione uma linha para editar o contato pessoal");
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
		label_cidade.setBounds(180, 239, 50, 14);
		frame.getContentPane().add(label_cidade);

		label_grauProximidade = new JLabel("Grau de Proximidade:");
		label_grauProximidade.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_grauProximidade.setHorizontalAlignment(SwingConstants.LEFT);
		label_grauProximidade.setBounds(180, 263, 123, 14);
		frame.getContentPane().add(label_grauProximidade);
		
		label_telefone = new JLabel("Adicionar Telefone:");
		label_telefone.setHorizontalAlignment(SwingConstants.LEFT);
		label_telefone.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_telefone.setBounds(239, 297, 102, 14);
		frame.getContentPane().add(label_telefone);
		
		button_listarTelefones = new JButton("Listar Telefones");
		button_listarTelefones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botaoListarTelefones();
			}
		});
		button_listarTelefones.setBounds(101, 293, 129, 23);
		frame.getContentPane().add(button_listarTelefones);
		
		button_criarTelefones = new JButton("Criar Telefone");
		button_criarTelefones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botaoCriarTelefone();
			}
		});
		button_criarTelefones.setBounds(479, 293, 129, 23);
		frame.getContentPane().add(button_criarTelefones);

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
				botaoLimpar();
			}
		});
		button_limpar.setBounds(633, 237, 89, 23);
		frame.getContentPane().add(button_limpar);
		
		textField_nome = new JTextField();
		textField_nome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_nome.setColumns(10);
		textField_nome.setBackground(Color.WHITE);
		textField_nome.setBounds(221, 211, 247, 20);
		frame.getContentPane().add(textField_nome);

		textField_grauProximidade = new JTextField();
		textField_grauProximidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_grauProximidade.setColumns(10);
		textField_grauProximidade.setBounds(289, 259, 28, 20);
		frame.getContentPane().add(textField_grauProximidade);

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
		
		textField_telefone = new JTextField();
		textField_telefone.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_telefone.setColumns(10);
		textField_telefone.setBounds(339, 293, 129, 20);
		frame.getContentPane().add(textField_telefone);

		try {
			
			textField_cidade = new JTextField();
			textField_cidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
			textField_cidade.setColumns(10);
			textField_cidade.setBounds(221, 235, 247, 20);
			frame.getContentPane().add(textField_cidade);

		} catch (Exception e1) {
		}
	}

	public void listagem() {
		try {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setRowCount(0);
			List<ContatoPessoal> lista = ServicoContatoPessoal.listarContatosPessoais();
			for (ContatoPessoal p : lista)
				model.addRow(new Object[] { p.getId(), p.getNome(), p.getCidade().getNome(), p.getGrauProximidade(), p.getTelefones().size() });

			label_resultado.setText("Resultados: " + lista.size() + " contatos pessoais - selecione uma linha para editar");
		} catch (Exception erro) {
			label_mensagem.setText(erro.getMessage());
		}
	}
	
	public void botaoLimpar() {
		textField_id.setText("");
		textField_nome.setText("");
		textField_grauProximidade.setText("");
		textField_telefone.setText("");
		textField_cidade.setText("");
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
				botaoLimpar();
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
			int grau = Integer.parseInt(textField_grauProximidade.getText().trim());
			String cidade = textField_cidade.getText();
			String nom = Normalizer.normalize(cidade, Normalizer.Form.NFD);
			Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	        String name = pattern.matcher(nom).replaceAll("");
			String c = name.toUpperCase();
			if (ServicoCidade.localizarCidade(c) == null) {
				ServicoCidade.criarCidade(c);
			}
			Cidade cid = ServicoCidade.localizarCidade(c);
			textField_cidade.setText(cid.getNome());
			int idCidade = cid.getId();
			ServicoContatoPessoal.criarContatoPessoal(nome, grau, idCidade);

			label_mensagem.setText("Contato pessoal criado");
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
			int grau = Integer.parseInt(textField_grauProximidade.getText().trim());
			String cidade = textField_cidade.getText();
			String nom = Normalizer.normalize(cidade, Normalizer.Form.NFD);
			Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	        String name = pattern.matcher(nom).replaceAll("");
			String c = name.toUpperCase();
			if (ServicoCidade.localizarCidade(c) == null) {
				ServicoCidade.criarCidade(c);
			}
			Cidade cid = ServicoCidade.localizarCidade(c);
			textField_cidade.setText(cid.getNome());
			int idCidade = cid.getId();
			ServicoContatoPessoal.alterarContatoPessoal(id, nome, grau, idCidade);

			label_mensagem.setText("Contato pessoal atualizado id: " + id);
			listagem();
		} catch (Exception ex2) {
			label_mensagem.setText(ex2.getMessage());
		}
	}
	public void botaoListarTelefones() {
		try {
			if (textField_id.getText().isEmpty()) {
				label_mensagem.setText("Selecione um contato pessoal");
				return;
			}

			int id = Integer.parseInt(textField_id.getText());
			ContatoPessoal p = ServicoContatoPessoal.localizarContatoPessoal(id);
			String telefones;
			if (p.getTelefones().size() == 0)
				telefones = p.getNome() + "\nSem telefones";
			else {
				telefones = p.getNome();
				for (String t : p.getTelefones())
					telefones = telefones + "\n" + t;
			}
			JOptionPane.showMessageDialog(frame, telefones, "Telefones", JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception erro) {
			label_mensagem.setText(erro.getMessage());
		}

	}
	public void botaoCriarTelefone() {
		try {
			if (textField_id.getText().isEmpty()) {
				label_mensagem.setText("Selecione um contato pessoal");
				return;
			}

			if (textField_telefone.getText().isEmpty()) {
				label_mensagem.setText("Campo de criar telefone vazio");
				return;
			}

			int id = Integer.parseInt(textField_id.getText());
			String numero = textField_telefone.getText();
			ServicoContato.adicionarTelefoneContato(numero, id);
			label_mensagem.setText("Telefone criado: " + numero);
			textField_telefone.setText("");
		} catch (Exception ex) {
			label_mensagem.setText(ex.getMessage());
		}
	}
}

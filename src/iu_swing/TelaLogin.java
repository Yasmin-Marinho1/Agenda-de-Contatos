package iu_swing;

/**********************************
 * IFPB - Bacharelado em Eng. de Software
 * Prof. Fausto Maranh�o Ayres
 **********************************/

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import modelo_negocio.Usuario;
import servico.Servico;
import servico.ServicoUsuario;

public class TelaLogin {

	private JFrame frame;
	private JLabel label;
	private JLabel label_1;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel label_2;
	private JButton button;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin window = new TelaLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Login");
		frame.setBounds(100, 100, 356, 231);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					Servico.conectar();
					
					Usuario u = ServicoUsuario.localizarUsuario("admin", "1234");
					if (u == null) {
						ServicoUsuario.criarUsuario("admin", "1234");
						label_2.setText("Usuário criado");
					}
					label_2.setText("");

				} catch (Exception ex) {
					label_2.setText(ex.getMessage());
				}
			}
			@Override
			public void windowClosed(WindowEvent arg0) {
				System.out.println("Fechando tela de Login");
				try {
					Servico.desconectar();
				} catch (Exception e) {
					label_2.setText(e.getMessage());
				}
			}
		});

		label = new JLabel("Nome");
		label.setBounds(100, 52, 46, 14);
		frame.getContentPane().add(label);

		label_1 = new JLabel("Senha");
		label_1.setBounds(100, 76, 46, 14);
		frame.getContentPane().add(label_1);

		textField = new JTextField();
		textField.setText("admin");
		textField.setBounds(156, 50, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setText("1234");
		textField_1.setBounds(156, 74, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		label_2 = new JLabel("Conectando...");
		label_2.setBounds(10, 170, 322, 14);
		frame.getContentPane().add(label_2);

		button = new JButton("Acessar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nome = textField.getText();
					String senha = textField_1.getText();
					Usuario u = ServicoUsuario.localizarUsuario(nome, senha);
					if (u == null)
						throw new Exception("Credenciais inválidas");

					ServicoUsuario.setLogado(u);
					frame.dispose();
					new TelaPrincipal();
				} catch (Exception ex) {
					label_2.setText(ex.getMessage());
				}
			}
		});
		button.setBounds(126, 118, 89, 23);
		frame.getContentPane().add(button);
	}
}

package iu_swing;
/**********************************
 * IFPB - Bacharelado em Eng. de Software
 * Prof. Fausto Maranhão Ayres
 **********************************/

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingConstants;

import servico.Servico;

public class TelaPrincipal {
	private JFrame frame;
	private JMenu mnContatoPessoal;
	private JMenu mnContatoComercial;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal window = new TelaPrincipal();
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
	public TelaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Agenda de Contatos");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					Servico.conectar();
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}
			}
			@Override
			public void windowClosed(WindowEvent arg0) {
				try {
					Servico.desconectar();
				} catch (Exception e) {
					label.setText(e.getMessage());
				}
			}
		});
		label = new JLabel("");
		label.setFont(new Font("Tahoma", Font.PLAIN, 26));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		java.net.URL urlImagem = getClass().getResource("/imagens/agenda.jpg");
		if (urlImagem != null) {
			ImageIcon imagem = new ImageIcon(urlImagem);
			imagem = new ImageIcon(imagem.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
			label.setIcon(imagem);
			label.setBounds(-32, 0, 513, 281);
		} else {
			label.setText("Agenda de Contatos");
			label.setBounds(-40, 0, 512, 200);
		}
		frame.getContentPane().add(label);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		mnContatoPessoal = new JMenu("Contato Pessoal");
		mnContatoPessoal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new TelaContatoPessoal();
			}
		});
		menuBar.add(mnContatoPessoal);
		
		mnContatoComercial = new JMenu("Contato Comercial");
		mnContatoComercial.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new TelaContatoComercial();
			}
		});
		menuBar.add(mnContatoComercial);
		
		
	}
}

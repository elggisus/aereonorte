package views;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import classes.Session;
import daos.UserDao;
import models.User;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import java.awt.Image;

public class VLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUser;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VLogin frame = new VLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VLogin() {
		setResizable(false);
		setTitle("Iniciar sesión - Aereonorte");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 456, 385);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "/logos/aereonorte_logo.jpg");
		Image img = icon.getImage().getScaledInstance(100, 40, java.awt.Image.SCALE_SMOOTH);

		txtUser = new JTextField();
		txtUser.setBounds(104, 153, 248, 36);
		contentPane.add(txtUser);
		txtUser.setColumns(10);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(104, 225, 248, 36);
		contentPane.add(txtPassword);

		JButton btnLogin = new JButton("Iniciar sesión");
		btnLogin.setBounds(126, 274, 200, 30);
		contentPane.add(btnLogin);

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String usuario = txtUser.getText().trim();
					String contrasena = new String(txtPassword.getPassword());
					if (usuario.isEmpty() || contrasena.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Ingrese usuario y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					UserDao userDao = new UserDao();
					User user = userDao.login(usuario, contrasena);
					if (user != null) {
						Session.getInstance().setUsername(user.getUsername());
						Session.getInstance().setIdUser(user.getIdUser());
						Session.getInstance().setName(user.getName());
						Session.getInstance().setRole(user.getRole());
						JOptionPane.showMessageDialog(null, "¡Bienvenido " + user.getName() + "!", "Acceso concedido", JOptionPane.INFORMATION_MESSAGE);
						dispose();
						new VMain().setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
					}
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JLabel lblLogo = new JLabel();
		lblLogo.setBounds(83, 6, 285, 115);
		try {
			ImageIcon icon2 = new ImageIcon(VLogin.class.getResource("/logos/aereonorte_logo.png"));
			Image img2 = icon2.getImage().getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_SMOOTH);
			lblLogo.setIcon(new ImageIcon(img2));
		} catch (Exception ex) {
			lblLogo.setText("Aereonorte");
		}
		contentPane.add(lblLogo);
		
		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setBounds(104, 125, 61, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Contrasena");
		lblNewLabel_1.setBounds(104, 202, 92, 16);
		contentPane.add(lblNewLabel_1);
		
		setLocationRelativeTo(null); // Centra la ventana en la pantalla

	}
}
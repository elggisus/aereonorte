package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import classes.ConnectionSql;
import classes.Session;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.Toolkit;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.Color;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class VMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VMain frame = new VMain();
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
	public VMain() {
		
		Session session = Session.getInstance();
		String nombreUsuario = session.getName();

		JLabel lblFechaHora = new JLabel();
		lblFechaHora.setFont(new Font("Arial", Font.BOLD, 28));
		lblFechaHora.setForeground(Color.BLACK);
		lblFechaHora.setBounds(6, 104, 500, 40);

		JLabel lblUsuario = new JLabel("Bienvenido: null");
		lblUsuario.setFont(new Font("Arial", Font.BOLD, 32));
		lblUsuario.setForeground(Color.BLACK);
		lblUsuario.setBounds(6, 6, 600, 50);
		lblUsuario.setText("Bienvenido: " + nombreUsuario);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(lblFechaHora);
		contentPane.add(lblUsuario);

		Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LocalDateTime fechaHora = LocalDateTime.now();
                String fechaHoraStr = fechaHora.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                lblFechaHora.setText("Fecha y hora: " + fechaHoraStr);
            }
        });
        timer.start();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(VMain.class.getResource("/icons/avion.png")));
		setTitle("Aereo Norte");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 930, 826);
		
		JMenuBar menuBar_1 = new JMenuBar();
		setJMenuBar(menuBar_1);
		

		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		    .put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "openCustomers");

		rootPane.getActionMap().put("openCustomers", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
		    public void actionPerformed(ActionEvent e) {
		        VCustomers vcustomers = new VCustomers();
		        vcustomers.setLocationRelativeTo(null);
		        vcustomers.setVisible(true);
		    }
		});
		
		JMenu mnNewMenu_2 = new JMenu("Configuracion del sistema");
		mnNewMenu_2.setIcon(new ImageIcon(VMain.class.getResource("/icons/ajustes.png")));
		menuBar_1.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Usuarios");
		mntmNewMenuItem_3.setIcon(new ImageIcon(VMain.class.getResource("/icons/business-people.png")));
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VUsers vusers = new VUsers();
				vusers.setLocationRelativeTo(null);
				vusers.setVisible(true);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_3);
		
		JMenu mnNewMenu_3 = new JMenu("Catalogos");
		mnNewMenu_3.setFont(new Font("Arial", Font.BOLD, 18));
		mnNewMenu_3.setIcon(new ImageIcon(VMain.class.getResource("/icons/lista.png")));
		menuBar_1.add(mnNewMenu_3);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Tipos Asientos");
		mntmNewMenuItem_5.setIcon(new ImageIcon(VMain.class.getResource("/icons/lista.png")));
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VSeatTypes vseatTypes = new VSeatTypes();
				vseatTypes.setLocationRelativeTo(null);
				vseatTypes.setVisible(true);
			}
		});
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Aviones");
		mntmNewMenuItem_6.setIcon(new ImageIcon(VMain.class.getResource("/icons/avion.png")));
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VAirplanes vairplanes = new VAirplanes();
				vairplanes.setLocationRelativeTo(null);
				vairplanes.setVisible(true);
			}
		});
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Rutas");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VRoutes vroutes = new VRoutes();
				vroutes.setLocationRelativeTo(null);
				vroutes.setVisible(true);
			}
		});
		mntmNewMenuItem_2.setIcon(new ImageIcon(VMain.class.getResource("/icons/maps-and-location.png")));
		mnNewMenu_3.add(mntmNewMenuItem_2);
		mnNewMenu_3.add(mntmNewMenuItem_6);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Asientos");
		
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VSeats vseats = new VSeats();
				vseats.setLocationRelativeTo(null);
				vseats.setVisible(true);
			}
		});
		mntmNewMenuItem_4.setIcon(new ImageIcon(VMain.class.getResource("/icons/asiento.png")));
		mnNewMenu_3.add(mntmNewMenuItem_4);
		mnNewMenu_3.add(mntmNewMenuItem_5);
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Horarios");
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VSchedules vseats = new VSchedules();
				vseats.setLocationRelativeTo(null);
				vseats.setVisible(true);
				
			}
		});
		mntmNewMenuItem_7.setIcon(new ImageIcon(VMain.class.getResource("/icons/hora-y-calendario.png")));
		mnNewMenu_3.add(mntmNewMenuItem_7);
		
		JMenu mnNewMenu = new JMenu("Clientes");
		mnNewMenu.setIcon(new ImageIcon(VMain.class.getResource("/icons/business-people.png")));
		menuBar_1.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Clientes [F1]");
		
		mntmNewMenuItem.setIcon(new ImageIcon(VMain.class.getResource("/icons/business-people.png")));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VCustomers vcustomers = new VCustomers();
				vcustomers.setLocationRelativeTo(null);
				vcustomers.setVisible(true);
			}
		});
		mntmNewMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
	
		
		JMenu mnNewMenu_1 = new JMenu("Ventas");
		mnNewMenu_1.setIcon(new ImageIcon(VMain.class.getResource("/icons/carro-de-la-carretilla.png")));
		mnNewMenu_1.setFont(new Font("Arial", Font.BOLD, 18));
		menuBar_1.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Nueva venta");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VSales  vsales = new VSales();
				vsales.setVisible(true);
				vsales.setLocationRelativeTo(null);
				
			}
		});
		mntmNewMenuItem_1.setIcon(new ImageIcon(VMain.class.getResource("/icons/circulo-plus.png")));
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
		JMenu mnReports = new JMenu("Reportes");
        mnReports.setIcon(new ImageIcon(VMain.class.getResource("/icons/lista.png")));
        mnReports.setFont(new Font("Arial", Font.BOLD, 18));
        menuBar_1.add(mnReports);

        JMenuItem mntmSalesReport = new JMenuItem("Reporte de Ventas");
        mntmSalesReport.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		VSalesReport vsalesReport = new VSalesReport();
				vsalesReport.setVisible(true);
				vsalesReport.setLocationRelativeTo(null);
        	}
        });
        mntmSalesReport.setIcon(new ImageIcon(VMain.class.getResource("/icons/lista.png")));
       
        mnReports.add(mntmSalesReport);
        
        // Backup menu
        JMenu mnBackup = new JMenu("Backup");
        mnBackup.setIcon(new ImageIcon(VMain.class.getResource("/icons/lista.png")));
        mnBackup.setFont(new Font("Arial", Font.BOLD, 18));
        menuBar_1.add(mnBackup);
        
                JMenuItem mntmBackup = new JMenuItem("Create Backup");
                mntmBackup.setIcon(new ImageIcon(VMain.class.getResource("/icons/lista.png")));
                mntmBackup.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Select backup destination");
                        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        int userSelection = fileChooser.showSaveDialog(VMain.this);
                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            java.io.File dir = fileChooser.getSelectedFile();
                            String backupPath = dir.getAbsolutePath() + "/aereonorte_backup.bak";
                            try {
                                ConnectionSql connSql = ConnectionSql.getInstance();
                                Connection conn = connSql.getConnection();
                                String dbName = conn.getCatalog();
                                String sql = "BACKUP DATABASE [" + dbName + "] TO DISK = N'" + backupPath + "' WITH NOFORMAT, NOINIT, NAME = 'Aereonorte-Full Backup', SKIP, NOREWIND, NOUNLOAD, STATS = 10";
                                System.out.println("Executing SQL: " + sql);
                                Statement stmt = conn.createStatement();
                                stmt.execute(sql);
                                JOptionPane.showMessageDialog(VMain.this, "Backup created successfully at: " + backupPath, "Success", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(VMain.this, "Error creating backup: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                });
                mnBackup.add(mntmBackup);
		
        JMenu mnSesion = new JMenu("Sesión");
        mnSesion.setIcon(new ImageIcon(VMain.class.getResource("/icons/logout.png")));
        mnSesion.setFont(new Font("Arial", Font.BOLD, 18));
        menuBar_1.add(mnSesion);

        JMenuItem mntmCerrarSesion = new JMenuItem("Cerrar sesión");
        mntmCerrarSesion.setIcon(new ImageIcon(VMain.class.getResource("/icons/logout.png")));
        mntmCerrarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = javax.swing.JOptionPane.showConfirmDialog(
                    VMain.this,
                    "¿Está seguro que desea cerrar sesión?",
                    "Confirmar cierre de sesión",
                    javax.swing.JOptionPane.YES_NO_OPTION
                );
                if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                    dispose(); // Close main window
                    VLogin vlogin = new VLogin();
                    vlogin.setLocationRelativeTo(null);
                    vlogin.setVisible(true);
                }
            }
        });
        mnSesion.add(mntmCerrarSesion);
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		int role = session.getRole();
		mnNewMenu.setVisible(role == 1); // Clientes
		mnNewMenu_2.setVisible(role == 1); // Configuración
		mnNewMenu_3.setVisible(role == 1); // Catálogos
		mnNewMenu_1.setVisible(role == 1 || role == 2); // Ventas
		mnReports.setVisible(role == 1 || role == 2 || role == 3); // Reportes
		mnBackup.setVisible(role == 1); // Backup solo para administrador
	}
}
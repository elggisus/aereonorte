package views;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import daos.UserDao;
import models.User;
import javax.swing.border.TitledBorder;
import java.awt.Component;
import java.awt.event.*;
import java.util.List;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class VUsers extends JFrame {
    private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
    private UserDao userDao = new UserDao();
    private JTextField txtName;
    private JTextField txtUser;
    private JTextField txtPassword;
    private JTable table;
    private int editingRow = -1;

    public JComboBox<String> cmbRol;

    public VUsers() {
        setTitle("Gestión de Usuarios");
        setSize(1536, 708);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "Nuevo usuario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "Listado de usuarios", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(panel, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
        			.addGap(18)
        			.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
        			.addGap(19))
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
        			.addGap(14)
        			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
        				.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE))
        			.addContainerGap())
        );
        
        JScrollPane scrollPane = new JScrollPane();
        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
        	gl_panel_1.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_1.createSequentialGroup()
        			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
        			.addContainerGap())
        );
        gl_panel_1.setVerticalGroup(
        	gl_panel_1.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
        			.addGap(24)
        			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
        			.addContainerGap())
        );
        
        table = new JTable();
        scrollPane.setViewportView(table);
        panel_1.setLayout(gl_panel_1);
        
        JLabel lblNewLabel = new JLabel("Nombre");
        
        txtName = new JTextField();
        txtName.setColumns(10);
        
        JLabel lblUsuario = new JLabel("Usuario");
        
        txtUser = new JTextField();
        txtUser.setColumns(10);
        
        JLabel lblContrasena = new JLabel("Contrasena");
        
        txtPassword = new JTextField();
        txtPassword.setColumns(10);
        
        JLabel lblRol = new JLabel("Rol");
        cmbRol = new JComboBox<>();
        cmbRol.addItem("Administrador"); // 1
        cmbRol.addItem("Cajero");        // 2
        cmbRol.addItem("Reportes");      // 3
        
        JButton btnNewButton = new JButton("Cancelar");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clearFields();
        	}
        });
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String name = txtName.getText();
                String username = txtUser.getText();
                String password = txtPassword.getText();
                int role = cmbRol.getSelectedIndex() + 1;
                if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Favor de llenar los campos requeridos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (editingRow == -1) {
                    User user = new User();
                    user.setName(name);
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setRole(role);
                    try {
                        userDao.save(user);
                        getUsers();
                        clearFields();
                        JOptionPane.showMessageDialog(null, "Usuario creado con exito", "Información", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Ocurrio un error al crear el usuario " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    int idUser = (int) model.getValueAt(editingRow, 0);
                    User user = new User();
                    user.setIdUser(idUser);
                    user.setName(name);
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setRole(role);
                    if (userDao.update(user)) {
                        getUsers();
                        clearFields();
                        JOptionPane.showMessageDialog(null, "Usuario editado con exito", "clear", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Ocurrio un error al actualizar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
       
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
        				.addGroup(gl_panel.createSequentialGroup()
        					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        						.addComponent(lblUsuario, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        						.addComponent(lblNewLabel)
        						.addComponent(lblRol))
        					.addGap(40)
        					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        						.addComponent(txtName, GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
        						.addComponent(txtUser, GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
        						.addComponent(cmbRol, 0, 697, Short.MAX_VALUE)))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblContrasena, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(txtPassword, GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)))
        			.addContainerGap())
        		.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
        			.addGap(310)
        			.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnGuardar, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
        			.addGap(297))
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(43)
        			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel)
        				.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblUsuario)
        				.addComponent(txtUser, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblContrasena)
        				.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblRol)
        				.addComponent(cmbRol, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED, 334, Short.MAX_VALUE)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(btnGuardar)
        				.addComponent(btnNewButton))
        			.addGap(20))
        );
        panel.setLayout(gl_panel);
        getContentPane().setLayout(groupLayout);

        model = new DefaultTableModel(new Object[]{"ID", "Nombre", "Usuario", "Status"}, 0);
        table.setModel(model);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    editingRow = row;
                    txtName.setText((String) model.getValueAt(row, 1));
                    txtUser.setText((String) model.getValueAt(row, 2));
                    txtPassword.setText(""); 
                }
            }
        });
        
        // Menú contextual para habilitar/deshabilitar usuario
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem itemHabilitar = new JMenuItem("Habilitar");
        JMenuItem itemDeshabilitar = new JMenuItem("Deshabilitar");
        popupMenu.add(itemHabilitar);
        popupMenu.add(itemDeshabilitar);

        itemHabilitar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int idUser = (int) model.getValueAt(row, 0);
                    System.out.println("Habilitar usuario ID: " + idUser);
                    
                    userDao.enable(idUser);
                    
                    JOptionPane.showMessageDialog(null, "Usuario habilitado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    
                    getUsers();
                    
                    
                }
            }
        });
        itemDeshabilitar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int idUser = (int) model.getValueAt(row, 0);
                    System.out.println("Deshabilitar usuario ID: " + idUser);
                    userDao.disable(idUser);
                    
                    JOptionPane.showMessageDialog(null, "Usuario deshabilitado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    
                    getUsers();
                    
                }
            }
        });

        table.setComponentPopupMenu(popupMenu);
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != -1) {
                    table.setRowSelectionInterval(row, row);
                }
            }
        });
        
        getUsers();
    }

    private void getUsers() {
		try {
			List<User> users = userDao.getAllUsers();
			model.setRowCount(0);
			for (User user : users) {
				model.addRow(new Object[] {
						user.getIdUser(),
						user.getName(),
						user.getUsername(),
						user.getStatus(),
						
				});
			}

			DefaultTableCellRenderer statusRender = new DefaultTableCellRenderer() {

				private static final long serialVersionUID = -6868279829515600174L;

				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

					int code = (value instanceof Number) ? ((Number) value).intValue() : User.ENABLED;
					String label = User.toLabel(code);
					setText(label);
					return c;
				}
			};

			table.getColumnModel().getColumn(3).setCellRenderer(statusRender);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

    
    private void clearFields() {
        txtName.setText("");
        txtUser.setText("");
        txtPassword.setText("");
        cmbRol.setSelectedIndex(0);
        editingRow = -1;
    }
    
}

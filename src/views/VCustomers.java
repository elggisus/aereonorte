package views;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import daos.CustomerDao;
import models.Customer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

public class VCustomers extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtEmail;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JTextField txtIdNumber;
	private JLabel lblNewLabel_4;
	private JTextField txtNacionality;
	private JLabel lblNewLabel_5;
	private JPanel panel_1;
	private JTable table;
	private JDateChooser dtBirthday = new JDateChooser();

	private DefaultTableModel tableModel;

	CustomerDao customerDao = new CustomerDao();
	private JTextField txtIdCustomer;
	private JTextField txtNameFilter;
	JComboBox<String> cmbStatusFilter;
	JLabel lblPaginateInfo;
	// Tabla
	private int currentPage = 1;
	private int pageSize = 2;
	private int totalRecords = 0;
	private int totalPages = 1;

	private JButton btnPrev;
	private JButton btnNext;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VCustomers frame = new VCustomers();
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
	@SuppressWarnings("serial")
	public VCustomers() {

		tableModel = new DefaultTableModel(
				new Object[] { "ID", "Nombre", "Correo", "Fecha de nacimiento", "# Identificacion", "Status" }, 0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int viewRow = table.getSelectedRow();
				if (viewRow == -1) {
					return;
				}

				int modelRow = table.convertRowIndexToModel(viewRow);

				Object idCustomer = tableModel.getValueAt(modelRow, 0);
				Object name = tableModel.getValueAt(modelRow, 1);
				Object email = tableModel.getValueAt(modelRow, 2);
				Object birthday = tableModel.getValueAt(modelRow, 3);
				Object idNumber = tableModel.getValueAt(modelRow, 4);

				txtIdCustomer.setText(idCustomer.toString());
				txtName.setText(name.toString());
				txtEmail.setText(email.toString());
				if (birthday instanceof java.sql.Date) {
					dtBirthday.setDate(new java.util.Date(((java.sql.Date) birthday).getTime()));
				} else if (birthday instanceof java.util.Date) {
					dtBirthday.setDate((java.util.Date) birthday);
				} else if (birthday != null) {
					try {
						java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
						dtBirthday.setDate(sdf.parse(birthday.toString()));
					} catch (Exception ex) {
						dtBirthday.setDate(null);
					}
				} else {
					dtBirthday.setDate(null);
				}

				txtIdNumber.setText(idNumber.toString());

			}
		});


		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 926, 662);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("Clientes");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 28));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Nuevo cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		txtName = new JTextField();
		txtName.setColumns(10);

		JButton btnNewButton = new JButton("Guardar");
		btnNewButton.setIcon(new ImageIcon(VCustomers.class.getResource("/icons/save.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveCustomer();
			}
		});

		JLabel lblNewLabel_1 = new JLabel("Nombres(*)");

		txtEmail = new JTextField();
		txtEmail.setColumns(10);

		lblNewLabel_2 = new JLabel("Correo(*)");

		lblNewLabel_3 = new JLabel("Fecha de nacimiento(*)");

		txtIdNumber = new JTextField();
		txtIdNumber.setColumns(10);

		lblNewLabel_4 = new JLabel("# Indentificacion");

		txtNacionality = new JTextField();
		txtNacionality.setColumns(10);

		lblNewLabel_5 = new JLabel("Nacionalidad");

		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setIcon(new ImageIcon(VCustomers.class.getResource("/icons/cancel.png")));
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearInputs();
			}
		});

		txtIdCustomer = new JTextField();
		txtIdCustomer.setColumns(10);
		txtIdCustomer.setVisible(false);

		panel_1 = new JPanel();
		panel_1.setBorder(
				new TitledBorder(null, "Listado de clientes", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setViewportView(table);

		// Menú contextual para habilitar/deshabilitar/eliminar cliente
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem itemHabilitar = new JMenuItem("Habilitar");
		JMenuItem itemDeshabilitar = new JMenuItem("Deshabilitar");
		JMenuItem itemEliminar = new JMenuItem("Eliminar");
		popupMenu.add(itemHabilitar);
		popupMenu.add(itemDeshabilitar);
		popupMenu.add(itemEliminar);

		table.setComponentPopupMenu(popupMenu);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
					.addGap(21))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(51)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
					.addGap(12))
				.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(51)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
					.addGap(12))
		);
		
		JLabel lblNewLabel_6 = new JLabel("Nombre");
		
		txtNameFilter = new JTextField();
		txtNameFilter.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Limpiar");
		btnNewButton_1.setIcon(new ImageIcon(VCustomers.class.getResource("/icons/eraser.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNameFilter.setText("");
				cmbStatusFilter.setSelectedIndex(0);
				getCustomers(new HashMap<>());
			}
		});
		
		JButton btnNewButton_1_1 = new JButton("Buscar");
		btnNewButton_1_1.setIcon(new ImageIcon(VCustomers.class.getResource("/icons/search.png")));
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				HashMap<String, Object> filters = new HashMap<>();
				filters.put("name", txtNameFilter.getText().trim());
				filters.put("status", Customer.fromLabel(cmbStatusFilter.getSelectedItem().toString()));
				getCustomers(filters);
			}
		});
		
		JLabel lblNewLabel_7 = new JLabel("Status");
		
		cmbStatusFilter = new JComboBox<>();
		cmbStatusFilter.addItem("Todos");
		cmbStatusFilter.addItem(Customer.toLabel(Customer.ENABLED));
		cmbStatusFilter.addItem(Customer.toLabel(Customer.DISABLED));
		cmbStatusFilter.addItem(Customer.toLabel(Customer.DELETED));
		
		btnPrev = new JButton("Anterior");
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentPage--;
				
				if(currentPage < 1) {
					currentPage = 1;
				}
				getCustomers(getCustomerFilter());
			}
		});
		
		btnNext = new JButton("Siguiente");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentPage++;
				
				if(currentPage > totalPages) {
					currentPage = totalPages;
				}
				getCustomers(getCustomerFilter());
			}
		});
		
		lblPaginateInfo = new JLabel("Pagina 1 de 2");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(217)
					.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton_1_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(147))
				.addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
					.addGap(19)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
							.addComponent(lblNewLabel_6)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtNameFilter, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
							.addGap(34)
							.addComponent(lblNewLabel_7)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(cmbStatusFilter, 0, 190, Short.MAX_VALUE)))
					.addContainerGap())
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(127)
					.addComponent(btnPrev, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblPaginateInfo)
					.addGap(18)
					.addComponent(btnNext, GroupLayout.PREFERRED_SIZE, 95, Short.MAX_VALUE)
					.addGap(115))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(14)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_6)
						.addComponent(txtNameFilter, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_7)
						.addComponent(cmbStatusFilter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(52)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton_1)
						.addComponent(btnNewButton_1_1))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPaginateInfo)
						.addComponent(btnPrev)
						.addComponent(btnNext))
					.addGap(17))
		);
		gl_panel_1.linkSize(SwingConstants.VERTICAL, new Component[] {btnNewButton_1, btnNewButton_1_1});
		panel_1.setLayout(gl_panel_1);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addGap(100)
							.addComponent(txtIdCustomer, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(txtName, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
							.addGap(46))
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(txtEmail, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
							.addGap(46))
						.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(dtBirthday, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
							.addGap(53))
						.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(txtIdNumber, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
							.addGap(46))
						.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(txtNacionality, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
							.addGap(46)))
					.addGap(4))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(17)
					.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnLimpiar, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
					.addGap(17))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(10)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtIdCustomer, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(dtBirthday, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(txtIdNumber, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(txtNacionality, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnLimpiar, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
					.addGap(16))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);

		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				if (row != -1) {
					table.setRowSelectionInterval(row, row);
					int status = (int) tableModel.getValueAt(row, 5);
					itemHabilitar.setEnabled(status != Customer.ENABLED && status != Customer.DELETED);
					itemDeshabilitar.setEnabled(status != Customer.DISABLED && status != Customer.DELETED);
					itemEliminar.setEnabled(status != Customer.DELETED);
				}
			}
		});

		itemHabilitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					int idCustomer = (int) tableModel.getValueAt(row, 0);
					try {
						customerDao.enable(idCustomer);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Cliente habilitado exitosamente.", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
					getCustomers(new HashMap<>());
				}
			}
		});
		itemDeshabilitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					int idCustomer = (int) tableModel.getValueAt(row, 0);
					try {
						customerDao.disable(idCustomer);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Cliente deshabilitado exitosamente.", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
					getCustomers(new HashMap<>());
				}
			}
		});
		itemEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					int idCustomer = (int) tableModel.getValueAt(row, 0);
					try {
						customerDao.delete(idCustomer);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente.", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
					getCustomers(new HashMap<>());
				}
			}
		});
		
		
		getCustomers(getCustomerFilter());

	}

	private void saveCustomer() {
		Customer customer = new Customer();
		customer.setName(txtName.getText().trim());
		customer.setEmail(txtEmail.getText().trim());
		customer.setIdNumber(txtIdNumber.getText().trim());

		Date date = dtBirthday.getDate();
		if (date != null) {
			java.sql.Date dateSql = new java.sql.Date(date.getTime());
			customer.setBirthday(dateSql.toString());
		}

		if (!customerValidate(customer)) {
			JOptionPane.showMessageDialog(null, "Llene los campos requeridos", "Información",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		try {

			int idCustomer = -1;
			if (!txtIdCustomer.getText().equals("")) {
				idCustomer = Integer.parseInt(txtIdCustomer.getText());
			}

			if (idCustomer != -1) {
				customer.setIdCustomer(idCustomer);
				customerDao.update(customer);
				JOptionPane.showMessageDialog(null, "Cliente Actualizado con exito", "Información",
						JOptionPane.INFORMATION_MESSAGE);

			} else {
				customerDao.insert(customer);
				JOptionPane.showMessageDialog(null, "Cliente Registrado con exito", "Información",
						JOptionPane.INFORMATION_MESSAGE);

			}

			clearInputs();
			getCustomers(getCustomerFilter());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void getCustomers(HashMap<String, Object> filters) {
    try {

    	totalRecords = customerDao.getCustomersCount(filters);
        totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        if (totalPages < 1) totalPages = 1;
        if (currentPage > totalPages) currentPage = totalPages;
        if (currentPage < 1) currentPage = 1;
        List<Customer> customers = customerDao.getCustomers(filters, currentPage, pageSize);
        tableModel.setRowCount(0);
        for (Customer customer : customers) {
            tableModel.addRow(new Object[] { customer.getIdCustomer(), customer.getName(), customer.getEmail(),
                    customer.getBirthday(), customer.getIdNumber(), customer.getStatus() });
        }
        

        lblPaginateInfo.setText("Pagina " + currentPage + " de " + totalPages);
        
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);

        DefaultTableCellRenderer estadoRenderer = new DefaultTableCellRenderer() {

			private static final long serialVersionUID = -6868279829515600174L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				int code = (value instanceof Number) ? ((Number) value).intValue() : Customer.ENABLED;
				String label = Customer.toLabel(code);
				setText(label);
				return c;
			}
		};

		table.getColumnModel().getColumn(5).setCellRenderer(estadoRenderer);

        
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
	}
	
	private HashMap<String, Object> getCustomerFilter(){
		HashMap<String, Object> filters = new HashMap<>();
	    if (!txtNameFilter.getText().trim().isEmpty()) {
	        filters.put("name", txtNameFilter.getText().trim());
	    }
	    filters.put("status", Customer.fromLabel(cmbStatusFilter.getSelectedItem().toString()));
	    
	    return filters;
	}

	

	private boolean customerValidate(Customer customer) {
		boolean isValid = true;

		if (customer.getName() == null || "".equals(customer.getName())) {
			isValid = false;
		}

		if (customer.getEmail() == null || "".equals(customer.getEmail())) {
			isValid = false;
		}

		if (customer.getBirthday() == null || "".equals(customer.getBirthday())) {
			isValid = false;
		}

		if (customer.getIdNumber() == null || "".equals(customer.getIdNumber())) {
			isValid = false;
		}

		return isValid;
	}
	
	public void clearInputs() {
		txtIdCustomer.setText("");
		txtName.setText("");
		txtEmail.setText("");
		dtBirthday.setDate(null);
		txtIdNumber.setText("");
		txtNacionality.setText("");
		
	}
}
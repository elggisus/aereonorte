package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import daos.CustomerDao;
import models.Customer;

public class VSelectCustomer extends JDialog {
	
    private static final long serialVersionUID = 1L;
	
    private JTextField txtSearch;
    private JTable table;
    private DefaultTableModel tableModel;
    private Customer selectedCustomer;
    private CustomerDao customerDao = new CustomerDao();

    public VSelectCustomer(Frame parent) {
        super(parent, "Buscar Cliente", true);
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new BorderLayout());
        txtSearch = new JTextField();
        JButton btnSearch = new JButton("Buscar");
        panelTop.add(txtSearch, BorderLayout.CENTER);
        panelTop.add(btnSearch, BorderLayout.EAST);
        add(panelTop, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Email"}, 0) {
            private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        btnSearch.addActionListener(e -> searchCustomers());
        txtSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { searchCustomers(); }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        selectedCustomer = new Customer();
                        selectedCustomer.setIdCustomer((int) tableModel.getValueAt(row, 0));
                        selectedCustomer.setName((String) tableModel.getValueAt(row, 1));
                        selectedCustomer.setEmail((String) tableModel.getValueAt(row, 2));
                        dispose();
                    }
                }
            }
        });

        searchCustomers();
    }

    private void searchCustomers() {
        String filter = txtSearch.getText().trim();
        try {
            List<Customer> customers = customerDao.searchCustomers(filter);
            tableModel.setRowCount(0);
            for (Customer c : customers) {
                tableModel.addRow(new Object[]{c.getIdCustomer(), c.getName(), c.getEmail()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar clientes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }
}

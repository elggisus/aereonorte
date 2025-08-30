package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import javax.swing.GroupLayout.Alignment;
import com.github.lgooddatepicker.components.DateTimePicker;
import javax.swing.LayoutStyle.ComponentPlacement;

import daos.SaleDao;
import models.Sale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.github.lgooddatepicker.components.DatePicker;

public class VSalesReport extends JFrame {
    private static final long serialVersionUID = 1L;
    private DefaultTableModel model;
    private JTable table;

    public VSalesReport() {
        setTitle("Reporte de Ventas");
        setSize(785, 697);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "Filtros", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setBounds(6, 6, 756, 175);
        getContentPane().add(panel);
        
        JLabel lblNewLabel = new JLabel("Fecha Inicio");
        
        JLabel lblNewLabel_1 = new JLabel("Fecha Fin");
        
        JButton btnNewButton = new JButton("Limpiar");
        
        JButton btnNewButton_1 = new JButton("Buscar");
        
        DatePicker datePicker = new DatePicker();
        
        DatePicker datePicker_1 = new DatePicker();
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(lblNewLabel)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(datePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(lblNewLabel_1)
        			.addGap(18)
        			.addComponent(datePicker_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(40, Short.MAX_VALUE))
        		.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
        			.addContainerGap(287, Short.MAX_VALUE)
        			.addComponent(btnNewButton_1)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnNewButton)
        			.addGap(275))
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(21)
        			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel)
        				.addComponent(datePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(datePicker_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_1))
        			.addPreferredGap(ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(btnNewButton_1, Alignment.TRAILING)
        				.addComponent(btnNewButton, Alignment.TRAILING))
        			.addGap(18))
        );
        panel.setLayout(gl_panel);

        model = new DefaultTableModel(
            new Object[]{"ID Venta", "Cliente", "Usuario", "Monto", "Fecha"}, 0
        );
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(6, 200, 756, 450);
        getContentPane().add(scrollPane);
        
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
                LocalDate startDate = datePicker.getDate();
                LocalDate endDate = datePicker_1.getDate();
                if (startDate != null && endDate != null) {
                    LocalDateTime start = startDate.atStartOfDay();
                    LocalDateTime end = endDate.atTime(23, 59, 59);
                    try {
                        SaleDao saleDao = new SaleDao();
                        List<Sale> sales = saleDao.getSalesByDateRange(start, end);
                        for (Sale sale : sales) {
                            model.addRow(new Object[]{
                                sale.getIdSale(),
                                sale.getCustomer(),
                                sale.getUser(),
                                sale.getAmount(),
                                sale.getRegistered()
                            });
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al buscar ventas: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione ambas fechas.");
                }
            }
        });
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                datePicker.clear();
                datePicker_1.clear();
                model.setRowCount(0);
            }
        });
    }
}
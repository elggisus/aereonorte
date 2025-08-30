package views;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.List;

import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import daos.SeatTypeDao;
import models.Customer;
import models.SeatType;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EtchedBorder;
import java.awt.Color;

public class VSeatTypes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtName;
	private JPanel panel_1;
	private JTable table;

	private DefaultTableModel tableModel;

	SeatTypeDao seatTypeDao = new SeatTypeDao();
	private JTextField txtIdSeatType;
	// Tabla

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VSeatTypes frame = new VSeatTypes();
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
	public VSeatTypes() {

		tableModel = new DefaultTableModel(
				new Object[] { "ID", "Nombre", "Status" }, 0) {
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

				Object idSeatType = tableModel.getValueAt(modelRow, 0);
				Object name = tableModel.getValueAt(modelRow, 1);
	

				txtIdSeatType.setText(idSeatType.toString());
				txtName.setText(name.toString());
				

			}
		});

		// TODO: cargar usuarios

		getSeatTypes();

		// Configuraciones
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 926, 662);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Tipos Asientos");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 28));
		lblNewLabel.setBounds(10, 0, 276, 55);
		contentPane.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Nuevo tipo de asiento", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 51, 316, 561);
		contentPane.add(panel);
		panel.setLayout(null);

		txtName = new JTextField();
		txtName.setBounds(31, 62, 229, 27);
		panel.add(txtName);
		txtName.setColumns(10);

		JButton btnNewButton = new JButton("Guardar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				saveCustomer();

			}
		});
		btnNewButton.setBounds(31, 500, 89, 23);
		panel.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("Nombre(*)");
		lblNewLabel_1.setBounds(31, 42, 65, 14);
		panel.add(lblNewLabel_1);

		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearInputs();
			}
		});
		btnLimpiar.setBounds(171, 500, 89, 23);
		panel.add(btnLimpiar);

		txtIdSeatType = new JTextField();
		txtIdSeatType.setBounds(220, 11, 86, 20);
		panel.add(txtIdSeatType);
		txtIdSeatType.setColumns(10);
		txtIdSeatType.setVisible(false);

		panel_1 = new JPanel();
		panel_1.setBorder(
				new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Listado de tipos de asientos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(336, 51, 564, 561);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 75, 529, 462);
		panel_1.add(scrollPane);

		scrollPane.setViewportView(table);

	}

	private void saveCustomer() {
		SeatType seatType = new SeatType();
		seatType.setName(txtName.getText().trim());
		

		if (!seatTypeValidate(seatType)) {
			JOptionPane.showMessageDialog(null, "Llene los campos requeridos", "Información",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		try {

			int idSeatType = -1;
			if (!txtIdSeatType.getText().equals("")) {
				idSeatType = Integer.parseInt(txtIdSeatType.getText());
			}

			if (idSeatType != -1) {
				seatType.setIdSeatType(idSeatType);
				seatTypeDao.update(seatType);
				JOptionPane.showMessageDialog(null, "Cliente Actualizado con exito", "Información",
						JOptionPane.INFORMATION_MESSAGE);

			} else {
				seatTypeDao.insert(seatType);
				JOptionPane.showMessageDialog(null, "Tipo de asiento Registrado con exito", "Información",
						JOptionPane.INFORMATION_MESSAGE);

			}

			clearInputs();
			getSeatTypes();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void getSeatTypes() {
		try {
			List<SeatType> seatTypes = seatTypeDao.getSeatTypes();
			tableModel.setRowCount(0);
			for (SeatType seatType : seatTypes) {
				tableModel.addRow(new Object[] {
						seatType.getIdSeatType(),
						seatType.getName(),
						seatType.getStatus()
						
				});
			}

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

			table.getColumnModel().getColumn(2).setCellRenderer(estadoRenderer);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void clearInputs() {
		txtIdSeatType.setText("");
		txtName.setText("");
		

	}

	private boolean seatTypeValidate(SeatType seatType) {
		boolean isValid = true;

		if (seatType.getName() == null || "".equals(seatType.getName())) {
			isValid = false;
		}

		

		return isValid;
	}
}

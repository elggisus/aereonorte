package views;

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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import daos.SeatDao;
import models.Airplane;
import models.Seat;
import models.SeatType;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class VSeats extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_1;
	private JTable table;

	private DefaultTableModel tableModel;

	SeatDao seatDao = new SeatDao();
	private JTextField txtIdSeatType;
	private JTextField txtRow;
	private JTextField txtNumber;
	private JComboBox<Airplane> cmbAirplanes;
	private JComboBox<SeatType> cmbSeatTypes;
	// Tabla

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VSeats frame = new VSeats();
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
	public VSeats() {

		tableModel = new DefaultTableModel(
				new Object[] { "ID","ID Airplane" ,"Avion","Id Seat Type","Tipo Asiento","Fila","Numero", "Precio" }, 0) {
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
	

				txtIdSeatType.setText(idSeatType.toString());
				

			}
		});

		// TODO: cargar usuarios

		// Configuraciones
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 926, 662);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("Asientos");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 28));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Nuevo asiento", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		JButton btnNewButton = new JButton("Guardar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				saveCustomer();

			}
		});

		JLabel lblNewLabel_1 = new JLabel("Avion(*)");

		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearInputs();
			}
		});

		txtIdSeatType = new JTextField();
		txtIdSeatType.setColumns(10);
		
		cmbAirplanes = new JComboBox<Airplane>();
		
		JLabel lblNewLabel_1_1 = new JLabel("Tipo de asiento(*)");
		
		cmbSeatTypes = new JComboBox<SeatType>();
		
		JLabel lblNewLabel_2 = new JLabel("Fila");
		
		txtRow = new JTextField();
		txtRow.setColumns(10);
		
		JLabel lblNewLabel_2_1 = new JLabel("Numero");
		
		txtNumber = new JTextField();
		txtNumber.setColumns(10);
		txtIdSeatType.setVisible(false);

		panel_1 = new JPanel();
		panel_1.setBorder(
				new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Listado de asientos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 75, 529, 462);
		panel_1.add(scrollPane);

		scrollPane.setViewportView(table);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 564, GroupLayout.PREFERRED_SIZE)
					.addGap(5))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(51)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
					.addGap(1))
				.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(51)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 561, GroupLayout.PREFERRED_SIZE))
		);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(214)
					.addComponent(txtIdSeatType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(25)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(25)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
					.addGap(51)
					.addComponent(btnLimpiar, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(txtNumber, GroupLayout.PREFERRED_SIZE, 229, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lblNewLabel_2_1, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addGap(233))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(txtRow, GroupLayout.PREFERRED_SIZE, 229, GroupLayout.PREFERRED_SIZE)
									.addContainerGap())
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
										.addGap(233))
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
												.addComponent(cmbSeatTypes, GroupLayout.PREFERRED_SIZE, 229, GroupLayout.PREFERRED_SIZE))
											.addContainerGap())
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(cmbAirplanes, 0, 229, Short.MAX_VALUE)
											.addGap(50))))))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(txtIdSeatType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(lblNewLabel_1)
					.addGap(11)
					.addComponent(cmbAirplanes, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_1_1)
					.addGap(11)
					.addComponent(cmbSeatTypes, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_2)
					.addGap(11)
					.addComponent(txtRow, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_2_1)
					.addGap(11)
					.addComponent(txtNumber, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addGap(206)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton)
						.addComponent(btnLimpiar)))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
		
		getSeats();
		getAirplanes();
		getSeatTypes();


	}

	private void saveCustomer() {
		Seat seat = new Seat();
		
		Airplane airplane = (Airplane) cmbAirplanes.getSelectedItem();
		
		SeatType seatType = (SeatType) cmbSeatTypes.getSelectedItem();

		seat.setIdAirplane(airplane.getIdAirplane());
		seat.setIdSeatType(seatType.getIdSeatType());
		seat.setRow(txtRow.getText().trim());
		seat.setNumber(Integer.parseInt(txtNumber.getText().trim()));
		

		if (!seatValidate(seat)) {
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
				JOptionPane.showMessageDialog(null, "Asiento Actualizado con exito", "Información",
						JOptionPane.INFORMATION_MESSAGE);

			} else {
				seatDao.insert(seat);
				getSeats();
				JOptionPane.showMessageDialog(null, "Asiento Registrado con exito", "Información",
						JOptionPane.INFORMATION_MESSAGE);

			}

			clearInputs();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void getAirplanes() {
		try {
			List<Airplane> airplanes = seatDao.getAirplanes();
			
			DefaultComboBoxModel<Airplane> model = new DefaultComboBoxModel<>();
	        for (Airplane a : airplanes) {
	            model.addElement(a);
	        }
	        cmbAirplanes.setModel(model);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	private void getSeatTypes() {
		try {
			List<SeatType> seatTypes = seatDao.getSeatTypes();
			
			DefaultComboBoxModel<SeatType> model = new DefaultComboBoxModel<>();
	        for (SeatType a : seatTypes) {
	            model.addElement(a);
	        }
	        cmbSeatTypes.setModel(model);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void getSeats() {
		try {
			List<Seat> seats = seatDao.getSeats();
			tableModel.setRowCount(0);
			for (Seat seat : seats) {
				tableModel.addRow(new Object[] {
					seat.getIdSeat(),
					seat.getIdAirplane(),
					seat.getAirplane(),
					seat.getIdSeatType(),
					seat.getSeatType(),
					seat.getRow(),
					seat.getNumber(),
					seat.getPrice()
						
				});
			}
			
			table.getColumnModel().getColumn(1).setMinWidth(0);
			table.getColumnModel().getColumn(1).setMaxWidth(0);
			table.getColumnModel().getColumn(1).setPreferredWidth(0);
			
			table.getColumnModel().getColumn(3).setMinWidth(0);
			table.getColumnModel().getColumn(3).setMaxWidth(0);
			table.getColumnModel().getColumn(3).setPreferredWidth(0);


//			DefaultTableCellRenderer estadoRenderer = new DefaultTableCellRenderer() {
//
//				private static final long serialVersionUID = -6868279829515600174L;
//
//				@Override
//				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
//						boolean hasFocus, int row, int column) {
//					Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//
//					int code = (value instanceof Number) ? ((Number) value).intValue() : Customer.ENABLED;
//					String label = Customer.toLabel(code);
//					setText(label);
//					return c;
//				}
//			};
//
//			table.getColumnModel().getColumn(2).setCellRenderer(estadoRenderer);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void clearInputs() {
		txtIdSeatType.setText("");
		

	}

	private boolean seatValidate(Seat seat) {
		boolean isValid = true;

		if (seat.getIdAirplane() == 0) {
			isValid = false;
		}

		

		return isValid;
	}
	
}

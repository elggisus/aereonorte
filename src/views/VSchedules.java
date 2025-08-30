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

import daos.ScheduleDao;
import models.Airplane;
import models.Route;
import models.Schedule;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JComboBox;
import com.github.lgooddatepicker.components.DateTimePicker;

public class VSchedules extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_1;
	private JTable table;

	private DefaultTableModel tableModel;
	DateTimePicker dtStartTime;
	DateTimePicker dtEndTIme;
	JComboBox<Airplane> cmbAirplanes; 
	JComboBox<Route> cmbRoutes;
	ScheduleDao scheduleDao = new ScheduleDao();
	private JTextField txtIdAirplane;
	private JTextField txtPrice;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VAirplanes frame = new VAirplanes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VSchedules() {

		tableModel = new DefaultTableModel(new Object[] { "ID","IdAirplane" ,"Avion", "IdRoute","Ruta","FechaIncio", "Fecha Fin","Precio","Status" }, 0) {

			private static final long serialVersionUID = 1L;

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

				Object idAirplane = tableModel.getValueAt(modelRow, 0);
				Object tailNumber = tableModel.getValueAt(modelRow, 1);
				Object seatsNumber = tableModel.getValueAt(modelRow, 2);

				txtIdAirplane.setText(idAirplane.toString());
			

			}
		});

	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 926, 662);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("Horarios");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 28));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Nuevo Horario", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		JButton btnNewButton = new JButton("Guardar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveSchedule();
			}
		});

		JLabel lblNewLabel_1 = new JLabel("Avion(*)");

		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearInputs();
			}
		});

		txtIdAirplane = new JTextField();
		txtIdAirplane.setColumns(10);
		txtIdAirplane.setVisible(false);

		panel_1 = new JPanel();
		panel_1.setBorder(
				new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Listado de Hoarios", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setViewportView(table);

		JButton btnEdit = new JButton("Editar");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		JButton btnDisable = new JButton("Deshabilitar");
		btnDisable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disableSelectedAirplane();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(5)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE))
				.addGap(18).addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 561, Short.MAX_VALUE).addGap(9)));
		gl_contentPane
				.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addGap(51)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 583,
												Short.MAX_VALUE)
										.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
								.addGap(28)));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addGap(19)
						.addComponent(btnEdit, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(btnDisable, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
						.addGap(326))
				.addGroup(gl_panel_1.createSequentialGroup().addContainerGap()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE).addGap(13)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addGap(9)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(btnEdit)
								.addComponent(btnDisable))
						.addGap(18).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
						.addGap(27)));
		panel_1.setLayout(gl_panel_1);
		
		cmbAirplanes = new JComboBox();
		
		JLabel lblNewLabel_1_1 = new JLabel("Ruta(*)");
		
		cmbRoutes = new JComboBox();
		
		dtStartTime = new DateTimePicker();
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Fecha inicio(*)");
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Fecha finaliza(*)");
		
		dtEndTIme = new DateTimePicker();
		
		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Precio Ticket(*)");
	
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtIdAirplane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(165, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
							.addGap(74)
							.addComponent(btnLimpiar, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(165, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(cmbRoutes, Alignment.LEADING, 0, 275, Short.MAX_VALUE)
								.addComponent(cmbAirplanes, 0, 275, Short.MAX_VALUE))
							.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(dtStartTime, GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel_1_1_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(165, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel_1_1_1_1_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(165, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(txtPrice, Alignment.LEADING)
								.addComponent(lblNewLabel_1_1_1_1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
								.addComponent(dtEndTIme, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))
							.addContainerGap())))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(txtIdAirplane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cmbAirplanes, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cmbRoutes, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1_1_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(dtStartTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_1_1_1_1)
					.addGap(6)
					.addComponent(dtEndTIme, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_1_1_1_1_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtPrice, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 524, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnLimpiar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
					.addGap(25))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
		
		getSchedules();
		//Se cargan datos de los combobox
		getAirplanes();
		getRoutes();
	}

	private void saveSchedule() {
		
		LocalDateTime dateStart = dtStartTime.getDateTimePermissive();
        if (dateStart == null) {
            throw new IllegalArgumentException("Debe seleccionar fecha y hora.");
        }
        String startDate = dateStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        LocalDateTime dateEnd = dtEndTIme.getDateTimePermissive();
        if (dateEnd == null) {
            throw new IllegalArgumentException("Debe seleccionar fecha y hora.");
        }
        String endDate = dateEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        Airplane airplane = (Airplane) cmbAirplanes.getSelectedItem();
		Route route = (Route) cmbRoutes.getSelectedItem();

        
        Schedule schedule = new Schedule();
        schedule.setIdAirplane(airplane.getIdAirplane());
        schedule.setIdRoute(route.getIdRoute());
        schedule.setStartDate(startDate);
        schedule.setEndDate(endDate);
        schedule.setPrice(Double.parseDouble(txtPrice.getText().trim()));
    
//		if (!airplaneValidate(airplane)) {
//			JOptionPane.showMessageDialog(null, "Llene los campos requeridos", "Información",
//					JOptionPane.INFORMATION_MESSAGE);
//			return;
//		}

		try {

//			int idAirplane = -1;
//			if (!txtIdAirplane.getText().equals("")) {
//				idAirplane = Integer.parseInt(txtIdAirplane.getText());
//			}
//
//			if (idAirplane != -1) {
//
//			} else {
//				
//
//			}
			
			scheduleDao.save(schedule);
			JOptionPane.showMessageDialog(null, "Horario registrado con exito", "Información",
					JOptionPane.INFORMATION_MESSAGE);

			clearInputs();
			getSchedules();
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void disableSelectedAirplane() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(null, "Seleccione un avión para deshabilitar", "Información",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		int modelRow = table.convertRowIndexToModel(selectedRow);
		Object idAirplane = tableModel.getValueAt(modelRow, 0);

		int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de deshabilitar este avión?", "Confirmar",
				JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			try {
				scheduleDao.disable(Integer.parseInt(idAirplane.toString()));
				JOptionPane.showMessageDialog(null, "Avión deshabilitado con éxito", "Información",
						JOptionPane.INFORMATION_MESSAGE);
				getAirplanes();
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	
	
	private void getSchedules() {
		try {
			List<Schedule> schedules = scheduleDao.getSchedules();
			tableModel.setRowCount(0);
			for (Schedule schedule : schedules) {
				tableModel.addRow(new Object[] {
					schedule.getIdSchedule(),
					schedule.getIdAirplane(),
					schedule.getAirplane(),
					schedule.getIdRoute(),
					schedule.getRoute(),
					schedule.getStartDate(),
					schedule.getEndDate(),
					schedule.getPrice(),
					schedule.getStatus()
						
				});
			}
			
			table.getColumnModel().getColumn(1).setMinWidth(0);
			table.getColumnModel().getColumn(1).setMaxWidth(0);
			table.getColumnModel().getColumn(1).setPreferredWidth(0);
			
			table.getColumnModel().getColumn(3).setMinWidth(0);
			table.getColumnModel().getColumn(3).setMaxWidth(0);
			table.getColumnModel().getColumn(3).setPreferredWidth(0);


			DefaultTableCellRenderer statusRenderer = new DefaultTableCellRenderer() {

				private static final long serialVersionUID = -6868279829515600174L;

				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

					int code = (value instanceof Number) ? ((Number) value).intValue() : Schedule.ENABLED;
					String label = Schedule.toLabel(code);
					setText(label);
					return c;
				}
			};

			table.getColumnModel().getColumn(8).setCellRenderer(statusRenderer);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void getAirplanes() {
		try {
			List<Airplane> airplanes = scheduleDao.getAirplanes();
			
			DefaultComboBoxModel<Airplane> model = new DefaultComboBoxModel<>();
	        for (Airplane a : airplanes) {
	            model.addElement(a);
	        }
	        cmbAirplanes.setModel(model);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	private void getRoutes() {
		try {
			List<Route> routes = scheduleDao.getRoutes();
			
			DefaultComboBoxModel<Route> model = new DefaultComboBoxModel<>();
	        for (Route route : routes) {
	            model.addElement(route);
	        }
	        cmbRoutes.setModel(model);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void clearInputs() {
		txtIdAirplane.setText("");
	
	}

	private boolean airplaneValidate(Airplane airplane) {
		boolean isValid = true;

		if (airplane.getTailNumber() == null || "".equals(airplane.getTailNumber())) {
			isValid = false;
		}

		if (airplane.getSeatsNumber() <= 0) {
			isValid = false;
		}

		return isValid;
	}
}

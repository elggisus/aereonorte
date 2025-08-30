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

import daos.AirplaneDao;
import models.Airplane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class VAirplanes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTailNumber;
	private JTextField txtSeatsNumber;
	private JPanel panel_1;
	private JTable table;

	private DefaultTableModel tableModel;

	AirplaneDao airplaneDao = new AirplaneDao();
	
	private JTextField txtIdAirplane;

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


	public VAirplanes() {
		
		tableModel = new DefaultTableModel(
				new Object[] { "ID", "Número de Cola", "Número de Asientos", "Estado" }, 0) {
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
				txtTailNumber.setText(tailNumber.toString());
				txtSeatsNumber.setText(seatsNumber.toString());
				

			}
		});

		// Menú contextual para habilitar/deshabilitar avión
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem itemHabilitar = new JMenuItem("Habilitar");
		JMenuItem itemDeshabilitar = new JMenuItem("Deshabilitar");
		JMenuItem itemEliminar = new JMenuItem("Eliminar");
		popupMenu.add(itemHabilitar);
		popupMenu.add(itemDeshabilitar);
		popupMenu.add(itemEliminar);

		// Actualiza el estado de los items según el estado del avión seleccionado
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				if (row != -1) {
					table.setRowSelectionInterval(row, row);
					int status = (int) tableModel.getValueAt(row, 3);
					itemHabilitar.setEnabled(status != Airplane.ENABLED && status != Airplane.DELETED);
					itemDeshabilitar.setEnabled(status != Airplane.DISABLED && status != Airplane.DELETED);
					itemEliminar.setEnabled(status != Airplane.DELETED);
				}
			}
		});

		itemHabilitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					int idAirplane = (int) tableModel.getValueAt(row, 0);
					try {
						airplaneDao.enable(idAirplane);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Avión habilitado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
					getAirplanes();
				}
			}
		});
		itemDeshabilitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					int idAirplane = (int) tableModel.getValueAt(row, 0);
					try {
						airplaneDao.disable(idAirplane);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Avión deshabilitado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
					getAirplanes();
				}
			}
		});
		itemEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					int idAirplane = (int) tableModel.getValueAt(row, 0);
					try {
						airplaneDao.delete(idAirplane);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Avión eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
					getAirplanes();
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
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 926, 662);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("Aviones");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 28));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Nuevo avión", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		txtTailNumber = new JTextField();
		txtTailNumber.setColumns(10);

		JButton btnNewButton = new JButton("Guardar");
		btnNewButton.setIcon(new ImageIcon(VAirplanes.class.getResource("/icons/save.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAirplane();
			}
		});

		JLabel lblNewLabel_1 = new JLabel("Número de Cola(*)");

		txtSeatsNumber = new JTextField();
		txtSeatsNumber.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Número de Asientos(*)");

		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setIcon(new ImageIcon(VAirplanes.class.getResource("/icons/cancel.png")));
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
				new TitledBorder(null, "Listado de aviones", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setViewportView(table);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 561, Short.MAX_VALUE)
					.addGap(9))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGap(51)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(28))
		);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
					.addGap(13))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(56)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
					.addGap(27))
		);
		panel_1.setLayout(gl_panel_1);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(txtIdAirplane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(txtTailNumber, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
								.addContainerGap())
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(155, Short.MAX_VALUE))
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(175, Short.MAX_VALUE))
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(txtSeatsNumber, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
								.addContainerGap()))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnLimpiar, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(txtIdAirplane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtTailNumber, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtSeatsNumber, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 273, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnLimpiar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
					.addGap(25))
		);
		gl_panel.linkSize(SwingConstants.HORIZONTAL, new Component[] {btnNewButton, btnLimpiar});
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
		
		getAirplanes();
		

	}

	private void saveAirplane() {
		Airplane airplane = new Airplane();
		airplane.setTailNumber(txtTailNumber.getText().trim());
		
		try {
			airplane.setSeatsNumber(Integer.parseInt(txtSeatsNumber.getText().trim()));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "El número de asientos debe ser un número válido", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (!airplaneValidate(airplane)) {
			JOptionPane.showMessageDialog(null, "Llene los campos requeridos", "Información",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		try {

			int idAirplane = -1;
			if (!txtIdAirplane.getText().equals("")) {
				idAirplane = Integer.parseInt(txtIdAirplane.getText());
			}

			if (idAirplane != -1) {
				airplane.setIdAirplane(idAirplane);
				airplaneDao.update(airplane);
				JOptionPane.showMessageDialog(null, "Avión editado con éxito", "Información",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				airplaneDao.insert(airplane);
				JOptionPane.showMessageDialog(null, "Avion registrado con exito", "Información",
						JOptionPane.INFORMATION_MESSAGE);
			}

			clearInputs();
			getAirplanes();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void getAirplanes() {
		try {
			List<Airplane> airplanes = airplaneDao.getAirplanes();
			tableModel.setRowCount(0);
			for (Airplane airplane : airplanes) {
				tableModel.addRow(new Object[] { airplane.getIdAirplane(), airplane.getTailNumber(),
						airplane.getSeatsNumber(), airplane.getStatus() });
			}

			DefaultTableCellRenderer estadoRenderer = new DefaultTableCellRenderer() {

				private static final long serialVersionUID = -6868279829515600174L;

				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

					int code = (value instanceof Number) ? ((Number) value).intValue() : Airplane.ENABLED;
					String label = Airplane.toLabel(code);
					setText(label);
					return c;
				}
			};

			table.getColumnModel().getColumn(3).setCellRenderer(estadoRenderer);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void clearInputs() {
		txtIdAirplane.setText("");
		txtTailNumber.setText("");
		txtSeatsNumber.setText("");
	}

	private boolean airplaneValidate(Airplane airplane) {
		boolean isValid = true;

		if (airplane.getTailNumber() == null || airplane.getTailNumber().trim().isEmpty()) {
			isValid = false;
		}
		if (airplane.getSeatsNumber() <= 0) {
			isValid = false;
		}
		return isValid;
	}
}
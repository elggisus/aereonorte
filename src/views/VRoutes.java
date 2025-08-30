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

import daos.RouteDao;
import models.Airplane;
import models.Route;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import java.awt.Color;

public class VRoutes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtOrigin;
	private JTextField txtDestination;
	private JPanel panel_1;
	private JTable table;

	private DefaultTableModel tableModel;

	RouteDao routeDao = new RouteDao();
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

	/**
	 * Create the frame.
	 */
	public VRoutes() {

		tableModel = new DefaultTableModel(new Object[] { "ID", "Origen", "Destino", "Status" }, 0) {
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
				txtOrigin.setText(tailNumber.toString());
				txtDestination.setText(seatsNumber.toString());

			}
		});

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 926, 662);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("Rutas");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 28));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Nueva ruta", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		txtOrigin = new JTextField();
		txtOrigin.setColumns(10);

		JButton btnNewButton = new JButton("Guardar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveRoute();
			}
		});

		JLabel lblNewLabel_1 = new JLabel("Origen(*)");

		txtDestination = new JTextField();
		txtDestination.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Destino(*)");

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
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Listado de rutas", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setViewportView(table);
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
				.addGroup(gl_panel_1.createSequentialGroup().addContainerGap()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE).addGap(13)));
		gl_panel_1.setVerticalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1.createSequentialGroup().addGap(50)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE).addGap(27)));
		panel_1.setLayout(gl_panel_1);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(txtIdAirplane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(txtOrigin, GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
										.addContainerGap())
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 140,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(145, Short.MAX_VALUE))
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 120,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(165, Short.MAX_VALUE))
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(txtDestination, GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
										.addContainerGap())
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
										.addGap(74)
										.addComponent(btnLimpiar, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
										.addContainerGap()))));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup()
				.addComponent(txtIdAirplane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblNewLabel_1)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(txtOrigin, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblNewLabel_2)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(txtDestination, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED, 361, Short.MAX_VALUE)
				.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnLimpiar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
				.addGap(25)));
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
		
		
		getRoutes();


	}

	private void saveRoute() {
		Route route = new Route();
		route.setOrigin(txtOrigin.getText().trim());
		route.setDestination(txtDestination.getText().trim());

		

		if (!routeValidator(route)) {
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

			} else {
				routeDao.save(route);
				JOptionPane.showMessageDialog(null, "Ruta registrada con exito", "Información",
						JOptionPane.INFORMATION_MESSAGE);

			}

			clearInputs();
			getRoutes();
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void getRoutes() {
		try {
			List<Route> routes = routeDao.getRoutes();
			tableModel.setRowCount(0);
			for (Route route : routes) {
				tableModel.addRow(new Object[] {
						route.getIdRoute(),
						route.getOrigin(),
						route.getDestination(),
						route.getStatus()
						
				});
			}

			DefaultTableCellRenderer statusRender = new DefaultTableCellRenderer() {

				private static final long serialVersionUID = -6868279829515600174L;

				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

					int code = (value instanceof Number) ? ((Number) value).intValue() : Airplane.ENABLED;
					String label = Route.toLabel(code);
					setText(label);
					return c;
				}
			};

			table.getColumnModel().getColumn(3).setCellRenderer(statusRender);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void clearInputs() {
		txtIdAirplane.setText("");
		txtOrigin.setText("");
		txtDestination.setText("");
	}

	private boolean routeValidator(Route route) {
		boolean isValid = true;

		if (route.getOrigin() == null || "".equals(route.getOrigin())) {
			isValid = false;
		}

		if (route.getDestination() == null || "".equals(route.getDestination())) {
			isValid = false;
		}

		return isValid;
	}
}

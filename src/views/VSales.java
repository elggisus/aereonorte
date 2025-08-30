package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import daos.AirplaneDao;
import daos.SaleDao;
import daos.ScheduleDao;
import models.BoardingPass;
import models.Customer;
import models.Sale;
import models.Schedule;
import models.Seat;

import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.awt.Desktop;
import java.io.File;

import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;

public class VSales extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTable table;
	private JTable tableSales;
	private JTable tblTickets;
	JComboBox<Schedule> cmbSchedules;
	JComboBox<Seat> cmbSeats;

	private DefaultTableModel tableTicketsModel;
	private DefaultComboBoxModel<Seat> seatsComboBoxModel;

	private ScheduleDao scheduleDao = new ScheduleDao();

	private AirplaneDao airplaneDao = new AirplaneDao();

	private SaleDao saleDao = new SaleDao();

	private JLabel lblTotal;

	private int selectedCustomerId = -1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VSales frame = new VSales();
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
	public VSales() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1314, 642);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Nueva venta", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(
				new TitledBorder(null, "Listado de ventas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_1, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE))
					.addGap(18))
		);

		JScrollPane scrollPane = new JScrollPane();

		JButton btnNewButton_1 = new JButton("Generar pases de abordar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableSales.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione una venta para generar los pases de abordar.",
							"Información", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				int idSale = (int) tableSales.getValueAt(selectedRow, 0);
				String folderName = "boarding-pass";
				File folder = new File(folderName);
				if (!folder.exists()) {
					folder.mkdir();
				}
				String pdfPath = folderName + File.separator + "boarding-pass-sale-" + idSale + ".pdf";
				Document document = new Document();
				try {
					PdfWriter.getInstance(document, new FileOutputStream(pdfPath)); // <-- Fix: initialize PdfWriter
																					// before open
					document.open();

					List<BoardingPass> boardingPasses = saleDao.getBoardingPassesBySale(idSale);

					Font subtitleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLDITALIC);
					Font labelFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
					Font valueFont = new Font(Font.FontFamily.HELVETICA, 12);

				
					
					Image logoImg = null;
					
					try {
						logoImg = Image.getInstance(VSales.class.getResource("/logos/aereonorte_logo.png"));
						logoImg.scaleAbsolute(200, 100);
						logoImg.setAlignment(Element.ALIGN_CENTER);
					} catch (Exception imgEx) {
						logoImg = null;
					}

					int i = 0;

					for (BoardingPass bp : boardingPasses) {
						if (i > 0)
							document.newPage();
						// Logo
						if (logoImg != null)
							document.add(logoImg);
						document.add(new Paragraph(" "));
						// Airline name
//						Paragraph airline = new Paragraph("AEREONORTE", titleFont);
//						airline.setAlignment(Paragraph.ALIGN_CENTER);
//						document.add(airline);
						document.add(new Paragraph(" "));
						Paragraph subtitle = new Paragraph("Boarding Pass", subtitleFont);
						subtitle.setAlignment(Paragraph.ALIGN_CENTER);
						document.add(subtitle);
						document.add(new Paragraph(" "));
						// Horizontal line
						com.itextpdf.text.pdf.draw.LineSeparator ls = new com.itextpdf.text.pdf.draw.LineSeparator();
						document.add(ls);
						document.add(new Paragraph(" "));
						com.itextpdf.text.pdf.PdfPTable infoQrTable = new com.itextpdf.text.pdf.PdfPTable(
								new float[] { 3, 1 });
						infoQrTable.setWidthPercentage(80);
						com.itextpdf.text.pdf.PdfPTable infoTable = new com.itextpdf.text.pdf.PdfPTable(2);
						infoTable.setWidthPercentage(100);
						infoTable.getDefaultCell().setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
						infoTable.addCell(new com.itextpdf.text.Phrase("Nombre Pasajero:", labelFont));
						infoTable.addCell(new com.itextpdf.text.Phrase(bp.getPassengerName(), valueFont));
						infoTable.addCell(new com.itextpdf.text.Phrase("Asiento:", labelFont));
						infoTable
								.addCell(new com.itextpdf.text.Phrase(bp.getRow() + " - " + bp.getNumber(), valueFont));
						infoTable.addCell(new com.itextpdf.text.Phrase("CARRO:", labelFont));
						infoTable.addCell(new com.itextpdf.text.Phrase(bp.getTailNumber(), valueFont));
						infoTable.addCell(new com.itextpdf.text.Phrase("Fecha y Hora:", labelFont));
						infoTable.addCell(new com.itextpdf.text.Phrase(bp.getStarDate(), valueFont));
						infoTable.addCell(new com.itextpdf.text.Phrase("Origen:", labelFont));
						infoTable.addCell(new com.itextpdf.text.Phrase(bp.getOrigin(), valueFont));
						infoTable.addCell(new com.itextpdf.text.Phrase("Destino:", labelFont));
						infoTable.addCell(new com.itextpdf.text.Phrase(bp.getDestination(), valueFont));
//				infoTable.addCell(new com.itextpdf.text.Phrase("Gate:", labelFont));
//				infoTable.addCell(new com.itextpdf.text.Phrase(gate, valueFont));
//				infoTable.addCell(new com.itextpdf.text.Phrase("Boarding:", labelFont));
//				infoTable.addCell(new com.itextpdf.text.Phrase(boarding, valueFont));
						PdfPCell infoCell = new PdfPCell(infoTable);
						infoCell.setBorder(Rectangle.NO_BORDER);
						infoCell.setPaddingRight(10f);
						infoQrTable.addCell(infoCell);
						// QR cell (bigger)
						String barcodeValue = String.valueOf(bp.getIdSaleDetail());
						BarcodeQRCode qrCode = new BarcodeQRCode(barcodeValue, 110, 110, null);
						Image qrImg = qrCode.getImage();
						qrImg.setAlignment(Element.ALIGN_CENTER);
						PdfPCell qrCell = new PdfPCell(qrImg, true);
						qrCell.setBorder(Rectangle.NO_BORDER);
						qrCell.setHorizontalAlignment(Element.ALIGN_CENTER);
						qrCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						infoQrTable.addCell(qrCell);
						infoQrTable.setHorizontalAlignment(Element.ALIGN_CENTER);
						document.add(infoQrTable);
						document.add(new Paragraph(" "));
						document.add(ls);
						document.add(new Paragraph(" "));
						Paragraph info = new Paragraph("Tengo un buen vuelo!", valueFont);
						info.setAlignment(Paragraph.ALIGN_CENTER);
						document.add(info);
						i++;
					}

					document.close(); // <-- Ensure document is closed after writing
					JOptionPane.showMessageDialog(null, "Pase(s) de abordar generado(s) en: " + pdfPath);
					if (Desktop.isDesktopSupported()) {
						Desktop.getDesktop().open(new File(pdfPath));
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error al generar el pase de abordar: " + ex.getMessage());
				}
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
						.addComponent(btnNewButton_1))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(6)
					.addComponent(btnNewButton_1)
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
					.addGap(181))
		);
		panel_1.setLayout(gl_panel_1);
		tableSales = new JTable();
		DefaultTableModel salesTableModel = new DefaultTableModel(
				new Object[] { "ID Venta", "Usuario", "Cliente", "Monto", "Fecha" }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableSales.setModel(salesTableModel);
		scrollPane.setViewportView(tableSales);

		JLabel lblNewLabel = new JLabel("Cliente");

		textField = new JTextField();
		textField.setColumns(10);
		textField.setEnabled(false);

		JButton btnNewButton_3 = new JButton("Buscar");
		btnNewButton_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VSelectCustomer dialog = new VSelectCustomer(VSales.this);
				dialog.setVisible(true);
				Customer selected = dialog.getSelectedCustomer();
				if (selected != null) {
					selectedCustomerId = selected.getIdCustomer();
					textField.setText(selected.getIdCustomer() + " - " + selected.getName());
					textField.setToolTipText(selected.getEmail());
				}
			}
		});

		JLabel lblNewLabel_1 = new JLabel("Horario - Ruta");

		cmbSchedules = new JComboBox<Schedule>();
		cmbSchedules.addActionListener(e -> {
			Schedule seleccion = (Schedule) cmbSchedules.getSelectedItem();
			if (seleccion.getIdAirplane() == null) {
				return;
			}

			getSeats(seleccion.getIdAirplane(), seleccion.getIdSchedule());

		});

		JLabel lblNewLabel_1_1 = new JLabel("Asientos");

		cmbSeats = new JComboBox<Seat>();
		seatsComboBoxModel = new DefaultComboBoxModel<>();
		cmbSeats.setModel(seatsComboBoxModel);
		cmbSeats.setRenderer(new ListCellRenderer<Seat>() {
			private final javax.swing.plaf.basic.BasicComboBoxRenderer defaultRenderer = new javax.swing.plaf.basic.BasicComboBoxRenderer();

			@Override
			public java.awt.Component getListCellRendererComponent(javax.swing.JList<? extends Seat> list, Seat value,
					int index, boolean isSelected, boolean cellHasFocus) {
				java.awt.Component c = defaultRenderer.getListCellRendererComponent(list, value, index, isSelected,
						cellHasFocus);
				if (value == null)
					return c;
				boolean isInTable = false;
				for (int i = 0; i < tableTicketsModel.getRowCount(); i++) {
					int idSeatInTable = (int) tableTicketsModel.getValueAt(i, 0);
					if (value.getIdSeat() == idSeatInTable) {
						isInTable = true;
						break;
					}
				}
				// Si el asiento está vendido (isSaled == 1), mostrar en gris y deshabilitar
				if ((value.getIsSaled() == 1 && value.getIdSeat() != 0) || isInTable) {
					c.setForeground(java.awt.Color.GRAY);
					c.setEnabled(false);
				} else {
					c.setForeground(java.awt.Color.BLACK);
					c.setEnabled(true);
				}
				return c;
			}
		});
		cmbSeats.addActionListener(new ActionListener() {
			Seat lastValidSeat = null;

			@Override
			public void actionPerformed(ActionEvent e) {
				Seat selectedSeat = (Seat) cmbSeats.getSelectedItem();
				if (selectedSeat == null)
					return;
				if (selectedSeat.getIdSeat() == 0) {
					lastValidSeat = selectedSeat;
					return;
				}
				for (int i = 0; i < tableTicketsModel.getRowCount(); i++) {
					int idSeatInTable = (int) tableTicketsModel.getValueAt(i, 0);
					if (selectedSeat.getIdSeat() == idSeatInTable) {
						JOptionPane.showMessageDialog(null, "Este asiento ya está seleccionado.", "Validación",
								JOptionPane.WARNING_MESSAGE);
						cmbSeats.setSelectedItem(lastValidSeat);
						return;
					}
				}
				lastValidSeat = selectedSeat;
			}
		});

		JButton btnNewButton_4 = new JButton("Agregar");
		btnNewButton_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Seat selectedSeat = (Seat) cmbSeats.getSelectedItem();
				if (selectedSeat == null || selectedSeat.getIdSeat() == 0) {
					JOptionPane.showMessageDialog(null, "Seleccione un asiento válido.", "Validación",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (selectedSeat.getIsSaled() == 1) {
					JOptionPane.showMessageDialog(null, "Este asiento ya está vendido.", "Validación",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				for (int i = 0; i < tableTicketsModel.getRowCount(); i++) {
					int idSeatInTable = (int) tableTicketsModel.getValueAt(i, 0);
					if (idSeatInTable == selectedSeat.getIdSeat()) {
						JOptionPane.showMessageDialog(null, "El asiento ya fue agregado.", "Validación",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				Schedule schedule = (Schedule) cmbSchedules.getSelectedItem();
				if (schedule.getIdAirplane() == null) {
					JOptionPane.showMessageDialog(null, "Seleccione un horario válido.", "Validación",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				String passengerName = JOptionPane.showInputDialog(null, "Ingrese el nombre del pasajero:",
						"Nombre del pasajero", JOptionPane.PLAIN_MESSAGE);
				if (passengerName == null || passengerName.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Debe ingresar el nombre del pasajero.", "Validación",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				selectedSeat.setPassengerName(passengerName.trim());

				tableTicketsModel.addRow(new Object[] { selectedSeat.getIdSeat(), selectedSeat.getRow(),
						selectedSeat.getNumber(), schedule.getPrice(), passengerName.trim() });
				cmbSeats.repaint();
				updateTotal();
			}
		});

		JButton btnRemoveSeat = new JButton("Quitar");
		btnRemoveSeat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblTickets.getSelectedRow();
				if (selectedRow != -1) {
					tableTicketsModel.removeRow(selectedRow);
					cmbSeats.repaint();
					updateTotal();
				} else {
					JOptionPane.showMessageDialog(null, "Seleccione un asiento de la lista para quitar.", "Información",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		JButton btnNewButton = new JButton("Generar Venta");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedCustomerId == -1) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente antes de generar la venta.",
							"Validación", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (tableTicketsModel.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Debe agregar al menos un boleto antes de generar la venta.",
							"Validación", JOptionPane.WARNING_MESSAGE);
					return;
				}
				Sale sale = new Sale();
				sale.setIdCustomer(selectedCustomerId);
				sale.setIdUser(1);
				sale.setAmount(updateTotal());
				List<Seat> selectedSeats = new java.util.ArrayList<>();
				for (int i = 0; i < tableTicketsModel.getRowCount(); i++) {
					int idSeat = (int) tableTicketsModel.getValueAt(i, 0);
					String row = tableTicketsModel.getValueAt(i, 1).toString();
					int number = Integer.parseInt(tableTicketsModel.getValueAt(i, 2).toString());
					String passengerName = tableTicketsModel.getValueAt(i, 4).toString();
					Seat seat = new Seat();
					seat.setIdSeat(idSeat);
					seat.setRow(row);
					seat.setNumber(number);
					seat.setPassengerName(passengerName);
					selectedSeats.add(seat);
				}
				sale.setSeats(selectedSeats);
				Schedule seleccion = (Schedule) cmbSchedules.getSelectedItem();
				if (seleccion.getIdAirplane() == null) {
					return;
				}
				saleDao.save(sale, seleccion);
				JOptionPane.showMessageDialog(null, "¡Venta generada exitosamente!", "Venta",
						JOptionPane.INFORMATION_MESSAGE);
				limpiarCampos();
				getSales();
			}
		});

		JButton btnNewButton_2 = new JButton("Cancelar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpiarCampos();
			}
		});

		lblTotal = new JLabel("Total: $0.00");
		lblTotal.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));

		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(369, Short.MAX_VALUE)
					.addComponent(lblTotal)
					.addGap(36))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(scrollPane_1)
							.addGap(28))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(6)
							.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(6)
							.addComponent(lblNewLabel_1)
							.addContainerGap())
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lblNewLabel)
								.addContainerGap())
							.addGroup(gl_panel.createSequentialGroup()
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(cmbSeats, 0, 263, Short.MAX_VALUE)
										.addGap(6)
										.addComponent(btnNewButton_4)
										.addGap(6)
										.addComponent(btnRemoveSeat))
									.addGroup(gl_panel.createSequentialGroup()
										.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
											.addComponent(textField, GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
											.addComponent(cmbSchedules, 0, 354, Short.MAX_VALUE))
										.addGap(6)
										.addComponent(btnNewButton_3)
										.addGap(6)))
								.addGap(31)))))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(80)
					.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(165))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_3)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(1)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1)
					.addGap(2)
					.addComponent(cmbSchedules, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1_1)
					.addGap(8)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(cmbSeats, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_4)
						.addComponent(btnRemoveSeat))
					.addGap(18)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblTotal)
					.addGap(66)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnNewButton_2))
					.addGap(54))
		);

		// set columns to tickets table
		tableTicketsModel = new DefaultTableModel(new Object[] { "ID", "Fila", "Número", "Precio", "Pasajero" }, 0) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		table = new JTable(tableTicketsModel);
		tblTickets = new JTable(tableTicketsModel);
		scrollPane_1.setViewportView(tblTickets);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);

		// Load data

		getSchedules();
		getSales();

	}

	private void getSchedules() {
		try {
			List<Schedule> schedules = scheduleDao.getSchedules();
			DefaultComboBoxModel<Schedule> model = new DefaultComboBoxModel<>();
			Schedule defaultSchedule = new Schedule();
			defaultSchedule.setIdSchedule(0);
			defaultSchedule.setRouteLabel("Seleccione un Horario - Ruta");
			model.addElement(defaultSchedule);
			for (Schedule schedule : schedules) {
				model.addElement(schedule);
			}
			cmbSchedules.setModel(model);
			cmbSchedules.setSelectedIndex(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void getSeats(int idAirplane, int idSchedule) {
		try {
			List<Seat> seats = airplaneDao.getSeats(idAirplane, idSchedule);
			seatsComboBoxModel.removeAllElements();
			Seat defaultSeat = new Seat();

			defaultSeat.setRow("Seleccione un Asiento");
			seatsComboBoxModel.addElement(defaultSeat);
			for (Seat seat : seats) {
				seatsComboBoxModel.addElement(seat);
			}
			cmbSeats.setSelectedIndex(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private double updateTotal() {
		double total = 0.0;
		for (int i = 0; i < tableTicketsModel.getRowCount(); i++) {
			Object priceObj = tableTicketsModel.getValueAt(i, 3);
			if (priceObj instanceof Number) {
				total += ((Number) priceObj).doubleValue();
			} else {
				try {
					total += Double.parseDouble(priceObj.toString());
				} catch (Exception e) {
				}
			}
		}
		lblTotal.setText(String.format("Total: $%.2f", total));

		return total;
	}

	private void limpiarCampos() {
		textField.setText("");
		textField.setToolTipText("");
		selectedCustomerId = -1;
		cmbSchedules.setSelectedIndex(0);
		seatsComboBoxModel.removeAllElements();
		tableTicketsModel.setRowCount(0);
		lblTotal.setText("Total: $0.00");
	}

	private void getSales() {
		try {
			List<Sale> allSales = saleDao.getSales();
			DefaultTableModel model = (DefaultTableModel) tableSales.getModel();
			model.setRowCount(0);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			for (Sale sale : allSales) {
				model.addRow(new Object[] { sale.getIdSale(), sale.getUser(), sale.getCustomer(),
						String.format("$%.2f", sale.getAmount()),
						sale.getRegistered() != null ? sale.getRegistered().format(formatter) : "" });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al cargar ventas: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
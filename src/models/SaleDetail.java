package models;

public class SaleDetail {

	private int idSaleDetail;
	private int idSale;
	private int idSeat;
	private int idSchedule;
	private int idTicket;
	private double subtotal;
	private String passengerName;
	
	public int getIdSaleDetail() {
		return idSaleDetail;
	}
	public void setIdSaleDetail(int idSaleDetail) {
		this.idSaleDetail = idSaleDetail;
	}
	public int getIdSale() {
		return idSale;
	}
	public void setIdSale(int idSale) {
		this.idSale = idSale;
	}
	public int getIdSeat() {
		return idSeat;
	}
	public void setIdSeat(int idSeat) {
		this.idSeat = idSeat;
	}
	public int getIdSchedule() {
		return idSchedule;
	}
	public void setIdSchedule(int idSchedule) {
		this.idSchedule = idSchedule;
	}
	public int getIdTicket() {
		return idTicket;
	}
	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	
	public String getPassengerName() {
		return passengerName;
	}
	
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	

	
}

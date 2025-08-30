package models;
import java.time.LocalDateTime;
import java.util.List;

public class Sale {
	
	public static final int ENABLED = 1;
	public static final int DISABLED = 2;
	public static final int DELETED = 99;
	
	private int idSale;
	private int idCustomer;
	private String customer;
	private int idUser;
	private String user;
	private double amount;
	private List<Seat> seats;
	private LocalDateTime registered;
	private int status;
	
	public int getIdSale() {
		return idSale;
	}
	public void setIdSale(int idSale) {
		this.idSale = idSale;
	}
	public int getIdCustomer() {
		return idCustomer;
	}
	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}
	
	
	public String getCustomer() {
		return customer;
	}
	
	
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public List<Seat> getSeats() {
		return seats;
	}
	
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
	
	public LocalDateTime getRegistered() {
		return registered;
	}
	
	public void setRegistered(LocalDateTime registered) {
		this.registered = registered;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public static String toLabel(int code) {
		switch (code) {
		case ENABLED:
			return "HABILITADO";
		case DISABLED:
			return "DESHABILITADO";
		default:
			return "DESCONOCIDO";
		}
	}

	public static int fromLabel(String label) {
		if ("HABILITADO".equalsIgnoreCase(label))
			return ENABLED;
		if ("DESHABILITADO".equalsIgnoreCase(label))
			return DISABLED;
		return ENABLED;
	}
	
}
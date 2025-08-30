package models;

import java.util.Objects;

public class Seat {
	
	private int idSeat;
	private int idAirplane;
	private String airplane;
	private int idSeatType;
	private String seatType;
	private String  row;
	private int number;
	private Double price;
	
	private int isSaled; 
	private String passengerName; 
	
	public int getIdSeat() {
		return idSeat;
	}
	public void setIdSeat(int idSeat) {
		this.idSeat = idSeat;
	}
	public int getIdAirplane() {
		return idAirplane;
	}
	public void setIdAirplane(int idAirplane) {
		this.idAirplane = idAirplane;
	}
	public int getIdSeatType() {
		return idSeatType;
	}
	public void setIdSeatType(int idSeatType) {
		this.idSeatType = idSeatType;
	}
	public String getRow() {
		return row;
	}
	public void setRow(String row) {
		this.row = row;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getAirplane() {
		return airplane;
	}
	public void setAirplane(String airplane) {
		this.airplane = airplane;
	}
	public String getSeatType() {
		return seatType;
	}
	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}
	
	public int getIsSaled() {
		return isSaled;
	}
	public void setIsSaled(int isSaled) {
		this.isSaled = isSaled;
	}
	
	public String getPassengerName() {
		return passengerName;
	}
	
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	
	
	
	 @Override
	    public String toString() {
	        if (row != null && row.equals("Seleccione un Asiento")) {
	            return row;
	        }
	        return row + " - " + number;
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Seat)) return false;
	        Seat a = (Seat) o;
	        return idSeat == a.idSeat;
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(idSeat);
	    }
	

}
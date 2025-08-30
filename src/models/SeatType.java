package models;

import java.util.Objects;

public class SeatType {
	
	public static final int ENABLED = 1;
	public static final int DISABLED = 2;
	public static final int DELETED = 99;
	
	private int idSeatType;
	private String name;
	private int status;
	
	
	public int getIdSeatType() {
		return idSeatType;
	}
	public void setIdSeatType(int idSeatType) {
		this.idSeatType = idSeatType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		case DELETED:
			return "ELIMINADO";
		default:
			return "DESCONOCIDO";
		}
	}

	public static int fromLabel(String label) {
		if ("HABILITADO".equalsIgnoreCase(label))
			return ENABLED;
		if ("DESHABILITADO".equalsIgnoreCase(label))
			return DISABLED;
		if ("ELIMINADO".equalsIgnoreCase(label))
			return DELETED;
		return ENABLED;
	}
	
	@Override
    public String toString() {
        return idSeatType + " - " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeatType)) return false;
        SeatType a = (SeatType) o;
        return idSeatType == a.idSeatType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSeatType);
    }

	
	

}

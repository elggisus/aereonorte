package models;

import java.util.Objects;

public class Airplane {

	public static final int ENABLED = 1;
	public static final int DISABLED = 2;
	public static final int DELETED = 99;

	private int idAirplane;
	private String tailNumber;
	private int seatsNumber;
	private int status;

	public void setIdAirplane(int idAirplane) {
		this.idAirplane = idAirplane;
	}

	public int getIdAirplane() {
		return this.idAirplane;
	}

	public String getTailNumber() {
		return tailNumber;
	}

	public void setTailNumber(String tailNumber) {
		this.tailNumber = tailNumber;
	}

	public int getSeatsNumber() {
		return seatsNumber;
	}

	public void setSeatsNumber(int seatsNumber) {
		this.seatsNumber = seatsNumber;
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
		return ENABLED;
	}
	
	
    @Override
    public String toString() {
        return idAirplane + " - " + tailNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airplane)) return false;
        Airplane a = (Airplane) o;
        return idAirplane == a.idAirplane;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAirplane);
    }

}

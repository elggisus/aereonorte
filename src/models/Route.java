package models;

import java.util.Objects;

public class Route {
	
	public static final int ENABLED = 1;
	public static final int DISABLED = 2;
	public static final int DELETED = 99;
	
	private Integer idRoute;
	private String origin;
	private String destination;
	private Integer status;
	
	public Integer getIdRoute() {
		return idRoute;
	}
	public void setIdRoute(Integer idRoute) {
		this.idRoute = idRoute;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
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
	
	@Override
    public String toString() {
        return origin + " - " + destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route a = (Route) o;
        return idRoute == a.idRoute;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRoute);
    }
	
	
	

}

package models;

import java.util.Objects;

public class Schedule {
	
	public static final int ENABLED = 1;
	public static final int DISABLED = 2;
	public static final int DELETED = 99;
	
	private Integer idSchedule;
	private Integer idAirplane;
	private String airplane;
	private Integer idRoute;
	private String route;
	private String startDate;
	private String endDate;
	private Double price;
	private Integer status;
	private String routeLabel;
	

	public Integer getIdSchedule() {
		return idSchedule;
	}

	public void setIdSchedule(Integer idSchedule) {
		this.idSchedule = idSchedule;
	}

	public Integer getIdAirplane() {
		return idAirplane;
	}

	public void setIdAirplane(Integer idAirplane) {
		this.idAirplane = idAirplane;
	}

	public String getAirplane() {
		return airplane;
	}

	public void setAirplane(String airplane) {
		this.airplane = airplane;
	}

	public Integer getIdRoute() {
		return idRoute;
	}

	public void setIdRoute(Integer idRoute) {
		this.idRoute = idRoute;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRouteLabel() {
		return routeLabel;
	}

	public void setRouteLabel(String routeLabel) {
		this.routeLabel = routeLabel;
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
        if (routeLabel != null && !routeLabel.isEmpty()) {
            return routeLabel;
        }
        return route + " - " + startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule a = (Schedule) o;
        return idSchedule == a.idSchedule;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSchedule);
    }
	
	

}
package models;

public class User {
	
	public static final int ENABLED = 1;
	public static final int DISABLED = 2;
	public static final int DELETED = 99;
	
	private int idUser;
	private String name;
	private String username;
	private String password;
	private int status;
	private int role;
	
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getRole() {
		return role;
	}
	
	public void setRole(int role) {
		this.role = role;
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

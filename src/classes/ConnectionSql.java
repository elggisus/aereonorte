package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSql {

	private static ConnectionSql instance;
	private Connection connection;

	private static final String URL =
			  "jdbc:sqlserver://localhost:1433;databaseName=aereonorte;" +
			  "encrypt=true;trustServerCertificate=true";
	private static final String USER = "sa";
	private static final String PASSWORD = "Pwlocal2025.";

	private ConnectionSql() {
		try {
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Conexión a SQL Server establecida.");
		} catch (ClassNotFoundException e) {
			System.err.println("No se encontró el driver JDBC: " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Error de conexión: " + e.getMessage());
		}
	}
	
	public static ConnectionSql getInstance() {
        if (instance == null) {
        	instance = new ConnectionSql();
        }
        return instance;
    }
	
	
	public Connection getConnection() {
		return connection;
	}
	
	public void closeConnection() {
        if (connection != null) {
            try {
            	connection.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

}
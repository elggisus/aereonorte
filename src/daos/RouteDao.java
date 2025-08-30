
package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import classes.ConnectionSql;
import models.Customer;
import models.Route;
import models.SeatType;

public class RouteDao {

	public List<Route> getRoutes() throws Exception {

		try {
			List<Route> list = new ArrayList<Route>();

			String query = "select id_route,origin,destination,status from routes order by id_route desc";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Route route = new Route();
				route.setIdRoute(rs.getInt("id_route"));
				route.setOrigin(rs.getString("origin"));
				route.setDestination(rs.getString("destination"));
				route.setStatus(rs.getInt("status"));


				list.add(route);
			}

			return list;

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}

	}

	public Integer save(Route route) throws Exception {

		try {
			String sql = "INSERT INTO routes(origin,destination,status) VALUES(?,?,?)";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, route.getOrigin());
			ps.setString(2, route.getDestination());
			ps.setInt(3,Route.ENABLED );
		
			int rows = ps.executeUpdate();
			if (rows > 0) {
				ResultSet keys = ps.getGeneratedKeys();
				if (keys.next()) {
					return keys.getInt(1);
				}
			}

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}

		return null;

	}

	public Integer update(SeatType seatType) throws Exception {

		try {
			String sql = "UPDATE seat_types SET name=? "
					+ "WHERE id_seat_type=?";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, seatType.getName());
			ps.setInt(2, seatType.getIdSeatType());

			return ps.executeUpdate();


		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}

	}
	
	public Integer disable(int idCustomer) throws Exception {

		try {
			String sql = "UPDATE customers SET status = ? "
					+ "WHERE id_customer=?";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, Customer.DISABLED);
		
			ps.setInt(2,idCustomer);

			return ps.executeUpdate();


		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}

	}


}

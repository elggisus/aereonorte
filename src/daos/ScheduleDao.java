
package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import classes.ConnectionSql;
import models.Airplane;
import models.Customer;
import models.Route;
import models.Schedule;
import models.SeatType;

public class ScheduleDao {
	
	public List<Schedule> getSchedules() throws Exception {

		try {
			List<Schedule> list = new ArrayList<>();

			String query = "select s.id_schedule,s.id_airplane,a.tail_number as 'airplane',\r\n"
					+ "s.id_route,concat(r.origin,'-',r.destination) as 'route',s.start_date,s.end_date,s.status,s.price from schedules s\r\n"
					+ "inner join airplanes a on a.id_airplane = s.id_airplane\r\n"
					+ "inner join routes r on r.id_route = s.id_route\r\n"
					+ "order by s.id_schedule desc";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Schedule schedule = new Schedule();
				schedule.setIdSchedule(rs.getInt("id_schedule"));
				schedule.setIdAirplane(rs.getInt("id_airplane"));
				schedule.setAirplane(rs.getString("airplane"));
				schedule.setIdRoute(rs.getInt("id_route"));
				schedule.setRoute(rs.getString("route"));
				schedule.setStartDate(rs.getString("start_date"));
				schedule.setEndDate(rs.getString("end_date"));
				schedule.setPrice(rs.getDouble("price"));
				schedule.setStatus(rs.getInt("status"));
				list.add(schedule);
			}

			return list;

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}

	}
	
	
	

	public List<Airplane> getAirplanes() throws Exception {

		try {
			List<Airplane> list = new ArrayList<>();

			String query = "select id_airplane,tail_number,seats_number,status from airplanes order by id_airplane desc";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Airplane airplane = new Airplane();
				airplane.setIdAirplane(rs.getInt("id_airplane"));
				airplane.setTailNumber(rs.getString("tail_number"));
				airplane.setSeatsNumber(rs.getInt("seats_number"));
				airplane.setStatus(rs.getInt("status"));


				list.add(airplane);
			}

			return list;

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}

	}
	
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

	public Integer save(Schedule schedule) throws Exception {

		try {
			String sql = "INSERT INTO schedules(id_airplane,id_route,start_date,end_date,price,status) VALUES(?,?,?,?,?,?)";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, schedule.getIdAirplane());
			ps.setInt(2, schedule.getIdRoute());
			ps.setString(3, schedule.getStartDate());
			ps.setString(4, schedule.getEndDate());
			ps.setDouble(5, schedule.getPrice());
			ps.setInt(6, Schedule.ENABLED);

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

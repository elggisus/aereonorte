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
import models.Seat;
import models.SeatType;

public class AirplaneDao {

	public List<Airplane> getAirplanes() throws Exception {

		try {
			List<Airplane> list = new ArrayList<Airplane>();

			String query = "Select id_airplane,tail_number,seats_number,status from airplanes order by id_airplane desc";
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
	
	public List<Seat> getSeats(int idAirplane, int idSchedule) throws Exception { 
		try {
			List<Seat> seats = new ArrayList<>();
			String sql =
				"SELECT \n"
				+ "    s.id_seat, \n"
				+ "    s.id_airplane, \n"
				+ "    s.id_seat_type, \n"
				+ "    st.name AS seatType, \n"
				+ "    s.row, \n"
				+ "    s.number, \n"
				+ "    CASE \n"
				+ "        WHEN sd.id_sale_detail IS NULL THEN 0 \n"
				+ "        ELSE 1 \n"
				+ "    END AS isSaled \n"
				+ "FROM seats s\n"
				+ "INNER JOIN seat_types st \n"
				+ "    ON st.id_seat_type = s.id_seat_type\n"
				+ "LEFT JOIN sale_details sd\n"
				+ "    ON sd.id_seat = s.id_seat \n"
				+ "   AND sd.id_schedule = ? \n"
				+ "WHERE s.id_airplane = ? \n"
				+ "ORDER BY s.row, s.number;";
			
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, idSchedule); 
			ps.setInt(2, idAirplane); 
			
			
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Seat seat = new Seat();
				seat.setIdSeat(rs.getInt("id_seat"));
				seat.setIdAirplane(rs.getInt("id_airplane"));
				seat.setIdSeatType(rs.getInt("id_seat_type"));
				seat.setSeatType(rs.getString("seatType"));
				seat.setRow(rs.getString("row"));
				seat.setNumber(rs.getInt("number"));
				seat.setIsSaled(rs.getInt("isSaled"));
				
				seats.add(seat);
			}
			
			
			return seats;
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		
	}

	public Integer insert(Airplane airplane) throws Exception {

		try {
			String sql = "INSERT INTO airplanes(tail_number,seats_number,status) VALUES(?,?,?)";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, airplane.getTailNumber());
			ps.setInt(2, airplane.getSeatsNumber());
			ps.setInt(3,Airplane.ENABLED );
		
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
	
	public void enable(int idAirplane) throws Exception {

		try {
			String sql = "UPDATE airplanes SET status = ? "
					+ "WHERE id_airplane = ?";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, Airplane.ENABLED);
			ps.setInt(2,idAirplane);

			ps.executeUpdate();


		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}

	}
	
	public void disable(int idAirplane) throws Exception {

		try {
			String sql = "UPDATE airplanes SET status = ? "
					+ "WHERE id_airplane = ?";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, Airplane.DISABLED);
			ps.setInt(2,idAirplane);

			ps.executeUpdate();


		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}

	}

	public Integer update(Airplane airplane) throws Exception {
		try {
			String sql = "UPDATE airplanes SET tail_number=?, seats_number=? WHERE id_airplane=?";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, airplane.getTailNumber());
			ps.setInt(2, airplane.getSeatsNumber());
			ps.setInt(3, airplane.getIdAirplane());
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	public void delete(int idAirplane) throws Exception {

		try {
			String sql = "UPDATE airplanes SET status = ? "
					+ "WHERE id_airplane = ?";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, Airplane.DELETED);
			ps.setInt(2,idAirplane);

			ps.executeUpdate();


		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}

	}


}
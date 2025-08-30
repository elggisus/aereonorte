
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

public class SeatDao {
	
	public List<Seat> getSeats() throws Exception {

		try {
			List<Seat> list = new ArrayList<>();

			String query = "Select s.id_seat,s.id_airplane,s.id_seat_type,s.row,s.number,\r\n"
					+ "a.tail_number as 'airplane',st.name as 'seatType'\r\n"
					+ "from seats s\r\n"
					+ "inner join airplanes a on a.id_airplane = s.id_airplane\r\n"
					+ "inner join seat_types st on st.id_seat_type = s.id_seat_type\r\n"
					+ "order by s.id_seat desc";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Seat seat = new Seat();
				seat.setIdSeat(rs.getInt("id_seat"));
				seat.setIdAirplane(rs.getInt("id_airplane"));
				seat.setAirplane(rs.getString("airplane"));
				seat.setIdSeatType(rs.getInt("id_seat_type"));
				seat.setSeatType(rs.getString("seatType"));
				seat.setRow(rs.getString("row"));
				seat.setNumber(rs.getInt("number"));


				list.add(seat);
			}

			return list;

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}

	}
	
	
	

	public List<Airplane> getAirplanes() throws Exception {

		try {
			List<Airplane> list = new ArrayList<>();

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
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}

	}
	
	public List<SeatType> getSeatTypes() throws Exception {

		try {
			List<SeatType> list = new ArrayList<SeatType>();

			String query = "Select id_seat_type,name,status from seat_types order by id_seat_type desc";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				SeatType seatType = new SeatType();
				seatType.setIdSeatType(rs.getInt("id_seat_type"));
				seatType.setName(rs.getString("name"));
				seatType.setStatus(rs.getInt("status"));


				list.add(seatType);
			}

			return list;

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}

	}

	public Integer insert(Seat seat) throws Exception {

		try {
			String sql = "INSERT INTO seats(id_airplane,id_seat_type,row,number) VALUES(?,?,?,?)";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, seat.getIdAirplane());
			ps.setInt(2, seat.getIdSeatType());
			ps.setString(3, seat.getRow());
			ps.setInt(4, seat.getNumber());

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

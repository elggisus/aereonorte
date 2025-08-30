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
import models.SeatType;

public class SeatTypeDao {

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

	public Integer insert(SeatType seatType) throws Exception {

		try {
			String sql = "INSERT INTO seat_types(name,status) VALUES(?,?)";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, seatType.getName());
			ps.setInt(2,SeatType.ENABLED );
		
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

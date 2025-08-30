package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import classes.ConnectionSql;
import models.BoardingPass;
import models.Sale;
import models.Schedule;
import models.Seat;

public class SaleDao {

	
	public List<Sale> getSales() throws Exception {

		try {
			List<Sale> list = new ArrayList<>();

			String query = "select s.id_sale,u.name as 'user',c.name as 'customer',s.amount,s.registered from sales s\n"
					+ "	inner join users u on s.id_user = u.id_user\n"
					+ "	inner join customers c on s.id_customer = c.id_customer";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Sale sale = new Sale();
				sale.setIdSale(rs.getInt("id_sale"));
				sale.setUser(rs.getString("user"));
				sale.setCustomer(rs.getString("customer"));
				sale.setAmount(rs.getDouble("amount"));
				sale.setRegistered(rs.getTimestamp("registered") != null ? rs.getTimestamp("registered").toLocalDateTime() : null);
				list.add(sale);
			}

			return list;

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}

	}
	
	public List<BoardingPass> getBoardingPassesBySale(int idSale) throws Exception {
		try {
			List<BoardingPass> list = new ArrayList<>();

			String query = "select sd.id_sale_detail, passenger_name, sc.start_date, r.origin, r.destination, se.row, se.number, a.tail_number from sale_details sd\n"
					+ "inner join seats s on sd.id_seat = s.id_seat\n"
					+ "inner join schedules sc on sd.id_schedule = sc.id_schedule\n"
					+ "inner join routes r on r.id_route = sc.id_route\n"
					+ "inner join seats se on se.id_seat = sd.id_seat\n"
					+ "inner join airplanes a on a.id_airplane = se.id_airplane\n"
					+ "where sd.id_sale = ?";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idSale);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				BoardingPass bp = new BoardingPass();
				bp.setIdSaleDetail(rs.getInt("id_sale_detail"));
				bp.setPassengerName(rs.getString("passenger_name"));
				bp.setStarDate(rs.getTimestamp("start_date") != null ? rs.getTimestamp("start_date").toLocalDateTime().toString() : null);
				bp.setOrigin(rs.getString("origin"));
				bp.setDestination(rs.getString("destination"));
				bp.setRow(rs.getString("row"));
				bp.setNumber(rs.getInt("number"));
				bp.setTailNumber(rs.getString("tail_number"));
				list.add(bp);
			}

			return list;

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}
	
	
	
	public void save(Sale sale, Schedule schedule) {
		String sql = "INSERT INTO sales (id_customer, id_user, amount, registered) VALUES (?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionSql.getInstance().getConnection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, sale.getIdCustomer());
			stmt.setInt(2, sale.getIdUser());
			stmt.setDouble(3, sale.getAmount());
			stmt.setTimestamp(4, sale.getRegistered() != null ? Timestamp.valueOf(sale.getRegistered()) : new Timestamp(System.currentTimeMillis()));
			int rows = stmt.executeUpdate();
			if (rows == 0) {
				conn.rollback();
				return;
			}
			rs = stmt.getGeneratedKeys();
			Integer idSale = null;
			if (rs.next()) {
				idSale = rs.getInt(1);
				sale.setIdSale(idSale);
			} else {
				conn.rollback();
				return;
			}

			for (Seat seat : sale.getSeats()) {
				String sqlSaleDetail = "insert into sale_details (id_sale,id_seat,id_schedule,subtotal,passenger_name) values(?,?,?,?,?)";
				try (PreparedStatement psSaleDetail = conn.prepareStatement(sqlSaleDetail)) {
					psSaleDetail.setInt(1, idSale);
					psSaleDetail.setInt(2, seat.getIdSeat());
					psSaleDetail.setInt(3, schedule.getIdSchedule());
					psSaleDetail.setDouble(4, schedule.getPrice());
					psSaleDetail.setString(5, seat.getPassengerName());
					psSaleDetail.executeUpdate();
				}

			}

			conn.commit();
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if (conn != null)
					conn.setAutoCommit(true);
			} catch (Exception e) {
			}
		}
	}

    public List<Sale> getSalesByDateRange(LocalDateTime start, LocalDateTime end) throws Exception {
        try {
            List<Sale> list = new ArrayList<>();
            String query = "select s.id_sale,u.name as 'user',c.name as 'customer',s.amount,s.registered from sales s\n"
                    + " inner join users u on s.id_user = u.id_user\n"
                    + " inner join customers c on s.id_customer = c.id_customer\n"
                    + " where s.registered >= ? and s.registered <= ?";
            
            System.out.println("Query: " + query);
            Connection conn = ConnectionSql.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setTimestamp(1, Timestamp.valueOf(start));
            ps.setTimestamp(2, Timestamp.valueOf(end));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Sale sale = new Sale();
                sale.setIdSale(rs.getInt("id_sale"));
                sale.setUser(rs.getString("user"));
                sale.setCustomer(rs.getString("customer"));
                sale.setAmount(rs.getDouble("amount"));
                sale.setRegistered(rs.getTimestamp("registered") != null ? rs.getTimestamp("registered").toLocalDateTime() : null);
                list.add(sale);
            }
            return list;
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }
}
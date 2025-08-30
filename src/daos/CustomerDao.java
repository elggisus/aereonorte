package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import classes.ConnectionSql;
import models.Customer;

public class CustomerDao {


	public List<Customer> getCustomers(HashMap<String,Object> filters, int page, int pageSize) throws Exception {
        try {
            List<Customer> list = new ArrayList<Customer>();
            StringBuilder query = new StringBuilder("SELECT id_customer, name, email, birthday, id_number, status FROM customers");
            List<Object> params = new ArrayList<>();
            int status = 0;
            boolean hasFilter = false;
            //Filtros
            if (filters != null) {
                if (filters.containsKey("name")) {
                    query.append(hasFilter ? " AND" : " WHERE");
                    query.append(" name LIKE ?");
                    params.add("%" + filters.get("name") + "%");
                    hasFilter = true;
                }
                if(filters.containsKey("status")) {
                    status = (int) filters.get("status");
                    if(status != 0 ) {
                        query.append(hasFilter ? " AND" : " WHERE");
                        query.append(" status = ?");
                        params.add(status);
                        hasFilter = true;
                    }
                }
            }
            int offset = (page > 0 ? (page - 1) * pageSize : 0);
            int fetch = pageSize > 0 ? pageSize : 1;
            query.append(" ORDER BY id_customer DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
            params.add(offset); // OFFSET
            params.add(fetch); // FETCH NEXT
            Connection conn = ConnectionSql.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(query.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setIdCustomer(rs.getInt("id_customer"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setBirthday(rs.getString("birthday"));
                customer.setIdNumber(rs.getString("id_number"));
                customer.setStatus(rs.getInt("status"));
                list.add(customer);
            }
            return list;
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }
	
	public Integer insert(Customer customer) throws Exception {

		try {
			String sql = "INSERT INTO customers(name,email,birthday,id_number,status) VALUES(?,?,?,?,?)";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, customer.getName());
			ps.setString(2, customer.getEmail());
			ps.setString(3, customer.getBirthday());
			ps.setString(4, customer.getIdNumber());
			ps.setInt(5, Customer.ENABLED);
			;
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

	public Integer update(Customer customer) throws Exception {

		try {
			String sql = "UPDATE customers SET name=?, email=?, birthday=?, id_number=?, nacionality=? "
					+ "WHERE id_customer=?";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, customer.getName());
			ps.setString(2, customer.getEmail());
			ps.setString(3, customer.getBirthday());
			ps.setString(4, customer.getIdNumber());
			ps.setString(5, customer.getNacionality());
			ps.setInt(6, customer.getIdCustomer());

			return ps.executeUpdate();


		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}

	}
	

	public List<Customer> searchCustomers(String filter) throws Exception {
		List<Customer> list = new ArrayList<>();
		String query = "SELECT id_customer, name, email, birthday, id_number, status FROM customers WHERE name LIKE ? OR  email LIKE ? AND status = 1 ORDER BY id_customer DESC";
		try {
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			String likeFilter = "%" + filter + "%";
			ps.setString(1, likeFilter);
			ps.setString(2, likeFilter);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setIdCustomer(rs.getInt("id_customer"));
				customer.setName(rs.getString("name"));
				customer.setEmail(rs.getString("email"));
				customer.setBirthday(rs.getString("birthday"));
				customer.setIdNumber(rs.getString("id_number"));
				customer.setStatus(rs.getInt("status"));
				list.add(customer);
			}
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return list;
	}
	
	public int getCustomersCount(HashMap<String,Object> filters) throws Exception {
    try {
        StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM customers");
        List<Object> params = new ArrayList<>();
        int status = 0;
        boolean hasFilter = false;
        if (filters != null) {
            if (filters.containsKey("name")) {
                query.append(hasFilter ? " AND" : " WHERE");
                query.append(" name LIKE ?");
                params.add("%" + filters.get("name") + "%");
                hasFilter = true;
            }
            if(filters.containsKey("status")) {
                status = (int) filters.get("status");
                if(status != 0 ) {
                    query.append(hasFilter ? " AND" : " WHERE");
                    query.append(" status = ?");
                    params.add(status);
                    hasFilter = true;
                }
            }
        }
        Connection conn = ConnectionSql.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(query.toString());
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    } catch (SQLException e) {
        throw new Exception(e.getMessage());
    }
}
	
	public Integer enable(int idCustomer) throws Exception {

		try {
			String sql = "UPDATE customers SET status = ? "
					+ "WHERE id_customer=?";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, Customer.ENABLED);
		
			ps.setInt(2,idCustomer);

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
	
	public Integer delete(int idCustomer) throws Exception {

		try {
			String sql = "UPDATE customers SET status = ? "
					+ "WHERE id_customer=?";
			Connection conn = ConnectionSql.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, Customer.DELETED);
		
			ps.setInt(2,idCustomer);

			return ps.executeUpdate();


		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}

	}

}
package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import classes.ConnectionSql;
import models.User;

public class UserDao {
	

    public User login(String username, String password) throws Exception {
        Connection conn = ConnectionSql.getInstance().getConnection();
        String query = "SELECT id_user, username, name,role,status FROM users WHERE username = ? AND password = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            User user = new User();
            user.setIdUser(rs.getInt("id_user"));
            user.setUsername(rs.getString("username"));
            user.setName(rs.getString("name"));
            user.setRole(rs.getInt("role"));
            user.setStatus(rs.getInt("status"));
            if(user.getStatus() != User.ENABLED) {
                throw new Exception("Usuario no habilitado");
            }
            return user;
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new java.util.ArrayList<>();
        try {
            Connection conn = ConnectionSql.getInstance().getConnection();
            String query = "SELECT id_user, name, username, password, status FROM users";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setIdUser(rs.getInt("id_user"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setStatus(rs.getInt("status"));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean save(User user) {
        try {
            Connection conn = ConnectionSql.getInstance().getConnection();
            String query = "INSERT INTO users (name, username, password, status, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setInt(4, User.ENABLED);
            ps.setInt(5, user.getRole());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(User user) {
        try {
            Connection conn = ConnectionSql.getInstance().getConnection();
            String query = "UPDATE users SET name=?, username=?, password=?, status=?, role=? WHERE id_user=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getStatus());
            ps.setInt(5, user.getRole());
            ps.setInt(6, user.getIdUser());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean enable(int idUser) {
        try {
            Connection conn = ConnectionSql.getInstance().getConnection();
            String query = "UPDATE users SET status=? WHERE id_user=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, User.ENABLED);
            ps.setInt(2, idUser);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean disable(int idUser) {
        try {
            Connection conn = ConnectionSql.getInstance().getConnection();
            String query = "UPDATE users SET status=? WHERE id_user=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, User.DISABLED);
            ps.setInt(2, idUser);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int idUser) {
        try {
            Connection conn = ConnectionSql.getInstance().getConnection();
            String query = "UPDATE users SET status=? WHERE id_user=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, User.DELETED);
            ps.setInt(2, idUser);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
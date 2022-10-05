package net.revature.repository;

import net.revature.model.User;

import java.util.*;

import java.sql.*;

public class UserRepository {

    public User addUser(User user) throws SQLException { // Create in CRUD

        try (Connection con = ConnectionFactory.createConnection()) {
            String sql = "insert into UserTable (username, pwd, first_name, last_name, role_id) values (?, ?, ?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFirstName());
            pstmt.setString(4, user.getLastName());
            pstmt.setInt(5, 1);

            int numberOfRecordsAdded = pstmt.executeUpdate();


            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            return new User(id, user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), 1);
        }
    }

    // Login
    public User getUserByUsernameAndPassword(String username, String password) throws SQLException { // Read in CRUD
        try (Connection con = ConnectionFactory.createConnection()) {
            String sql = "SELECT * FROM UserTable as u WHERE u.username = ? AND u.pwd = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("user_id");
                String un = rs.getString("username");
                String pw = rs.getString("pwd");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int roleId = rs.getInt("role_id");

                return new User(id, un, pw, firstName, lastName, roleId);
            } else {
                return null;
            }

        }
    }
}

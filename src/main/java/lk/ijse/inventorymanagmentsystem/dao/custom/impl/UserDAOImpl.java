package lk.ijse.inventorymanagmentsystem.dao.custom.impl;

import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;
import lk.ijse.inventorymanagmentsystem.dao.custom.UserDAO;
import lk.ijse.inventorymanagmentsystem.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public User login(String username, String password) throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT  user_id,username, password, Role FROM User WHERE username = ? AND password = ?",
                username, password
        );

        if(rs.next()) {
            String user = rs.getString("username");
            String pass = rs.getString("password");
            String userId = rs.getString("user_id");
            String Role = rs.getString("Role");




            // Return a UserDTO object with all relevant info
            return new User( Integer.parseInt(userId) ,user, pass ,Role);

        } else {
            // Invalid login
            return null;
        }
    }

    @Override
    public boolean save(User entity) throws SQLException {
        boolean result = CrudUtil.execute("INSERT INTO User (username, password, role) VALUES (?,?,?)",
                entity.getUsername(),
                entity.getPassword(),
                entity.getRole()

        );

        return result;
    }

    @Override
    public boolean update(User DTO) throws SQLException {
        return false;
    }

    @Override
    public List<User> getAll() throws SQLException {
        ResultSet rs = CrudUtil.executeQuery("SELECT * FROM User");

        List<User> items = new ArrayList<>();

        while(rs.next()){
            String id = rs.getString("user_id");
            String userName = rs.getString("username");
            String password = rs.getString("password");
            String role = rs.getString("role");

            User user =new User (Integer.parseInt(id),userName,password,role);
            items.add(user);
        }
        return items;
    }

    @Override
    public boolean delete(int user_id) throws SQLException {
        return CrudUtil.execute(
                "DELETE FROM User WHERE user_id = ?",
                user_id
        );
    }

    @Override
    public List<User> search(String search) throws SQLException {
        return List.of();
    }

    @Override
    public int getUserCount() throws SQLException {
        ResultSet rs = CrudUtil.executeQuery("SELECT COUNT(*) FROM User");

        int count = 0;

        if(rs.next()) {
            return count = rs.getInt("COUNT(*)");
        }

        return count;
    }
}

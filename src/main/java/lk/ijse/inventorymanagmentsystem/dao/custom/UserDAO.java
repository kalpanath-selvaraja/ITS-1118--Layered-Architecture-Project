package lk.ijse.inventorymanagmentsystem.dao.custom;

import lk.ijse.inventorymanagmentsystem.dao.CrudDAO;
import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;
import lk.ijse.inventorymanagmentsystem.dto.UserDTO;
import lk.ijse.inventorymanagmentsystem.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    public User login(String username, String password) throws SQLException ;

    boolean delete(int user_id) throws SQLException;

    int getUserCount() throws SQLException;
}

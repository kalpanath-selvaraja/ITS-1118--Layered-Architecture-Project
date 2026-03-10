package lk.ijse.inventorymanagmentsystem.bo.custom;

import lk.ijse.inventorymanagmentsystem.bo.SuperBO;
import lk.ijse.inventorymanagmentsystem.dao.custom.UserDAO;
import lk.ijse.inventorymanagmentsystem.dto.UserDTO;

import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {
    UserDTO login(String username, String password) throws SQLException;

    List<UserDTO> getAllUsers() throws SQLException;

    boolean deleteUser(int user_id) throws SQLException;

    boolean addUser(UserDTO userDTO) throws SQLException;

    int getUserCount() throws SQLException;
}

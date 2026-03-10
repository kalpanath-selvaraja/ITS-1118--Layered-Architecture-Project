package lk.ijse.inventorymanagmentsystem.bo.custom.impl;

import lk.ijse.inventorymanagmentsystem.bo.custom.UserBO;
import lk.ijse.inventorymanagmentsystem.dao.DAOFactory;
import lk.ijse.inventorymanagmentsystem.dao.custom.UserDAO;
import lk.ijse.inventorymanagmentsystem.dto.UserDTO;
import lk.ijse.inventorymanagmentsystem.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {

    private final UserDAO userDAO =
            (UserDAO) DAOFactory.getInstance().getDAOTypes(DAOFactory.DAOTypes.USER);

    @Override
    public UserDTO login(String username, String password) throws SQLException {

        User user = userDAO.login(username, password);
        return (user != null) ? new UserDTO(user) : null;
    }

    @Override
    public List <UserDTO> getAllUsers() throws SQLException {

        List<User> user = userDAO.getAll();

        List <UserDTO> userDTOS = new ArrayList<>();
        for (User user1 : user) {
            userDTOS.add(new UserDTO(user1));
        }

        return userDTOS ;
    }

    @Override
    public boolean deleteUser(int user_id) throws SQLException {
        return userDAO.delete(user_id);
    }

    @Override
    public boolean addUser(UserDTO userDTO) throws SQLException {

        return userDAO.save(new User(userDTO));

    }

    @Override
    public int getUserCount() throws SQLException {
        return userDAO.getUserCount();
    }

}

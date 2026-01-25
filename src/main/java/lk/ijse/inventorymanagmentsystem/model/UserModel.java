/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.inventorymanagmentsystem.dto.UserDTO;
import lk.ijse.inventorymanagmentsystem.util.CrudUtil;

/**
 *
 * @author kalpanath
 */


        
public class UserModel {
    UserDTO userDTO ;
    
    

    public  UserDTO login( String username, String password) throws SQLException {
        ResultSet rs = CrudUtil.execute(
            "SELECT  user_id,username, password, Role FROM User WHERE username = ? AND password = ?", 
            username, password
        );

        if(rs.next()) {
            String user = rs.getString("username");
            String pass = rs.getString("password");
            String userId = rs.getString("user_id");
            String Role = rs.getString("Role");
            
            System.out.println(userId);
            System.out.println(user);
            System.out.println(pass);


            // Return a UserDTO object with all relevant info
            return new UserDTO( Integer.parseInt(userId) ,user, pass ,Role);
            
        } else {
            // Invalid login
            return null;
        }
    }
    
    public List<UserDTO> getAllUsers() throws SQLException{
        ResultSet rs = CrudUtil.executeQuery("SELECT * FROM User");
        
        List<UserDTO> items = new ArrayList<>();
        
        while(rs.next()){
            String id = rs.getString("user_id");
            String userName = rs.getString("username");
            String password = rs.getString("password");
            String role = rs.getString("role");
            
            userDTO =new UserDTO (Integer.parseInt(id),userName,password,role);
            items.add(userDTO);
        }
        return items;
    }  
    
    public boolean deleteUser(int userId) throws SQLException {
        return CrudUtil.execute(
            "DELETE FROM User WHERE user_id = ?",
            userId
        );
    }
    
    public boolean addUsers(UserDTO userDTO) throws SQLException{
        boolean result = CrudUtil.execute("INSERT INTO User (username, password, role) VALUES (?,?,?)", 
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getRole()
                
                
                );
        
        return result;
    }

}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.inventorymanagmentsystem.dto.EmployeeDTO;
import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;

/**
 *
 * @author kalpanath
 * 
 * 
 */




public class EmployeeModel {
    
    
    
    public static int getEmployeeIdByUserId(int userId) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT emp_id FROM Employee WHERE user_id=?", userId);

        if (rs.next()) { 
            return rs.getInt("emp_id");
        }
        
        return 0 ;
    }
    
    public  boolean addEmployeeFromUser(int userId, String empId) throws SQLException {
        return CrudUtil.execute(
            "UPDATE Employee SET user_id = ? WHERE emp_id = ?",
            userId , empId
        );
    }
    
    public int checkUSerId(int id) throws SQLException{
        ResultSet rs = CrudUtil.execute("SELECT user_id FROM Employee WHERE user_id = ?", id);
        
        int result = -1;
        
        if(rs.next()){
            result = rs.getInt("user_id");
            return result;
        }
        
        return result;
    }
    
    public List<EmployeeDTO> getAllEmployee() throws SQLException{
        ResultSet rs = CrudUtil.execute("SELECT * FROM Employee");
        
        
        List<EmployeeDTO> items = new ArrayList<>();

       
        
        while(rs.next()){
            String id = rs.getString("emp_id");
            String userName = rs.getString("name");
            String contact = rs.getString("contact");
            int userId = rs.getInt("user_id");

            EmployeeDTO  emp = new EmployeeDTO(  Integer.parseInt(id),userName,contact,userId);
            items.add(emp);
        }
        return items;
        
    }
    
    public boolean removeUserEmployee(int id) throws SQLException{
        
        boolean rs = CrudUtil.execute("UPDATE Employee SET user_id = null WHERE user_id =?", id);
        
        return rs;
    }
    
    public boolean addEmployee(EmployeeDTO employeeDTO) throws SQLException {

        boolean result = CrudUtil.execute(
            "INSERT INTO Employee (name, contact) VALUES (?,?)",
            employeeDTO.getEmpName(),
            employeeDTO.getEmpContact()
            
        );

        return result;
    }
 
}

package lk.ijse.inventorymanagmentsystem.dao.custom.impl;

import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;
import lk.ijse.inventorymanagmentsystem.dao.custom.EmployeeDAO;
import lk.ijse.inventorymanagmentsystem.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public boolean save(Employee DTO) throws SQLException {
        boolean result = CrudUtil.execute(
                "INSERT INTO Employee (name, contact) VALUES (?,?)",
                DTO.getEmpName(),
                DTO.getEmpContact()

        );

        return result;
    }

    @Override
    public boolean update(Employee DTO) throws SQLException {
        return false;
    }

    @Override
    public List<Employee> getAll() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Employee");
        List<Employee> items = new ArrayList<>();

        while(rs.next()){
            String id = rs.getString("emp_id");
            String userName = rs.getString("name");
            String contact = rs.getString("contact");
            int userId = rs.getInt("user_id");

            Employee  emp = new Employee(  Integer.parseInt(id),userName,contact,userId);
            items.add(emp);
        }
        return items;
    }

    @Override
    public List<Employee> search(String search) throws SQLException {
        return List.of();
    }


    /// UNIQUE METHODS ///////////////

    public int getEmployeeIdByUserId(int userId) throws SQLException {
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



    public boolean removeUserEmployee(int id) throws SQLException{

        boolean rs = CrudUtil.execute("UPDATE Employee SET user_id = null WHERE user_id =?", id);

        return rs;
    }


}

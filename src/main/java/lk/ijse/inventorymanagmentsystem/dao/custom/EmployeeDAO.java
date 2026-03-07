package lk.ijse.inventorymanagmentsystem.dao.custom;

import lk.ijse.inventorymanagmentsystem.dao.CrudDAO;
import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;
import lk.ijse.inventorymanagmentsystem.dto.EmployeeDTO;
import lk.ijse.inventorymanagmentsystem.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EmployeeDAO extends CrudDAO<Employee> {
    public int getEmployeeIdByUserId(int userId) throws SQLException;

    public boolean addEmployeeFromUser(int userId, String empId) throws SQLException ;

    public int checkUSerId(int id) throws SQLException;

    public boolean removeUserEmployee(int id) throws SQLException ;

}

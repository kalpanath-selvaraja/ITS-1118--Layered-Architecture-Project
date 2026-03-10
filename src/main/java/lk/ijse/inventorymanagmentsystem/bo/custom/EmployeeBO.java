package lk.ijse.inventorymanagmentsystem.bo.custom;

import lk.ijse.inventorymanagmentsystem.bo.SuperBO;
import lk.ijse.inventorymanagmentsystem.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException;

    List<EmployeeDTO> getAllEmployee() throws SQLException;

    int getEmployeeId(int userId)throws  SQLException;

    boolean addEmployeeByUser(int userId, String empId) throws SQLException;

    int checkUserId(int userId) throws SQLException;

    boolean removeuser(int userId) throws SQLException;
}

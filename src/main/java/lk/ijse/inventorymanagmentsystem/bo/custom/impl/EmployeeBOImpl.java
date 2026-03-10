package lk.ijse.inventorymanagmentsystem.bo.custom.impl;

import lk.ijse.inventorymanagmentsystem.bo.custom.EmployeeBO;
import lk.ijse.inventorymanagmentsystem.dao.DAOFactory;
import lk.ijse.inventorymanagmentsystem.dao.custom.EmployeeDAO;
import lk.ijse.inventorymanagmentsystem.dto.EmployeeDTO;
import lk.ijse.inventorymanagmentsystem.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAOTypes(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException{
        return employeeDAO.save(new Employee(employeeDTO));
    }

    @Override
    public List<EmployeeDTO> getAllEmployee() throws SQLException{

        List<Employee> employees =  employeeDAO.getAll();

        List<EmployeeDTO> employeeDTO = new ArrayList<>();
        for (Employee employee : employees) {
            employeeDTO.add(new EmployeeDTO(employee));
        }

        return employeeDTO;
    }

    @Override
    public int getEmployeeId(int userId)throws  SQLException{
        return employeeDAO.getEmployeeIdByUserId(userId);
    }

    @Override
    public boolean addEmployeeByUser(int userId, String empId) throws SQLException{
        return employeeDAO.addEmployeeFromUser(userId,empId);
    }

    @Override
    public int checkUserId(int userId) throws SQLException{
        return employeeDAO.checkUserId(userId);
    }

    @Override
    public boolean removeuser(int userId) throws SQLException{
        return employeeDAO.removeUserEmployee(userId);
    }

}

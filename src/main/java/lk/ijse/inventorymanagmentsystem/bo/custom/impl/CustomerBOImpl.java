package lk.ijse.inventorymanagmentsystem.bo.custom.impl;

import lk.ijse.inventorymanagmentsystem.bo.custom.CustomerBO;
import lk.ijse.inventorymanagmentsystem.dao.DAOFactory;
import lk.ijse.inventorymanagmentsystem.dao.custom.CustomerDAO;
import lk.ijse.inventorymanagmentsystem.dto.CustomerDTO;
import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;
import lk.ijse.inventorymanagmentsystem.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO cusDAO = (CustomerDAO) DAOFactory.getInstance().getDAOTypes(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public  int saveCustomer(CustomerDTO customerDTO) throws SQLException {
       return cusDAO.saveandReturnId(new Customer(customerDTO.getName(),customerDTO.getContact()));
    }

    @Override
    public CustomerDTO searchCustomer(String contact)throws SQLException{

       return new CustomerDTO(cusDAO.searchByContact(contact));

    }

    @Override
    public boolean updateCustomer(CustomerDTO DTO) throws SQLException{
        return cusDAO.update(new  Customer(DTO));

    }

    @Override
    public List<CustomerDTO> getAllCustomers() throws SQLException {

        List<Customer> customerList = cusDAO.getAll();

        List<CustomerDTO> newCuslist = new ArrayList<>();

        for(Customer customer : customerList){
            newCuslist.add(new CustomerDTO(customer.getId(),customer.getName(),customer.getContact()));
        }

        return newCuslist;
    }



}

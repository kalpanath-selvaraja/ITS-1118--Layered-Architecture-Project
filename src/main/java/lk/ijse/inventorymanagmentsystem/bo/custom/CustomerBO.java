package lk.ijse.inventorymanagmentsystem.bo.custom;

import lk.ijse.inventorymanagmentsystem.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO{
    public  int saveCustomer(CustomerDTO customerDTO) throws SQLException;

    public Boolean checkAndInsertCustomer(String name, String contact)throws SQLException;

    public CustomerDTO searchCustomer(String contact)throws SQLException;

    public boolean updateCustomer(CustomerDTO dto) throws SQLException;

    public int getCustomerID(String contact) throws SQLException;

    public List<CustomerDTO> getAllCustomers() throws SQLException;
}

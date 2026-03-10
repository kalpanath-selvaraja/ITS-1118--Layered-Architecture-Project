package lk.ijse.inventorymanagmentsystem.dao.custom;


import lk.ijse.inventorymanagmentsystem.dao.CrudDAO;
import lk.ijse.inventorymanagmentsystem.entity.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
    public Customer searchByContact(String contact)throws SQLException;

    public int saveandReturnId(Customer cus) throws SQLException ;

    Integer getIdByContact(String contact) throws SQLException;
}

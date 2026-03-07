package lk.ijse.inventorymanagmentsystem.dao.custom.impl;

import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;
import lk.ijse.inventorymanagmentsystem.dao.custom.CustomerDAO;
import lk.ijse.inventorymanagmentsystem.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public Customer searchByContact(String contact)throws SQLException{

        ResultSet rs =  CrudUtil.execute("SELECT * FROM Customer WHERE contact=?", Integer.valueOf(contact));

        Customer cus = null;

        if(rs.next()){
            int id = rs.getInt("cus_id");
            String name= rs.getString("name");
            int contactNumber = rs.getInt("contact");

            cus = new Customer(id , name , String.valueOf(contactNumber));
        }
        return cus;
    }

    @Override
    public int saveandReturn(Customer cus) throws SQLException {

        return CrudUtil.executeAndReturnId("INSERT INTO Customer (name, contact) VALUES (?,?)",
                cus.getName(), cus.getContact());
    }

    @Override
    public boolean update(Customer cus) throws SQLException{
        boolean result = CrudUtil.execute("UPDATE Customer  SET name =?, contact=? WHERE cus_id = ?",
                cus.getName() ,cus.getContact(),cus.getId());

        return result;
    }

    @Override
    public List<Customer> getAll() throws SQLException {


        ResultSet rs = CrudUtil.execute("SELECT * FROM Customer");

        List<Customer> customerList = new ArrayList<>();


        while(rs.next()) {
            int id = rs.getInt("cus_id");
            String name = rs.getString("name");
            String contact = rs.getString("contact");




            Customer customer = new Customer(id, name, contact);
            customerList.add(customer);
        }

        return customerList;
    }

    @Override
    public boolean save(Customer DTO) throws SQLException {
        return false;
    }
}

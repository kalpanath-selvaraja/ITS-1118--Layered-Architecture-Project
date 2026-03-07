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


        int result = CrudUtil.executeAndReturnId("INSERT INTO Customer (name, contact) VALUES (?,?)",
                customerDTO.getName(), customerDTO.getContact());

        return result;


    }

    @Override
    public Boolean checkAndInsertCustomer(String name, String contact)throws SQLException{

        ResultSet rs = CrudUtil.execute("SELECT name, contact FROM Customer WHERE name = ?", name);

        if(rs.next()){
            String Currrentname = rs.getString("name");
            String Currrentcontact = rs.getString("contact");
            if(Currrentname.matches(name)){
                if(Currrentcontact.matches(contact)){
                    return true;


                }
                CrudUtil.execute("INSERT INTO Customer (name, contact) VALUES (?, ?)",Currrentname,Currrentcontact);

            }

            return false;
        }else{
            return null;
        }


    }

    @Override
    public CustomerDTO searchCustomer(String contact)throws SQLException{

        ResultSet rs =  CrudUtil.execute("SELECT * FROM Customer WHERE contact=?", Integer.valueOf(contact));

        CustomerDTO cusDTO = null;

        if(rs.next()){
            int id = rs.getInt("cus_id");
            String name= rs.getString("name");
            int contactNumber = rs.getInt("contact");

            cusDTO = new CustomerDTO(id , name , String.valueOf(contactNumber));
        }
        return cusDTO;
    }

    @Override
    public boolean updateCustomer(CustomerDTO DTO) throws SQLException{
        boolean result = CrudUtil.execute("UPDATE Customer  SET name =?, contact=? WHERE cus_id = ?",
                DTO.getName(),DTO.getContact(),DTO.getId());

        return result;
    }

    @Override
    public int getCustomerID(String contact) throws SQLException {

        int con = Integer.parseInt(contact);

        String sql = "SELECT cus_id FROM Customer WHERE contact = ?";
        ResultSet rs = CrudUtil.execute(sql,con ); // Use executeQuery, not executeUpdate

        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1; // or throw exception if customer not found
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

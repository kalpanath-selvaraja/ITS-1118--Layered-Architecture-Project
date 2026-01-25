package lk.ijse.inventorymanagmentsystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.inventorymanagmentsystem.dto.CustomerDTO;
import lk.ijse.inventorymanagmentsystem.util.CrudUtil;

/**
 *
 * @author kalpanath
 */
public class CustomerModel {
    
    public  int saveCustomer(CustomerDTO customerDTO) throws SQLException{
        
        
        int result = CrudUtil.executeAndReturnId("INSERT INTO Customer (name, contact) VALUES (?,?)", 
                customerDTO.getName(), customerDTO.getContact());
        
        return result;
        

    }
    
    
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
    
    public boolean updateCustomer(String name, String contact, int id) throws SQLException{
        boolean result = CrudUtil.execute("UPDATE Customer  SET name =?, contact=? WHERE cus_id = ?", 
                name,contact,id);
        
        return result;
    }
    
    public int getCustomerID(String contact) throws SQLException {
        
        int con = Integer.parseInt(contact);
        
        String sql = "SELECT cus_id FROM Customer WHERE contact = ?";
        ResultSet rs = CrudUtil.execute(sql,con ); // Use executeQuery, not executeUpdate
        
        if (rs.next()) {
            return rs.getInt(1);
        }
            return -1; // or throw exception if customer not found
    }
    
    
    public List<CustomerDTO> getAllCustomers() throws SQLException {
    
        
        ResultSet rs = CrudUtil.execute("SELECT * FROM Customer");
        
        List<CustomerDTO> customerList = new ArrayList<>();
        

        while(rs.next()) {
            int id = rs.getInt("cus_id");
            String name = rs.getString("name");
            String contact = rs.getString("contact");
            
            System.out.println(id);
            System.out.println(name);
            System.out.println(contact);

            
            CustomerDTO customerDTO = new CustomerDTO(id, name, contact);
            customerList.add(customerDTO);
        }
        
        return customerList;
    }
}

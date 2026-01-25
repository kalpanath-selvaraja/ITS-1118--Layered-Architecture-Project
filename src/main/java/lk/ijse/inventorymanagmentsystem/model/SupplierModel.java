/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import lk.ijse.inventorymanagmentsystem.dto.ItemDTO;

import lk.ijse.inventorymanagmentsystem.dto.SupplierDTO;
import lk.ijse.inventorymanagmentsystem.util.CrudUtil;


/**
 *
 * @author kalpanath
 */
public class SupplierModel {
    
    
    
    public List<SupplierDTO> searchSupplier(String name) throws SQLException {

        String SQL = "SELECT * FROM Supplier WHERE name LIKE ?";

        String searchValue = "%" + name + "%";

        ResultSet rs = CrudUtil.execute(SQL, searchValue);

        List<SupplierDTO> items = new ArrayList<>();

        while (rs.next()) {
            int sup_id = rs.getInt("supplier_id");
            String supName = rs.getString("name");
            String eMail = rs.getString("email");
            String contact = rs.getString("contact");

            items.add(new SupplierDTO(sup_id, supName, eMail, contact));
        }

        return items;
    }
    
    public List<SupplierDTO>  getSupplier () throws SQLException{
        
        ResultSet rs = CrudUtil.execute("SELECT * FROM Supplier");
        
        List<SupplierDTO> items = new ArrayList<>();
        
        while(rs.next()){
            int sup_id = rs.getInt("supplier_id");
            String supName = rs.getString("name");
            String eMail = rs.getString("email");
            String contact = rs.getString("contact");
            
            items.add(new SupplierDTO(sup_id, supName, eMail, contact));
            
        }
        
        return items;
        
    }

    
    public boolean updateSupplierWithItems(SupplierDTO supDTO, List<ItemDTO> cartItems) throws SQLException {
        
        boolean rs = CrudUtil.execute(
            "UPDATE Supplier SET name = ?, email = ?, contact = ? WHERE supplier_id = ?",
            supDTO.getSupplier_name(),
            supDTO.getEmail(),
            supDTO.getContact(),
            supDTO.getSupplier_id()
        );

        if (!rs) return false; // Stop if supplier update fails

        // Clear old item associations
        CrudUtil.execute("DELETE FROM SupplierItem WHERE supplier_id = ?", supDTO.getSupplier_id());

        
        for(ItemDTO item : cartItems) {
            CrudUtil.execute(
                "INSERT INTO SupplierItem(supplier_id, item_id) VALUES(?, ?)",
                supDTO.getSupplier_id(),
                item.getItemId()
            );
        }

        return true;
    }


    public boolean checkSupsplier(int Id) throws SQLException{
        
        ResultSet rs = CrudUtil.execute("SELECT supplier_id FROM Supplier WHERE supplier_id = ?; ", Id);
        
        int result  = rs.getInt("supplier_id");
        
        return result >0;
    }
    
    public boolean checkSupplierconnected(int Id) throws SQLException{
        
        ResultSet rs = CrudUtil.execute("SELECT EXISTS(SELECT 1 FROM Item WHERE Supplier_id = ? )", Id);
        if (rs.next()) {
            return rs.getInt(1) == 1;
        }
        return false;
    }
    
    public boolean checkSupplierHasItems(int supplierId) throws SQLException {
        // Count items linked to this supplier
        ResultSet rs = CrudUtil.execute(
            "SELECT COUNT(*) AS total FROM SupplierItem WHERE supplier_id = ?", supplierId
        );

        if (rs.next()) {
            int count = rs.getInt("total");
            return count > 0;  // true if there are items
        }

        return false;
    }
    
    public boolean deleteSupplier(int supplierId) throws SQLException {
        // Check if supplier has items
        if (checkSupplierHasItems(supplierId)) {
            System.out.println("Cannot delete: Supplier still has items.");
            return false;
        }

        // Safe to delete
        boolean result = CrudUtil.execute(
            "DELETE FROM Supplier WHERE supplier_id = ?", supplierId
        );

        return result;
    }

    public int getSupplierID(String name) throws SQLException{
        ResultSet rs = CrudUtil.execute("SELECT supplier_id FROM Supplier WHERE name = ?", name);
        int result = 0;
        if(rs.next()){
            result  = rs.getInt("supplier_id");
             return result;
        }
        return 0;

       
    }
    
    public boolean checkSupplierexsists(String name , String email ,String contact) throws SQLException{
        
        ResultSet result = CrudUtil.execute("SELECT contact FROM Supplier WHERE contact = ? ", contact);
        
        if(result.next()){
           new Alert(Alert.AlertType.INFORMATION , "Sorry this Supplier already exsists!").show();
           return false;
        }
        
        boolean result1 = CrudUtil.execute("INSERT INTO Supplier(name , email, contact)VALUES (?,?,?)", name, email, contact);
        
        return result1;

    }
    
    public List<ItemDTO> getItemsForItem(int  ID) throws SQLException{
        ResultSet rs = CrudUtil.execute("SELECT i.item_id, i.name, i.unit_price, i.quantity, i.warranty_id " +
                     "FROM Item i " +
                     "JOIN SupplierItem si ON i.item_id = si.item_id " +
                     "WHERE si.supplier_id = ?", ID);
        
         List<ItemDTO> items = new ArrayList<>();
        
        while(rs.next()){
            int itemId = rs.getInt("item_id");
            String supName = rs.getString("name");
            
            
            items.add(new ItemDTO(itemId, supName ));
            
        }
        
        return items;
    }
}

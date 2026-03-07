/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lk.ijse.inventorymanagmentsystem.dto.ItemDTO;
import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;

/**
 *
 * @author kalpanath
 */
public class ItemViewModel {


    public List<ItemDTO> searchItem(String itemName)throws SQLException{
        
            ResultSet rs = CrudUtil.execute(

                "SELECT i.item_id, " +
                "i.name , " +
                "i.unit_price, " +
                "i.quantity, " +
                "w.months AS WarrantyMonths, " +
                "s.name AS SupplierName " +  // no comma here
                "FROM Item i " +
                "LEFT JOIN Warranty w ON i.warranty_id = w.warranty_id " +
                "LEFT JOIN SupplierItem si ON i.item_id = si.item_id " +
                "LEFT JOIN Supplier s ON si.supplier_id = s.supplier_id " +
                "WHERE i.name LIKE ?",
                "%" + itemName + "%"
            );


        
        List<ItemDTO> items = new ArrayList<>();

        
        while(rs.next()){
            int itemId = rs.getInt("item_id");
            String itName = rs.getString("name");
            int quantity = rs.getInt("quantity");
            int unitPrice = rs.getInt("unit_price");
            int months = rs.getInt("WarrantyMonths");
            String supName = rs.getString("SupplierName");
            
            items.add( new ItemDTO(itemId,itName, quantity,unitPrice,months,supName));
        }
        return items;
    }
    
    
    public List<ItemDTO> getItems()throws SQLException{
        
        
        ResultSet rs = CrudUtil.execute(
            "SELECT i.item_id, " +
                "i.name , " +
                "i.unit_price, " +
                "i.quantity, " +
                "w.months AS WarrantyMonths, " +
                "s.name AS SupplierName " +  // no comma here
                "FROM Item i " +
                "LEFT JOIN Warranty w ON i.warranty_id = w.warranty_id " +
                "LEFT JOIN SupplierItem si ON i.item_id = si.item_id " +
                "LEFT JOIN Supplier s ON si.supplier_id = s.supplier_id " 
        );


        List<ItemDTO> itemList = new ArrayList();
        
        while(rs.next()){
            int itemId = rs.getInt("item_id");
            String itName = rs.getString("name");
            int quantity = rs.getInt("quantity");
            int unitPrice = rs.getInt("unit_price");
            int months = rs.getInt("WarrantyMonths");
            String supName = rs.getString("SupplierName");
            
            ItemDTO ivDTO =  new ItemDTO(itemId,itName, quantity,unitPrice,months,supName);
            itemList.add(ivDTO);
        }
        
        return itemList;
        
    }
    
    public int getWarrantyIdByMonths(int months) throws SQLException {
        
        ResultSet rs = CrudUtil.execute("SELECT warranty_id FROM Warranty WHERE months = ?", months);
        if(rs.next()) {
            return rs.getInt("warranty_id");
        }
        return 0; 
    }



    
    public boolean updateItems(ItemDTO itemDTO , int warrantyId) throws SQLException{
        
       
        
         Object warrantyParam = (warrantyId == 0) ? null : warrantyId;
        
        boolean result = CrudUtil.execute(
                "UPDATE Item SET name = ? , "
                + "unit_price = ? , "
                + "quantity = ?, "
                + "warranty_id = ? "
                + "WHERE item_id = ?", 
                
                itemDTO.getItemName(),
                itemDTO.getUnitPrice(),
                itemDTO.getQuantity(),
                warrantyParam,
                itemDTO.getItemId()
                );
        
        
            CrudUtil.execute(
                    "INSERT INTO "
                            + "SupplierItem (supplier_id, item_id) " 
                            + "VALUES (? , ?) " 
                            + "ON DUPLICATE KEY UPDATE supplier_id = ?;" , itemDTO.getSupplierId() ,
                            itemDTO.getItemId(),     itemDTO.getSupplierId());

            
        
        
        return result;
        
    }
    
    public boolean addItems(ItemDTO itemDTO, int warrantyId) throws SQLException {

        int warrantyParam = (warrantyId == 0) ? null : warrantyId;

        int result = CrudUtil.executeAndReturnId(
                "INSERT INTO Item (name, unit_price, quantity, warranty_id) "
              + "VALUES (?, ?, ?, ?)",

                itemDTO.getItemName(),
                itemDTO.getUnitPrice(),
                itemDTO.getQuantity(),
                warrantyParam
        );
        boolean Added = false;
        if(result != -1 ){
             Added = CrudUtil.execute(
                "INSERT INTO SupplierItem (supplier_id, item_id) "
              + "VALUES (?, ?) "
              + "ON DUPLICATE KEY UPDATE supplier_id = ?",

                itemDTO.getSupplierId(),
                result,
                itemDTO.getSupplierId()
            );
        }

        

        return Added;
    }
    
    

    
}

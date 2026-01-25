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
import lk.ijse.inventorymanagmentsystem.util.CrudUtil;

/**
 *
 * @author kalpanath
 */
public class ItemModel {
    public List<ItemDTO> searchItem(String itemName)throws SQLException{
        
        
        
        String sql =
            "SELECT i.item_id, " +
            "i.name, " +
            "i.unit_price, " +
            "i.quantity, " +
            "w.months AS WarrantyMonths " +
            "FROM Item i " +
            "LEFT JOIN Warranty w ON i.warranty_id = w.warranty_id " +
            "WHERE i.name LIKE ?";
        
        
        
            String searchValue = "%" + itemName + "%";

            ResultSet rs = CrudUtil.execute(sql, searchValue);

         


        ItemDTO itemDTO = null;
        
        List<ItemDTO> items = new ArrayList<>();

        
        while(rs.next()){
            int itemId = rs.getInt("item_id");
            String itName = rs.getString("name");
            int quantity = rs.getInt("quantity");
            int unitPrice = rs.getInt("unit_price");
            int months = rs.getInt("WarrantyMonths");
            
            items.add( new ItemDTO(itemId,itName, quantity,unitPrice,months));
        }
        return items;
    }
    
    public List<ItemDTO> getItems()throws SQLException{
        
        
        ResultSet rs = CrudUtil.execute("SELECT " +
            "i.item_id," +
            " i.name, " +
            " i.unit_price, " +
            " i.quantity, " +
            " i.is_active, " +
            " w.months " +
            " FROM Item i JOIN Warranty w ON i.item_id = w.item_id" );

        List<ItemDTO> itemList = new ArrayList();
        
        while(rs.next()){
            int itemID = rs.getInt("item_id");
            String name = rs.getString("name");
            double price =rs.getDouble("unit_price");
            int warranty  =rs.getInt("months");
            int qty = rs.getInt("quantity");

            ItemDTO itemDTO = new ItemDTO(itemID, name, qty, price,warranty);
            itemList.add(itemDTO);
        }
        
        return itemList;
        
    }
    
    public boolean decreseItemQty(int itemId, int qty) throws SQLException {
       boolean result = CrudUtil.execute("UPDATE Item SET quantity = quantity - ? WHERE item_id = ? AND quantity >= ?", qty, itemId, qty);
       return result;
    }

    public boolean deleteItems(ItemDTO dto)throws SQLException{
        
        boolean result = CrudUtil.execute("UPDATE Item SET is_active = FALSE WHERE itemId = ?", dto.getItemId());
        return result;
    }
    
    public int getLowStocksNumber() throws SQLException{
        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) AS Low_Stock FROM Item WHERE quantity <= 5 AND quantity > 0");
        
        int qty = 0;
        
        if(rs.next()){
            qty = rs.getInt("Low_Stock");
            System.out.println(qty);

            return qty;
        }
        
        return qty;
    }

}


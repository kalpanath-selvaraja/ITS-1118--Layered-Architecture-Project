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
import lk.ijse.inventorymanagmentsystem.dto.WarrantyDTO;
import lk.ijse.inventorymanagmentsystem.util.CrudUtil;

/**
 *
 * @author kalpanath
 */
public class WarrantyModel {
    public List<WarrantyDTO>  getWarrantyMonths() throws SQLException{
        ResultSet rs = CrudUtil.execute("SELECT months FROM Warranty" );
        List<WarrantyDTO> warrantyList = new ArrayList();
        
        while(rs.next()){
            int months = rs.getInt("months");
            
            WarrantyDTO wDTO = new WarrantyDTO (months);
            warrantyList.add(wDTO);
            
        }
        return warrantyList;
    }
    
    public WarrantyDTO getWarrantymonths(int id) throws SQLException{
        ResultSet rs = CrudUtil.execute(
        "SELECT i.warranty_id, w.months " +
        "FROM Item i "+
        "JOIN Warranty w "+
        "ON i.warranty_id = w.warranty_id "+
        "WHERE i.item_id = ? ");
        
        while(rs.next()){
            
            String months = rs.getString("months");
            WarrantyDTO wDTO = new WarrantyDTO (Integer.parseInt(months));
           
            return wDTO;
        }
        return null;
        
        
    }

    public List<WarrantyDTO> getWarrantyTable() throws SQLException{
        
        ResultSet rs = CrudUtil.execute(
                
                "SELECT "
              + "o.order_id, "
              + "oi.order_item_id, "
              + "o.date, "
              + "oi.unit_price, "
              + "oi.warranty_status, "
              + "i.name "
              + "FROM Orders o "
              + "JOIN OrderItem oi ON o.order_id = oi.order_id "
              + "JOIN Item i ON oi.item_id = i.item_id");
        
         List<WarrantyDTO> warrantyList = new ArrayList();
        
        while(rs.next()){
            
            String orderItemId = rs.getString("order_item_id");
            String orderId = rs.getString("order_id");
            String date = rs.getString("date");
            String price = rs.getString("unit_price");
            String status = rs.getString("warranty_status");
            String name = rs.getString("name");
            
            WarrantyDTO warrantydto = new WarrantyDTO(orderItemId , orderId, date, price, status , name);
            warrantyList.add(warrantydto);
            
        }
        return warrantyList;
        
    }
    
    public List<WarrantyDTO>  serarchOrders(String id) throws SQLException{
        
        ResultSet rs = CrudUtil.executeQuery(
                " SELECT  "
                +"oi.order_item_id, "        
                + "o.order_id,"
                + " o.date,"
                + " i.name,"
                + " oi.unit_price,"
                + " oi.warranty_status FROM Orders o INNER JOIN OrderItem oi"
                + " ON o.order_id = oi.order_id INNER JOIN Item i  "
                + "ON oi.item_id = i.item_id WHERE o.order_id = ? ", id);
        
        
        List<WarrantyDTO> warrantyList = new ArrayList();
       
        while(rs.next()){
            
            String orderItemId = rs.getString("order_item_id");
            String orderId = rs.getString("order_id");
            String date = rs.getString("date");
            String price = rs.getString("unit_price");
            String status = rs.getString("warranty_status");
            String name = rs.getString("name");
            
            WarrantyDTO warrantydto = new WarrantyDTO(orderItemId, orderId, date, price, status , name);
            warrantyList.add(warrantydto);
        }
        return warrantyList;

    }
    
    public void applyWarrantyStatus() throws SQLException{
        CrudUtil.execute(
                "UPDATE OrderItem oi " +
                "JOIN Orders o ON oi.order_id = o.order_id " +
                "JOIN Item i ON oi.item_id = i.item_id " +
                "LEFT JOIN Warranty w ON i.warranty_id = w.warranty_id " +
                "SET oi.warranty_status = 'Expired' " +
                "WHERE w.warranty_id IS NOT NULL " +
                "AND DATE_ADD(o.date, INTERVAL w.months MONTH) < CURDATE() " +
                "AND oi.warranty_status = 'Active'");
    }
    
    public String claimWarranty(String orderItemId) throws SQLException{
        ResultSet rs = CrudUtil.execute("SELECT warranty_status FROM OrderItem WHERE order_item_id = ?", orderItemId);
        String status = null ;
        if(rs.next()){
            status  = rs.getString("warranty_status");
        }
        
        
        String currentStatus = null;
        
        if("Active".equals(status) ){
            boolean result  = CrudUtil.execute("UPDATE OrderItem SET warranty_status ='Claimed' WHERE order_item_id = ?", orderItemId);
            
            if (result == true) currentStatus = "Active";
        }
        if("Expired".equals(status) ){
             currentStatus = "Expired";
        }
        if("No Warranty".equals(status) ){
          currentStatus = "thsi Item has No Warranty";
        }
        
        if("Claimed".equals(status)) currentStatus = "Warranty has already beem calimed";
        
        return currentStatus;
    }
    
    
    public int claimedWarrantyCount() throws SQLException{
        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) AS claimed_warranty FROM OrderItem WHERE warranty_status = ?" ,"Claimed");
        
        int qty = 0;
        
        if(rs.next()){
            qty = rs.getInt("claimed_warranty");
            System.out.println("bjnkm" + qty);
            return qty;
        }
        
        return qty;
    } 
}



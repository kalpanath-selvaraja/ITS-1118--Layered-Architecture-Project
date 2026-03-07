/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.model;

/**
 *
 * @author kalpanath
 */


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import lk.ijse.inventorymanagmentsystem.dto.OrderItemDTO;
import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;

public class OrderItemModel {

    // -------------------------------------------------------------
    // 1. Save a single order item
    
    private final ItemModel itemModel = new ItemModel();
    // -------------------------------------------------------------
    public boolean saveOrderItem(OrderItemDTO dto) throws SQLException {
        
        
        String sql = "INSERT INTO OrderItem (order_id, item_id, quantity, unit_price) VALUES (?, ?, ?, ?)";

        boolean result =  CrudUtil.execute(
                sql,
                dto.getOrderID(),
                dto.getItemID(),
                dto.getQuantity(),
                dto.getUnitPrice()
        );
        
        
        int warranty = dto.getWarranty();
        if(warranty>0){
            CrudUtil.execute("UPDATE OrderItem SET warranty_status = 'Active' WHERE order_id = ?",dto.getOrderID() );
        }else{
            CrudUtil.execute("UPDATE OrderItem SET warranty_status = 'No Warranty' WHERE order_id = ?",dto.getOrderID() );

        }
        
        if(result){
            boolean decresed = itemModel.decreseItemQty(dto.getItemID(), dto.getQuantity());
            
            if(!decresed){
                throw new SQLException();
            }
        } else {
                throw new SQLException();
        }
        return true;
    }

    public ArrayList<OrderItemDTO> getItemsByOrderId(int orderId) throws SQLException {
        String sql = "SELECT * FROM OrderItem  WHERE order_id = ?";

        ResultSet rs = CrudUtil.execute(sql, orderId);
        ArrayList<OrderItemDTO> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new OrderItemDTO(
                    rs.getInt("order_id"),
                    rs.getInt("item_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("unit_price")
                    
            ));
        }

        return list;
    }

//
//    public boolean deleteItemsByOrderId(int orderId) throws SQLException {
//        String sql = "DELETE FROM OrderItem WHERE order_id = ?";
//        return CrudUtil.execute(sql, orderId);
//    }
}

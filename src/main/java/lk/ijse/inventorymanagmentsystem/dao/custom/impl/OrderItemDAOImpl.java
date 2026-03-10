package lk.ijse.inventorymanagmentsystem.dao.custom.impl;

import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;
import lk.ijse.inventorymanagmentsystem.dao.custom.OrderItemDAO;
import lk.ijse.inventorymanagmentsystem.entity.OrderItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAOImpl implements OrderItemDAO {


    @Override
    public ArrayList<OrderItem> getItemsByOrderId(int orderId) throws SQLException {

        String sql = "SELECT * FROM OrderItem  WHERE order_id = ?";

        ResultSet rs = CrudUtil.execute(sql, orderId);
        ArrayList<OrderItem> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new OrderItem(
                    rs.getInt("order_id"),
                    rs.getInt("item_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("unit_price")

            ));
        }

        return list;

    } // 1

    @Override
    public boolean save(OrderItem orderItem) throws SQLException { // 2

        return CrudUtil.execute(
                "INSERT INTO OrderItem (order_id, item_id, quantity, unit_price) VALUES (?, ?, ?, ?)",
                orderItem.getOrderID(),
                orderItem.getItemID(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice()
        );

    }


    @Override
    public boolean updateWarrantyStatus(int orderId, String status) throws SQLException {
        return CrudUtil.execute(
                "UPDATE OrderItem SET warranty_status = ? WHERE order_id = ?",
                status,
                orderId
        );
    }



    // used in a different use case
    @Override
    public boolean UpdateWarranty(String orderItemId) throws SQLException {
        boolean result  = CrudUtil.execute("UPDATE OrderItem SET warranty_status ='Claimed' WHERE order_item_id = ?", orderItemId);
        return result;
    }

    // used in a different use case
    @Override
    public String getWarrantyStatus(String orderItemId) throws SQLException {

        ResultSet rs = CrudUtil.execute("SELECT warranty_status FROM OrderItem WHERE order_item_id = ?", orderItemId);
        String status = null ;
        if(rs.next()){
            status  = rs.getString("warranty_status");
            return  status;
        }

        return status;
    }

    @Override
    public int claimedWarrantyCount() throws SQLException{
        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) AS claimed_warranty FROM OrderItem WHERE warranty_status = ?" ,"Claimed");

        int qty = 0;

        if(rs.next()){
            qty = rs.getInt("claimed_warranty");

            return qty;
        }

        return qty;
    }


    @Override
    public boolean update(OrderItem DTO) throws SQLException {
        return false;
    }

    @Override
    public List<OrderItem> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public List<OrderItem> search(String search) throws SQLException {
        return List.of();
    }
}

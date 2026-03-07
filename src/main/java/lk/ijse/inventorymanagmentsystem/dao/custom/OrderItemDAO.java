package lk.ijse.inventorymanagmentsystem.dao.custom;

import lk.ijse.inventorymanagmentsystem.dao.CrudDAO;
import lk.ijse.inventorymanagmentsystem.entity.OrderItem;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderItemDAO extends CrudDAO<OrderItem> {
    public ArrayList<OrderItem> getItemsByOrderId(int orderId) throws SQLException ;
}

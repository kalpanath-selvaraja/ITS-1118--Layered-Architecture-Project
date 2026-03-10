package lk.ijse.inventorymanagmentsystem.bo.custom;

import lk.ijse.inventorymanagmentsystem.bo.SuperBO;
import lk.ijse.inventorymanagmentsystem.dto.OrderItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderItemBO extends SuperBO {
    boolean saveOrder(OrderItemDTO orderItemDTO) throws SQLException;

    ArrayList<OrderItemDTO> getItemsByOrderId(int orderId) throws SQLException;
}

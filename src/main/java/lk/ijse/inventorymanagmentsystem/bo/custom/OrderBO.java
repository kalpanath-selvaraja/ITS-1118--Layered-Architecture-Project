package lk.ijse.inventorymanagmentsystem.bo.custom;

import lk.ijse.inventorymanagmentsystem.bo.SuperBO;
import lk.ijse.inventorymanagmentsystem.dto.OrderDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OrderBO extends SuperBO {
    public List<OrderDTO> search(int order_id)throws SQLException;

    public String getOrderCount() throws SQLException;

    public Map<String, Double> getDailySales() throws SQLException;

    public int saveAndReturnID(OrderDTO orderDTO) throws SQLException;

    public List<OrderDTO> getAll() throws SQLException;
}

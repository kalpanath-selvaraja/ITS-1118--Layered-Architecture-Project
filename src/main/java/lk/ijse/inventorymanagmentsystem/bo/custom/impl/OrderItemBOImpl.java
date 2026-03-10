package lk.ijse.inventorymanagmentsystem.bo.custom.impl;

import lk.ijse.inventorymanagmentsystem.bo.custom.OrderItemBO;
import lk.ijse.inventorymanagmentsystem.dao.DAOFactory;
import lk.ijse.inventorymanagmentsystem.dao.custom.ItemDAO;
import lk.ijse.inventorymanagmentsystem.dao.custom.OrderItemDAO;
import lk.ijse.inventorymanagmentsystem.dao.custom.impl.ItemDAOImpl;
import lk.ijse.inventorymanagmentsystem.dto.OrderItemDTO;
import lk.ijse.inventorymanagmentsystem.entity.OrderItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemBOImpl implements OrderItemBO {
    OrderItemDAO orderItemDAO = (OrderItemDAO) DAOFactory.getInstance().getDAOTypes(DAOFactory.DAOTypes.ORDER_ITEM);
    ItemDAO itemDAO = (ItemDAOImpl) DAOFactory.getInstance().getDAOTypes(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean saveOrder(OrderItemDTO orderItemDTO) throws SQLException {

        OrderItem orderItem = new OrderItem(orderItemDTO);

        boolean saved = orderItemDAO.save(orderItem);

        if (!saved) {
            throw new SQLException("Failed to save order item");
        }

        // 2. Set warranty status based on warranty value
        String warrantyStatus = (orderItemDTO.getWarranty() > 0)? "Active": "No Warranty";

        boolean warrantyUpdated = orderItemDAO.updateWarrantyStatus(orderItemDTO.getOrderID(),warrantyStatus);

        if (!warrantyUpdated) {
            throw new SQLException("Failed to update warranty status");
        }

        // 3. Decrease item quantity in inventory
        boolean decreased = itemDAO.decreseItemQty(orderItemDTO.getItemID(),orderItemDTO.getQuantity());

        if (!decreased) {
            throw new SQLException("Failed to decrease item quantity");
        }

        return true;
    }

    @Override
    public ArrayList<OrderItemDTO> getItemsByOrderId(int orderId) throws SQLException{

        List<OrderItem> orderItemsList = orderItemDAO.getItemsByOrderId(orderId);

        ArrayList<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        for (OrderItem orderItem : orderItemsList) {
             orderItemDTOList.add(new OrderItemDTO(orderItem))  ;
        }
        return orderItemDTOList;
    }



}

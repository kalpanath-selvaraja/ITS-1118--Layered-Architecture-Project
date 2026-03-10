package lk.ijse.inventorymanagmentsystem.bo.custom.impl;

import lk.ijse.inventorymanagmentsystem.bo.custom.WarrantyBO;
import lk.ijse.inventorymanagmentsystem.dao.DAOFactory;
import lk.ijse.inventorymanagmentsystem.dao.custom.OrderItemDAO;
import lk.ijse.inventorymanagmentsystem.dao.custom.QueryDAO;
import lk.ijse.inventorymanagmentsystem.dao.custom.WarrantyDAO;
import lk.ijse.inventorymanagmentsystem.dto.WarrantyDTO;
import lk.ijse.inventorymanagmentsystem.entity.Warranty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WarrantyBOImpl implements WarrantyBO {

    WarrantyDAO warrantyDAO = (WarrantyDAO) DAOFactory.getInstance().getDAOTypes(DAOFactory.DAOTypes.WARRANTY);
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAOTypes(DAOFactory.DAOTypes.QUERY);
    private final OrderItemDAO orderItemDAO =(OrderItemDAO) DAOFactory.getInstance().getDAOTypes(DAOFactory.DAOTypes.ORDER_ITEM);

    @Override
    public List<WarrantyDTO> getWarrantyMonths() throws SQLException {
        List<Warranty> warranty = warrantyDAO.getAllMonths();

        List<WarrantyDTO> warrantyList = new ArrayList();

        for(Warranty w : warranty){
            warrantyList.add(new WarrantyDTO(w));
        }

        return warrantyList;

    }

    @Override
    public List<WarrantyDTO> searchWarrantyByOrderId(String orderId) throws SQLException {
        // Business validation
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty");
        }

        // Get warranty views from QueryDAO
        List<Warranty> warrantyViews = queryDAO.serarchOrders(orderId);

        // Convert to DTOs
        List<WarrantyDTO> warrantyList = new ArrayList();

        for(Warranty w : warrantyViews){
            warrantyList.add(new WarrantyDTO(w));
        }

        return warrantyList;

    }

    @Override
    public String claimWarranty(String orderItemId) throws SQLException {
        // Business validation
        if (orderItemId == null || orderItemId.trim().isEmpty()) {
            throw new IllegalArgumentException("Order Item ID cannot be empty");
        }

        // Get current warranty status
        String status = orderItemDAO.getWarrantyStatus(orderItemId);

        // 🔍 DETAILED DEBUG - Check exactly what we're getting
        System.out.println("Raw status: [" + status + "]");
        System.out.println("Status length: " + (status != null ? status.length() : "null"));
        System.out.println("Status bytes: " + java.util.Arrays.toString(status != null ? status.getBytes() : null));

        if (status == null) {
            return "Order item not found";
        }

        status = status.trim();

        System.out.println("After trim: [" + status + "]");
        System.out.println("After trim length: " + status.length());
        System.out.println("Equals 'Claimed': " + status.equals("Claimed"));
        System.out.println("EqualsIgnoreCase 'Claimed': " + status.equalsIgnoreCase("Claimed"));

        // Business logic based on status
        switch (status) {
            case "Active":
                boolean claimed = orderItemDAO.UpdateWarranty(orderItemId);
                 if(claimed){
                    return "Active";
                 }else {
                     throw new SQLException("Failed to update warranty status");
                 }


            case "Expired":
                return "Expired";

            case "No Warranty":
                return "No Warranty";

            case "Claimed":
                return "Claimed";

            default:
                return "Unknown warranty status: " + status;
        }
    }



    @Override
    public void updateExpiredWarranties() throws SQLException {
        // This should be run as a scheduled job
         queryDAO.applyWarrantyStatus();
    }

    @Override
    public int getClaimedWarrantyCount() throws SQLException {
        return orderItemDAO.claimedWarrantyCount();
    }

    @Override
    public List<WarrantyDTO> getWarrantyTable() throws SQLException {
        List<Warranty> warrantyList = queryDAO.getWarrantyTable();

        List<WarrantyDTO> warrantyDTOList = new ArrayList();

        for(Warranty w : warrantyList){
            warrantyDTOList.add(new WarrantyDTO(w));
        }

        return warrantyDTOList;
    }


}



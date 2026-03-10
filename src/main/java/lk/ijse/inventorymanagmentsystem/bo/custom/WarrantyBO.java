package lk.ijse.inventorymanagmentsystem.bo.custom;

import lk.ijse.inventorymanagmentsystem.bo.SuperBO;
import lk.ijse.inventorymanagmentsystem.dto.WarrantyDTO;

import java.sql.SQLException;
import java.util.List;

public interface WarrantyBO extends SuperBO {
    List<WarrantyDTO> getWarrantyMonths() throws SQLException;

    List<WarrantyDTO> searchWarrantyByOrderId(String orderId) throws SQLException;

    String claimWarranty(String orderItemId) throws SQLException;

    void updateExpiredWarranties() throws SQLException;

    int getClaimedWarrantyCount() throws SQLException;

    List<WarrantyDTO> getWarrantyTable() throws SQLException;
}

package lk.ijse.inventorymanagmentsystem.bo.custom;

import lk.ijse.inventorymanagmentsystem.bo.SuperBO;
import lk.ijse.inventorymanagmentsystem.dto.CheckoutDTO;

import java.sql.SQLException;

public interface RecordSaleBO extends SuperBO {
    int processCheckout(CheckoutDTO checkoutDTO) throws SQLException;

    double getTotalSales() throws SQLException;
}

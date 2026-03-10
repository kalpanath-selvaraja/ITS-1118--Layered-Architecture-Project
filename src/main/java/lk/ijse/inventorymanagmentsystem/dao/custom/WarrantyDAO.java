package lk.ijse.inventorymanagmentsystem.dao.custom;

import lk.ijse.inventorymanagmentsystem.dao.CrudDAO;
import lk.ijse.inventorymanagmentsystem.entity.Warranty;

import java.sql.SQLException;
import java.util.List;

public interface WarrantyDAO extends CrudDAO <Warranty>{
    List<Warranty> getAllMonths() throws SQLException;

    int claimedWarrantyCount() throws SQLException;
}

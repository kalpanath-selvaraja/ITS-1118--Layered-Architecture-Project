package lk.ijse.inventorymanagmentsystem.dao.custom;

import lk.ijse.inventorymanagmentsystem.dao.CrudDAO;
import lk.ijse.inventorymanagmentsystem.entity.SupplierItem;

import java.sql.SQLException;

public interface SupplierItemDAO extends CrudDAO<SupplierItem> {
    public SupplierItem getSupplierItemCount(int supplierID) throws SQLException;
}

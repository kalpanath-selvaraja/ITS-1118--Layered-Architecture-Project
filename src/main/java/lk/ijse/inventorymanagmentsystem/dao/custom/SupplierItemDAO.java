package lk.ijse.inventorymanagmentsystem.dao.custom;

import lk.ijse.inventorymanagmentsystem.dao.CrudDAO;
import lk.ijse.inventorymanagmentsystem.dto.ItemDTO;
import lk.ijse.inventorymanagmentsystem.entity.SupplierItem;

import java.sql.SQLException;
import java.util.List;

public interface SupplierItemDAO extends CrudDAO<SupplierItem> {
    public boolean SupplierItemCount(int supplierId) throws SQLException;

    public boolean saveSupplierItem(int supplierId, List<ItemDTO> cartItems) throws SQLException ;
}

package lk.ijse.inventorymanagmentsystem.dao.custom;

import lk.ijse.inventorymanagmentsystem.dao.CrudDAO;
import lk.ijse.inventorymanagmentsystem.dto.ItemDTO;
import lk.ijse.inventorymanagmentsystem.entity.Item;
import lk.ijse.inventorymanagmentsystem.entity.SupplierItem;

import java.sql.SQLException;
import java.util.List;

public interface SupplierItemDAO extends CrudDAO<SupplierItem> {
    public boolean SupplierItemCount(int supplierId) throws SQLException;

//    public boolean saveSupplierItem(int supplierId, List<ItemDTO> cartItems) throws SQLException ;


    boolean saveItem(int supplierId, List<Item> cartItems) throws SQLException;

    boolean saveSupplierItem(int supplier_id, int item_id) throws SQLException;

    boolean delete(int supplier_id) throws SQLException;
}

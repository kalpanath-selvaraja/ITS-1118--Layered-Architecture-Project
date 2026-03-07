package lk.ijse.inventorymanagmentsystem.dao.custom.impl;

import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;
import lk.ijse.inventorymanagmentsystem.dao.custom.SupplierItemDAO;
import lk.ijse.inventorymanagmentsystem.dto.ItemDTO;
import lk.ijse.inventorymanagmentsystem.entity.SupplierItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SupplierItemDAOImpl implements SupplierItemDAO {
    @Override
    public boolean SupplierItemCount(int supplierId) throws SQLException {
        // Count items linked to this supplier
        ResultSet rs = CrudUtil.execute(
                "SELECT COUNT(*) AS total FROM SupplierItem WHERE supplier_id = ?", supplierId
        );

        if (rs.next()) {
            int count = rs.getInt("total");
            return count > 0;  // true if there are items
        }

        return false;
    }

    @Override
    public boolean saveSupplierItem(int supplierId,List<ItemDTO> cartItems) throws SQLException {
        try{
            for(ItemDTO item : cartItems) {
                CrudUtil.execute(
                        "INSERT INTO SupplierItem(supplier_id, item_id) VALUES(?, ?)",
                        supplierId,
                        item.getItemId()
                );
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Override
    public boolean save(SupplierItem DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean update(SupplierItem DTO) throws SQLException {
        return false;
    }

    @Override
    public List<SupplierItem> getAll() throws SQLException {
        return List.of();
    }

    public boolean delete(SupplierItem entity) throws SQLException {
        return CrudUtil.execute("DELETE FROM SupplierItem WHERE supplier_id = ?", entity.getSupplier_id());
    }

    @Override
    public List<SupplierItem> search(String search) throws SQLException {
        return List.of();
    }
}

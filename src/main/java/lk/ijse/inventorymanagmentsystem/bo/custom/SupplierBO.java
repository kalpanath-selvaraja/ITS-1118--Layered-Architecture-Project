package lk.ijse.inventorymanagmentsystem.bo.custom;

import lk.ijse.inventorymanagmentsystem.bo.SuperBO;
import lk.ijse.inventorymanagmentsystem.dao.SuperDAO;
import lk.ijse.inventorymanagmentsystem.dto.ItemDTO;
import lk.ijse.inventorymanagmentsystem.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.List;

public interface SupplierBO extends SuperBO {

    boolean updateSupplierWithItems(SupplierDTO supDTO, List<ItemDTO> cartItems) throws SQLException;

    boolean save(SupplierDTO supplierDTO) throws SQLException;

    boolean deleteSupplier(int supplierId) throws SQLException;

    int getSupplierIdByName(String name) throws SQLException;

    List<SupplierDTO> getAllSuppliers() throws SQLException;


    List<ItemDTO> getItemsForSupplier(int supplierId) throws SQLException;

    List<ItemDTO> getItemsForItem(int id) throws SQLException;

    List<SupplierDTO> searchSupplier(String search) throws SQLException;

    boolean checkSupplierExists(String contact) throws SQLException;


    boolean checkSupplierConnected(int id) throws SQLException;
}

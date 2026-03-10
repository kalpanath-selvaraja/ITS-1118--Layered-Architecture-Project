package lk.ijse.inventorymanagmentsystem.dao.custom;

import lk.ijse.inventorymanagmentsystem.dao.CrudDAO;
import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;
import lk.ijse.inventorymanagmentsystem.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SupplierDAO extends CrudDAO<Supplier> {
    public boolean updateSuppler(Supplier supplier) throws SQLException ;

    public boolean delete(int supplierId) throws SQLException ;

    public int getSupplierID(String name) throws SQLException;

    public boolean existsByContact(String contact) throws SQLException ;
}

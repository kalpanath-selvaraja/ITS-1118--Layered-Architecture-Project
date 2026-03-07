package lk.ijse.inventorymanagmentsystem.dao.custom.impl;

import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;
import lk.ijse.inventorymanagmentsystem.dao.custom.SupplierDAO;
import lk.ijse.inventorymanagmentsystem.dto.ItemDTO;
import lk.ijse.inventorymanagmentsystem.dto.SupplierDTO;
import lk.ijse.inventorymanagmentsystem.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public boolean save(Supplier supplier) {
        try{
                CrudUtil.execute("INSERT INTO Supplier(name , email, contact)VALUES (?,?,?)",
                        supplier.getSupplier_name(), supplier.getEmail(), supplier.getContact());
        }catch(Exception e){
           e.printStackTrace();
            return false;
        }

        return true;

    }

    @Override
    public boolean update(Supplier DTO) throws SQLException {
        return false;
    }

    @Override
    public List<Supplier> search(String search) throws SQLException {

        String SQL = "SELECT * FROM Supplier WHERE name LIKE ?";

        String searchValue = "%" + search + "%";

        ResultSet rs = CrudUtil.execute(SQL, searchValue);

        List<Supplier> items = new ArrayList<>();

        while (rs.next()) {
            int sup_id = rs.getInt("supplier_id");
            String supName = rs.getString("name");
            String eMail = rs.getString("email");
            String contact = rs.getString("contact");

            items.add(new Supplier(sup_id, supName, eMail, contact));
        }

        return items;
    }

    @Override
    public List<Supplier>  getAll () throws SQLException{

        ResultSet rs = CrudUtil.execute("SELECT * FROM Supplier");

        List<Supplier> items = new ArrayList<>();

        while(rs.next()){
            int sup_id = rs.getInt("supplier_id");
            String supName = rs.getString("name");
            String eMail = rs.getString("email");
            String contact = rs.getString("contact");

            items.add(new Supplier(sup_id, supName, eMail, contact));

        }

        return items;

    }

    @Override
    public boolean updateSuppler(Supplier supplier) throws SQLException {
         boolean rs = CrudUtil.execute(
                "UPDATE Supplier SET name = ?, email = ?, contact = ? WHERE supplier_id = ?",
                supplier.getSupplier_name(),
                supplier.getEmail(),
                supplier.getContact(),
                supplier.getSupplier_id()

        );

         return rs;
    }

    @Override
    public void delete(int supplierId) throws SQLException {
        CrudUtil.execute("DELETE FROM Supplier WHERE supplier_id = ?", supplierId);
    }

    @Override
    public int getSupplierID(String name) throws SQLException{
        ResultSet rs = CrudUtil.execute("SELECT supplier_id FROM Supplier WHERE name = ?", name);
        int result = 0;
        if(rs.next()){
            result  = rs.getInt("supplier_id");
            return result;
        }
        return 0;


    }

    @Override
    public boolean getContact(String contact) throws SQLException {
        ResultSet result = CrudUtil.execute("SELECT contact FROM Supplier WHERE contact = ? ", contact);
        return result.next();
    }
}

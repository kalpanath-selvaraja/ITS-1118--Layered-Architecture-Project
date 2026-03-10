package lk.ijse.inventorymanagmentsystem.dao.custom.impl;

import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;
import lk.ijse.inventorymanagmentsystem.dao.custom.WarrantyDAO;
import lk.ijse.inventorymanagmentsystem.entity.Warranty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WarrantyDAOImpl implements WarrantyDAO {

    @Override
    public List<Warranty> getAllMonths() throws SQLException{
        ResultSet rs = CrudUtil.execute("SELECT months FROM Warranty" );
        List<Warranty> warrantyList = new ArrayList();

        while(rs.next()){
            int months = rs.getInt("months");

            Warranty wDTO = new Warranty (months);
            warrantyList.add(wDTO);

        }
        return warrantyList;
    }

    @Override
    public int claimedWarrantyCount() throws SQLException{
        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) AS claimed_warranty FROM OrderItem WHERE warranty_status = ?" ,"Claimed");

        int qty = 0;

        if(rs.next()){
            qty = rs.getInt("claimed_warranty");
            return qty;
        }

        return qty;
    }






















    @Override
    public boolean save(Warranty warranty) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO Warranty (months) VALUES (?)",
                warranty.getMonths()
        );
    }

    @Override
    public boolean update(Warranty warranty) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Warranty SET months = ? WHERE warranty_id = ?",
                warranty.getMonths(),
                warranty.getWarrantyId()
        );
    }

    @Override
    public List<Warranty> getAll() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Warranty");

        List<Warranty> warranties = new ArrayList<>();

        while (rs.next()) {
            warranties.add(new Warranty(
                    rs.getInt("warranty_id"),
                    rs.getInt("months")
            ));
        }

        return warranties;
    }


    @Override
    public List<Warranty> search(String search) throws SQLException {
        return List.of();
    }
}

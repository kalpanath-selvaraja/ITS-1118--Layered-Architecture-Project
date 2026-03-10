package lk.ijse.inventorymanagmentsystem.dao.custom;

import lk.ijse.inventorymanagmentsystem.dao.CrudDAO;
import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;
import lk.ijse.inventorymanagmentsystem.dto.ItemDTO;
import lk.ijse.inventorymanagmentsystem.dto.WarrantyDTO;
import lk.ijse.inventorymanagmentsystem.entity.Item;
import lk.ijse.inventorymanagmentsystem.entity.Warranty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface QueryDAO extends CrudDAO {

    List<Item> getItemsBySupplier(int supplierId) throws SQLException;

//    WarrantyDTO getWarrantymonths(int id) throws SQLException;


    List<Warranty>  serarchOrders(String id) throws SQLException;

    void applyWarrantyStatus() throws SQLException;


    public List<Warranty> getWarrantyTable() throws SQLException;

    List<Item> searchItem(String itemName)throws SQLException;

    List<Item> getItemsView()throws SQLException;


    List<Item> getItemsForItem(int ID) throws SQLException;
}

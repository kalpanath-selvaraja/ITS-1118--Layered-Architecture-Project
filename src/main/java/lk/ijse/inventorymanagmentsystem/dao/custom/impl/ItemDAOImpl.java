package lk.ijse.inventorymanagmentsystem.dao.custom.impl;

import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;
import lk.ijse.inventorymanagmentsystem.dao.custom.ItemDAO;
import lk.ijse.inventorymanagmentsystem.dto.ItemDTO;
import lk.ijse.inventorymanagmentsystem.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {


    @Override
    public boolean save(Item DTO) throws SQLException {
        return false;
    }

    public List<Item> search(String itemName)throws SQLException{
        String sql =
                "SELECT i.item_id, " +
                        "i.name, " +
                        "i.unit_price, " +
                        "i.quantity, " +
                        "w.months AS WarrantyMonths " +
                        "FROM Item i " +
                        "LEFT JOIN Warranty w ON i.warranty_id = w.warranty_id " +
                        "WHERE i.name LIKE ?";

        String searchValue = "%" + itemName + "%";

        ResultSet rs = CrudUtil.execute(sql, searchValue);

        List<Item> items = new ArrayList<>();


        while(rs.next()){
            int itemId = rs.getInt("item_id");
            String itName = rs.getString("name");
            int quantity = rs.getInt("quantity");
            int unitPrice = rs.getInt("unit_price");
            int months = rs.getInt("WarrantyMonths");

            items.add( new Item(itemId,itName, quantity,unitPrice,months));
        }
        return items;
    }

    @Override
    public boolean update(Item DTO) throws SQLException {
        return false;
    }

    @Override
    public List<Item> getAll() throws SQLException {
        return List.of();
    }

    public List<Item> getItems()throws SQLException{


        ResultSet rs = CrudUtil.execute("SELECT " +
                "i.item_id," +
                " i.name, " +
                " i.unit_price, " +
                " i.quantity, " +
                " i.is_active, " +
                " w.months " +
                " FROM Item i JOIN Warranty w ON i.item_id = w.item_id" );

        List<Item> itemList = new ArrayList();

        while(rs.next()){
            int itemID = rs.getInt("item_id");
            String name = rs.getString("name");
            double price =rs.getDouble("unit_price");
            int warranty  =rs.getInt("months");
            int qty = rs.getInt("quantity");

            Item item = new Item(itemID, name, price,qty ,warranty);
            itemList.add(item);
        }

        return itemList;

    }



    public boolean decreseItemQty(int itemId, int qty) throws SQLException {
        boolean result = CrudUtil.execute("UPDATE Item SET quantity = quantity - ? WHERE item_id = ? AND quantity >= ?", qty, itemId, qty);
        return result;
    }//


    public int getLowStocksNumber() throws SQLException{
        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) AS Low_Stock FROM Item WHERE quantity <= 5 AND quantity > 0");

        int qty = 0;

        if(rs.next()){
            qty = rs.getInt("Low_Stock");


            return qty;
        }

        return qty;
    }//



    /// ///////////////////////////////////////////////////////////////////
///
///



    @Override
    public int getWarrantyIdByMonths(int months) throws SQLException {

        ResultSet rs = CrudUtil.execute("SELECT warranty_id FROM Warranty WHERE months = ?", months);
        if(rs.next()) {
            return rs.getInt("warranty_id");
        }
        return 0;
    }//


    @Override
    public boolean updateItems(Item item , int warrantyId) throws SQLException{

        Object warrantyParam = (warrantyId == 0) ? null : warrantyId;

        boolean result = CrudUtil.execute(
                "UPDATE Item SET name = ? , "
                        + "unit_price = ? , "
                        + "quantity = ?, "
                        + "warranty_id = ? "
                        + "WHERE item_id = ?",

                item.getItemName(),
                item.getUnitPrice(),
                item.getQuantity(),
                warrantyParam,
                item.getItemId()
        );

//
//        CrudUtil.execute(
//                "INSERT INTO "
//                        + "SupplierItem (supplier_id, item_id) "
//                        + "VALUES (? , ?) "
//                        + "ON DUPLICATE KEY UPDATE supplier_id = ?;" , item.getSupplierId() ,
//                item.getItemId(),     item.getSupplierId());
//
        return result;

    }//

    @Override
    public int addItems(ItemDTO itemDTO, int warrantyId) throws SQLException {

        Object warrantyParam = (warrantyId == 0) ? null : warrantyId;

        int result = CrudUtil.executeAndReturnId(
                "INSERT INTO Item (name, unit_price, quantity, warranty_id) "
                        + "VALUES (?, ?, ?, ?)",

                itemDTO.getItemName(),
                itemDTO.getUnitPrice(),
                itemDTO.getQuantity(),
                warrantyParam
        );
//        boolean Added = false;
//        if(result != -1 ){
//            Added = CrudUtil.execute(
//                    "INSERT INTO SupplierItem (supplier_id, item_id) "
//                            + "VALUES (?, ?) "
//                            + "ON DUPLICATE KEY UPDATE supplier_id = ?",
//
//                    itemDTO.getSupplierId(),
//                    result,
//                    itemDTO.getSupplierId()
//            );
//        }



        return result;
    }





}

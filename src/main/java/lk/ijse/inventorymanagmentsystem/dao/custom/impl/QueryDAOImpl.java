package lk.ijse.inventorymanagmentsystem.dao.custom.impl;

import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;
import lk.ijse.inventorymanagmentsystem.dao.custom.QueryDAO;
import lk.ijse.inventorymanagmentsystem.dto.ItemDTO;
import lk.ijse.inventorymanagmentsystem.entity.Item;
import lk.ijse.inventorymanagmentsystem.entity.Warranty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public List<Item> getItemsBySupplier(int supplierId) throws SQLException {

        ResultSet rs = CrudUtil.execute(
                "SELECT i.item_id, i.name, i.unit_price, i.quantity, i.warranty_id " +
                        "FROM Item i " +
                        "JOIN SupplierItem si ON i.item_id = si.item_id " +
                        "WHERE si.supplier_id = ?",
                supplierId
        );

        List<Item> items = new ArrayList<>();

        while (rs.next()) {
            items.add(new Item(
                    rs.getInt("item_id"),
                    rs.getString("name"),
                    rs.getDouble("unit_price"),
                    rs.getInt("quantity"),
                    rs.getInt("warranty_id")
            ));
        }

        return items;
    }



    @Override
    public boolean save(Object DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Object DTO) throws SQLException {
        return false;
    }

    @Override
    public List<QueryDAO> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public List<QueryDAO> search(String search) throws SQLException {
        return List.of();
    }


    /// /////////Warranty //////////


    @Override
    public List<Warranty> getWarrantyTable() throws SQLException{

        ResultSet rs = CrudUtil.execute(

                "SELECT "
                        + "o.order_id, "
                        + "oi.order_item_id, "
                        + "o.date, "
                        + "oi.unit_price, "
                        + "oi.warranty_status, "
                        + "i.name "
                        + "FROM Orders o "
                        + "JOIN OrderItem oi ON o.order_id = oi.order_id "
                        + "JOIN Item i ON oi.item_id = i.item_id");

        List<Warranty> warrantyList = new ArrayList();

        while(rs.next()){

            String orderItemId = rs.getString("order_item_id");
            String orderId = rs.getString("order_id");
            String date = rs.getString("date");
            String price = rs.getString("unit_price");
            String status = rs.getString("warranty_status");
            String name = rs.getString("name");

            Warranty warranty = new Warranty(orderItemId , orderId, date, price, status , name);
            warrantyList.add(warranty);

        }

        return warrantyList;

    }

    @Override
    public List<Warranty>  serarchOrders(String id) throws SQLException{

        ResultSet rs = CrudUtil.executeQuery(
                " SELECT  "
                        +"oi.order_item_id, "
                        + "o.order_id,"
                        + " o.date,"
                        + " i.name,"
                        + " oi.unit_price,"
                        + " oi.warranty_status FROM Orders o INNER JOIN OrderItem oi"
                        + " ON o.order_id = oi.order_id INNER JOIN Item i  "
                        + "ON oi.item_id = i.item_id WHERE o.order_id = ? ", id);


        List<Warranty> warrantyList = new ArrayList();

        while(rs.next()){

            String orderItemId = rs.getString("order_item_id");
            String orderId = rs.getString("order_id");
            String date = rs.getString("date");
            String price = rs.getString("unit_price");
            String status = rs.getString("warranty_status");
            String name = rs.getString("name");

            Warranty warranty = new Warranty(orderItemId, orderId, date, price, status , name);
            warrantyList.add(warranty);
        }
        return warrantyList;

    }

    @Override
    public void applyWarrantyStatus() throws SQLException{
        CrudUtil.execute(
                "UPDATE OrderItem oi " +
                        "JOIN Orders o ON oi.order_id = o.order_id " +
                        "JOIN Item i ON oi.item_id = i.item_id " +
                        "LEFT JOIN Warranty w ON i.warranty_id = w.warranty_id " +
                        "SET oi.warranty_status = 'Expired' " +
                        "WHERE w.warranty_id IS NOT NULL " +
                        "AND DATE_ADD(o.date, INTERVAL w.months MONTH) < CURDATE() " +
                        "AND oi.warranty_status = 'Active'");
    }

    /// ////////////////////////////////



    @Override
    public List<Item> searchItem(String itemName)throws SQLException{

        ResultSet rs = CrudUtil.execute(


                "SELECT i.item_id, " +
                        "i.name , " +
                        "i.unit_price, " +
                        "i.quantity, " +
                        "w.months AS WarrantyMonths, " +
                        "s.name AS SupplierName " +  // no comma here
                        "FROM Item i " +
                        "LEFT JOIN Warranty w ON i.warranty_id = w.warranty_id " +
                        "LEFT JOIN SupplierItem si ON i.item_id = si.item_id " +
                        "LEFT JOIN Supplier s ON si.supplier_id = s.supplier_id " +
                        "WHERE i.name LIKE ?",
                "%" + itemName + "%"
        );



        List<Item> items = new ArrayList<>();


        while(rs.next()){
            int itemId = rs.getInt("item_id");
            String itName = rs.getString("name");
            int quantity = rs.getInt("quantity");
            int unitPrice = rs.getInt("unit_price");
            int months = rs.getInt("WarrantyMonths");
            String supName = rs.getString("SupplierName");

            items.add( new Item(itemId,itName, quantity,unitPrice,months,supName));
        }
        return items;
    }

    @Override
    public List<Item> getItemsView()throws SQLException{


        ResultSet rs = CrudUtil.execute(
                "SELECT i.item_id, " +
                        "i.name , " +
                        "i.unit_price, " +
                        "i.quantity, " +
                        "w.months AS WarrantyMonths, " +
                        "s.name AS SupplierName " +  // no comma here
                        "FROM Item i " +
                        "LEFT JOIN Warranty w ON i.warranty_id = w.warranty_id " +
                        "LEFT JOIN SupplierItem si ON i.item_id = si.item_id " +
                        "LEFT JOIN Supplier s ON si.supplier_id = s.supplier_id "
        );


        List<Item> itemList = new ArrayList();

        while(rs.next()){
            int itemId = rs.getInt("item_id");
            String itName = rs.getString("name");
            int quantity = rs.getInt("quantity");
            int unitPrice = rs.getInt("unit_price");
            int months = rs.getInt("WarrantyMonths");
            String supName = rs.getString("SupplierName");

            Item item =  new Item(itemId,itName, quantity,unitPrice,months,supName);
            itemList.add(item);
        }

        return itemList;

    }





    /// ////////////////////////////////////

    @Override
    public List<Item> getItemsForItem(int ID) throws SQLException{
        ResultSet rs = CrudUtil.execute("SELECT i.item_id, i.name, i.unit_price, i.quantity, i.warranty_id " +
                "FROM Item i " +
                "JOIN SupplierItem si ON i.item_id = si.item_id " +
                "WHERE si.supplier_id = ?", ID);

        List<Item> items = new ArrayList<>();

        while(rs.next()){
            int itemId = rs.getInt("item_id");
            String supName = rs.getString("name");


            items.add(new Item(itemId, supName ));

        }

        return items;
    }
}

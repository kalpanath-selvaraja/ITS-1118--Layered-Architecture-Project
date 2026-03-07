package lk.ijse.inventorymanagmentsystem.dao.custom.impl;

import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;
import lk.ijse.inventorymanagmentsystem.dao.custom.ItemDAO;
import lk.ijse.inventorymanagmentsystem.dao.custom.OrderItemDAO;
import lk.ijse.inventorymanagmentsystem.entity.OrderItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAOImpl implements OrderItemDAO {


    @Override
    public ArrayList<OrderItem> getItemsByOrderId(int orderId) throws SQLException {

        String sql = "SELECT * FROM OrderItem  WHERE order_id = ?";

        ResultSet rs = CrudUtil.execute(sql, orderId);
        ArrayList<OrderItem> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new OrderItem(
                    rs.getInt("order_id"),
                    rs.getInt("item_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("unit_price")

            ));
        }

        return list;

    }

    @Override
    public boolean save(OrderItem DTO) throws SQLException {
        String sql = "INSERT INTO OrderItem (order_id, item_id, quantity, unit_price) VALUES (?, ?, ?, ?)";

        boolean result =  CrudUtil.execute(
                sql,
                DTO.getOrderID(),
                DTO.getItemID(),
                DTO.getQuantity(),
                DTO.getUnitPrice()
        );


        int warranty = DTO.getWarranty();
        if(warranty>0){
            CrudUtil.execute("UPDATE OrderItem SET warranty_status = 'Active' WHERE order_id = ?",DTO.getOrderID() );
        }else{
            CrudUtil.execute("UPDATE OrderItem SET warranty_status = 'No Warranty' WHERE order_id = ?",DTO.getOrderID() );

        }

        ItemDAO itemDAO = new ItemDAOImpl();

        if(result){
            boolean decresed = itemDAO.decreseItemQty(DTO.getItemID(), DTO.getQuantity());

            if(!decresed){
                throw new SQLException();
            }
        } else {
            throw new SQLException();
        }
        return true;
    }

    @Override
    public boolean update(OrderItem DTO) throws SQLException {
        return false;
    }

    @Override
    public List<OrderItem> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public List<OrderItem> search(String search) throws SQLException {
        return List.of();
    }
}

package lk.ijse.inventorymanagmentsystem.dao.custom.impl;

import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;
import lk.ijse.inventorymanagmentsystem.dao.custom.OrderDAO;
import lk.ijse.inventorymanagmentsystem.db.DBConnection;
import lk.ijse.inventorymanagmentsystem.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public List<Order> search(int order_id)throws SQLException{

        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM Order WHERE order_id = ?",order_id  //
        );


        List<Order> items = new ArrayList<>();


        while(rs.next()){
            int orderId = rs.getInt("order_id");
            int cusId = rs.getInt("cus_id");
            int empId = rs.getInt("emp_id");
            int date = rs.getInt("date");
            int total = rs.getInt("total");

            items.add( new Order(orderId,cusId , empId,date,total));
        }
        return items;
    }



    @Override
    public String getCount () throws SQLException{
        ResultSet rs =CrudUtil.execute("SELECT COUNT(*) AS total_orders FROM Orders");

        if(rs.next()){
            String count = rs.getString("total_orders");

            return count;
        }

        return null;
    }

    @Override
    public Map<String, Double> getDailySales() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT DATE(date) AS order_date, SUM(total) AS daily_total "
                + "FROM Orders "
                + "GROUP BY DATE (date) "
                + "ORDER BY DATE(date) ");

        Map<String, Double> data = new HashMap<>();

        while (rs.next()) {
            String date = rs.getString("order_date");
            double total = rs.getDouble("daily_total");
            data.put(date, total);
        }

        return data;
    }

    @Override
    public boolean save(Order order) throws SQLException {
        return false;
    }

    @Override
    public int saveAndReturn(Order order) throws SQLException {
        return CrudUtil.executeAndReturnId("INSERT INTO Orders (cus_id, emp_id, total) VALUES (?, ?, ?)"
                ,order.getCusid(),order.getEmpid(),order.getTotal());
    }

    @Override
    public boolean update(Order DTO) throws SQLException {
        return false;
    }

    @Override
    public List<Order> getAll() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Orders");

        List<Order> orderList = new ArrayList<>();

        while(rs.next()) {
            int orderId = rs.getInt("order_id");
            int cusId = rs.getInt("cus_id");
            int empId = rs.getInt("emp_id");
            Timestamp dateTime = rs.getTimestamp("date");
            double total = rs.getDouble("total");


            Order order = new Order(orderId, cusId, empId, dateTime,total);
            orderList.add(order);
        }
        return orderList;
    }

    @Override
    public List<Order> search(String search) throws SQLException {
        return List.of();
        // Empty Method with no use in here

    }

    @Override
    public double getTotalSales() throws SQLException {
        double total = 0;

        Connection con = DBConnection.getinstance().getConnection();

        try (
                Statement stm = con.createStatement();
                ResultSet rs = stm.executeQuery("SELECT SUM(total) AS total FROM Orders")) {

            if (rs.next()) {
                total = rs.getDouble("total");
            }
        }

        return total;
    }


}

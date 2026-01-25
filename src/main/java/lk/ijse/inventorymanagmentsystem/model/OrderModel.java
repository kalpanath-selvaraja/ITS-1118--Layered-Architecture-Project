/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.model;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lk.ijse.inventorymanagmentsystem.db.DBConnection;
import lk.ijse.inventorymanagmentsystem.dto.CustomerDTO;
import lk.ijse.inventorymanagmentsystem.dto.OrderDTO;
import lk.ijse.inventorymanagmentsystem.util.CrudUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author kalpanath
 */
public class OrderModel {
    
    public  int saveOrder(OrderDTO order) throws SQLException {
        String sql = "INSERT INTO Orders (cus_id, emp_id, total) VALUES (?, ?, ?)";

        return CrudUtil.executeAndReturnId(sql,order.getCusid(),order.getEmpid(),order.getTotal());
    }

    public List<OrderDTO> getAllOrders() throws SQLException {
    

        
        ResultSet rs = CrudUtil.execute("SELECT * FROM Orders");
        
        List<OrderDTO> orderList = new ArrayList<>();
        
        while(rs.next()) {
            int orderId = rs.getInt("order_id");
            int cusId = rs.getInt("cus_id");
            int empId = rs.getInt("emp_id");
            Timestamp dateTime = rs.getTimestamp("date");
            double total = rs.getDouble("total");
            
            
            
            
            OrderDTO orderDTO = new OrderDTO(orderId, cusId, empId, dateTime,total);
            orderList.add(orderDTO);
        }
//        
        return orderList;
    }
    
    
    public List<OrderDTO> searchItem(int order_id)throws SQLException{
        
        ResultSet rs = CrudUtil.execute(
            "SELECT * FROM Order WHERE order_id = ?",order_id  // 
        );

        
        List<OrderDTO> items = new ArrayList<>();

        
        while(rs.next()){
            int orderId = rs.getInt("order_id");
            int cusId = rs.getInt("cus_id");
            int empId = rs.getInt("emp_id");
            int date = rs.getInt("date");
            int total = rs.getInt("total");
            
            items.add( new OrderDTO(orderId,cusId , empId,date,total));
        }
        return items;
    }
    
    public void printReport()throws SQLException , JRException{
        
        Connection conn = DBConnection.getinstance().getConnection();
        
        InputStream inputStream = getClass().getResourceAsStream("/reports/OrderReport.jrxml" );
        
        if(inputStream == null){
            System.out.println("no jfxml");
        }
        
        JasperReport jr =JasperCompileManager.compileReport(inputStream);
        
        JasperPrint jp = JasperFillManager.fillReport(jr, null , conn);
        
        JasperViewer.viewReport(jp, false);
    
    }
    
    public void printInvoice (int orderId) throws SQLException , JRException{
        
        Connection conn = DBConnection.getinstance().getConnection();
        
        InputStream inputStream = getClass().getResourceAsStream("/reports/Bill.jrxml" );
        
        if(inputStream == null){
            System.out.println("no jfxml");
        }
        JasperReport jr =JasperCompileManager.compileReport(inputStream);
        
        Map<String, Object> params = new HashMap<>();
        params.put("ORDER_ID", orderId);
        
        JasperPrint jp = JasperFillManager.fillReport(jr, params , conn);
        
        JasperViewer.viewReport(jp, false);
    }
    
    public String getOrderCount () throws SQLException{
        ResultSet rs =CrudUtil.execute("SELECT COUNT(*) AS total_orders FROM Orders");
        
        if(rs.next()){
           String count = rs.getString("total_orders");
            System.out.println(count);
           return count;
        }
        
        return null;
    }
    
    
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

    
}

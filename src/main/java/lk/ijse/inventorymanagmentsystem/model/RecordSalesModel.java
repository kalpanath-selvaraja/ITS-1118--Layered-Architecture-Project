/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import lk.ijse.inventorymanagmentsystem.db.DBConnection;
import lk.ijse.inventorymanagmentsystem.dto.CartItemDTO;
import lk.ijse.inventorymanagmentsystem.dto.CheckoutDTO;
import lk.ijse.inventorymanagmentsystem.dto.CustomerDTO;
import lk.ijse.inventorymanagmentsystem.dto.OrderDTO;
import lk.ijse.inventorymanagmentsystem.dto.OrderItemDTO;
import lk.ijse.inventorymanagmentsystem.util.CrudUtil;

/**
 *
 * @author kalpanath
 */
public class RecordSalesModel {
    
    
    private  final CustomerModel customerModel = new CustomerModel() ;
    private  final OrderItemModel orderItemModel = new OrderItemModel() ;
    private  final OrderModel orderModel = new OrderModel() ;
    
    int orderId = 0;
    
    public int recordsales(CheckoutDTO checkoutDTO) throws SQLException {

        Connection conn = DBConnection.getinstance().getConnection();

        try {
            conn.setAutoCommit(false);

            boolean needsCustomer = checkoutDTO.isCustomerRequired();
            CustomerDTO customer = checkoutDTO.getCustomer();

            Integer cusId = null;

            if (needsCustomer && customer != null) {

                cusId = CrudUtil.executeAndReturnId(
                        "SELECT cus_id FROM Customer WHERE contact=?",
                        customer.getContact()
                );

                if (cusId == -1) {
                    cusId = customerModel.saveCustomer(customer);
                }
            }

            OrderDTO orderDTO = new OrderDTO(
                    cusId,
                    checkoutDTO.getEmpId(),
                    checkoutDTO.getTotal()
            );
            
            System.out.println("Saving order with emp_id = " + checkoutDTO.getEmpId());

            
            orderId = orderModel.saveOrder(orderDTO);

            for (CartItemDTO item : checkoutDTO.getCartItems()) {

                OrderItemDTO orderItemDTO = new OrderItemDTO(
                        orderId,
                        item.getItemId(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getWarranty()
                );

                orderItemModel.saveOrderItem(orderItemDTO);
            }

            conn.commit();
            return orderId;

        } catch (Exception e) {
            conn.rollback();
            e.printStackTrace();
            return orderId;
        } finally {
            conn.setAutoCommit(true);
        }
    }



    public  double getTotalSales() throws SQLException {
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

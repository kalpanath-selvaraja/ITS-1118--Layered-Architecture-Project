/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.inventorymanagmentsystem.model.ItemModel;
import lk.ijse.inventorymanagmentsystem.model.OrderModel;
import lk.ijse.inventorymanagmentsystem.model.RecordSalesModel;
import lk.ijse.inventorymanagmentsystem.model.WarrantyModel;

/**
 * FXML Controller class
 *
 * @author kalpanath
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label lblTotalSalesAmount;        
    @FXML
    private Label totalOrders; 
    
    @FXML
    private Label lowStocks; 
    
    @FXML
    private Label lblWarrantyClaims; 
    
    @FXML
    private LineChart<String, Number> salesChart;

    OrderModel orderModel = new OrderModel();

    
    
    RecordSalesModel recordSalesModel = new RecordSalesModel();
    WarrantyModel warrantyModel = new WarrantyModel();
    ItemModel itemModel = new ItemModel();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadSalesChart();
            
            try {
                warrantyModel.applyWarrantyStatus();
            } catch (SQLException ex) {
                new Alert(Alert.AlertType.ERROR, "Database Error in Warranty Status").show();
            }
            
            try {
                
                lblTotalSalesAmount.setText(String.valueOf(recordSalesModel.getTotalSales()));
            } catch (SQLException ex) {
                new Alert(Alert.AlertType.ERROR, "Database Error in Warranty Status").show();
                
            }
            lblWarrantyClaims.setText(String.valueOf(warrantyModel.claimedWarrantyCount()));
            
            totalOrders.setText(orderModel.getOrderCount());
            lowStocks.setText(String.valueOf(itemModel.getLowStocksNumber()));
            
        } catch (SQLException ex) {
            System.getLogger(HomeController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        

    }    
    
    private void loadSalesChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Daily Sales");

        try {
            Map<String, Double> data = orderModel.getDailySales();

            for (Map.Entry<String, Double> entry : data.entrySet()) {
                series.getData().add(
                    new XYChart.Data<>(entry.getKey(), entry.getValue())
                );
            }

            salesChart.getData().clear();
            salesChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}

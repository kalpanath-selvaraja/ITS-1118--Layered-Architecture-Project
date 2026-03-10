/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.controller;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.inventorymanagmentsystem.bo.BOFactory;
import lk.ijse.inventorymanagmentsystem.bo.custom.OrderBO;
import lk.ijse.inventorymanagmentsystem.dto.OrderDTO;
import lk.ijse.inventorymanagmentsystem.util.Reports;

/**
 * FXML Controller class
 *
 * @author kalpanath
 */

public class OrderController implements Initializable{
    

    @FXML
    private TextField orderNameField;
  
   
    @FXML
    private TableView<OrderDTO> tabelOrder;

    @FXML
    private TableColumn<OrderDTO, Integer> orderId;
    @FXML
    private TableColumn<OrderDTO, Integer> cusId;
    @FXML
    private TableColumn<OrderDTO, Integer> empId;
    @FXML
    private TableColumn<OrderDTO, Timestamp> dateandTime;
    @FXML
    private TableColumn<OrderDTO, Double> total;      

    
    OrderBO orderBO = (OrderBO) BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.ORDER);
    
    private final String ORDER_ID_REGEX = "^[0-9]*$";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

        
        orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        cusId.setCellValueFactory(new PropertyValueFactory<>("cusid"));
        empId.setCellValueFactory(new PropertyValueFactory<>("empid"));
        dateandTime.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        lordOrderTable();
    }
    
       @FXML
    private void handleItemSearch(KeyEvent event ){
        if(event.getCode() == KeyCode.ENTER){
            String order_id = orderNameField.getText().trim();
            
            if(order_id.matches(ORDER_ID_REGEX)){ 
                new Alert(Alert.AlertType.ERROR, "Please enter a valid order ID (numbers only)").show();
            } else {
                try {
                    
                    
                    
                    List<OrderDTO> oDTO = orderBO.search(Integer.parseInt(order_id));
                    
                    
                    if (oDTO != null) {
                        
                        loadTable(oDTO);
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Item not found!").show();
                    }


                    } catch(SQLException e) {
                        e.printStackTrace();
                }
            }
        }
    }
    
    
    
    private void loadTable(List<OrderDTO> dto) {

        ObservableList<OrderDTO> list = tabelOrder.getItems();
        


        if (list == null) {
            list = FXCollections.observableArrayList();
            tabelOrder.setItems(list);
        }

        list.clear();
        list.addAll(dto);
    }
    
   private void lordOrderTable() {
        try {
        
            
            List<OrderDTO> customerList = orderBO.getAll();
            
            ObservableList<OrderDTO> obList = FXCollections.observableArrayList();
            
            for (OrderDTO orderDTO : customerList) {
                obList.add(orderDTO);
            }
            
            tabelOrder.setItems(obList);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleprint(ActionEvent event){
        try{
            Reports.printReport();
        }catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error").show();
        }
    }
    
}


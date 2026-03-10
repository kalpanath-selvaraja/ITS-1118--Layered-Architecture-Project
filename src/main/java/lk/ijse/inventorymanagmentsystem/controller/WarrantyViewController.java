/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.inventorymanagmentsystem.bo.BOFactory;
import lk.ijse.inventorymanagmentsystem.bo.custom.WarrantyBO;
import lk.ijse.inventorymanagmentsystem.dto.WarrantyDTO;

/**
 * FXML Controller class
 *
 * @author kalpanath
 */
public class WarrantyViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<WarrantyDTO> warrantyOrderTable;
    
    @FXML
    private TableColumn<WarrantyDTO, String> orderItemId;
    
    @FXML
    private TableColumn<WarrantyDTO, String> orderId;
    
    @FXML
    private TableColumn<WarrantyDTO, String> itemName;
    
    @FXML
    private TableColumn<WarrantyDTO, String> price;
    
    @FXML
    private TableColumn<WarrantyDTO, String> date;
    
    @FXML
    private TableColumn<WarrantyDTO, String> Status;
    
    @FXML
    private TextField itemNameField;
    
    @FXML
    private TextField orderitemField;

    @FXML
    private TextField searchOrderField;

    @FXML
    private TextField  orderIdField;
    
    @FXML
    private TableColumn<WarrantyDTO, Void> colAction;

    
    private final String ORDER_ID_REGEX = "^[0-9]+$";

    
     private final ObservableList<WarrantyDTO> warrantyList =  FXCollections.observableArrayList();

     WarrantyBO  warrantyBO = (WarrantyBO) BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.WARRANTY);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        warrantyOrderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        
        orderItemId.setCellValueFactory(new PropertyValueFactory<>("orderItemId"));
        orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        price.setCellValueFactory(new PropertyValueFactory<>("unitprice"));
        Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        itemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAction.setCellFactory(parm -> new TableCell<>() {
            
            private final Button btn = new Button("Select");
            
            {
                btn.setStyle("""
                    -fx-background-color: #3b82f6;
                    -fx-text-fill: white;
                    -fx-background-radius: 6;
                """);

                // Attach event handler here
                btn.setOnAction(event -> {
                    WarrantyDTO currentItem = getTableView().getItems().get(getIndex() );
                    orderitemField.setText(currentItem.getOrderItemId());
                    itemNameField.setText(currentItem.getName());
                    orderIdField.setText(currentItem.getOrderId());
                    
                    
                });
            }
                        
            @Override
            protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
            }

        });
        
        warrantyOrderTable.getColumns().forEach(col ->
        col.setStyle("-fx-alignment: CENTER;")
        );

        
        loadtables();
        
    }   
    
    @FXML
    private void searchOrders(KeyEvent event) {
        if(event.getCode() != KeyCode.ENTER) return;
        String odId = searchOrderField.getText().trim();
        
        if(!odId.matches(ORDER_ID_REGEX)){
            new Alert(Alert.AlertType.INFORMATION,"Order id has to be a Number").show();
            return;
        }
        
        try {
            List<WarrantyDTO> searchedResult;
        
            searchedResult = warrantyBO.searchWarrantyByOrderId(odId);
            warrantyList.clear();
            warrantyList.addAll(searchedResult);
        } catch (SQLException ex) {

            System.getLogger(WarrantyViewController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
         
         
    }

    
    private void loadtables(){
        try {
            List<WarrantyDTO> List = warrantyBO.getWarrantyTable();
            warrantyList.addAll(List);
            warrantyOrderTable.setItems(warrantyList);
            
            
            
        } catch (SQLException ex) {
            System.getLogger(WarrantyViewController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    public void claimWarranty(){
        String oiId = orderitemField.getText();
        
        
        try {
            String  currentStatus = warrantyBO.claimWarranty(oiId);
            
            if(currentStatus == null){
                new Alert (Alert.AlertType.ERROR , "warranty status is Empty").show();
                return;
            } 
            
            switch (currentStatus) {
                case "Active":
                    new Alert(Alert.AlertType.INFORMATION, "Warranty claimed successfully").show();
                    warrantyList.clear();
                    loadtables();
                    break;
                case "Expired":
                    new Alert(Alert.AlertType.WARNING, "Warranty has expired, cannot claim").show();
                    warrantyList.clear();
                    loadtables();
                    break;
                case "Claimed":
                    new Alert(Alert.AlertType.INFORMATION, "Warranty has already been claimed").show();
                    warrantyList.clear();
                    loadtables();
                    break;
                case "No Warranty":
                    new Alert(Alert.AlertType.INFORMATION, "This item has no warranty").show();
                    warrantyList.clear();
                    loadtables();
                    break;
                default:
                    new Alert(Alert.AlertType.ERROR, "Unknown warranty status").show();
            }

        } catch (SQLException ex) {
            new Alert (Alert.AlertType.ERROR , "DataBase Error at ClaimWarranty").show();            
        }
       
       
       
       
    }
    
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.inventorymanagmentsystem.bo.custom.CustomerBO;
import lk.ijse.inventorymanagmentsystem.dto.CustomerDTO;
import lk.ijse.inventorymanagmentsystem.dto.ItemDTO;
import lk.ijse.inventorymanagmentsystem.model.CustomerModel;
import lk.ijse.inventorymanagmentsystem.util.Navigation;

/**
 * FXML Controller class
 *
 * @author kalpanath
 */
public class CustomerUpdateController implements Initializable {

    @FXML
    private TextField cusID;
    
    @FXML
    private TextField cusNameField;
    
    @FXML
    private TextField cusContactField;

    
    @FXML
    private AnchorPane customerPane;
    
    
        
//    private final CustomerBO cusBO =
    private final CustomerModel customerModel = new CustomerModel();
    
    private final String CUSTOMER_NAME_REGEX =  "^[A-Za-z '-]{3,}$"; 
    private final String CUSTOMER_CONTACT_REGEX = "^[0-9]{10}$";
 
    
    
    
    
    @Override
    public void initialize (URL url, ResourceBundle r){
        cusID.setDisable(true);
    }
    
    public void setFields(CustomerDTO selectedCustomer){
        cusID.setText(String.valueOf(selectedCustomer.getId()));
        
        cusNameField.setText(selectedCustomer.getName());
        cusContactField.setText(selectedCustomer.getContact());
    }

    public void setParentController(AnchorPane controller) {
        this.customerPane = controller;
    }

    

            
            
    

    
    @FXML
    private void updateCustomer() {

        String idText = cusID.getText();
        String name = cusNameField.getText() != null ? cusNameField.getText().trim() : "";
        String contact = cusContactField.getText() != null ? cusContactField.getText().trim() : "";

        if(!name.matches(CUSTOMER_NAME_REGEX)){
            new Alert(Alert.AlertType.ERROR, "Invalid Customer Name!").show();
            return;
        }
        if(!contact.matches(CUSTOMER_CONTACT_REGEX)){
            new Alert(Alert.AlertType.ERROR, "Invalid Customer Contact!").show();
            return;
        }

        int customerId;
        try {
            customerId = Integer.parseInt(idText);
        } catch(NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID!").show();
            return;
        }

        try {
            boolean result = customerModel.updateCustomer(new CustomerDTO(customerId , name, contact ));
            if(result){
                new Alert(Alert.AlertType.INFORMATION, "Customer details updated successfully!").show();
                
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer update failed!").show();
            }
        } catch(SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        }
    }

    
    @FXML
    private void back(){
        
        try {
            CustomerViewController   controller = Navigation.loadWithController(
                    customerPane,
                    "/lk/ijse/inventorymanagmentsystem/CustomerView.fxml"
            );
        } catch (IOException ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Load UI Error").show();
        }

    }
        

    
    
    
    
}

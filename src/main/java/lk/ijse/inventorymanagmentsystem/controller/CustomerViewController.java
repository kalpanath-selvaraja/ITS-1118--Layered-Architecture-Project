package lk.ijse.inventorymanagmentsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.AnchorPane;
import lk.ijse.inventorymanagmentsystem.bo.custom.BOFactory;
import lk.ijse.inventorymanagmentsystem.bo.custom.CustomerBO;
import lk.ijse.inventorymanagmentsystem.dao.DAOFactory;
import lk.ijse.inventorymanagmentsystem.dto.CustomerDTO;
import lk.ijse.inventorymanagmentsystem.util.Navigation;
//import lk.ijse.inventorymanagmentsystem.model.CustomerModel;

/**
 * FXML Controller class
 *
 * @author kalpanath
 */
public class CustomerViewController implements Initializable {
    
    
   
    
    
    @FXML
    private TextField nameField;
    @FXML
    private TextField customerName;
    
    @FXML
    private TextField customerContact;
    
    @FXML
    private TableView<CustomerDTO> tableCustomer;



    @FXML
    private TableColumn <CustomerDTO, Number> colId;
    
    @FXML
    private TableColumn <CustomerDTO, String> colName;
    
    @FXML
    private TableColumn<CustomerDTO, String> colContact;
    
    @FXML
    private TableColumn<CustomerDTO, Void>colAction;
    
    private final String CUSTOMER_NAME_REGEX = "^[A-Za-z '-]{3,}$"; 
    private final String CUSTOMER_CONTACT_REGEX = "^[0-9]{10}$";    
    private final String CUSTOMER_ID_REGEX = "^[0-9]+$";              

    
    private  Button currentlySelectedButton = null;
    private  CustomerDTO selectedCustomer = null;

     
    @FXML
    private AnchorPane customerPane;

    private final CustomerBO customerBO= (CustomerBO) BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.CUSTOMER);

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        loadCustomerTable();
        
        colAction.setCellFactory(param -> new TableCell<>() {
                private final Button action = new Button ("Select");
                {
                    
                    
                    action.setStyle("""
                        -fx-background-color: #3b82f6;
                        -fx-text-fill: white;
                        -fx-background-radius: 6;
                    """);
                    
                    
                    action.setOnAction(event ->{
                        
                        
                        CustomerDTO cusDTO = getTableView().getItems().get(getIndex());
                            
                        if(action ==  currentlySelectedButton){
                            action.setText("Select");
                            currentlySelectedButton = null;
                            selectedCustomer = null;
                        } else {

                            if (currentlySelectedButton != null) {
                                currentlySelectedButton.setText("Select");
                            }


                            action.setText("Selected");
                            currentlySelectedButton = action;
                            selectedCustomer = cusDTO;
                            
                            


                            
                            
                        }   
                            
                            
                    });
                }
        
                
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(action);
                    }
                }
        });        
    }   
    
    
    @FXML
    private void handleSaveCustomer(){
        
        String name = customerName.getText().trim();
        String contact = customerContact.getText().trim();
        
        
        
        
        
        
        if(!name.matches(CUSTOMER_NAME_REGEX)){
             new Alert(Alert.AlertType.ERROR, "Invalid Customer Name!").show();
        }else if(!contact.matches(CUSTOMER_CONTACT_REGEX)){
             new Alert(Alert.AlertType.ERROR, "Invalid Customer Contact!").show();
        }else{
            try {
                CustomerDTO cusDTO = new CustomerDTO(name, String.valueOf(contact));


                int isSaved = customerBO.saveCustomer(cusDTO);

                if(isSaved != -1  ){



                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Customer Saved Succsefully");
                    alert.setHeaderText("Customer Saved");
                    alert.show();

                    cleanFields();

                }else{



                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Customer Not Saved ");
                    alert.setHeaderText("Sorry Something Went Wrong !!!!");
                    alert.show();
                }
            }catch(SQLException e){
                    e.printStackTrace();
            } 
        }
    }
    
    
    @FXML
    private void handleSearchCustomer(KeyEvent event ){
        
        if(event.getCode() == KeyCode.ENTER){
            
            String cuscontact = nameField.getText().trim();
            
            if(!cuscontact.matches(CUSTOMER_CONTACT_REGEX)){
                
                new Alert(Alert.AlertType.ERROR, "Invalid input please eneter a proper contact number!").show();
 
            } else {
                try {
                
                    CustomerDTO cusDTO = customerBO.searchCustomer(cuscontact);

                    if(cusDTO != null){
                           updateCustomerTable(cusDTO);

                    }else {
                        new Alert(Alert.AlertType.ERROR, "Customer not found!").show();
                    }

                    } catch(Exception e) {
                        e.printStackTrace();
                }
            }
            
            
            
        }
    }
    
    
    
    
    @FXML
    private void cleanFields() {
    
        nameField.setText("");
        customerName.setText("");
        customerContact.setText("");
        
    }
    
  
    

    public  void loadCustomerTable() {
        try{
            List<CustomerDTO> customerList = customerBO.getAllCustomers();////
            
            ObservableList<CustomerDTO> obList = FXCollections.observableArrayList();
            
            for (CustomerDTO customerDTO : customerList){   
                obList.add(customerDTO);
            }
            tableCustomer.setItems(obList);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    

    
    private  void updateCustomerTable(CustomerDTO cusDTO){
        
        ObservableList<CustomerDTO> obList = FXCollections.observableArrayList();
        obList.add(cusDTO);
        tableCustomer.setItems(obList);
    }
    
    
    
    
    @FXML
    private void updateBtnUpdate(ActionEvent event ) {
            
            
        if(selectedCustomer == null){
                    new Alert(Alert.AlertType.ERROR , "No customer was selected !").show();
                    return;
            }
        
        CustomerUpdateController controller;
        try {
           
            controller = Navigation.loadWithController(
                    customerPane,
                    "/lk/ijse/inventorymanagmentsystem/CustomerUpdate.fxml"
            );
            
           
                
                controller.setParentController(customerPane);
                controller.setFields(selectedCustomer);
                selectedCustomer = null;
            
        } catch (IOException ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Load UI Error").show();
        }  
    }  
    


          


    
    


    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.controller;

import java.io.IOException;
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
import javafx.scene.layout.AnchorPane;
import lk.ijse.inventorymanagmentsystem.bo.custom.BOFactory;
import lk.ijse.inventorymanagmentsystem.bo.custom.SupplierBO;
import lk.ijse.inventorymanagmentsystem.dto.SupplierDTO;
import lk.ijse.inventorymanagmentsystem.util.Navigation;

/**
 * FXML Controller class
 *
 * @author kalpanath
 */
public class SupplierViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML
    private AnchorPane contentPane;
    
    String FXML= "/lk/ijse/inventorymanagmentsystem/SupplierManagment.fxml";
    
    @FXML
    private TextField searchSupplier;
    
    
    
    @FXML
    private TableView<SupplierDTO>  supplierTable;
    
    
    
    
    @FXML
    private TableColumn<SupplierDTO ,Integer> supid; 
    @FXML
    private TableColumn<SupplierDTO ,String> supName; 
    @FXML
    private TableColumn<SupplierDTO ,String> supEmail;    
    @FXML
    private TableColumn<SupplierDTO ,String> supContact; 
    
    @FXML
    private TableColumn<SupplierDTO , Void> supAction;
    
    private final String CUSTOMER_NAME_REGEX = "^[a-zA-Z]*$";
    

    
    private final ObservableList<SupplierDTO> masterSupplierList = FXCollections.observableArrayList();
    
    private static Button currentlySelectedButton = null;
    private static SupplierDTO selectedSupplier = null;
    
    SupplierDTO supDTO ;

    SupplierBO supplierBO = (SupplierBO) BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.SUPPLIER);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        supplierTable.setItems(masterSupplierList);
        
        
        supid.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        supName.setCellValueFactory(new PropertyValueFactory<>("supplier_name"));
        supEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        supContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
       
        supAction.setCellFactory(param -> new TableCell<>() {
                private final Button action = new Button ("Select");
                {
                    
                    
                    action.setStyle("""
                        -fx-background-color: #3b82f6;
                        -fx-text-fill: white;
                        -fx-background-radius: 6;
                    """);
                    
                    
                    action.setOnAction(event ->{
                        
                        
                        SupplierDTO DTO = getTableView().getItems().get(getIndex());
                            
                        if(action ==  currentlySelectedButton){
                            action.setText("Select");
                            currentlySelectedButton = null;
                            selectedSupplier = null;
                            

                        } else {

                            if (currentlySelectedButton != null) {
                                currentlySelectedButton.setText("Select");
                                
                                
                            }


                            action.setText("Selected");
                            currentlySelectedButton = action;
                            selectedSupplier = DTO;
                            
                            
                            
                            
                            

                            System.out.println("Selected: " + DTO.getSupplier_name());
                            
                            
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
        
        
        
        
        try {
            loadTableDefault();
        } catch (SQLException e) {
            e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Database Error, error occurred. Please try again.").show();
        }
        
        
    }  
    
    @FXML
    private void searchSupplier(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            String sup_Name = searchSupplier.getText().trim();
            
            if(!sup_Name.matches(CUSTOMER_NAME_REGEX)){
                System.out.println("sup name is empty");    
            }else{
                try{
                    
                    List<SupplierDTO> supDTO = supplierBO.searchSupplier(sup_Name);
                    
                    if(supDTO.isEmpty()){
                        new Alert(Alert.AlertType.ERROR,"No Such supplier exits. Please try again.").show();
                    }else{
                        loadTable(supDTO);
                                
                    }   
                }catch(SQLException e){
                        e.printStackTrace();   
                    new Alert(Alert.AlertType.ERROR,"Database error occurred. Please try again.").show();
                }  
            }
        }
    }


    
    public void loadTable(List<SupplierDTO> supDTO){

        masterSupplierList.clear();

        masterSupplierList.addAll(supDTO);
    }
    
    private void loadTableDefault() throws SQLException{
        
        List<SupplierDTO>  dto = supplierBO.getAllSuppliers();
            
        masterSupplierList.clear();
        masterSupplierList.addAll(dto);
        
    }
    
    @FXML
    private void loadUi (){
        try{
            Navigation.load(contentPane, FXML);
            
        }catch(IOException i){
            i.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Ui  error occurred. Please try again.").show();

        }
    } 
    
    @FXML
    private void addSupplierbtn(){
        SupplierManagmentController controller;
        try {
            controller = Navigation.loadWithController(
                    contentPane,
                    "/lk/ijse/inventorymanagmentsystem/SupplierManagment.fxml"
            );
            
            controller.usedfor("ADD");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.getLogger(SupplierViewController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }  
    }
    
    
    @FXML
    private void updateSupplier(){
        if(selectedSupplier == null){
            new Alert(Alert.AlertType.ERROR,"Select supplier to update.").show();
            return;
        }
        SupplierManagmentController controller ;
        try {
            controller = Navigation.loadWithController(
                contentPane,
                "/lk/ijse/inventorymanagmentsystem/SupplierManagment.fxml"
            );

            // Optional: pass any data to the controller
            controller.setSupplier(selectedSupplier);
            controller.usedfor("Update");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    @FXML 
    private void deleteSupplier()  {
        if(selectedSupplier == null){
            new Alert(Alert.AlertType.ERROR,"Select supplier to delete.").show();
            return;
        }
        try {
            SupplierManagmentController controller = Navigation.loadWithController(
                contentPane,
                "/lk/ijse/inventorymanagmentsystem/SupplierManagment.fxml"
            );

            // Optional: pass any data to the controller
            controller.setSupplier(selectedSupplier);
            controller.usedfor("Delete");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    } 
    
    
}

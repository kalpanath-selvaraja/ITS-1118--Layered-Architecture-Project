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
import javafx.scene.DepthTest;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.inventorymanagmentsystem.dto.ItemDTO;
import lk.ijse.inventorymanagmentsystem.dto.SupplierDTO;
import lk.ijse.inventorymanagmentsystem.model.ItemViewModel;
import lk.ijse.inventorymanagmentsystem.model.SupplierModel;
import lk.ijse.inventorymanagmentsystem.util.Navigation;

/**
 * FXML Controller class
 *
 * @author kalpanath
 */
public class SupplierManagmentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private AnchorPane contentPane;
    
    
    
    
    @FXML
    private Label add;
    @FXML
    private Label update;
    
    @FXML
    private Label delete;
    
    @FXML
    private ImageView plusImage;

    @FXML
    private ImageView saveImage;
    
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;
    
    @FXML
    private Button btnDelete;

    
    @FXML
    private TextField supplierId;
    @FXML
    private TextField supplierName;
    @FXML
    private TextField supplierEmail;
    @FXML
    private TextField supplierContact;
    
    
    
        SupplierDTO supDTO ;
        
        

    @FXML
    private TableView<ItemDTO> itemTable;
    
    @FXML
    private TableView<ItemDTO> cartItemTable;
    
    @FXML
    private TableColumn<ItemDTO ,Integer> colItemId;
    
    @FXML
    private TableColumn<ItemDTO ,Integer> cartItemId;
    
    
    @FXML
    private TableColumn<ItemDTO, String>colItemName;
    
    @FXML
    private TableColumn<ItemDTO, String>cartItemName;
    
    
    @FXML
    private TableColumn colAction;
    @FXML
    private TableColumn cartAction;
    
    private final SupplierModel spModel = new SupplierModel();
    
    private final String CUSTOMER_NAME_REGEX = "^[a-zA-Z]*$";
    
    ItemViewModel itemViewModel = new ItemViewModel();
    SupplierModel supplierModel = new SupplierModel();
    
    
    ObservableList<ItemDTO> items = FXCollections.observableArrayList();
    ObservableList<ItemDTO> CartItem = FXCollections.observableArrayList();
    
    ObservableList<ItemDTO> selectedItems = FXCollections.observableArrayList();

    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        supplierId.setDisable(true);
        itemTable.setItems(items);
        cartItemTable.setItems(CartItem);

        
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        
        
        cartItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        cartItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        
        
        
        
        colAction.setCellFactory(param -> new TableCell<ItemDTO, Void>() {
            private final Button action = new Button("Add");

            {
                action.setStyle("""
                    -fx-background-color: #3b82f6;
                    -fx-text-fill: white;
                    -fx-background-radius: 6;
                """);

                action.setOnAction(event -> {
                    ItemDTO item = getTableView().getItems().get(getIndex());

                    CartItem.add(item);

                    // Optional: print current parent list for debugging
                    
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    ItemDTO rowItem = getTableView().getItems().get(getIndex());
                    // Set button text based on whether it's already added
                    if (selectedItems.contains(rowItem)) {
                        action.setText("Added");
                    } else {
                        action.setText("Add");
                    }
                    setGraphic(action);
                }
            }
        });
 
        
        cartAction.setCellFactory(cell -> new TableCell<ItemDTO, Void>() {
        
            Button btn = new Button("Remove");
            
            {
                btn.setOnAction(event -> {
                    
                    ItemDTO item = getTableView().getItems().get(getIndex());
                    CartItem.remove(item);
                    cartItemTable.setItems(CartItem);
                    
                    
                });   
            }
           
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item,empty);
                setGraphic(empty?null:btn);
            }
            
        });
        
        loadTable();
        
    } 
    
    
    public void setSupplier(SupplierDTO supplier) {
        
        supplierId.setText(String.valueOf(supplier.getSupplier_id()));
        supplierName.setText(supplier.getSupplier_name());
        supplierEmail.setText(supplier.getEmail());
        supplierContact.setText(supplier.getContact());
        
        loadTableCart(supplier.getSupplier_id());
        
    }

    public void usedfor(String used){
        if(used.equals("ADD") ){
            
            plusImage.setVisible(true);
            btnAdd.setVisible(true);
            saveImage.setVisible(false);
            btnUpdate.setVisible(false);
            btnDelete.setVisible(false);
        }
        
        if(used.equals("Update")){
            plusImage.setVisible(false);
            btnAdd.setVisible(false);
            saveImage.setVisible(true);
            btnUpdate.setVisible(true);
            btnDelete.setVisible(false);
        }
        
        if(used.equals("Delete")){
            btnDelete.setVisible(true);
            plusImage.setVisible(false);
            btnAdd.setVisible(false);
            saveImage.setVisible(false);
            btnUpdate.setVisible(false);
        }
    }

    @FXML
    private void addSupplier() throws SQLException{
        
        String name = supplierName.getText().trim();
        String contact = supplierContact.getText().trim();
        String email = supplierEmail.getText().trim();
        
        if(!name.matches(CUSTOMER_NAME_REGEX)){
            
        }
        
        boolean result  = spModel.checkSupplierexsists(name, email, contact );
        if(!result){
            new Alert(Alert.AlertType.ERROR , "Supplier was not added").show();
            return;
        }
        
        cleanFields();
            
            
        
        
    }
    
    @FXML
    private void updateSupplier(){
        
        String ID = supplierId.getText().trim();
        String name = supplierName.getText().trim();
        String contact = supplierContact.getText().trim();
        String email = supplierEmail.getText().trim();
        
        if(ID == null){
            new Alert(Alert.AlertType.ERROR, "Select a Supplier").show();
        }
        
        if(!name.matches(name) && !contact.matches(name) && !email.matches(name)){
            new Alert(Alert.AlertType.ERROR, "Invalid Input").show();
        }else{
            supDTO = new SupplierDTO(Integer.parseInt(ID) ,name, email , contact );
            
            try {
                boolean result =  spModel.updateSupplierWithItems(supDTO , CartItem);
                if(result){
                    new Alert(Alert.AlertType.INFORMATION, "Updated Successfully !").show();
                    

                }else{
                    new Alert(Alert.AlertType.ERROR, "Update Was UnSuccessful !").show();

                }
                
            } catch (SQLException ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "DataBase Error").show();
            }   
        }
    }
    
    
    @FXML 
    private void deleteSupplier()  {
        String ID = supplierId.getText().trim();
        
        if(ID.isEmpty()){
            new Alert(Alert.AlertType.ERROR, " Plese select a Supplier to delete").show();
        }else{
            try {
                boolean result = spModel.checkSupplierHasItems(Integer.parseInt(ID));
                
                if(result){
                    new Alert(Alert.AlertType.ERROR, "This Supplier is connect to an Item cant delete").show();
                }else{
                    boolean result2 = spModel.deleteSupplier(Integer.parseInt(ID));
                    
                    if(result2){
                        new Alert(Alert.AlertType.INFORMATION, " Supplier is Deleted Successfully").show();
 
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Supplier delete is Unsuccesfull").show();
                    }
                }
                
            } catch (SQLException ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Database Error").show();

            }
        }

    }
    
    
    @FXML
    private void cleanFields(){
        
        supplierId.setText("");
        supplierName.setText("");
        supplierContact.setText("");
        supplierEmail.setText("");
        
    }
    
    @FXML
    private void back(){
        
        SupplierViewController  controller;
        try {
            controller = Navigation.loadWithController(
                    contentPane,
                    "/lk/ijse/inventorymanagmentsystem/SupplierView.fxml"
            );
        } catch (IOException ex) {
            ex.printStackTrace();
            System.getLogger(SupplierViewController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }
    
    @FXML
    private void loadTable(){
       
        try {
            List<ItemDTO> itemList = itemViewModel.getItems();
            items.setAll(itemList);
            itemTable.setItems(items);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
    
    @FXML
    private void loadTableCart(int id){
        try {
            List<ItemDTO> cartItemList = supplierModel.getItemsForItem(id);
            CartItem.setAll(cartItemList);        
            cartItemTable.setItems(CartItem);     
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
}

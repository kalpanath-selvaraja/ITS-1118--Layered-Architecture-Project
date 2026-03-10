/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.inventorymanagmentsystem.bo.BOFactory;
import lk.ijse.inventorymanagmentsystem.bo.custom.ItemBO;
import lk.ijse.inventorymanagmentsystem.bo.custom.SupplierBO;
import lk.ijse.inventorymanagmentsystem.bo.custom.WarrantyBO;
import lk.ijse.inventorymanagmentsystem.dto.ItemDTO;
import lk.ijse.inventorymanagmentsystem.dto.SupplierDTO;
import lk.ijse.inventorymanagmentsystem.dto.WarrantyDTO;

import lk.ijse.inventorymanagmentsystem.util.Navigation;

/**
 *
 * @author kalpanath
 */
public class ItemManageController {
    
    private AnchorPane mainContentPane;
    
    @FXML
    private Label lblAdd;
    
    @FXML
    private Label lblUpdate;

    @FXML
    private Label lblDelete;

    @FXML
    private TableView<SupplierDTO> supplierTable;

    @FXML
    private TableColumn<SupplierDTO, String> colSupplierId;
    
    @FXML
    private TableColumn<SupplierDTO, Void> colAction;

    @FXML
    private TableColumn<SupplierDTO, String> colSupplierName;

    private final ObservableList<SupplierDTO> supplierList = FXCollections.observableArrayList();
    
    @FXML
    private TextField idField;

    @FXML
    private TextField itemNameField;
    
    @FXML
    private TextField itemPriceField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField warrantyField;
    
    @FXML
    private TextField supplierField;
    
    @FXML
    private Button btnAdd;
    
    @FXML
    private Button btnUpdate;
    
    @FXML
    private Button btnDelete;
    
    private int currentSupplierID;
     int warrantyId  ;
    private Button currentlySelectedButton = null;
    private SupplierDTO selectedNewSupplier = null;

    SupplierBO supplierBO = (SupplierBO) BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.SUPPLIER);
    WarrantyBO warrantyBO = (WarrantyBO) BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.WARRANTY);
    ItemBO itemBO = (ItemBO) BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.ITEM);
    
    @FXML
    public void initialize() throws SQLException{
        idField.setDisable(true);
      
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplier_name"));
        
                
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button action = new Button ("Select");
            {
                    
                    
            action.setStyle("""
                -fx-background-color: #3b82f6;
                -fx-text-fill: white;
                -fx-background-radius: 6;
            """);
                    
                    
            action.setOnAction(event ->{
                        
                        
            SupplierDTO supplierDTO = getTableView().getItems().get(getIndex());
                            
            if(action ==  currentlySelectedButton){
                    action.setText("Select");
                    currentlySelectedButton = null;
                    selectedNewSupplier = null;
            } else {
                    if (currentlySelectedButton != null) {
                    currentlySelectedButton.setText("Select");                   
            }
                    action.setText("Selected");
                    currentlySelectedButton = action;
                    currentSupplierID = supplierDTO.getSupplier_id();
                    supplierField.clear();
                    supplierField.setText(supplierDTO.getSupplier_name());
                    
                     selectedNewSupplier = supplierDTO;
            }
            
            });
                }
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);   
                    }else {
                        setGraphic(action);
                    }
                }
        });

        loadSuppliers();
    }
    
    
    public void setMainContentPane(AnchorPane pane) {
        this.mainContentPane = pane;
    }
    
    public void setDetails(ItemDTO selectedItem){
        idField.setText(String.valueOf(selectedItem.getItemId()));
        itemNameField.setText(selectedItem.getItemName());
        quantityField.setText(String.valueOf(selectedItem.getQuantity()));
        warrantyField.setText(String.valueOf(selectedItem.getWarranty()));
        itemPriceField.setText(String.valueOf(selectedItem.getUnitPrice()));
        supplierField.setText(selectedItem.getSupplierName());
        
        
        
        try {
            currentSupplierID = supplierBO.getSupplierIdByName(selectedItem.getSupplierName());
          
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, "Failed to load supplier info: " + ex.getMessage()).show();
            currentSupplierID = -1; 
            
        }
        
        
    }
    

    public void loadSuppliers() throws SQLException {
        supplierList.clear();
        supplierList.addAll(supplierBO.getAllSuppliers());
        supplierTable.setItems(supplierList);
        
    }
    
    public void SetupUses(String Use){
        if("Add".equals(Use)){
            btnUpdate.setVisible(false);
            lblUpdate.setVisible(false);
        }
        
        if("Update".equals(Use)){
            btnAdd.setVisible(false);
            lblAdd.setVisible(false);
        }
    }
    
    
    @FXML
    private void handleUpdateItem(){
       
        
        if (itemNameField.getText().isBlank() ||
            quantityField.getText().isBlank() ||
            itemPriceField.getText().isBlank() ||
            warrantyField.getText().isBlank() ||
            supplierField.getText().isBlank()) {

            new Alert(Alert.AlertType.ERROR, "All fields must be filled!").show();
            return;
        }

        int quantity;
        double price;
        int warrantyValue;

        try {
            quantity = Integer.parseInt(quantityField.getText().trim());
            price = Double.parseDouble(itemPriceField.getText().trim());
            warrantyValue = Integer.parseInt(warrantyField.getText().trim());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Quantity, Price, and Warranty must be numbers").show();
            return;
        }
    
        boolean warrantyMatched = false;
        if (warrantyValue > 0) {
            try {
                List<WarrantyDTO> wDTO = warrantyBO.getWarrantyMonths();

                for (WarrantyDTO dto : wDTO) {
                    if (dto.getMonths() == warrantyValue) {
                        // MATCH FOUND
                        warrantyMatched = true;
                        
                        break;
                    }
                }
            } catch (SQLException ex) {
                new Alert(Alert.AlertType.ERROR,"Database  Error").show();
            }
        }
        
        
        
        ItemDTO itemDTO = null ;
        int id = Integer.parseInt(idField.getText().trim()); // only this one we need
        
        if (currentSupplierID <= 0) {
            new Alert(Alert.AlertType.ERROR, "Please select a valid supplier!").show();
            return;
        }


        if(warrantyMatched || warrantyValue == 0){
             itemDTO = new ItemDTO(
                id,        
                itemNameField.getText(),
                quantity,
                price,
                warrantyValue
                ,currentSupplierID);
        }else{
            new Alert(Alert.AlertType.ERROR, "Sorry Please USE 3 , 6 , 12 for Warranty").show();
            return;
        }
        
        
        
        try{
          warrantyId =  itemBO.getWarrantyId(warrantyValue);
          
           
        
        }catch(SQLException e){
              new Alert(Alert.AlertType.ERROR, "Sorry Database error for Warranty").show();
              return;
        }
        
       try {
            boolean result = itemBO.updateItems(itemDTO,warrantyId);
            if(result){
                new Alert(Alert.AlertType.INFORMATION, "Item updated successfully!").show();
                back();
            } else {
                new Alert(Alert.AlertType.WARNING, "Item update failed!").show();
                back();
            }
        } catch (SQLException ex) {
           new Alert(Alert.AlertType.ERROR, "Database error: ").show();
            ex.printStackTrace();
            
        }

        
        
    }
    
    @FXML
    private void handleAddItem(){
        if (itemNameField.getText().isBlank() ||
            quantityField.getText().isBlank() ||
            itemPriceField.getText().isBlank() ||
            warrantyField.getText().isBlank() ||
            supplierField.getText().isBlank()) {

            new Alert(Alert.AlertType.ERROR, "All fields must be filled!").show();
            return;
        }
        
        if (selectedNewSupplier == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a supplier from the table!").show();
            return;
        }

        
        
        int quantity;
        double price;
        int warrantyValue;

        try {
            quantity = Integer.parseInt(quantityField.getText().trim());
            price = Double.parseDouble(itemPriceField.getText().trim());
            warrantyValue = Integer.parseInt(warrantyField.getText().trim());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Quantity, Price, and Warranty must be numbers").show();
            return;
        }

        
        
        
        boolean warrantyMatched = false;

        if (warrantyValue > 0) {
            try {
                List<WarrantyDTO> wDTO = warrantyBO.getWarrantyMonths();

                for (WarrantyDTO dto : wDTO) {
                    if (dto.getMonths() == warrantyValue) {
                        // MATCH FOUND
                        warrantyMatched = true;

                        break;
                    }
                }

            } catch (SQLException ex) {
                
            }
        }
        
        ItemDTO itemDTO = null ;
        if(warrantyMatched ||warrantyValue == 0){
            itemDTO = new ItemDTO(
                    itemNameField.getText(),
                    quantity,
                    price,
                    warrantyValue,selectedNewSupplier.getSupplier_id());
        }else{
            new Alert(Alert.AlertType.ERROR, "Sorry Please USE 3 , 6 , 12 , 24, 36 ,48 or 60 for Warranty").show();
            return;
        }
        
        
        try {
            warrantyId =  itemBO.getWarrantyId(itemDTO.getWarranty());
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, "Database Error").show();
            
        }
        
        try {
            boolean result = itemBO.save(itemDTO,warrantyId);
            if(result){
                new Alert(Alert.AlertType.INFORMATION, "Item Was Added successfully!").show();
                back();
            } else {
                new Alert(Alert.AlertType.WARNING, "Item Adding wad failed!").show();
                back();
            }
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + ex.getMessage()).show();
            return;
            
        }

        
    }
    
    
    @FXML
    private void back(){
        
        try {
            Navigation.loadWithController(mainContentPane,
                    "/lk/ijse/inventorymanagmentsystem/ItemView.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Load UI Error").show();
        }

    }
    

    

}

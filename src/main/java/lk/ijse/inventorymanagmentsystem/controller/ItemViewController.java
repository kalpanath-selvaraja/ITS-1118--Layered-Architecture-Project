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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.inventorymanagmentsystem.dto.ItemDTO;
import lk.ijse.inventorymanagmentsystem.model.ItemViewModel;
import lk.ijse.inventorymanagmentsystem.util.Navigation;

/**
 * FXML Controller class
 *
 * @author kalpanath
 */
public class ItemViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane contentPane;
    
    
    //private final String FXML = "/lk/ijse/inventorymanagmentsystem/UpdateAndDeleteItems.fxml";


     @FXML
    private TableView<ItemDTO> itemTable;
    
    

    
    @FXML
    private TextField itemNameField;

    @FXML
    private TableColumn<ItemDTO, Integer> itemId;
    @FXML
    private TableColumn<ItemDTO, String> itemName;
    @FXML
    private TableColumn<ItemDTO, Double> unitPrice;
    @FXML
    private TableColumn<ItemDTO, Integer> quantity;
    @FXML
    private TableColumn<ItemDTO, Integer> warranty;
    @FXML
    private TableColumn<ItemDTO, String> supplier;
    @FXML
    private TableColumn<ItemDTO, String> stockStatus;
    
    @FXML
    private TableColumn<ItemDTO, Void> action;
    
    


    @FXML
    private ComboBox<String> statusFilter;

    private final ObservableList<ItemDTO> masterItemList = FXCollections.observableArrayList();

    
    private final String CUSTOMER_NAME_REGEX = "^[a-zA-Z]*$";

   

    private final ItemViewModel itemViewModel = new ItemViewModel();
    
    private  Button currentlySelectedButton = null;
    private static ItemDTO selectedItem = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        itemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        unitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        

        
        
        
        warranty.setCellValueFactory(new PropertyValueFactory<>("warranty"));
        
        supplier.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        
        
        stockStatus.setCellValueFactory(cellData -> {
            int qty = cellData.getValue().getQuantity();

            if (qty == 0) {
                
                
                return new javafx.beans.property.SimpleStringProperty("OUT OF STOCK");
            } else if (qty <= 5) {
                return new javafx.beans.property.SimpleStringProperty("LOW STOCK");
            } else {
                return new javafx.beans.property.SimpleStringProperty("AVAILABLE");
            }
        });
        
        stockStatus.setCellFactory(column -> new TableCell<>() {

            private final Label label = new Label();

            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);

                if (empty || status == null) {
                    setGraphic(null);
                    return;
                }

                label.setText(status);
                label.getStyleClass().clear();

                switch (status) {
                    case "OUT OF STOCK" -> label.getStyleClass().add("status-out");
                    case "LOW STOCK" -> label.getStyleClass().add("status-low");
                    case "AVAILABLE" -> label.getStyleClass().add("status-available");
                }

                label.setMaxWidth(Double.MAX_VALUE);
                label.setAlignment(Pos.CENTER);

                setGraphic(label);
            }
        });
        action.setCellFactory(param -> new TableCell<>() {
                private final Button action = new Button ("Select");
                {
                    
                    
                    action.setStyle("""
                        -fx-background-color: #3b82f6;
                        -fx-text-fill: white;
                        -fx-background-radius: 6;
                    """);
                    
                    
                    action.setOnAction(event ->{
                        
                        
                        ItemDTO itemDTO = getTableView().getItems().get(getIndex());
                            
                        if(action ==  currentlySelectedButton){
                            action.setText("Select");
                            currentlySelectedButton = null;
                            selectedItem = null;
                            

                        } else {

                            if (currentlySelectedButton != null) {
                                currentlySelectedButton.setText("Select");
                                
                                
                            }


                            action.setText("Selected");
                            currentlySelectedButton = action;
                            selectedItem = itemDTO;
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

        setupStatusFilter();
        loadItems();

    }
    
 
   @FXML
    private void handleItemSearch(KeyEvent event ){
        if(event.getCode() == KeyCode.ENTER){
            String Name = itemNameField.getText().trim();
            
            if(!Name.matches(CUSTOMER_NAME_REGEX)){ 
                new Alert(Alert.AlertType.ERROR, "Please Eneter Item Name").show();
            } else {
                try{
                    
                    
                    
                    List<ItemDTO> ivDTO = itemViewModel.searchItem(Name);
                    
                    
                    if (ivDTO != null) {
                        
                        loadTable(ivDTO);
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Item not found!").show();
                    }


                } catch(SQLException e) {
                       e.printStackTrace();
                }
            }
        }
    }
    
    private void loadItems() {
        try {
            List<ItemDTO> itemList = itemViewModel.getItems();
            masterItemList.setAll(itemList);
            itemTable.setItems(masterItemList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private void setupStatusFilter() {

            statusFilter.getItems().addAll(
                    "ALL",
                    "AVAILABLE",
                    "LOW STOCK",
                    "OUT OF STOCK"
            );

            statusFilter.setValue("ALL"); 

            statusFilter.setOnAction(event -> applyFilter());
        }
    
    private void applyFilter() {

        String selectedStatus = statusFilter.getValue();

        if (selectedStatus.equals("ALL")) {
            itemTable.setItems(masterItemList);
            return;
        }

        ObservableList<ItemDTO> filteredList = FXCollections.observableArrayList();

        for (ItemDTO item : masterItemList) {

            int qty = item.getQuantity();

            if (selectedStatus.equals("OUT OF STOCK") && qty == 0) {
                filteredList.add(item);
            } 
            else if (selectedStatus.equals("LOW STOCK") && qty > 0 && qty <= 5) {
                filteredList.add(item);
            } 
            else if (selectedStatus.equals("AVAILABLE") && qty > 5) {
                filteredList.add(item);
            }
        }

        itemTable.setItems(filteredList);
    }


    
    private void loadTable(List<ItemDTO> dto) { 
        
        // TO reload the table with the search up

        ObservableList<ItemDTO> list = itemTable.getItems();  // cheks by the loaded Table
        
        System.out.println("  ----   ");

        if (list == null) {
            list = FXCollections.observableArrayList();
            itemTable.setItems(list);
        }

        list.clear(); // optional (clear previous search result)
        list.addAll(dto);
    }
    

    @FXML
    private void handleButtonEdit(ActionEvent event ) {
        
        if(selectedItem == null){
            new Alert(Alert.AlertType.ERROR, "Please Select An Item to Upddate").show();
            return;
        }
        
        ItemManageController controller;
        try {
            controller = Navigation.loadWithController(
                    contentPane,
                    "/lk/ijse/inventorymanagmentsystem/itemManagment.fxml"
            );
            try {
                controller.loadSuppliers();
                controller.SetupUses("Update");
                
            } catch (SQLException ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR ,"UI config Error").show();
    
            }
            controller.setDetails(selectedItem);
            controller.setMainContentPane(contentPane);

            selectedItem = null;
        } catch (IOException ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR ,"Load UI Error").show();
        }  
    }
        
        
        

    @FXML
    private void handleButtonAdd(ActionEvent event ) {
        ItemManageController controller;
        try {
            controller = Navigation.loadWithController(
                    contentPane,
                    "/lk/ijse/inventorymanagmentsystem/itemManagment.fxml"
            );
            
            try {
                controller.loadSuppliers();
            } catch (SQLException ex) {
                System.getLogger(ItemViewController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
                controller.SetupUses("Add");
                controller.setMainContentPane(contentPane);
                selectedItem = null;
            
        } catch (IOException ex) {
            ex.printStackTrace();
            System.getLogger(SupplierViewController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }  
    }  
        

    


}

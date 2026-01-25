/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import lk.ijse.inventorymanagmentsystem.App;
import lk.ijse.inventorymanagmentsystem.util.Session;

public class DashboardController {



    @FXML
    private VBox sideMenuContainer;
    
    @FXML
    private AnchorPane mainContent;

    private boolean isCollapsed = false;
    
    @FXML
    private Label unField ;
    
    @FXML
    private Button QuickSale;
    @FXML
    private Button order;
    @FXML
    private Button customer;
    @FXML
    private Button items;
    @FXML
    private Button home;
    @FXML
    private Button supplier;
    
    @FXML
    private Button user;


    @FXML
    private Button warrnaty;
    
    
    private List<Button> sidebarButtons;
    
    @FXML
    private VBox sidebar;

    @FXML
    private StackPane root;

   
    
    
    
    
    @FXML
    public void initialize() throws IOException {
        
        //System.out.println(Session.getCurrentUser());
        sidebarButtons = new ArrayList<>();
        sidebarButtons.add(order);
        sidebarButtons.add(customer);
        sidebarButtons.add(items);
        sidebarButtons.add(QuickSale);
        sidebarButtons.add(home);
        sidebarButtons.add(user);
        sidebarButtons.add(warrnaty);
        sidebarButtons.add(supplier);

        for (Button btn : sidebarButtons) {
            if (btn == null) {
                System.out.println("A sidebar button is null!");
            }
        }
        
        root.getStylesheets().add(getClass().getResource("/styles/dashboard.css").toExternalForm());
        

        setActiveButton(home);
        loadUI("Home");
        
        unField.setText(Session.getCurrentUserName());
    }

    
    
    
    
    
    
    
 

    @FXML
    private void goCustomer() throws IOException {
        Parent ui = FXMLLoader.load(getClass().getResource("Customer.fxml"));
        mainContent.getChildren().setAll(ui);
    }
    
    
    
    
    private void setActiveButton(Button activeBtn) {
        for (Button btn : sidebarButtons) {
            btn.getStyleClass().remove("sidebar-btn-active"); // remove previous highlight
        }
        activeBtn.getStyleClass().add("sidebar-btn-active"); // highlight current
    }
    
    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to logout?",
                ButtonType.YES, ButtonType.NO);

        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Session.clear();
            try {
                App.setRoot("Login");
            } catch (IOException ex) {
                new Alert(Alert.AlertType.ERROR, "Load login UI Failed").show();
                return;
            }
        }
    }

    
    @FXML
    private BorderPane mainPane;
    
    
    @FXML
    public void Home(ActionEvent event) {
        setActiveButton(home);
        loadUI("Home");
    }


    @FXML
    private void quickSales(ActionEvent event) {
        setActiveButton(QuickSale);
        loadUI("QuickSale");
    }

    @FXML
    private void loadItems(ActionEvent event) {
        setActiveButton(items);
        loadUI("ItemView");
    }

    @FXML
    private void loadCustomers(ActionEvent event) {
        setActiveButton(customer);
        loadUI("CustomerView");
    }

    @FXML
    private void order(ActionEvent event) {
        setActiveButton(order);
        loadUI("OrderView");
    }
    
    @FXML
    private void loadSupplier(ActionEvent event) {
        setActiveButton(supplier);
        loadUI("SupplierView");
    }
    
    @FXML
    private void loadWarranty(ActionEvent event) {
        setActiveButton(warrnaty);
        loadUI("WarrantyView");
    }
    
    @FXML
    private void loadUsers(ActionEvent event) {
        setActiveButton(user);
        loadUI("Users");
    }

    @FXML
    private void loadReports(ActionEvent event) {
        setActiveButton(user);
        loadUI("ReportsView");
    }

    
    @FXML
    private AnchorPane contentArea;

    private void loadUI(String fxmlFile) {
        try {
            // Load the new FXML
            Parent newContent = FXMLLoader.load(getClass().getResource("/lk/ijse/inventorymanagmentsystem/" + fxmlFile + ".fxml"));

            // Clear old content
            contentArea.getChildren().clear();

            // Make the new content fill the anchor pane
            contentArea.getChildren().add(newContent);
            AnchorPane.setTopAnchor(newContent, 0.0);
            AnchorPane.setBottomAnchor(newContent, 0.0);
            AnchorPane.setLeftAnchor(newContent, 0.0);
            AnchorPane.setRightAnchor(newContent, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

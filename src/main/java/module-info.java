module lk.ijse.inventorymanagmentsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires net.sf.jasperreports.core;

    // JFoenix

    // MySQL Connector/J 9.5.0
    requires mysql.connector.j;
    requires java.desktop;
    requires javafx.graphics;
//    requires lk.ijse.inventorymanagmentsystem;
//    requires lk.ijse.inventorymanagmentsystem;
//    requires lk.ijse.inventorymanagmentsystem;
//    requires lk.ijse.inventorymanagmentsystem;

    // Open packages for reflection (FXML)
    opens lk.ijse.inventorymanagmentsystem to javafx.fxml;
    opens lk.ijse.inventorymanagmentsystem.controller to javafx.fxml;
    opens lk.ijse.inventorymanagmentsystem.dto to javafx.base;


    // Export packages
    exports lk.ijse.inventorymanagmentsystem;
    exports lk.ijse.inventorymanagmentsystem.controller;
}

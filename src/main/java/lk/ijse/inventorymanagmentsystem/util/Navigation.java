/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.util;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author kalpanath
 */
public class Navigation {


    // Simple load (no data)
    public static void load(AnchorPane container, String fxml) throws IOException {
        Parent root = FXMLLoader.load(Navigation.class.getResource(fxml));
        setContent(container, root);
    }

    // Load with controller access (for data passing)
    public static <T> T loadWithController(
            AnchorPane container,
            String fxml
    ) throws IOException {

        FXMLLoader loader = new FXMLLoader(Navigation.class.getResource(fxml));
        Parent root = loader.load();

        setContent(container, root);

        return loader.getController();
    }

    // Shared anchor logic
    private static void setContent(AnchorPane container, Parent content) {
        container.getChildren().setAll(content);

        AnchorPane.setTopAnchor(content, 0.0);
        AnchorPane.setBottomAnchor(content, 0.0);
        AnchorPane.setLeftAnchor(content, 0.0);
        AnchorPane.setRightAnchor(content, 0.0);
    }
}

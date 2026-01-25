package lk.ijse.inventorymanagmentsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        // Load FXML
        //Parent root = loadFXML("QuickSale");
        
        //Parent root = loadFXML("Dashboard");
        //Parent root = loadFXML("CustomerView");
        Parent root = loadFXML("Login");
        //Parent root = loadFXML("ItemView");
        //Parent root = loadFXML("SupplierView");
        //Parent root = loadFXML("WarrantyView");
       //Parent root = loadFXML("Users");

        // Create scene
        scene = new Scene(root);
        
        
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        
        
        System.out.println(bounds.getHeight());
        System.out.println(bounds.getWidth());
        System.out.println(bounds.getMinY());
        System.out.println(bounds.getMinX());

        // Attach your CSS
        //scene.getStylesheets().add(getClass().getResource("/styles/dashboard.css").toExternalForm());

        // Set scene to stage and show
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}


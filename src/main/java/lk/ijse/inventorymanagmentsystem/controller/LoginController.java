package lk.ijse.inventorymanagmentsystem.controller;

import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import lk.ijse.inventorymanagmentsystem.App;
import lk.ijse.inventorymanagmentsystem.bo.custom.BOFactory;
import lk.ijse.inventorymanagmentsystem.bo.custom.EmployeeBO;
import lk.ijse.inventorymanagmentsystem.bo.custom.UserBO;
import lk.ijse.inventorymanagmentsystem.dto.EmployeeDTO;
import lk.ijse.inventorymanagmentsystem.dto.UserDTO;
import lk.ijse.inventorymanagmentsystem.util.Session;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;



public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    
    
    @FXML
    private ImageView slideshowImage;

    
    private final String[] images = {
        "/images/picture1.jpg",
        "/images/picture2.jpg",
        "/images/picture3.jpg"

    };

    private int currentIndex = 0;
    private Timeline timeline;


    // store the logged-in user ID
    EmployeeDTO eDTO =new EmployeeDTO();

    UserDTO user = null;
    
    
    UserBO userBO = (UserBO) BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.USER);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.EMPLOYEE);

    @FXML
    public void initialize() {
        loadImage();
        startSlideshow();
    }

    private void loadImage() {
        Image image = new Image(
                getClass().getResource(images[currentIndex]).toExternalForm()
        );
        slideshowImage.setImage(image);
    }

    private void startSlideshow() {
        timeline = new Timeline(
            new KeyFrame(Duration.seconds(3), e -> {
                currentIndex = (currentIndex + 1) % images.length;
                loadImage();
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void logIn() {
        String username = usernameField.getText();
        String password = passwordField.getText();


        
        if (username.isBlank() || password.isBlank()) {
            new Alert(Alert.AlertType.ERROR, "Username and password cannot be empty!").show();
            return;
        }

        try {
            user = userBO.login(username, password);
            if(user == null){
                new Alert(Alert.AlertType.ERROR, "Invalid username or password!").show();
                return;
            }

            int currentUserId = user.getUserId();
            int employeeId = employeeBO.getEmployeeId(currentUserId);
            String role = user.getRole();

            if(employeeId == 0){
                new Alert(Alert.AlertType.ERROR,"No employee found for this user!").show();
                return;
            }

            Session.setUserId(currentUserId);
            Session.setEmpId(employeeId);
            Session.setCurrentUser(role);
            Session.setCurrentUserName(username);

            App.setRoot("Dashboard");

        } catch(SQLException ex) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + ex.getMessage()).show();
        } catch(IOException ex) {
            new Alert(Alert.AlertType.ERROR, "UI loading error: " + ex.getMessage()).show();
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.inventorymanagmentsystem.bo.custom.BOFactory;
import lk.ijse.inventorymanagmentsystem.bo.custom.EmployeeBO;
import lk.ijse.inventorymanagmentsystem.bo.custom.UserBO;
import lk.ijse.inventorymanagmentsystem.dto.EmployeeDTO;
import lk.ijse.inventorymanagmentsystem.dto.UserDTO;
import lk.ijse.inventorymanagmentsystem.util.Navigation;
import lk.ijse.inventorymanagmentsystem.util.Session;


/**
 * FXML Controller class
 *
 * @author kalpanath
 */
public class UsersController implements Initializable {

    /**
     * Initializes the controller class.
     */


    @FXML
    private AnchorPane contentPane;

    String FXML= "/lk/ijse/inventorymanagmentsystem/SupplierManagment.fxml";

    @FXML
    private TableView<UserDTO> tblUser;

    @FXML
    private TableColumn<UserDTO, String> colId;

    @FXML
    private TableColumn<UserDTO, String> colUsername;

    @FXML
    private TableColumn<UserDTO, String> colPassword;

    @FXML
    private TableColumn<UserDTO, Void> colAction;
    
    
    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordContactField;

    @FXML
    private TextField roleField;
    
    @FXML
    private TableColumn<UserDTO, String> colRole;
    
    
    
    @FXML
    private TableView<EmployeeDTO> empTable;

    @FXML
    private TableColumn<EmployeeDTO, String> colEmpId;

    @FXML
    private TableColumn<EmployeeDTO, String> colEmpname;

    @FXML
    private TableColumn<EmployeeDTO, String> colEmpContact;

    @FXML
    private TableColumn<EmployeeDTO, Void> colEmpActionAdd
            ;
    @FXML
    private TableColumn<EmployeeDTO, Void> colEmpActionSelect;
    
    @FXML
    private TableColumn<EmployeeDTO, String> empUserId;
    

    private final ObservableList<UserDTO> userList = FXCollections.observableArrayList();
    private final ObservableList<EmployeeDTO> EmployeeList = FXCollections.observableArrayList();
    
    UserBO userBO= (UserBO) BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.USER);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.EMPLOYEE);
    private static UserDTO selectedUser; // THIS is the bridge
    private EmployeeDTO selectedEmployee; // THIS is the bridge

    
    private final String USER_NAME_REGEX = "^[A-Za-z0-9]{3,}$";
    private final String EMPLOYEE_NAME_REGEX = "^[A-Za-z]{3,}$";
    private final String USER_PASSWORD_REGEX = "^[A-Za-z0-9]{4,}$";
    private final String EMPLOYEE_CONTACT_REGEX = "^[0-9]{10}$";
    private final String USER_ROLE_REGEX = "^[A-Za-z]{3,20}$";
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String role = Session.getCurrentUser();


        if (!"Admin".equalsIgnoreCase(role)) {
            // Show error message
            new Alert(Alert.AlertType.ERROR,
                    "Access Denied! This feature is only available to administrators.").show();

            try {
                Navigation.load(contentPane,FXML); // Stop initialization
            } catch (IOException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Ui  error occurred. Please try again.").show();
            }
        }


            
        colId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
           
        
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button btnUpdate = new Button("Select");
            {
                btnUpdate.setOnAction(e -> {
                    selectedEmployee = null;
                    UserDTO user = getTableView().getItems().get(getIndex());  
                    
                    selectedUser = user;
                    getTableView().refresh();
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    UserDTO user = getTableView().getItems().get(getIndex());

                    if (user == selectedUser) {
                        btnUpdate.setText("Selected");
                    } else {
                        btnUpdate.setText("Select");
                    }

                    setGraphic(btnUpdate);
                }
            }
        });
        
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        colEmpname.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colEmpContact.setCellValueFactory(new PropertyValueFactory<>("empContact"));
        empUserId.setCellValueFactory(new PropertyValueFactory<>("empUserId"));
           
        
        colEmpActionAdd.setCellFactory(param -> new TableCell<>() {
            private final Button btnADD = new Button("ADD");
            {
                btnADD.setOnAction(e -> {
                    
                    EmployeeDTO emp = getTableView().getItems().get(getIndex());  
                    try {
                        
                        if(selectedUser == null){
                            
                            new Alert(Alert.AlertType.ERROR, "Please select a User").show();
                            return;
                        }
                        
                        int usrId = emp.getEmpUserId();
                        
                        System.out.println(usrId);// Null
                        
                        if(usrId != 0){
                            new Alert(Alert.AlertType.ERROR, "Soory this employee alrady has an User Account").show();
                            //selectedUser // make here this null;
                            return;
                        }
                        
                        
                        
                        int result = employeeBO.checkUserId(selectedUser.getUserId());
                        System.out.println(result);
                        
                        if(result>0){
                            new Alert(Alert.AlertType.INFORMATION,"Sorry This User ID is Already assigned").show();
                            selectedUser = null;
                            return;
                        }
                        
                        boolean isAdded = employeeBO.addEmployeeByUser(selectedUser.getUserId(), String.valueOf(emp.getEmployeeID()));
                        
                        if(isAdded){
                            new Alert(Alert.AlertType.INFORMATION, "User Accounnt was Successfully Added to Employee").show();
                            selectedUser = null;
                            loadUserTable();
                            loadEmployeeTable();
                        }
                        
                        //user.getCurrentUserId()
                    } catch (SQLException ex) {
                            new Alert(Alert.AlertType.ERROR, "Failed in User Account Assigning").show();

                        System.getLogger(UsersController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    
                    setGraphic(btnADD);
                }
            }
        });
        
        colEmpActionSelect.setCellFactory(param -> new TableCell<>() {
            
            
            private final Button btnSelectEmp = new Button("Select");
            {
                btnSelectEmp.setOnAction(e -> {
                    
                    selectedUser = null;
                    
                        
                    EmployeeDTO emp = getTableView().getItems().get(getIndex());
                    selectedEmployee = emp;
                     getTableView().refresh();
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    EmployeeDTO emp = getTableView().getItems().get(getIndex());
                    
                    if(emp == selectedEmployee){
                        btnSelectEmp.setText("Selected");
                    }else{
                        btnSelectEmp.setText("Select");
                    }
                    setGraphic(btnSelectEmp);
                }
            }
        });
        
        
        
        loadUserTable();
        loadEmployeeTable();
    }
    
    @FXML
    private void removeUser(){
        
        if(selectedEmployee == null){
                new Alert(Alert.AlertType.ERROR, "Please Select a  Employee ").show();
                selectedUser = null;
                return;
        }
        
        try {
            
            int result1 = employeeBO.checkUserId(selectedEmployee.getEmpUserId());
            
            if(result1 == -1){
                new Alert(Alert.AlertType.INFORMATION, "This Employee has No User Accounts to Remove").show();
                selectedEmployee = null;
                
                return;
                
            }
            
            boolean result2  = employeeBO.removeuser(selectedEmployee.getEmpUserId());
            
            if(result2){
                new Alert(Alert.AlertType.INFORMATION, "Employee Assigned removed").show();
                
                loadEmployeeTable();
                loadUserTable();
                
                
            }
            
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, "User_ID Error").show();
            System.getLogger(UsersController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    private void loadUserTable(){

        try {

            userList.clear();
            userList.addAll(userBO.getAllUsers());
            

            
            tblUser.setItems(userList);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    
    private void loadEmployeeTable(){

        try {

            EmployeeList.clear();
            EmployeeList.addAll(employeeBO.getAllEmployee());
            
            empTable.setItems(EmployeeList);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
        
    public void cleanFields(){
        nameField.clear();
            passwordContactField.clear();
            roleField.clear();
    }
    
    @FXML
    public void deleteUser() {
        if (selectedUser == null) {
            new Alert(Alert.AlertType.ERROR,"please Select a User").show();
            return;
        }
        try {
            int result = employeeBO.checkUserId(selectedUser.getUserId());
            
            if(result != -1){
                new Alert(Alert.AlertType.ERROR,"This User is Assgned to an Employee can Delete Without removing").show();
                selectedUser = null;
                return;
            }
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,"DataBase Error ").show();

        }
        

        try {

            int usercount = userBO.getUserCount();

            if(usercount <= 1){
                new Alert(Alert.AlertType.INFORMATION,"Cannot delete the last user. At least one user must exist in the system").show();
                return;
            }

            boolean isDeleted = userBO.deleteUser(selectedUser.getUserId());

            if (isDeleted) {
                loadUserTable();
                selectedUser = null;
                new Alert(Alert.AlertType.INFORMATION,"User Was deleted SuccesFully").show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addUser(){
        
        
        String userName = nameField.getText(); 
        String password = passwordContactField.getText();
        String role = roleField.getText();
        
        if(!userName.matches(USER_NAME_REGEX)){
            new Alert(Alert.AlertType.ERROR,"Invalid Name Input").show();
            return;
        }
        
        if( !password.matches(USER_PASSWORD_REGEX)){
            new Alert(Alert.AlertType.ERROR,"Invalid PassWord Input").show();
            return;
        }
        
        if(!role.matches(USER_ROLE_REGEX)){
            new Alert(Alert.AlertType.ERROR,"Invalid Role Input").show();
            return;
        }
        
        UserDTO userDTO = new UserDTO(userName, password, role);
        
        try {
            boolean result = userBO.addUser(userDTO);
            
            if(result){
                new Alert(Alert.AlertType.INFORMATION,"User was Added succesfully ").show();
                loadEmployeeTable();
                loadUserTable();
                cleanFields();
            }else {
                new Alert(Alert.AlertType.WARNING, "User adding failed").show();
            }
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,"DataBase Error ").show();
        }
        
    }
    
    
    @FXML
    public void addEmployee() {

        String employeeName = nameField.getText().trim();
        String contact = passwordContactField.getText().trim();
        

        if (!employeeName.matches(EMPLOYEE_NAME_REGEX)|| !contact.matches(EMPLOYEE_CONTACT_REGEX)) {

            new Alert(Alert.AlertType.ERROR, "Invalid Input").show();
            return;
        }

       

        EmployeeDTO employeeDTO = new EmployeeDTO(
                employeeName,
                contact
                
        );

        try {
            boolean result = employeeBO.saveEmployee(employeeDTO);

            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Employee was added successfully").show();
                loadEmployeeTable();
                loadUserTable();
                cleanFields();

            } else {
                new Alert(Alert.AlertType.WARNING, "Employee adding failed").show();
                
            }

        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, "Database Error").show();
        }
    }

    

}

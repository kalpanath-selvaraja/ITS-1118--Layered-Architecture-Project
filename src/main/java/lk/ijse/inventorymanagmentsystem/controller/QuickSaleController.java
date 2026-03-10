
package lk.ijse.inventorymanagmentsystem.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import lk.ijse.inventorymanagmentsystem.bo.custom.BOFactory;
import lk.ijse.inventorymanagmentsystem.bo.custom.ItemBO;
import lk.ijse.inventorymanagmentsystem.bo.custom.RecordSaleBO;
import lk.ijse.inventorymanagmentsystem.dto.CartItemDTO;
import lk.ijse.inventorymanagmentsystem.dto.CheckoutDTO;
import lk.ijse.inventorymanagmentsystem.dto.CustomerDTO;
import lk.ijse.inventorymanagmentsystem.dto.ItemDTO;
import lk.ijse.inventorymanagmentsystem.util.Reports;
import lk.ijse.inventorymanagmentsystem.util.Session;
import net.sf.jasperreports.engine.JRException;

/**
* FXML Controller class
 *
 * @author kalpanath
 */
public class QuickSaleController {   
    @FXML
    private Label totalLabel; // JavaFX Label

    @FXML
    private TextField itemNameField;
    
    @FXML
    private TableView<ItemDTO> tblItems;

    @FXML
    private TableColumn<ItemDTO, String> colItemName;
    
    @FXML
    private TableColumn<ItemDTO, Integer> colItemId;
    
    @FXML
    private TableColumn<ItemDTO, Double> colPrice;
    
    @FXML
    private TableColumn<ItemDTO, Integer> colWarranty;

    @FXML
    private TableView<CartItemDTO> tblCart;
    
    @FXML
    private TableColumn<CartItemDTO , Integer> colCartItemId;

    @FXML
    private TableColumn<CartItemDTO , String> colCartItemName;
    
    @FXML
    private TableColumn<CartItemDTO , Integer> colCartQty;
    
    @FXML
    private TableColumn<CartItemDTO , Double> colCartPrice;
    
    @FXML
    private TableColumn<CartItemDTO , Integer> colCartWarranty;

    

    
    private final ObservableList<ItemDTO> masterItemList = FXCollections.observableArrayList();

    
    
    private final String ITEM_QTY_REGEX = "^[1-9][0-9]*$";
    private final String CUSTOMER_NAME_REGEX = "^[a-zA-Z]*$";
    private final String CUSTOMER_CONTACT_REGEX = "^[0-9]{10}$";
    


    
    private final ObservableList<CartItemDTO> cart = FXCollections.observableArrayList();
    
   

    
    @FXML
    private TableColumn<ItemDTO, Void> colAction;
    
    @FXML
    private TableColumn<CartItemDTO, Void> colActionCart;


    RecordSaleBO recordSaleBO = (RecordSaleBO) BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.RECORDSALE);

    ItemBO itemBO =  (ItemBO) BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.ITEM);
    
    @FXML
    public void initialize() {

        // Items Table
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colWarranty.setCellValueFactory(new PropertyValueFactory<>("warranty"));
        
        // Cart table
        
        // Cart table setup
        
        colCartItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colCartItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colCartQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCartPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colCartWarranty.setCellValueFactory(new PropertyValueFactory<>("warranty"));

        tblCart.setItems(cart);

        // Button foer the item table
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Add to Cart");

            {
                btn.setStyle("""
                    -fx-background-color: #3b82f6;
                    -fx-text-fill: white;
                    -fx-background-radius: 6;
                """);

                // Attach event handler here
                btn.setOnAction(event -> {
                    ItemDTO currentItem = getTableView().getItems().get(getIndex() );
                    addToCart(currentItem);

                    updateTotalLabel();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
            }
        });
        
                //Button for the cart tacle
        
        colActionCart.setCellFactory(parm -> new TableCell<>(){
           
            private final Button btnDelete = new Button("DELETE");
            
            {
                btnDelete.setOnAction(event ->{
                    CartItemDTO  selectedItem = getTableView().getItems().get(getIndex());

                    cart.remove(selectedItem);
                    updateTotalLabel();
                });
            }
        
            @Override
            protected void updateItem(Void item, boolean empty){
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnDelete);
                }
            }
        });
        
        loadTableDefault();

    }    
    
    
    
    
    
    private int total (){
        
        int total = 0;
        for (CartItemDTO c : cart) {
            total += c.getUnitPrice() * c.getQuantity();
        }
        
        return total;
    }
    
    
    
    
    @FXML
    private void toSave(ActionEvent event) throws SQLException {

        if (cart.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Cart is empty").show();
            return;
        }

        
        
        int empId = Session.getEmpId();
                
                
        System.out.println("sgjbh"+empId);

        boolean needsCustomer = isCustomerRequired();
        CustomerDTO customer = null;

        if (needsCustomer) {
            customer = showCustomerDialog();

            if (customer == null) {
                return; // user cancelled dialog
            }
        }

        CheckoutDTO checkoutDTO = new CheckoutDTO(
                cart,           // ObservableList<CartItemDTO>
                empId,
                customer,
                needsCustomer,
                total()
        );
        
        int result = recordSaleBO.processCheckout(checkoutDTO);

        if (result >0) {
            try {
                Reports.printInvoice(result);
            } catch (JRException ex) {
                new Alert(Alert.AlertType.ERROR, "Printing Error!").show();

            }
            new Alert(Alert.AlertType.INFORMATION, "Order placed successfully!").show();
            clearCart();
        }
    }

    
    
    
    

    private boolean isCustomerRequired() {
        for (CartItemDTO item : cart) {
            if (item.getWarranty()>= 12) {
                return true;
            }
        }
        return false;
    }

    private CustomerDTO showCustomerDialog() {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Customer Details");
        dialog.setHeaderText("Warranty item requires customer info");

         
        ButtonType saveBtn = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);

        TextField txtName = new TextField();
        TextField txtContact = new TextField();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Name"), 0, 0);
        grid.add(txtName, 1, 0);
        grid.add(new Label("Contact"), 0, 1);
        grid.add(txtContact, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == saveBtn) {
            
            if(!txtName.getText().matches(CUSTOMER_NAME_REGEX)|| !txtContact.getText().matches(CUSTOMER_CONTACT_REGEX)){
                new Alert(Alert.AlertType.ERROR, "Invalid Input").show();
                return null;
            }

            if (txtName.getText().isEmpty() || txtContact.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "All fields required").show();
                return null;
            }

            return new CustomerDTO(
                    txtName.getText(),
                    txtContact.getText()
            );
        }

        return null;
    }

 

        
        
    private void updateTotalLabel() {
        double total = 0;

        for (CartItemDTO c : cart) {
            total += c.getUnitPrice()* c.getQuantity();
        }

        totalLabel.setText(String.format("%.2f", total));
    }

    
    public void showCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart empty");
            return;
        }
            for (CartItemDTO ci : cart) {
                System.out.println(
                    ci.getQuantity() + " x " + ci.getItemName() +
                    " | Unit Price: " + ci.getUnitPrice()+
                    " | Warranty: " + ci.getWarranty()+ " months" +
                    " | Line Total: " + (ci.getQuantity() * ci.getUnitPrice())
                );
            }

        
    }
    
    @FXML
    private void handleItemSearch(KeyEvent event ){
        if(event.getCode() == KeyCode.ENTER){
            String itemName = itemNameField.getText().trim();
            
            if(!itemName.matches(CUSTOMER_NAME_REGEX)){ 
                new Alert(Alert.AlertType.ERROR, "Please Eneter Item Name").show();
            } else {
                try {
                    
                    
                    
                    List<ItemDTO> itemDTO = itemBO.searchItem(itemName);
                    
                    
                    if (itemDTO != null) {
                        
                        loadTable(itemDTO);
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Item not found!").show();
                    }


                    } catch(SQLException e) {
                        e.printStackTrace();
                }
            }
        }
    }
    

    private void loadTable(List<ItemDTO> dto) {

        masterItemList.clear(); //clear previous search result
        masterItemList.addAll(dto);
        tblItems.setItems(masterItemList);
    }
    
    private void loadTableDefault(){
        try {
            List<ItemDTO>  list = itemBO.getItems();
            masterItemList.clear();
            masterItemList.addAll(list);
            tblItems.setItems(masterItemList);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.getLogger(QuickSaleController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }
    
    @FXML
    private void addToCart(ItemDTO itemDTO){
        
        
        
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Add to Cart");
        dialog.setHeaderText(itemDTO.getItemName());
        dialog.setContentText("Enter quantity:");

        Optional<String> result = dialog.showAndWait();

        //if user clicks Cancel
        if (result.isEmpty()) {
            return;
        }
        int qty;
        try{
            qty = Integer.parseInt(result.get());
            if(qty <=0){
                throw new NumberFormatException();
            }
            
            if(qty > itemDTO.getQuantity()){
                new Alert(Alert.AlertType.ERROR, "Quamtity exedes the stock Availability!").show();
                return;
            }
        } catch(NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Input a valid quantity!").show();
            return;
        }
        
        CartItemDTO cartItem = new CartItemDTO(
                
                    itemDTO.getItemId(),
                    itemDTO.getItemName(),
                    itemDTO.getUnitPrice(),
                    qty,
                    itemDTO.getWarranty()
                    
        );
            
        cartItem.setQuantity(qty);
                
        cart.add(cartItem);
        System.out.println("Added to cart: " + itemDTO.getItemName());

        updateTotalLabel();
        showCart();



        
    }   
    
    
    
    public void clearCart() {
    cart.clear();
    }
    
    public void cleanFields(){
        cart.clear();
        
    }

}
//

                   /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.dto;

                   import lk.ijse.inventorymanagmentsystem.entity.Item;

                   import java.util.List;

                   /**
 *
 * @author kalpanath
 */
public class ItemDTO {
    
    private int itemId ;
    private String itemName ;
   
    private int quantity;
     private double unitPrice ;
    private int warranty;
    
    private String supplierName;
    
    private int supplierId;   // VERY IMPORTANT

    public ItemDTO(List<Item> cartItems) {
        this.itemId = cartItems.get(0).getItemId();
        this.itemName = cartItems.get(0).getItemName();
        this.quantity = cartItems.get(0).getQuantity();
        this.unitPrice = cartItems.get(0).getUnitPrice();
        this.warranty = cartItems.get(0).getWarranty();

    }

    public ItemDTO(Item item) {
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.quantity = item.getQuantity();
        this.unitPrice = item.getUnitPrice();
        this.warranty = item.getWarranty();
        this.supplierName = item.getSupplierName();
    }


    public int getSupplierId() {
        return supplierId;
    }

    public ItemDTO() {
    }

    public ItemDTO(int itemId) {
        this.itemId = itemId;
    }

    public ItemDTO(int itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }
    
    
    public ItemDTO(int itemId, String itemName, int quantity, double unitPrice, int warranty) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.warranty = warranty;
    }

    public ItemDTO(int itemId, String itemName, double unitPrice, int warranty) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.warranty = warranty;
    }

    public ItemDTO(int itemId, String itemName, int quantity, double unitPrice, int warranty, int supplierId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.warranty = warranty;
        this.supplierId = supplierId;
    }

    
    
    public ItemDTO(int itemId, String itemName, int quantity, double unitPrice, int warranty, String supplierName) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.warranty = warranty;
        this.supplierName = supplierName;
    }

    public ItemDTO(String itemName, int quantity, double unitPrice, int warranty, int supplierId) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.warranty = warranty;
        this.supplierId = supplierId;
    }
    
    
    
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    
    
    

   
}

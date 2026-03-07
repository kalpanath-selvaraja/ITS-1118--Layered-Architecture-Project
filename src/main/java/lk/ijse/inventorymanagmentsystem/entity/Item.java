package lk.ijse.inventorymanagmentsystem.entity;

public class Item {


    private int itemId ;
    private String itemName ;

    private int quantity;
    private double unitPrice ;
    private int warranty;

    private String supplierName;

    private int supplierId;   // VERY IMPORTANT

    public int getSupplierId() {
        return supplierId;
    }

    public Item() {
    }

    public Item(int itemId) {
        this.itemId = itemId;
    }

    public Item(int itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }


    public Item(int itemId, String itemName, int quantity, double unitPrice, int warranty) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.warranty = warranty;
    }

    public Item(int itemId, String itemName, double unitPrice, int warranty) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.warranty = warranty;
    }

    public Item(int itemId, String itemName, int quantity, double unitPrice, int warranty, int supplierId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.warranty = warranty;
        this.supplierId = supplierId;
    }



    public Item(int itemId, String itemName, int quantity, double unitPrice, int warranty, String supplierName) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.warranty = warranty;
        this.supplierName = supplierName;
    }

    public Item(String itemName, int quantity, double unitPrice, int warranty, int supplierId) {
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

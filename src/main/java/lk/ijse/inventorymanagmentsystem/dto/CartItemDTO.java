/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.dto;


/**
 *
 * @author kalpanath
 */
public class CartItemDTO {
    private int itemId;
    private String it_Name;
    private double unitPrice;
    private int quantity;
    private int warranty;
  
    
    

    public CartItemDTO(int itemId, String it_Name, double unitPrice, int quantity, int warranty) {
        this.itemId = itemId;
        this.it_Name = it_Name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.warranty = warranty;
    }

    
    

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return it_Name;
    }

    public void setItemName(String itemName) {
        this.it_Name = itemName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }


    public String getIt_Name() {
        return it_Name;
    }

    public void setIt_Name(String it_Name) {
        this.it_Name = it_Name;
    }


    
    
}


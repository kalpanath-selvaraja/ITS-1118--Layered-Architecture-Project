/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.dto;

import lk.ijse.inventorymanagmentsystem.entity.OrderItem;

/**
 *
 * @author kalpanath
 */
public class OrderItemDTO {
    private int orderID;
    private int itemID;
    private int quantity;
    private double unitPrice;
    private int warranty;

    public OrderItemDTO() {
    }

    public OrderItemDTO(int orderID, int itemID, int quantity, double unitPrice, int warranty) {
        this.orderID = orderID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.warranty = warranty;
    }

    public OrderItemDTO(int orderID, int itemID, int quantity, double unitPrice) {
        this.orderID = orderID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public OrderItemDTO(OrderItem orderItem) {
        this.orderID = orderItem.getOrderID();
        this.itemID = orderItem.getItemID();
        this.quantity = orderItem.getQuantity();
        this.unitPrice = orderItem.getUnitPrice();

    }


    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
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

    public void setWarranty(int lineTotal) {
        this.warranty = lineTotal;
    }
    
    
    
    
}

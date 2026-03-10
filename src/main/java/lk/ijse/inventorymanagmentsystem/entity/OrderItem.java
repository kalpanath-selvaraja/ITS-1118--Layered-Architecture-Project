package lk.ijse.inventorymanagmentsystem.entity;

import lk.ijse.inventorymanagmentsystem.dto.OrderItemDTO;

public class OrderItem {

    private int orderID;
    private int itemID;
    private int quantity;
    private double unitPrice;
    private int warranty;

    public OrderItem() {
    }

    public OrderItem(int orderID, int itemID, int quantity, double unitPrice, int warranty) {
        this.orderID = orderID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.warranty = warranty;
    }

    public OrderItem(int orderId, int itemId, int quantity, double unitPrice) {
        this.orderID = orderId;
        this.itemID = itemId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public OrderItem(OrderItemDTO orderItemDTO) {
        this.orderID = orderItemDTO.getOrderID();
        this.itemID = orderItemDTO.getItemID();
        this.quantity = orderItemDTO.getQuantity();
        this.unitPrice = orderItemDTO.getUnitPrice();
        this.warranty = orderItemDTO.getWarranty();
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.dto;

import lk.ijse.inventorymanagmentsystem.entity.Warranty;

/**
 *
 * @author kalpanath
 */
public class WarrantyDTO {
    int warrantyId;
    int months;
    
    // For warranty details of an item
    String orderItemId;
    String orderId;
    String date;
    String unitprice;
    String status;
    String name;
    

    public WarrantyDTO() {
    }

    public WarrantyDTO(int months) {
        this.months = months;
    }

    public WarrantyDTO(String orderItemId, String orderId, String date, String unitprice, String status, String name) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.date = date;
        this.unitprice = unitprice;
        this.status = status;
        this.name = name;
    }

    public WarrantyDTO(Warranty w) {
        this.orderItemId = w.getOrderItemId();
        this.orderId = w.getOrderId();
        this.date = w.getDate();
        this.unitprice = w.getUnitprice();
        this.status = w.getStatus();
        this.name = w.getName();
        this.months = w.getMonths();

    }


    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }
    
    
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(String unitprice) {
        this.unitprice = unitprice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    
    public WarrantyDTO(int warrantyId, int months) {
        this.warrantyId = warrantyId;
        this.months = months;
    }

    public int getWarrantyId() {
        return warrantyId;
    }

    public void setWarrantyId(int warrantyId) {
        this.warrantyId = warrantyId;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    @Override
    public String toString() {
        return months + " Months";
    }

    
}

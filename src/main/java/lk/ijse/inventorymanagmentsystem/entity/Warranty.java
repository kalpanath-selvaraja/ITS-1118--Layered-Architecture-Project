package lk.ijse.inventorymanagmentsystem.entity;

public class Warranty {
    private  int warrantyId;
    private int months;


    // For warranty details of an item
    String orderItemId;
    String orderId;
    String date;
    String unitprice;
    String status;
    String name;

    public Warranty() {
    }

    public Warranty(int warrantyId, int months) {
        this.warrantyId = warrantyId;
        this.months = months;
    }

    public Warranty(int months) {
        this.months = months;
    }

    public Warranty(String orderItemId, String orderId, String date, String price, String status, String name) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.date = date;
        this.unitprice = price;
        this.status = status;
        this.name = name;
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
}

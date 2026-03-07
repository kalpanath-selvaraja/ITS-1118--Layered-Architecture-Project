package lk.ijse.inventorymanagmentsystem.entity;

import java.sql.Timestamp;

public class Order {
    private Integer orderId;
    private Integer cusid;

    private int empid;
    private Timestamp dateTime;
    private double total;
    private int date;

    public Order() {
    }

    public Order(Integer orderId, Integer cusid, int empid, Timestamp dateTime, double total, int date) {
        this.orderId = orderId;
        this.cusid = cusid;
        this.empid = empid;
        this.dateTime = dateTime;
        this.total = total;
        this.date = date;
    }

    public Order(int orderId, int cusId, int empId, Timestamp dateTime, double total) {
        this.orderId = orderId;
        this.cusid = cusId;
        this.empid = empId;
        this.dateTime = dateTime;
        this.total = total;
    }

    public Order(int orderId, int cusId, int empId, int date, int total) {
        this.orderId = orderId;
        this.cusid = cusId;
        this.empid = empId;
        this.date = date;
        this.total = total;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCusid() {
        return cusid;
    }

    public void setCusid(Integer cusid) {
        this.cusid = cusid;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}

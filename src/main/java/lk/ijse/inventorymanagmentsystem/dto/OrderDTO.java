/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.dto;

import java.sql.Timestamp;

/**
 *
 * @author kalpanath
 */
public class OrderDTO {
    private Integer orderId;
    private Integer cusid;
 
    private int empid;
    private Timestamp dateTime;
    private double total;
    private int date;
    
    
    public OrderDTO() {
    }

    public OrderDTO(Integer cusid, int empid, double total) {
        this.cusid = cusid;
        this.empid = empid;
        this.total = total;
    }

    public OrderDTO(Integer orderId, Integer cusid, int empid, double total, int date) {
        this.orderId = orderId;
        this.cusid = cusid;
        this.empid = empid;
        this.total = total;
        this.date = date;
    }
    
    
    
    
    public OrderDTO(Integer orderId, Integer cusid, int empid, Timestamp dateTime, double total) {
        this.orderId = orderId;
        this.cusid = cusid;
        this.empid = empid;
        this.dateTime = dateTime;
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

    
    
    
}

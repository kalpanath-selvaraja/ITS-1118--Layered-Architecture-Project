/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.dto;

import javafx.collections.ObservableList;

/**
 *
 * @author kalpanath
 */
public class CheckoutDTO {

    private ObservableList<CartItemDTO> cartItems;
    private int empId;
    private CustomerDTO customer; // nullable
    private boolean customerRequired;
    private double total;

    public CheckoutDTO(ObservableList<CartItemDTO> cartItems,
                       int empId,
                       CustomerDTO customer,
                       boolean customerRequired,
                       double total) {
        this.cartItems = cartItems;
        this.empId = empId;
        this.customer = customer;
        this.customerRequired = customerRequired;
        this.total = total;
    }

    public ObservableList<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ObservableList<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public boolean isCustomerRequired() {
        return customerRequired;
    }

    public void setCustomerRequired(boolean customerRequired) {
        this.customerRequired = customerRequired;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    
}

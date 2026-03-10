/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.dto;

import lk.ijse.inventorymanagmentsystem.entity.Customer;

/**
 *
 * @author kalpanath
 */

//
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;

public class CustomerDTO {


    private int id;
    private String name;
    private String contact;
    private  CustomerDTO cusDTO;
    

    public CustomerDTO() {
    }

    public CustomerDTO(String name, String contact ) {
        this.name = name;
        this.contact = contact;

    }
    
    public CustomerDTO(int id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;

    }

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.contact = customer.getContact();

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public CustomerDTO getCusDTO() {
        return cusDTO;
    }

    public void setCusDTO(CustomerDTO cusDTO) {
        this.cusDTO = cusDTO;
    }

    
 
    @Override
    public String toString() {
        return "CustomerDTO{" + "id=" + id + ", name=" + name + ", contact=" + contact + '}';
    }
    
}
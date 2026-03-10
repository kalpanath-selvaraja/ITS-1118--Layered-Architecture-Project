/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.dto;

import lk.ijse.inventorymanagmentsystem.entity.Supplier;

import java.util.List;

/**
 *
 * @author kalpanath
 */
public class SupplierDTO {
    private int supplier_id ;
    private String supplier_name;
    private String email;
    private String contact;



    public SupplierDTO() {
    }

    public SupplierDTO(String supplier_name, String email, String contact) {
        this.supplier_name = supplier_name;
        this.email = email;
        this.contact = contact;
    }
    
    
    
    
    public SupplierDTO(int supplier_id, String supplier_name, String email, String contact) {
        this.supplier_id = supplier_id;
        this.supplier_name = supplier_name;
        this.email = email;
        this.contact = contact;
    }

    public SupplierDTO(List<Supplier> suppliersList) {
        this.supplier_id = suppliersList.get(0).getSupplier_id();
        this.supplier_name = suppliersList.get(0).getSupplier_name();
        this.email = suppliersList.get(0).getEmail();
        this.contact = suppliersList.get(0).getContact();
    }

    public SupplierDTO(Supplier supplier) {
        this.supplier_id = supplier.getSupplier_id();
        this.supplier_name = supplier.getSupplier_name();
        this.email = supplier.getEmail();
        this.contact = supplier.getContact();
    }

//    public SupplierDTO(Supplier supplier) {
//        this.supplier_name = supplier.getSupplier_name();
//        this.email = supplier.getEmail();
//        this.contact = supplier.getContact();
//    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    
    

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.dto;

import lk.ijse.inventorymanagmentsystem.entity.Employee;

/**
 *
 * @author kalpanath
 */
public class EmployeeDTO {
    int employeeID;
    String empName;
    String empContact;
    int empUserId;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String empName, String empContact) {
        this.empName = empName;
        this.empContact = empContact;
    }
    
    
    
    
    public EmployeeDTO(int employeeID, String empName, String empContact, int empUserId) {
        this.employeeID = employeeID;
        this.empName = empName;
        this.empContact = empContact;
        this.empUserId = empUserId;
    }

    public EmployeeDTO(Employee employee) {
        this.employeeID = employee.getEmployeeID();
        this.empName = employee.getEmpName();
        this.empContact = employee.getEmpContact();
        this.empUserId = employee.getEmpUserId();

    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpContact() {
        return empContact;
    }

    public void setEmpContact(String empContact) {
        this.empContact = empContact;
    }

    public int getEmpUserId() {
        return empUserId;
    }

    public void setEmpUserId(int empUserId) {
        this.empUserId = empUserId;
    }
    
    

    public EmployeeDTO(int employeeID) {
        this.employeeID = employeeID;
    }
    
    public int getEmployeeID() {
        return employeeID;
    }
    

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }
    
    
}

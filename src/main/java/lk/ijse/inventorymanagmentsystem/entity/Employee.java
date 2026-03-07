package lk.ijse.inventorymanagmentsystem.entity;

public class Employee {
    private int employeeID;
    private String empName;
    private String empContact;
    private int empUserId;

    public Employee() {
    }

    public Employee(int employeeID, String empName, String empContact, int empUserId) {
        this.employeeID = employeeID;
        this.empName = empName;
        this.empContact = empContact;
        this.empUserId = empUserId;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
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
}

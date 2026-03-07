package lk.ijse.inventorymanagmentsystem.entity;

public class Supplier {
    private int supplier_id ;
    private String supplier_name;
    private String email;
    private String contact;

    public Supplier() {
    }

    public Supplier(int supplier_id, String supplier_name, String email, String contact) {
        this.supplier_id = supplier_id;
        this.supplier_name = supplier_name;
        this.email = email;
        this.contact = contact;
    }

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

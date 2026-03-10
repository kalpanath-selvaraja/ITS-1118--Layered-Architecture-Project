package lk.ijse.inventorymanagmentsystem.entity;

public class SupplierItem {
    private int supplier_id;
    private int item_id;

    public SupplierItem() {
    }

    public SupplierItem(int supplier_id, int item_id) {
        this.supplier_id = supplier_id;
        this.item_id = item_id;
    }

    public SupplierItem(Supplier supplier) {
        this.supplier_id = supplier.getSupplier_id();
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }
}

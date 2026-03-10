package lk.ijse.inventorymanagmentsystem.bo.custom;

import lk.ijse.inventorymanagmentsystem.bo.SuperBO;
import lk.ijse.inventorymanagmentsystem.dto.ItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {
    public boolean decreseItemQty(int itemId, int qty) throws SQLException;

    int getLowStocksNumber() throws SQLException;

    int getWarrantyId(int months) throws SQLException;

    boolean updateItems(ItemDTO itemDTO, int warrantyId) throws SQLException;


    boolean save(ItemDTO itemDTO, int warrantyId) throws SQLException;

    List<ItemDTO> searchItem(String itemName)throws SQLException;

    List<ItemDTO>  getItems() throws SQLException;
}

package lk.ijse.inventorymanagmentsystem.dao.custom;

import lk.ijse.inventorymanagmentsystem.dao.CrudDAO;
import lk.ijse.inventorymanagmentsystem.dto.ItemDTO;
import lk.ijse.inventorymanagmentsystem.entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item> {

    public List<Item> getItems()throws SQLException ; //  item

    public boolean decreseItemQty(int itemId, int qty) throws SQLException;

    public int getLowStocksNumber() throws SQLException;

    public int getWarrantyIdByMonths(int months) throws SQLException;

    public boolean updateItems(Item item , int warrantyId) throws SQLException;

    public int addItems(ItemDTO itemDTO, int warrantyId) throws SQLException;
}

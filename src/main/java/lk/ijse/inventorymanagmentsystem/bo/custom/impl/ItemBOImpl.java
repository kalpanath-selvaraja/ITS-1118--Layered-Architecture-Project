package lk.ijse.inventorymanagmentsystem.bo.custom.impl;


import lk.ijse.inventorymanagmentsystem.bo.custom.ItemBO;
import lk.ijse.inventorymanagmentsystem.dao.DAOFactory;
import lk.ijse.inventorymanagmentsystem.dao.custom.ItemDAO;
import lk.ijse.inventorymanagmentsystem.dao.custom.QueryDAO;
import lk.ijse.inventorymanagmentsystem.dao.custom.SupplierItemDAO;
import lk.ijse.inventorymanagmentsystem.dto.ItemDTO;
import lk.ijse.inventorymanagmentsystem.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAOTypes(DAOFactory.DAOTypes.ITEM);
    private final SupplierItemDAO supplierItemDAO = (SupplierItemDAO) DAOFactory.getInstance().getDAOTypes(DAOFactory.DAOTypes.SUPPLIER_ITEM);
    private final QueryDAO queryDAO =(QueryDAO) DAOFactory.getInstance().getDAOTypes(DAOFactory.DAOTypes.QUERY);

    @Override
    public boolean decreseItemQty(int itemId, int qty) throws SQLException {
        return itemDAO.decreseItemQty(itemId, qty);
    }

    @Override
    public int getLowStocksNumber() throws SQLException{
        return itemDAO.getLowStocksNumber();
    }

    @Override
    public int getWarrantyId(int months) throws SQLException {
        return itemDAO.getWarrantyIdByMonths( months);

    }

    @Override
    public boolean updateItems(ItemDTO itemDTO, int warrantyId) throws SQLException{
        int warrantyParam = (warrantyId == 0) ? null : warrantyId;

       boolean result = itemDAO.updateItems(new Item(itemDTO), warrantyParam);

       if(!result){
           return false;
       }

        boolean supplierupdated = supplierItemDAO.saveSupplierItem(itemDTO.getSupplierId(),itemDTO.getItemId());

       return supplierupdated;
    }

    @Override
    public boolean save(ItemDTO itemDTO, int warrantyId) throws SQLException {


        int warrantyParam = (warrantyId == 0) ? null : warrantyId;

        int item_id = itemDAO.addItems(itemDTO, warrantyParam);



        if(item_id == -1){
            return false;
        }

        boolean supplierupdated = supplierItemDAO.saveSupplierItem(itemDTO.getSupplierId(),item_id);

        return supplierupdated;
    }


    @Override
    public List<ItemDTO> searchItem(String itemName)throws SQLException{
        List<Item> itemDAO= queryDAO.searchItem(itemName);

        List<ItemDTO> itemDTO= new ArrayList<>();
        for(Item item:itemDAO){
            itemDTO.add(new ItemDTO(item));
        }

        return  itemDTO;

    }

    @Override
    public List<ItemDTO>  getItems() throws SQLException {
        List<Item> items = queryDAO.getItemsView();

        List<ItemDTO> itemDTO= new ArrayList<>();
        for(Item item:items){
            itemDTO.add(new ItemDTO(item));
        }

        return itemDTO;
    }


}

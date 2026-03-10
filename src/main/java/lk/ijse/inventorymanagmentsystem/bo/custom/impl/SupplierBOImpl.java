package lk.ijse.inventorymanagmentsystem.bo.custom.impl;

import lk.ijse.inventorymanagmentsystem.bo.custom.SupplierBO;
import lk.ijse.inventorymanagmentsystem.dao.DAOFactory;
import lk.ijse.inventorymanagmentsystem.dao.custom.QueryDAO;
import lk.ijse.inventorymanagmentsystem.dao.custom.SupplierDAO;
import lk.ijse.inventorymanagmentsystem.dao.custom.SupplierItemDAO;
import lk.ijse.inventorymanagmentsystem.dto.ItemDTO;
import lk.ijse.inventorymanagmentsystem.dto.SupplierDTO;
import lk.ijse.inventorymanagmentsystem.entity.Item;
import lk.ijse.inventorymanagmentsystem.entity.Supplier;
import lk.ijse.inventorymanagmentsystem.entity.SupplierItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAOTypes(DAOFactory.DAOTypes.SUPPLIER);
    SupplierItemDAO supplierItemDAO = (SupplierItemDAO) DAOFactory.getInstance().getDAOTypes(DAOFactory.DAOTypes.SUPPLIER_ITEM);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAOTypes(DAOFactory.DAOTypes.QUERY);

    @Override
    public boolean updateSupplierWithItems(SupplierDTO supDTO, List<ItemDTO> cartItems) throws SQLException {

        // convert to Supplier Entity
        Supplier supplier = new Supplier(supDTO);

        // 1. Update supplier
        boolean updated = supplierDAO.updateSuppler(supplier);

        if (!updated) {
            return false;
        }

        // 2. Clear old item
        SupplierItem supplierItem = new SupplierItem(supplier);
        boolean deleted = supplierItemDAO.delete(supplierItem.getSupplier_id());


        List<Item> cartItem = new ArrayList<>();

        for (ItemDTO item : cartItems) {
            cartItem.add(new Item(cartItems));
        }

        boolean ItemSaved = supplierItemDAO.saveItem(supplier.getSupplier_id(), cartItem);

        if (!ItemSaved) {
            return false;
        }

        return true;

    }

    @Override
    public boolean save(SupplierDTO supplierDTO) throws SQLException {

        // Business validation: Check if supplier already exists
        if (supplierDAO.existsByContact(supplierDTO.getContact())) {
            throw new IllegalArgumentException("Supplier with this contact already exists");
        }


        Supplier supplier = new Supplier(supplierDTO);

        return supplierDAO.save(supplier);
    }

    @Override
    public boolean deleteSupplier(int supplierId) throws SQLException {
        // Business rule: Check if supplier has items
        boolean result = supplierDAO.delete(supplierId);

        return result;


    }

    @Override
    public int getSupplierIdByName(String name) throws SQLException {
        return supplierDAO.getSupplierID(name);
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() throws SQLException {
        List<Supplier> suppliersList = supplierDAO.getAll();

        List<SupplierDTO> supplierDTOs = new ArrayList<>();

        for (Supplier supplier : suppliersList) {
            supplierDTOs.add(new SupplierDTO(supplier));
        }

        return supplierDTOs;
    }

    @Override
    public List<ItemDTO> getItemsForSupplier(int supplierId) throws SQLException {
        // Get items through QueryDAO (JOIN query)
        List<Item> items = queryDAO.getItemsBySupplier(supplierId);

        // Convert entities to DTOs
        List<ItemDTO> itemDTOs = new ArrayList<>();
        for (Item item : items) {
            itemDTOs.add(new ItemDTO(items));
        }
        return  itemDTOs;

    }

    @Override
    public List<ItemDTO> getItemsForItem(int id) throws SQLException {
        List<Item> item = queryDAO.getItemsForItem(id);

        List<ItemDTO> itemsDTOList = new ArrayList<>();

        for (Item list : item) {
            itemsDTOList.add(new ItemDTO(list));
        }

        return  itemsDTOList;
    }

    @Override
    public List<SupplierDTO> searchSupplier(String search) throws SQLException{
        List<Supplier> suppliersList = supplierDAO.search(search);

        List<SupplierDTO> supplierDTOs = new ArrayList<>();
        for (Supplier supplier : suppliersList) {
            supplierDTOs.add(new SupplierDTO(supplier));
        }

        return supplierDTOs;

    }

    @Override
    public boolean checkSupplierExists(String contact) throws SQLException {
        return supplierDAO.existsByContact(contact);
    }

    @Override
    public boolean checkSupplierConnected(int id) throws SQLException {
        return supplierItemDAO.SupplierItemCount(id);
    }


}
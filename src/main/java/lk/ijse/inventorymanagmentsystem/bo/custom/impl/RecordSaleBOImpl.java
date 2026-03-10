package lk.ijse.inventorymanagmentsystem.bo.custom.impl;

import lk.ijse.inventorymanagmentsystem.bo.custom.RecordSaleBO;
import lk.ijse.inventorymanagmentsystem.dao.DAOFactory;
import lk.ijse.inventorymanagmentsystem.dao.custom.CustomerDAO;
import lk.ijse.inventorymanagmentsystem.dao.custom.ItemDAO;
import lk.ijse.inventorymanagmentsystem.dao.custom.OrderDAO;
import lk.ijse.inventorymanagmentsystem.dao.custom.OrderItemDAO;
import lk.ijse.inventorymanagmentsystem.db.DBConnection;
import lk.ijse.inventorymanagmentsystem.dto.CartItemDTO;
import lk.ijse.inventorymanagmentsystem.dto.CheckoutDTO;
import lk.ijse.inventorymanagmentsystem.dto.CustomerDTO;
import lk.ijse.inventorymanagmentsystem.entity.Customer;
import lk.ijse.inventorymanagmentsystem.entity.Order;
import lk.ijse.inventorymanagmentsystem.entity.OrderItem;

import java.sql.Connection;
import java.sql.SQLException;

public class RecordSaleBOImpl implements RecordSaleBO {
    private final CustomerDAO customerDAO =
            (CustomerDAO) DAOFactory.getInstance().getDAOTypes(DAOFactory.DAOTypes.CUSTOMER);

    private final OrderDAO orderDAO =
            (OrderDAO) DAOFactory.getInstance().getDAOTypes(DAOFactory.DAOTypes.ORDER);

    private final OrderItemDAO orderItemDAO =
            (OrderItemDAO) DAOFactory.getInstance().getDAOTypes(DAOFactory.DAOTypes.ORDER_ITEM);

    private final ItemDAO itemDAO =
            (ItemDAO) DAOFactory.getInstance().getDAOTypes(DAOFactory.DAOTypes.ITEM);

    @Override
    public int processCheckout(CheckoutDTO checkoutDTO) throws SQLException {
        Connection connection = null;
        int orderId = 0;

        try{
            connection = DBConnection.getinstance().getConnection();
            connection.setAutoCommit(false);

            Integer customerId = null;

            if (checkoutDTO.isCustomerRequired() && checkoutDTO.getCustomer() != null) {
                CustomerDTO customerDTO = checkoutDTO.getCustomer();

                // Check if customer exists by contact
                customerId = customerDAO.getIdByContact(customerDTO.getContact());

                // If customer doesn't exist, create new one
                if (customerId == null || customerId == -1) {
                    Customer customer = new Customer(customerDTO);
                    customerId = customerDAO.saveandReturnId(customer);

                    if (customerId == -1) {
                        connection.rollback();
                        throw new SQLException("Failed to save customer");
                    }
                }
            }


            // 2 save Order

            Order order = new Order(customerId,checkoutDTO.getEmpId(), checkoutDTO.getTotal());

            orderId =  orderDAO.saveAndReturn(order);

            if (orderId == -1) {
                connection.rollback();
                throw new SQLException("Failed to save order");
            }

            // 3. Save order items and update inventory

            for (CartItemDTO cartItem : checkoutDTO.getCartItems()) {
                OrderItem orderItem = new OrderItem(
                        orderId,
                        cartItem.getItemId(),
                        cartItem.getQuantity(),
                        cartItem.getUnitPrice(),
                        cartItem.getWarranty()
                );
                boolean itemSaved = orderItemDAO.save(orderItem);
                if (!itemSaved) {
                    connection.rollback();
                    throw new SQLException("Failed to save order item");
                }

                // Update warranty status
                String warrantyStatus = (cartItem.getWarranty() > 0)? "Active": "No Warranty";

                boolean warrantyUpdated = orderItemDAO.updateWarrantyStatus(orderId,warrantyStatus);

                if (!warrantyUpdated) {
                    connection.rollback();
                    throw new SQLException("Failed to update warranty status");
                }

                boolean quantityDecreased = itemDAO.decreseItemQty(
                        cartItem.getItemId(),
                        cartItem.getQuantity()
                );

                if (!quantityDecreased) {
                    connection.rollback();
                    throw new SQLException("Insufficient stock for item: " + cartItem.getItemId());
                }
            }
            // All operations successful - commit
            connection.commit();
            return orderId;

        }catch(Exception e){
            if (connection != null) {
                connection.rollback();
            }
            throw e;

        }finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }

    }

    @Override
    public double getTotalSales() throws SQLException {
        return orderDAO.getTotalSales();
    }

}

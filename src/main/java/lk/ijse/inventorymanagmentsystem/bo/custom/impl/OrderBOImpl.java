package lk.ijse.inventorymanagmentsystem.bo.custom.impl;

import lk.ijse.inventorymanagmentsystem.bo.custom.OrderBO;
import lk.ijse.inventorymanagmentsystem.dao.DAOFactory;
import lk.ijse.inventorymanagmentsystem.dao.custom.OrderDAO;
import lk.ijse.inventorymanagmentsystem.dto.OrderDTO;
import lk.ijse.inventorymanagmentsystem.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAOTypes(DAOFactory.DAOTypes.ORDER);

    @Override
    public List<OrderDTO> search(int order_id)throws SQLException {
        List<Order> order =  orderDAO.search(order_id);

        List<OrderDTO> orderDTO = new ArrayList<>();
        for(Order returned_order:order){
            orderDTO.add(new OrderDTO(returned_order));
        }

        return orderDTO;
    }

    @Override
    public String getOrderCount() throws SQLException{
        return orderDAO.getCount();
    }

    @Override
    public Map<String, Double> getDailySales() throws SQLException{
        return orderDAO.getDailySales();
    }

    @Override
    public int saveAndReturnID(OrderDTO orderDTO) throws SQLException{

        // Convert DTO to Entity
        Order order = new Order(orderDTO);

        return orderDAO.saveAndReturn(order);
    }

    @Override
    public List<OrderDTO> getAll() throws SQLException{

        List<Order> orders = orderDAO.getAll();

        List<OrderDTO> orderDTO = new ArrayList<>();

        for(Order returned_order:orders){

            orderDTO.add(new OrderDTO(returned_order));
        }
        return orderDTO;
    }



}

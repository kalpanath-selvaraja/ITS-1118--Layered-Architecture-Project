package lk.ijse.inventorymanagmentsystem.dao.custom;

import lk.ijse.inventorymanagmentsystem.dao.CrudDAO;
import lk.ijse.inventorymanagmentsystem.dao.CrudUtil;
import lk.ijse.inventorymanagmentsystem.db.DBConnection;
import lk.ijse.inventorymanagmentsystem.dto.OrderDTO;
import lk.ijse.inventorymanagmentsystem.entity.Order;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderDAO extends CrudDAO<Order> {

    public List<Order> search(int order_id)throws SQLException;

//    public void printReport()throws SQLException , JRException;
//
//    public void printInvoice (int orderId) throws SQLException , JRException;

    public String getCount () throws SQLException;

    public Map<String, Double> getDailySales() throws SQLException ;

    public int saveAndReturn(Order order) throws SQLException;

    double getTotalSales() throws SQLException;
}

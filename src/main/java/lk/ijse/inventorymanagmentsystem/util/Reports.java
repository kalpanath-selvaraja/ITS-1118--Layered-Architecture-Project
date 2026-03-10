package lk.ijse.inventorymanagmentsystem.util;

import lk.ijse.inventorymanagmentsystem.db.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Reports {
    public static void printReport()throws SQLException, JRException {

        Connection conn = DBConnection.getinstance().getConnection();

        InputStream inputStream = Reports.class.getResourceAsStream("/reports/OrderReport.jrxml" );



        JasperReport jr = JasperCompileManager.compileReport(inputStream);

        JasperPrint jp = JasperFillManager.fillReport(jr, null , conn);

        JasperViewer.viewReport(jp, false);

    }

    public static void printInvoice (int orderId) throws SQLException , JRException{

        Connection conn = DBConnection.getinstance().getConnection();

        InputStream inputStream = Reports.class.getResourceAsStream("/reports/Bill.jrxml" );


        JasperReport jr =JasperCompileManager.compileReport(inputStream);

        Map<String, Object> params = new HashMap<>();
        params.put("ORDER_ID", orderId);

        JasperPrint jp = JasperFillManager.fillReport(jr, params , conn);

        JasperViewer.viewReport(jp, false);
    }
}

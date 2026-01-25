/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lk.ijse.inventorymanagmentsystem.db.DBConnection;

public class CrudUtil {

    public static <T> T execute(String sql, Object... obj) throws SQLException {
       
        // generic cus resultset cannot return boolean
        
        Connection conn = DBConnection.getinstance().getConnection();
        
        PreparedStatement ptsm = conn.prepareStatement(sql); // PreparedStatement used to prevent hacking 
        
        for(int i=0; i<obj.length; i++) {
            ptsm.setObject(i+1, obj[i]); // ptsm.setObject(4, 43);
        }
        
        if(sql.startsWith("select") || sql.startsWith("SELECT")) {  
        
            ResultSet rs = ptsm.executeQuery(); // executeQuery() only works for SELECT Querys 
            return (T)rs;
            
        } else {
        
            int result = ptsm.executeUpdate(); // for UPDATE , DELETE, INSERT
            boolean rs = result>0;
            return (T)(Boolean)rs;
            
        }

    }
    
    public static ResultSet executeQuery(String sql, Object... params) throws SQLException {
        Connection conn = DBConnection.getinstance().getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            pstm.setObject(i + 1, params[i]);
        }

        return pstm.executeQuery();
    }

    
    
    public static int executeAndReturnId(String sql, Object... params) throws SQLException {
        
        Connection conn = DBConnection.getinstance().getConnection();
        PreparedStatement ptsm;
        
       
        
        boolean isSelect = sql.trim().toUpperCase().startsWith("SELECT");

        if (isSelect) {
            ptsm = conn.prepareStatement(sql);
        } else {
            ptsm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        }


        for (int i = 0; i < params.length; i++) {
            ptsm.setObject(i + 1, params[i]);
        }

        if (isSelect) {

            ResultSet rs = ptsm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);  
            }
        } else {
  
            int affectedRows = ptsm.executeUpdate(); // Int return no of rows affected
            if (affectedRows == 0) {
                throw new SQLException("No rows affected.");
            }

            ResultSet rs = ptsm.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);  
            }
        }

        return -1; 
    }

    public static int getEmployeeIdByUserId(String sql, int UserID) throws SQLException {
        Connection conn = DBConnection.getinstance().getConnection();
        PreparedStatement ptsm = conn.prepareStatement(sql); 
        ptsm.setObject(1, UserID);
        
        

        ResultSet rs = ptsm.executeQuery();  // ✅ use executeQuery for SELECT

        
        return rs.getInt(1);  // get the first column (assumed ID)
        
        // or throw exception if not found
    }
}
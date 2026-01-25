/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.db;

/**
 *
 * @author kalpanath
 */

import java.sql.Connection;

import java.sql.SQLException;

import java.sql.DriverManager;





public class DBConnection {
    
    final static String DB_URL = "jdbc:mysql://localhost:3306/inventory";
    final static String DB_USERNAME = "root";
    final static String DB_PASSWORD = "mysql";
    
    private static Connection connection;
    
    private static DBConnection dbc;
    
    DBConnection() throws SQLException{
        connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        
    }
    
    public static DBConnection getinstance()throws SQLException{
        if(dbc == null){
            dbc = new DBConnection();    
        }
        
        return dbc;
    }
    
    public Connection getConnection(){
        return connection;
    }
}   
    
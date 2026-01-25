/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.inventorymanagmentsystem.util;

/**
 *
 * @author kalpanath
 */
public class Session {
    private static int userId;
    private static int empId;
    private static String currentUser;
    private static String currentUserName;

    public static String getCurrentUserName() {
        return currentUserName;
    }

    public static void setCurrentUserName(String currentUserName) {
        Session.currentUserName = currentUserName;
    }
    
    
    

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String currentUser) {
        Session.currentUser = currentUser;
    }

    
    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        Session.userId = userId;
    }

    public static int getEmpId() {
        return empId;
    }

    public static void setEmpId(int empId) {
        Session.empId = empId;
    }

    public static void clear() {
        empId = 0;
    }

    
   
}

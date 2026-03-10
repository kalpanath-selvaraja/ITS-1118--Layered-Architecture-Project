package lk.ijse.inventorymanagmentsystem.dao;

import lk.ijse.inventorymanagmentsystem.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory instance;

    public DAOFactory(){}

    public static DAOFactory getInstance(){
        return instance == null ? new DAOFactory() : instance ;
    }

    public enum DAOTypes {
        CUSTOMER, ITEM, EMPLOYEE,SUPPLIER_ITEM, ORDER, ORDER_ITEM, SUPPLIER, QUERY, USER, WARRANTY
    }

    public SuperDAO getDAOTypes (DAOTypes daoType){
        switch(daoType){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case SUPPLIER_ITEM:
                return new SupplierItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_ITEM:
                return new OrderItemDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            case USER:
                return new UserDAOImpl();
            case WARRANTY:
                return new WarrantyDAOImpl();
            default :
                return null;
        }

    }

}

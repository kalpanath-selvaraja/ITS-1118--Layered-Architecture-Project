package lk.ijse.inventorymanagmentsystem.dao;

import lk.ijse.inventorymanagmentsystem.dao.custom.impl.CustomerDAOImpl;

public class DAOFactory {

    private static DAOFactory instance;

    public DAOFactory(){}

    public static DAOFactory getInstance(){
        return instance == null ? new DAOFactory() : instance ;
    }

    public enum DAOTypes {
        CUSTOMER
    }

    public SuperDAO getDAOTypes (DAOTypes daoType){
        switch(daoType){
            case CUSTOMER:
                return new CustomerDAOImpl();

            default :
                return null;
        }

    }

}

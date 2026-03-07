package lk.ijse.inventorymanagmentsystem.bo.custom.impl;

import lk.ijse.inventorymanagmentsystem.bo.custom.SuperBO;

public class BOFactory {

    private static BOFactory instance;

    private BOFactory() {}

    public static BOFactory getInstance() {
        return instance==null?instance = new BOFactory():instance;
    }

    public enum BOTypes{
        CUSTOMER,ITEM, ORDER
    }

    public SuperBO getBOFactory(BOTypes boTypes){
        switch(boTypes){
            case CUSTOMER :
                return new CustomerBOImpl();
            default :
                return null ;

        }
    }



}

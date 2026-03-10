package lk.ijse.inventorymanagmentsystem.bo;

import lk.ijse.inventorymanagmentsystem.bo.custom.impl.*;

public class BOFactory {

    private static BOFactory instance;

    private BOFactory() {}

    public static BOFactory getInstance() {
        return instance==null?instance = new BOFactory():instance;
    }

    public enum BOTypes{
        CUSTOMER,ITEM,  EMPLOYEE,SUPPLIER_ITEM, ORDER, ORDER_ITEM, SUPPLIER, USER, WARRANTY, RECORDSALE
    }

    public SuperBO getBOFactory(BOTypes boTypes){
        switch(boTypes){
            case CUSTOMER :
                return new CustomerBOImpl();
            case ITEM :
                return new ItemBOImpl();
            case ORDER :
                return new OrderBOImpl();
            case SUPPLIER_ITEM :
                    return new SupplierBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case ORDER_ITEM :
                return new OrderItemBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case WARRANTY :
                return new WarrantyBOImpl();
            case USER:
                return new UserBOImpl();
            case RECORDSALE:
                return new RecordSaleBOImpl();

            default :
                return null ;

        }
    }



}

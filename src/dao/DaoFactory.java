package dao;

import bo.custom.impl.*;
import dao.custom.impl.*;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory(){
    }

    public static DaoFactory getInstance(){
        return daoFactory==null ? daoFactory=new DaoFactory() : daoFactory;
    }

    public enum DaoType{
        CUSTOMER,ROOM,SERVICE,RESERVATION,RESERVATIONDETAIL,SERVICEDETAIL,QUERY;
    }

    public <T extends SuperDAO> T getDao(DaoType daoType){
        switch (daoType){
            case CUSTOMER:
                return (T) new CustomerDAOImpl();
            case ROOM:
                return (T) new RoomDAOImpl();
            case SERVICE:
                return (T) new ServiceDAOImpl();
            case RESERVATION:
                return (T) new ReservationDAOImpl();
            case SERVICEDETAIL:
                return (T) new ServiceDetailDAOImpl();
            case RESERVATIONDETAIL:
                return (T) new ReservationDetailDAOImpl();
            case QUERY:
                return (T) new QueryDAOImpl();
            default:
                return null;
        }
    }
}

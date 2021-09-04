package bo;

import bo.custom.impl.*;
import dao.custom.impl.ReservationDetailDAOImpl;
import dao.custom.impl.ServiceDetailDAOImpl;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory(){}

    public static BoFactory getInstance(){
        return boFactory==null ? boFactory=new BoFactory() : boFactory;
    }

    public enum BoType{
        CUSTOMER,ROOM,SERVICE,RESERVATION,RESERVATIONDETAIL,SERVICEDETAIL;
    }

    public <T extends SuperBO> T getBo(BoType boType){
        switch (boType){
            case CUSTOMER:
                return (T) new CustomerBOImpl();
            case ROOM:
                return (T) new RoomBOImpl();
            case SERVICE:
                return (T) new ServiceBOImpl();
            case RESERVATION:
                return (T) new ReservationBOImpl();
            case RESERVATIONDETAIL:
                return (T) new ReservationDetailDAOImpl();
            case SERVICEDETAIL:
                return (T) new ServiceDetailDAOImpl();
            default:
                return null;
        }
    }
}

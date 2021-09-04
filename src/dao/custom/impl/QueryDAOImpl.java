package dao.custom.impl;

import dao.CrudUtil;
import dao.QueryDAO;

import java.sql.ResultSet;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public String getCustomerId() throws Exception {
        ResultSet customerIdSet = CrudUtil.execute("SELECT CID FROM Customer ORDER BY CID DESC LIMIT 1");
        String customerId="C001";
        if(customerIdSet.next()){
            String[] cs = customerIdSet.getString(1).split("C");
            int newId=Integer.parseInt(cs[1])+1;
            if (newId<10){
                customerId="C00"+(newId);
            }else if(newId<100){
                customerId="C0"+newId;
            }else{
                customerId="C"+newId;
            }
        }
        return customerId;
    }

    @Override
    public String getReservationId() throws Exception {
        ResultSet resIdSet = CrudUtil.execute("SELECT ResId FROM Reservation ORDER BY ResId DESC LIMIT 1");
        String resId="B001";
        if(resIdSet.next()){
            String[] cs = resIdSet.getString(1).split("B");
            int newId=Integer.parseInt(cs[1])+1;
            if (newId<10){
                resId="B00"+(newId);
            }else if(newId<100){
                resId="B0"+newId;
            }else{
                resId="B"+newId;
            }
        }
        return resId;
    }

    @Override
    public String getRoomId() throws Exception {
        ResultSet RoomIdSet = CrudUtil.execute("SELECT RID FROM Room ORDER BY RID DESC LIMIT 1");
        String roomId="R001";
        if(RoomIdSet.next()){
            String[] cs = RoomIdSet.getString(1).split("R");
            int newId=Integer.parseInt(cs[1])+1;
            if (newId<10){
                roomId="R00"+(newId);
            }else if(newId<100){
                roomId="R0"+newId;
            }else{
                roomId="R"+newId;
            }
        }
        return roomId;
    }

    @Override
    public String getServiceId() throws Exception {
        ResultSet serviceIdSet = CrudUtil.execute("SELECT SID FROM Service ORDER BY SID DESC LIMIT 1");
        String serviceId="S001";
        if(serviceIdSet.next()){
            String[] cs = serviceIdSet.getString(1).split("S");
            int newId=Integer.parseInt(cs[1])+1;
            if (newId<10){
                serviceId="S00"+(newId);
            }else if(newId<100){
                serviceId="S0"+newId;
            }else{
                serviceId="S"+newId;
            }
        }
        return serviceId;
    }
}

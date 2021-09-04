package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ReservationDetailDAO;
import entity.AllReservationDetail;
import entity.ReservationDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationDetailDAOImpl implements ReservationDetailDAO {
    @Override
    public boolean save(ReservationDetail reservationDetail) throws Exception {
        return CrudUtil.execute("INSERT INTO ReservationDetail VALUES(?,?,?,?,?)",
                    reservationDetail.getReservationId(),
                    reservationDetail.getRoomId(),
                    reservationDetail.getCheckInDate(),
                    reservationDetail.getCheckOutDate(),
                    reservationDetail.getFullPayment()
                );
    }

    @Override
    public boolean update(ReservationDetail reservationDetail) throws Exception {
        return CrudUtil.execute("UPDATE ReservationDetail SET RID=?, InDate=?, OutDate=?,Payment=? WHERE ResId=?",
                    reservationDetail.getRoomId(),
                    reservationDetail.getCheckInDate(),
                    reservationDetail.getCheckOutDate(),
                    reservationDetail.getFullPayment(),
                    reservationDetail.getReservationId()
                );
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM ReservationDetail WHERE ResId=?",s);
    }

    @Override
    public ReservationDetail search(String s) throws Exception {
        ResultSet res = CrudUtil.execute("SELECT * FROM ReservationDetail WHERE ResId=?", s);
        if(res.next()){
            return new ReservationDetail(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getDouble(5)
            );
        }
        return null;
    }

    @Override
    public ArrayList<ReservationDetail> getAll() throws Exception {
        ArrayList<ReservationDetail> reservationDetailList=new ArrayList<>();
        ResultSet res = CrudUtil.execute("SELECT * FROM ReservationDetail");
        while (res.next()){
            reservationDetailList.add(new ReservationDetail(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getDouble(5)
            ));
        }
        return reservationDetailList;
    }

    @Override
    public ArrayList<AllReservationDetail> getAllReservationDetail() throws SQLException, ClassNotFoundException {
        ArrayList<AllReservationDetail> allReservationDetails=new ArrayList<>();
        ResultSet res = CrudUtil.execute("SELECT * FROM ((reservation inner join serviceDetail on reservation.resId=serviceDetail.resId) inner join reservationDetail on serviceDetail.resId=reservationDetail.resId)");
        while(res.next()){
            allReservationDetails.add(new AllReservationDetail(
                    res.getString(1),
                    res.getString(2),
                    res.getString(5),
                    res.getString(9),
                    res.getString(3),
                    res.getString(10),
                    res.getString(11),
                    res.getInt(6),
                    res.getDouble(7),
                    res.getDouble(12)
            ));
        }
        return allReservationDetails;
    }

    @Override
    public AllReservationDetail getSelectedReservationDetail(String id) throws Exception {
        ResultSet res = CrudUtil.execute("SELECT * from ((reservation inner join serviceDetail on reservation.resId=serviceDetail.resId) inner join reservationDetail on serviceDetail.resId=reservationDetail.resId) where reservation.ResId=?",id);
        if(res.next()){
            return new AllReservationDetail(
                    res.getString(1),
                    res.getString(2),
                    res.getString(5),
                    res.getString(9),
                    res.getString(3),
                    res.getString(10),
                    res.getString(11),
                    res.getInt(6),
                    res.getDouble(7),
                    res.getDouble(12)
            );
        }
        return null;
    }

    @Override
    public ArrayList<AllReservationDetail> getAllReservationDetailByDate() throws SQLException, ClassNotFoundException {
        ArrayList<AllReservationDetail> allReservationDetails=new ArrayList<>();
        ResultSet res = CrudUtil.execute("SELECT * from ((reservation inner join serviceDetail on reservation.resId=serviceDetail.resId) inner join reservationDetail on serviceDetail.resId=reservationDetail.resId) where reservationDetail.OutDate=CURRENT_DATE()");
        while(res.next()){
            allReservationDetails.add(new AllReservationDetail(
                    res.getString(1),
                    res.getString(2),
                    res.getString(5),
                    res.getString(9),
                    res.getString(3),
                    res.getString(10),
                    res.getString(11),
                    res.getInt(6),
                    res.getDouble(7),
                    res.getDouble(12)
            ));
        }
        return allReservationDetails;
    }
}

package dao.custom;

import dao.CrudDAO;
import entity.AllReservationDetail;
import entity.ReservationDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReservationDetailDAO extends CrudDAO<ReservationDetail,String> {
    public ArrayList<AllReservationDetail> getAllReservationDetail() throws SQLException, ClassNotFoundException;
    public AllReservationDetail getSelectedReservationDetail(String id) throws Exception;
    public ArrayList<AllReservationDetail> getAllReservationDetailByDate() throws SQLException, ClassNotFoundException;
}

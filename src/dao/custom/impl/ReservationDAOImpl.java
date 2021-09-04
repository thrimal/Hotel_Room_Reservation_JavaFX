package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ReservationDAO;
import entity.Reservation;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ReservationDAOImpl implements ReservationDAO {
    @Override
    public boolean save(Reservation reservation) throws Exception {
        return CrudUtil.execute("INSERT INTO Reservation VALUES(?,?,?)",
                reservation.getReservationId(),
                reservation.getCustomerId(),
                reservation.getReservationDate()
        );
    }

    @Override
    public boolean update(Reservation reservation) throws Exception {
        return CrudUtil.execute("UPDATE Reservation SET CID=?,ResDate=? WHERE ResId=?",
                reservation.getCustomerId(),
                reservation.getReservationDate(),
                reservation.getReservationId()
        );
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM Reservation WHERE ResId=?",s);
    }

    @Override
    public Reservation search(String s) throws Exception {
        ResultSet res = CrudUtil.execute("SELECT * FROM Reservation WHERE ResId=?", s);
        if(res.next()){
            return new Reservation(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3)
            );
        }
        return null;
    }

    @Override
    public ArrayList<Reservation> getAll() throws Exception {
        ArrayList<Reservation> reservationList=new ArrayList<>();
        ResultSet res = CrudUtil.execute("SELECT * FROM Reservation");
        while (res.next()){
            reservationList.add(new Reservation(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3)
            ));
        }
        return reservationList;
    }
}

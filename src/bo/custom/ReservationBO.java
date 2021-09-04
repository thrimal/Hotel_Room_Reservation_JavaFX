package bo.custom;

import bo.SuperBO;
import dto.*;
import entity.AllReservationDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReservationBO extends SuperBO {
    public String getReservationId()throws Exception;
    public boolean placeOrder(ReservationDTO reservationDTO) throws Exception;
    public boolean addNewCustomer(CustomerDTO customerDTO)throws Exception;
    public ReservationDTO getSelectedReservationDetail(String id) throws Exception;
    public ArrayList<ReservationDTO> getAllReservationDetail() throws SQLException, ClassNotFoundException;
    public ArrayList<ReservationDTO> getAllReservationDetailByDate() throws SQLException, ClassNotFoundException;
    public boolean cancelBooking(String id)throws Exception;
}

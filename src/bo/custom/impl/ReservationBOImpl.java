package bo.custom.impl;

import bo.custom.ReservationBO;
import dao.CrudUtil;
import dao.DaoFactory;
import dao.QueryDAO;
import dao.custom.CustomerDAO;
import dao.custom.ReservationDAO;
import dao.custom.ReservationDetailDAO;
import dao.custom.ServiceDetailDAO;
import db.DBConnection;
import dto.*;
import entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationBOImpl implements ReservationBO {
    private QueryDAO queryDAO=DaoFactory.getInstance().getDao(DaoFactory.DaoType.QUERY);
    private ReservationDAO reservationDAO=DaoFactory.getInstance().getDao(DaoFactory.DaoType.RESERVATION);
    private CustomerDAO customerDAO=DaoFactory.getInstance().getDao(DaoFactory.DaoType.CUSTOMER);
    private ServiceDetailDAO serviceDetailDAO=DaoFactory.getInstance().getDao(DaoFactory.DaoType.SERVICEDETAIL);
    private ReservationDetailDAO reservationDetailDAO=DaoFactory.getInstance().getDao(DaoFactory.DaoType.RESERVATIONDETAIL);

    @Override
    public String getReservationId() throws Exception {
        return queryDAO.getReservationId();
    }

    @Override
    public boolean addNewCustomer(CustomerDTO customerDTO)throws Exception{
        return customerDAO.save(new Customer(
                customerDTO.getCustomerId(),
                customerDTO.getCustomerName(),
                customerDTO.getAddress(),
                customerDTO.getContact(),
                customerDTO.getEmail(),
                customerDTO.getGender(),
                customerDTO.getNIC()
        ));
    }

    @Override
    public ReservationDTO getSelectedReservationDetail(String id) throws Exception {
        AllReservationDetail allReservationDetail=reservationDetailDAO.getSelectedReservationDetail(id);
        return new ReservationDTO(
                allReservationDetail.getReservationId(),
                allReservationDetail.getCustomerId(),
                allReservationDetail.getServiceId(),
                allReservationDetail.getRoomId(),
                allReservationDetail.getReservationDate(),
                allReservationDetail.getCheckInDate(),
                allReservationDetail.getCheckOutDate(),
                allReservationDetail.getTerms(),
                allReservationDetail.getServiceAmount(),
                allReservationDetail.getFullPayment()
        );
    }

    @Override
    public ArrayList<ReservationDTO> getAllReservationDetail() throws SQLException, ClassNotFoundException {
        ArrayList<AllReservationDetail> allReservationDetails=reservationDetailDAO.getAllReservationDetail();
        ArrayList<ReservationDTO> reservationDTOS=new ArrayList<>();
        for (AllReservationDetail a:allReservationDetails) {
            reservationDTOS.add(new ReservationDTO(
                    a.getReservationId(),
                    a.getCustomerId(),
                    a.getServiceId(),
                    a.getRoomId(),
                    a.getReservationDate(),
                    a.getCheckInDate(),
                    a.getCheckOutDate(),
                    a.getTerms(),
                    a.getServiceAmount(),
                    a.getFullPayment()
            ));
        }
        return reservationDTOS;
    }

    @Override
    public ArrayList<ReservationDTO> getAllReservationDetailByDate() throws SQLException, ClassNotFoundException {
        ArrayList<AllReservationDetail> allReservationDetails=reservationDetailDAO.getAllReservationDetailByDate();
        ArrayList<ReservationDTO> reservationDTOS=new ArrayList<>();
        for (AllReservationDetail a:allReservationDetails) {
            reservationDTOS.add(new ReservationDTO(
                    a.getReservationId(),
                    a.getCustomerId(),
                    a.getServiceId(),
                    a.getRoomId(),
                    a.getReservationDate(),
                    a.getCheckInDate(),
                    a.getCheckOutDate(),
                    a.getTerms(),
                    a.getServiceAmount(),
                    a.getFullPayment()
            ));
        }
        return reservationDTOS;
    }

    @Override
    public boolean cancelBooking(String id) throws Exception {
        Connection connection=DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean isDeleted=serviceDetailDAO.delete(id);
            if(!isDeleted){
                connection.rollback();
                return false;
            }
            isDeleted=reservationDetailDAO.delete(id);
            if(! isDeleted){
                connection.rollback();
                return false;
            }
            isDeleted=reservationDAO.delete(id);
            if(! isDeleted){
                connection.rollback();
                return false;
            }
            connection.commit();
            return true;
        }catch (Throwable throwable){
            throwable.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public boolean placeOrder(ReservationDTO reservationDTO) throws Exception {
        Connection connection= DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isAdded = reservationDAO.save(new Reservation(
                    reservationDTO.getReservationId(),
                    reservationDTO.getCustomerId(),
                    reservationDTO.getReservationDate()
            ));
            if(! isAdded){
                connection.rollback();
                return false;
            }

            isAdded=reservationDetailDAO.save(new ReservationDetail(
                    reservationDTO.getReservationId(),
                    reservationDTO.getRoomId(),
                    reservationDTO.getCheckInDate(),
                    reservationDTO.getCheckOutDate(),
                    reservationDTO.getFullPayment()
            ));
            if(! isAdded){
                connection.rollback();
                return false;
            }

            isAdded=serviceDetailDAO.save(new ServiceDetail(
                    reservationDTO.getReservationId(),
                    reservationDTO.getServiceId(),
                    reservationDTO.getTerms(),
                    reservationDTO.getServiceAmount()
            ));
            if(! isAdded){
                connection.rollback();
                return false;
            }
            connection.commit();
            return true;
        }catch (Throwable throwables){
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}

package entity;

public class ReservationDetail implements SuperEntity{
    private String reservationId;
    private String roomId;
    private String checkInDate;
    private String checkOutDate;
    private double fullPayment;

    public ReservationDetail() {
    }

    public ReservationDetail(String reservationId, String roomId, String checkInDate, String checkOutDate, double fullPayment) {
        this.setReservationId(reservationId);
        this.setRoomId(roomId);
        this.setCheckInDate(checkInDate);
        this.setCheckOutDate(checkOutDate);
        this.setFullPayment(fullPayment);
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public double getFullPayment() {
        return fullPayment;
    }

    public void setFullPayment(double fullPayment) {
        this.fullPayment = fullPayment;
    }
}

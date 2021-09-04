package entity;

public class Reservation implements SuperEntity{
    private String reservationId;
    private String customerId;
    private String reservationDate;

    public Reservation(String reservationId, String customerId, String reservationDate) {
        this.setReservationId(reservationId);
        this.setCustomerId(customerId);
        this.setReservationDate(reservationDate);
    }

    public Reservation() {
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }
}

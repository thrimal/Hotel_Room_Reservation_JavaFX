package view.tm;

public class ReservationTM {
    private String reservationId;
    private String customerId;
    private String serviceId;
    private String roomId;
    private String reservationDate;
    private String checkInDate;
    private String checkOutDate;
    private int terms;
    private double serviceAmount;
    private double fullPayment;

    public ReservationTM() {
    }

    public ReservationTM(String reservationId, String customerId, String serviceId, String roomId, String reservationDate, String checkInDate, String checkOutDate, int terms, double serviceAmount, double fullPayment) {
        this.setReservationId(reservationId);
        this.setCustomerId(customerId);
        this.setServiceId(serviceId);
        this.setRoomId(roomId);
        this.setReservationDate(reservationDate);
        this.setCheckInDate(checkInDate);
        this.setCheckOutDate(checkOutDate);
        this.setTerms(terms);
        this.setServiceAmount(serviceAmount);
        this.setFullPayment(fullPayment);
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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
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

    public int getTerms() {
        return terms;
    }

    public void setTerms(int terms) {
        this.terms = terms;
    }

    public double getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(double serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public double getFullPayment() {
        return fullPayment;
    }

    public void setFullPayment(double fullPayment) {
        this.fullPayment = fullPayment;
    }
}

package entity;

public class ServiceDetail implements SuperEntity{
    private String reservationId;
    private String serviceId;
    private int terms;
    private double serviceAmount;

    public ServiceDetail() {
    }

    public ServiceDetail(String reservationId, String serviceId, int terms, double serviceAmount) {
        this.setReservationId(reservationId);
        this.setServiceId(serviceId);
        this.setTerms(terms);
        this.setServiceAmount(serviceAmount);
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
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
}

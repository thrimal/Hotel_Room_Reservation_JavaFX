package dao;

public interface QueryDAO extends SuperDAO {
    public String getCustomerId() throws Exception;
    public String getReservationId() throws Exception;
    public String getRoomId() throws Exception;
    public String getServiceId() throws Exception;
}

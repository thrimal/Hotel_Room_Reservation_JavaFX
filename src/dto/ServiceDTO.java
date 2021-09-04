package dto;

import javafx.collections.ObservableArray;

public class ServiceDTO {
    private String serviceId;
    private String serviceType;
    private double serviceAmount;

    public ServiceDTO() {
    }

    public ServiceDTO(String serviceId, String serviceType, double serviceAmount) {
        this.setServiceId(serviceId);
        this.setServiceType(serviceType);
        this.setServiceAmount(serviceAmount);
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public double getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(double serviceAmount) {
        this.serviceAmount = serviceAmount;
    }
}

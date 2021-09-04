package dto;

public class CustomerDTO {
    private String customerId;
    private String customerName;
    private String address;
    private int contact;
    private String email;
    private String gender;
    private String NIC;

    public CustomerDTO() {
    }

    public CustomerDTO(String customerId, String customerName, String address, int contact, String email, String gender, String NIC) {
        this.setCustomerId(customerId);
        this.setCustomerName(customerName);
        this.setAddress(address);
        this.setContact(contact);
        this.setEmail(email);
        this.setGender(gender);
        this.setNIC(NIC);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }
}

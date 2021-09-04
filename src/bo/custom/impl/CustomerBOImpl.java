package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DaoFactory;
import dao.QueryDAO;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;

import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO{

    private CustomerDAO customerDAO=DaoFactory.getInstance().getDao(DaoFactory.DaoType.CUSTOMER);
    private QueryDAO queryDAO=DaoFactory.getInstance().getDao(DaoFactory.DaoType.QUERY);


    @Override
    public boolean deleteCustomer(String id) throws Exception {
        return customerDAO.delete(id);
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws Exception{
        return customerDAO.update(new Customer(
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
    public CustomerDTO searchCustomer(String id) throws Exception {
        Customer customer = customerDAO.search(id);
        return new CustomerDTO(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getAddress(),
                customer.getContact(),
                customer.getEmail(),
                customer.getGender(),
                customer.getNIC()
        );
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomer() throws Exception {
        ArrayList<Customer> customers=customerDAO.getAll();
        ArrayList<CustomerDTO> customerList=new ArrayList<>();
        for (Customer c:customers) {
            customerList.add(new CustomerDTO(
                    c.getCustomerId(),
                    c.getCustomerName(),
                    c.getAddress(),
                    c.getContact(),
                    c.getEmail(),
                    c.getGender(),
                    c.getNIC()
            ));
        }
        return customerList;
    }

    @Override
    public String getCustomerId() throws Exception {
        return queryDAO.getCustomerId();
    }
}

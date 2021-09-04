package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import java.sql.ResultSet;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean save(Customer customer) throws Exception {
        return CrudUtil.execute("INSERT INTO Customer VALUES(?,?,?,?,?,?,?)",
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getAddress(),
                customer.getContact(),
                customer.getEmail(),
                customer.getGender(),
                customer.getNIC());
    }

    @Override
    public boolean update(Customer customer) throws Exception {
        return CrudUtil.execute("UPDATE Customer SET Name=?,Address=?,Contact=?,Email=?,Gender=?,NIC=? WHERE CID=?",
                customer.getCustomerName(),
                customer.getAddress(),
                customer.getContact(),
                customer.getEmail(),
                customer.getGender(),
                customer.getNIC(),
                customer.getCustomerId());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM Customer WHERE CID=?",s);
    }

    @Override
    public Customer search(String s) throws Exception {
        ResultSet res=CrudUtil.execute("SELECT * From Customer WHERE NIC=?",s);
        if(res.next()){
            return new Customer(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getInt(4),
                    res.getString(5),
                    res.getString(6),
                    res.getString(7)
            );
        }
        return null;
    }

    @Override
    public ArrayList<Customer> getAll() throws Exception {
        ResultSet res = CrudUtil.execute("SELECT * From Customer");
        ArrayList<Customer> customerList=new ArrayList<>();
        while (res.next()){
            customerList.add(new Customer(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getInt(4),
                    res.getString(5),
                    res.getString(6),
                    res.getString(7)
            ));
        }
        return customerList;
    }
}

package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ServiceDAO;
import entity.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceDAOImpl implements ServiceDAO {
    @Override
    public boolean save(Service service) throws Exception {
        return CrudUtil.execute("INSERT INTO Service VALUES(?,?,?)",
                service.getServiceId(),
                service.getServiceType(),
                service.getServiceAmount());
    }

    @Override
    public boolean update(Service service) throws Exception {
        return CrudUtil.execute("UPDATE Service SET ServiceType=?,Amount=? WHERE SID=?",
                service.getServiceType(),
                service.getServiceAmount(),
                service.getServiceId());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM Service WHERE SID=?",s);
    }

    @Override
    public Service search(String s) throws Exception {
        ResultSet res = CrudUtil.execute("SELECT * FROM Service WHERE SID=?", s);
        if(res.next()){
            return new Service(res.getString(1),res.getString(2),res.getDouble(3));
        }
        return null;
    }

    @Override
    public ArrayList<Service> getAll() throws Exception {
        ResultSet res = CrudUtil.execute("SELECT * FROM Service");
        ArrayList<Service> serviceList=new ArrayList();
        while(res.next()){
            serviceList.add(new Service(
                    res.getString(1),
                    res.getString(2),
                    res.getDouble(3)
            ));
        }
        return serviceList;
    }

    @Override
    public Service getServiceByType(String type) throws SQLException, ClassNotFoundException {
        ResultSet res=CrudUtil.execute("SELECT * FROM Service WHERE ServiceType=?",type);
        if(res.next()){
            return new Service(
                    res.getString(1),res.getString(2),res.getDouble(3)
            );
        }
        return null;
    }
}

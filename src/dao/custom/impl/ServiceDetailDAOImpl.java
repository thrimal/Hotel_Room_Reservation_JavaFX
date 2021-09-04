package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ServiceDetailDAO;
import entity.ServiceDetail;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ServiceDetailDAOImpl implements ServiceDetailDAO {
    @Override
    public boolean save(ServiceDetail serviceDetail) throws Exception {
        return CrudUtil.execute("INSERT INTO ServiceDetail VALUES(?,?,?,?)",

                serviceDetail.getReservationId(),
                serviceDetail.getServiceId(),
                serviceDetail.getTerms(),
                serviceDetail.getServiceAmount()
                );
    }

    @Override
    public boolean update(ServiceDetail serviceDetail) throws Exception {
        return CrudUtil.execute("UPDATE ServiceDetail SET SID=?,Terms=?,SerAmount=? WHERE ResId=?",
                serviceDetail.getServiceId(),
                serviceDetail.getTerms(),
                serviceDetail.getServiceAmount(),
                serviceDetail.getReservationId()
                );
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM ServiceDetail WHERE ResId=?",s);
    }

    @Override
    public ServiceDetail search(String s) throws Exception {
        ResultSet res = CrudUtil.execute("SELECT * FROM ServiceDetail WHERE SID=?", s);
        if(res.next()){
            return new ServiceDetail(
                    res.getString(1),
                    res.getString(2),
                    res.getInt(3),
                    res.getDouble(4)
            );
        }
        return null;
    }

    @Override
    public ArrayList<ServiceDetail> getAll() throws Exception {
        ArrayList<ServiceDetail> serviceDetailList=new ArrayList<>();
        ResultSet res = CrudUtil.execute("SELECT * FROM ServiceDetail");
        while (res.next()){
            serviceDetailList.add(new ServiceDetail(
                    res.getString(1),
                    res.getString(2),
                    res.getInt(3),
                    res.getDouble(4)
            ));
        }
        return serviceDetailList;
    }
}

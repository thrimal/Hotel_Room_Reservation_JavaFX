package dao.custom;

import dao.CrudDAO;
import entity.Service;

import java.sql.SQLException;

public interface ServiceDAO extends CrudDAO<Service,String> {
    public Service getServiceByType(String type) throws SQLException, ClassNotFoundException;
}

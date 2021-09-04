package bo.custom;

import bo.SuperBO;
import dto.ServiceDTO;
import entity.Service;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ServiceBO extends SuperBO {
    public boolean saveService(ServiceDTO serviceDTO)throws Exception;
    public boolean deleteService(String id)throws Exception;
    public boolean updateService(ServiceDTO serviceDTO)throws Exception;
    public ServiceDTO searchService(String id)throws Exception;
    public ArrayList<ServiceDTO> getAllService()throws Exception;
    public ServiceDTO getServiceByType(String type) throws SQLException, ClassNotFoundException;
    public String getServiceId()throws Exception;
}

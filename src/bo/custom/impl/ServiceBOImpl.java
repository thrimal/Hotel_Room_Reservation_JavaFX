package bo.custom.impl;

import bo.custom.ServiceBO;
import dao.DaoFactory;
import dao.QueryDAO;
import dao.custom.ServiceDAO;
import dto.ServiceDTO;
import entity.Service;

import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceBOImpl implements ServiceBO {

    private ServiceDAO serviceDAO= DaoFactory.getInstance().getDao(DaoFactory.DaoType.SERVICE);

    private QueryDAO queryDAO=DaoFactory.getInstance().getDao(DaoFactory.DaoType.QUERY);

    @Override
    public boolean saveService(ServiceDTO serviceDTO) throws Exception {
        return serviceDAO.save(new Service(
                serviceDTO.getServiceId(),
                serviceDTO.getServiceType(),
                serviceDTO.getServiceAmount()
        ));
    }

    @Override
    public boolean deleteService(String id) throws Exception {
        return serviceDAO.delete(id);
    }

    @Override
    public boolean updateService(ServiceDTO serviceDTO) throws Exception {
        return serviceDAO.update(new Service(
                serviceDTO.getServiceId(),
                serviceDTO.getServiceType(),
                serviceDTO.getServiceAmount()
        ));
    }

    @Override
    public ServiceDTO searchService(String id) throws Exception {
        Service service = serviceDAO.search(id);
        return new ServiceDTO(
                service.getServiceId(),
                service.getServiceType(),
                service.getServiceAmount()
        );
    }

    @Override
    public ArrayList<ServiceDTO> getAllService() throws Exception {
        ArrayList<Service> allService = serviceDAO.getAll();
        ArrayList<ServiceDTO> serviceList=new ArrayList<>();
        for (Service s : allService){
            serviceList.add(new ServiceDTO(
                    s.getServiceId(),
                    s.getServiceType(),
                    s.getServiceAmount()
            ));
        }
        return serviceList;
    }

    @Override
    public ServiceDTO getServiceByType(String type) throws SQLException, ClassNotFoundException {
        Service service = serviceDAO.getServiceByType(type);
        return new ServiceDTO(
                service.getServiceId(),
                service.getServiceType(),
                service.getServiceAmount()
        );
    }

    @Override
    public String getServiceId() throws Exception {
        return queryDAO.getServiceId();
    }
}

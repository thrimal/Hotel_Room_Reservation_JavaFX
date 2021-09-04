package dao.custom;

import dao.CrudDAO;
import entity.Room;

import java.util.ArrayList;

public interface RoomDAO extends CrudDAO<Room,String> {
    public ArrayList<Room> getRoomsByType(String type)throws Exception;
    public boolean updateStatusUnavailable(String id) throws Exception;
    public boolean updateStatusAvailable(String id)throws Exception;
}

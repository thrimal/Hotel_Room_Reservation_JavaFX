package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import dto.RoomDTO;

import java.util.ArrayList;

public interface RoomBO extends SuperBO {
    public boolean saveRoom(RoomDTO roomDTO)throws Exception;
    public boolean deleteRoom(String id)throws Exception;
    public boolean updateRoom(RoomDTO roomDTO)throws Exception;
    public RoomDTO searchRoom(String id)throws Exception;
    public ArrayList<RoomDTO> getAllRooms()throws Exception;
    public ArrayList<RoomDTO> getAllRoomsByCondition(String condition)throws Exception;
    public boolean updateStatusUnavailable(String id) throws Exception;
    public boolean updateStatusAvailable(String id)throws Exception;
    public String getRoomId()throws Exception;
}

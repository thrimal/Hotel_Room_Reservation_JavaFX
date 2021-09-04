package bo.custom.impl;

import bo.custom.RoomBO;
import dao.DaoFactory;
import dao.QueryDAO;
import dao.custom.RoomDAO;
import dto.RoomDTO;
import entity.Room;

import java.sql.ResultSet;
import java.util.ArrayList;

public class RoomBOImpl implements RoomBO {

    private RoomDAO roomDAO= DaoFactory.getInstance().getDao(DaoFactory.DaoType.ROOM);
    private QueryDAO queryDAO=DaoFactory.getInstance().getDao(DaoFactory.DaoType.QUERY);


    @Override
    public boolean saveRoom(RoomDTO roomDTO) throws Exception {
        return roomDAO.save(new Room(
                roomDTO.getRoomId(),
                roomDTO.getRoomStatus(),
                roomDTO.getRoomType(),
                roomDTO.getRoomCondition(),
                roomDTO.getRoomAmount()));
    }

    @Override
    public boolean deleteRoom(String id) throws Exception {
        return roomDAO.delete(id);
    }

    @Override
    public boolean updateRoom(RoomDTO roomDTO) throws Exception {
        return roomDAO.update(new Room(
                roomDTO.getRoomId(),
                roomDTO.getRoomStatus(),
                roomDTO.getRoomType(),
                roomDTO.getRoomCondition(),
                roomDTO.getRoomAmount()
                ));
    }

    @Override
    public RoomDTO searchRoom(String id) throws Exception {
        Room room=roomDAO.search(id);
        return new RoomDTO(
                room.getRoomId(),
                room.getRoomStatus(),
                room.getRoomType(),
                room.getRoomCondition(),
                room.getRoomAmount());
    }

    @Override
    public ArrayList<RoomDTO> getAllRooms() throws Exception {
        ArrayList<Room> rooms=roomDAO.getAll();
        ArrayList<RoomDTO> roomList=new ArrayList<>();
        for (Room r : rooms){
            roomList.add(new RoomDTO(
                    r.getRoomId(),
                    r.getRoomStatus(),
                    r.getRoomType(),
                    r.getRoomCondition(),
                    r.getRoomAmount()));
        }
        return roomList;
    }

    @Override
    public ArrayList<RoomDTO> getAllRoomsByCondition(String condition) throws Exception {
        ArrayList<Room> daoRoomsByType = roomDAO.getRoomsByType(condition);
        ArrayList<RoomDTO> roomsByType=new ArrayList<>();
        for (Room r:daoRoomsByType) {
            roomsByType.add(new RoomDTO(
                    r.getRoomId(),
                    r.getRoomStatus(),
                    r.getRoomType(),
                    r.getRoomCondition(),
                    r.getRoomAmount()
            ));
        }
        return roomsByType;
    }

    @Override
    public boolean updateStatusUnavailable(String id) throws Exception {
        return roomDAO.updateStatusUnavailable(id);
    }

    @Override
    public boolean updateStatusAvailable(String id) throws Exception {
        return roomDAO.updateStatusAvailable(id);
    }

    @Override
    public String getRoomId() throws Exception {
        return queryDAO.getRoomId();
    }
}

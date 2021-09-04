package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.RoomDAO;
import entity.Room;

import java.sql.ResultSet;
import java.util.ArrayList;

public class RoomDAOImpl implements RoomDAO {
    @Override
    public boolean save(Room room) throws Exception {
        return CrudUtil.execute("INSERT INTO Room VALUES(?,?,?,?,?)",
                room.getRoomId(),
                room.getRoomStatus(),
                room.getRoomType(),
                room.getRoomCondition(),
                room.getRoomAmount());
    }

    @Override
    public boolean update(Room room) throws Exception {
        return CrudUtil.execute("UPDATE Room SET Status=?,RoomType=?,RoomCondition=?,PerDayAmount=? WHERE RID=?",
                room.getRoomStatus(),
                room.getRoomType(),
                room.getRoomCondition(),
                room.getRoomAmount(),
                room.getRoomId());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM Room WHERE RID=?",s);
    }

    @Override
    public Room search(String s) throws Exception {
        ResultSet res=CrudUtil.execute("SELECT * FROM Room WHERE RID=?",s);
        if(res.next()){
            return new Room(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getDouble(5));
        }
        return null;
    }

    @Override
    public ArrayList<Room> getAll() throws Exception {
        ResultSet res=CrudUtil.execute("SELECT * FROM Room");
        ArrayList<Room> roomList=new ArrayList<>();
        while(res.next()){
            roomList.add(new Room(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getDouble(5)
            ));
        }
        return roomList;
    }

    @Override
    public ArrayList<Room> getRoomsByType(String type) throws Exception {
        ArrayList<Room> roomListByCondition=new ArrayList<>();
        ResultSet res = CrudUtil.execute("SELECT * FROM Room WHERE status='Available' && RoomCondition=?",type);
        while(res.next()){
            roomListByCondition.add(new Room(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getDouble(5)
            ));
        }
        return roomListByCondition;
    }

    @Override
    public boolean updateStatusUnavailable(String id) throws Exception {
        return CrudUtil.execute("UPDATE Room SET Status='Non Available' WHERE RID=?",id);
    }

    @Override
    public boolean updateStatusAvailable(String id) throws Exception {
        return CrudUtil.execute("UPDATE Room SET Status='Available' WHERE RID=?",id);
    }
}

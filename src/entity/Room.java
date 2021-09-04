package entity;

public class Room implements SuperEntity{
    private String roomId;
    private String roomStatus;
    private String roomType;
    private String roomCondition;
    private double roomAmount;

    public Room() {
    }

    public Room(String roomId, String roomStatus, String roomType, String roomCondition, double roomAmount) {
        this.setRoomId(roomId);
        this.setRoomStatus(roomStatus);
        this.setRoomType(roomType);
        this.setRoomCondition(roomCondition);
        this.setRoomAmount(roomAmount);
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomCondition() {
        return roomCondition;
    }

    public void setRoomCondition(String roomCondition) {
        this.roomCondition = roomCondition;
    }

    public double getRoomAmount() {
        return roomAmount;
    }

    public void setRoomAmount(double roomAmount) {
        this.roomAmount = roomAmount;
    }
}

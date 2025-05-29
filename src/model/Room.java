package model;

public class Room {
    private int roomId;
    private int roomTypeId;
    private String roomNumber;
    private boolean isAvailable;
    
    public Room (int roomId, int roomTypeId, String roomNumber, boolean isAvailable) {
        this.roomId = roomId;
        this.roomTypeId = roomTypeId;
        this.roomNumber = roomNumber;
        this.isAvailable = isAvailable;
    }
    
    // Getters
    public int getRoomId() { return roomId; }
    public int getRoomTypeId() { return roomTypeId; }
    public String getRoomNumber() { return roomNumber; }
    public boolean getIsAvailable() { return isAvailable; }
    
    // Setters
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public void setRoomTypeId(int roomTypeId) { this.roomTypeId = roomTypeId; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public void setIsAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }
}

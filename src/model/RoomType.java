package model;

public class RoomType {
    private int roomTypeId;
    private String typeName;
    private int price;
    
    public RoomType(int roomTypeId, String typeName, int price) {
        this.roomTypeId = roomTypeId;
        this.typeName = typeName;
        this.price = price;
    }
    
    // Getters
    public int getRoomTypeId() { return roomTypeId; }
    public String getTypeName() { return typeName; }
    public int getPrice() { return price; }
    
    // Setters;
    public void setRoomTypeId(int roomTypeId) { this.roomTypeId = roomTypeId; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
    public void setPrice(int price) { this.price = price; }
}

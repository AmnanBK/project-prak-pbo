package model;

import java.util.Date;

public class Reservation {
    private int reservationId;
    private int guestId;
    private int roomId;

    private String firstName;
    private String lastName;
    private String guestName;
    private String email;
    private String phoneNumber;

    private String roomType;
    private String roomNumber;

    private Date checkInDate;
    private Date checkOutDate;

    private int totalPrice;
    private boolean checkedOut;

    // Constructor: Basic reservation
    public Reservation(int reservationId, int guestId, int roomId, Date checkInDate, Date checkOutDate) {
        this.reservationId = reservationId;
        this.guestId = guestId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    // Constructor: Display purposes with guest name and room number
    public Reservation(int reservationId, String guestName, String roomNumber,
                       Date checkInDate, Date checkOutDate, int totalPrice, boolean isActive) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.checkedOut = !isActive;
    }

    // Constructor: Full reservation info with guest and room details
    public Reservation(int reservationId, int guestId,
                       String firstName, String lastName, String email, String phoneNumber,
                       String roomType, String roomNumber,
                       Date checkInDate, Date checkOutDate) {
        this.reservationId = reservationId;
        this.guestId = guestId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roomType = roomType;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    // Getters
    public int getReservationId() { return reservationId; }
    public int getGuestId() { return guestId; }
    public int getRoomId() { return roomId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getGuestName() { return guestName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getRoomType() { return roomType; }
    public String getRoomNumber() { return roomNumber; }
    public Date getCheckInDate() { return checkInDate; }
    public Date getCheckOutDate() { return checkOutDate; }
    public int getTotalPrice() { return totalPrice; }
    public boolean isCheckedOut() { return checkedOut; }

    // Setters
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }
    public void setGuestId(int guestId) { this.guestId = guestId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; }
    public void setCheckOutDate(Date checkOutDate) { this.checkOutDate = checkOutDate; }
}
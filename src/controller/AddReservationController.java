package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.List;

import model.Guest;
import model.GuestDAO;
import model.Reservation;
import model.ReservationDAO;
import model.Room;
import model.RoomDAO;
import view.DashboardView;
import view.AddReservationView;

public class AddReservationController {
    private AddReservationView view;
    private GuestDAO guestDAO;
    private ReservationDAO reservationDAO;
    private RoomDAO roomDAO;
    private boolean guestExist = false;
    private List<Room> currentRoomList = new ArrayList<>();

    public AddReservationController(AddReservationView view) {
        this.view = view;
        this.guestDAO = new GuestDAO();
        this.reservationDAO = new ReservationDAO();
        this.roomDAO = new RoomDAO();

        setRoomsChoice();
        initController();
    }

    private void initController() {
        view.setBtnSearchListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchGuest();
            }
        });

        view.setBtnBackListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DashboardView dashboardView = new DashboardView();
                new DashboardController(dashboardView);
                view.dispose();
            }
        });

        view.setBtnSubmitListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitReservation();
            }
        });

        view.setRoomTypeChangeListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRoomsChoice();
            }
        });
    }

    private void searchGuest() {
        try {
            int guestId = Integer.parseInt(view.getGuestNIK());
            Guest guest = guestDAO.findById(guestId);
            if (guest != null) {
                view.setFirstName(guest.getFirstName());
                view.setLastName(guest.getLastName());
                view.setEmail(guest.getEmail());
                view.setPhone(guest.getPhoneNumber());
                guestExist = true;
            } else {
                JOptionPane.showMessageDialog(view, "Guest not found.");
                guestExist = false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Invalid NIK format. Please enter a valid number.");
        }
    }

    private void submitReservation() {
        try {
            int guestId = Integer.parseInt(view.getGuestNIK());
            String firstName = view.getFirstName();
            String lastName = view.getLastName();
            String email = view.getEmail();
            String phone = view.getPhone();
            String roomNumber = view.getRoomNumber();
            String roomType = view.getRoomType();
            java.util.Date checkInDate = view.getCheckInDate();
            java.util.Date checkOutDate = view.getCheckOutDate();

            if (checkInDate == null || checkOutDate == null || checkInDate.after(checkOutDate) ) {
                JOptionPane.showMessageDialog(view, "Please select valid check-in and check-out dates.");
                return;
            }

            if (!guestExist) {
                Guest newGuest = new Guest(guestId, firstName, lastName, email, phone);
                boolean guestInserted = guestDAO.insert(newGuest);
                if (!guestInserted) {
                    JOptionPane.showMessageDialog(view, "Failed to insert new guest.");
                    return;
                }
            }

            int roomTypeId = getRoomTypeIdFromName(roomType);
            Room selectedRoom = currentRoomList.stream()
                .filter(r -> r.getRoomNumber().equals(roomNumber))
                .findFirst()
                .orElse(null);
            if (selectedRoom == null) {
                JOptionPane.showMessageDialog(view, "Room not found.");
                return;
            }

            Reservation reservation = new Reservation(
                0,
                guestId,
                selectedRoom.getRoomId(),
                new java.sql.Date(checkInDate.getTime()),
                new java.sql.Date(checkOutDate.getTime())
            );

            boolean success = reservationDAO.insert(reservation);
            if (success) {
                JOptionPane.showMessageDialog(view, "Reservation successful!");
                view.dispose();
                DashboardView dashboardView = new DashboardView();
                new DashboardController(dashboardView);
            } else {
                JOptionPane.showMessageDialog(view, "Failed to save reservation.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Invalid NIK.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void setRoomsChoice() {
        String roomType = view.getRoomType();
        int roomTypeId = getRoomTypeIdFromName(roomType);

        try {
            currentRoomList = roomDAO.getAvailableRoomsByType(roomTypeId);
            String[] roomNumbers = currentRoomList.stream().map(Room::getRoomNumber).toArray(String[]::new);
            view.setRoomNumberOptions(roomNumbers);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Failed to load rooms: " + e.getMessage());
        }
    }

    private int getRoomTypeIdFromName(String roomType) {
        if (roomType.equals("Standard")) return 1;
        else if (roomType.equals("Deluxe")) return 2;
        else if (roomType.equals("Suite")) return 3;
        else if (roomType.equals("Presidential")) return 4;
        return 0;
    }
}
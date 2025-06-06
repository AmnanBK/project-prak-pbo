package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import model.Guest;
import model.GuestDAO;
import model.Reservation;
import model.ReservationDAO;
import model.Room;
import model.RoomDAO;
import model.RoomType;
import model.RoomTypeDAO;
import view.DashboardView;
import view.AddReservationView;
import view.ShowReservationView;

public class AddReservationController {
    private AddReservationView view;
    private GuestDAO guestDAO;
    private ReservationDAO reservationDAO;
    private RoomDAO roomDAO;
    private boolean guestExist = false;
    private List<Room> currentRoomList = new ArrayList<>();
    private boolean isEditMode = false;
    private Reservation reservationEdit;
    private String currentRoomNumber = "";

    public AddReservationController(AddReservationView view) {
        this.view = view;
        this.guestDAO = new GuestDAO();
        this.reservationDAO = new ReservationDAO();
        this.roomDAO = new RoomDAO();
        loadData();
        initController();
    }
    
    public AddReservationController(AddReservationView view, Reservation reservation) {
        this.view = view;
        this.guestDAO = new GuestDAO();
        this.reservationDAO = new ReservationDAO();
        this.roomDAO = new RoomDAO();
        this.reservationEdit = reservation;
        this.isEditMode = true;
        this.guestExist = true;
        this.currentRoomNumber = reservation.getRoomNumber();
        loadData();
        initController();
    }

    private void initController() {
        initBtnSearchListener();
        initBtnBackListener();
        initBtnSubmitListener();
        initRoomTypeChangeListener();
    }

    private void initBtnSearchListener() {
        view.setBtnSearchListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int guestId = Integer.parseInt(view.getGuestId());
                    Guest guest = guestDAO.findById(guestId);
                    if (guest != null) {
                        view.setFirstName(guest.getFirstName());
                        view.setLastName(guest.getLastName());
                        view.setEmail(guest.getEmail());
                        view.setPhone(guest.getPhoneNumber());
                        guestExist = true;
                    } else {
                        showMessage("Guest not found.");
                        guestExist = false;
                    }
                } catch (NumberFormatException ex) {
                    showMessage("Invalid ID format. Please enter a valid number.");
                }
            }
        });
    }

    private void initBtnBackListener() {
        view.setBtnBackListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DashboardView dashboardView = new DashboardView();
                new DashboardController(dashboardView);
                view.dispose();
            }
        });
    }

    private void initBtnSubmitListener() {
        view.setBtnSubmitListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int guestId = Integer.parseInt(view.getGuestId());
                    String firstName = view.getFirstName();
                    String lastName = view.getLastName();
                    String email = view.getEmail();
                    String phone = view.getPhone();
                    String roomNumber = view.getRoomNumber();
                    String roomType = view.getRoomType();
                    Date checkInDate = view.getCheckInDate();
                    Date checkOutDate = view.getCheckOutDate();

                    if (firstName == null || lastName == null || email == null || phone == null) {
                        showMessage("Please complete all fields");
                        return;
                    }

                    if ((firstName + lastName).matches(".*\\d.*")) {
                        JOptionPane.showMessageDialog(view, "Name should not contain numbers.");
                        return;
                    }

                    if (phone.length() < 10) {
                        showMessage("Phone number must be at least 10 digits long.");
                        return;
                    }

                    if (checkInDate == null || checkOutDate == null || checkInDate.after(checkOutDate) ) {
                        JOptionPane.showMessageDialog(view, "Please select valid check-in and check-out dates.");
                        return;
                    }

                    Guest guest = new Guest(guestId, firstName, lastName, email, phone);
                    if (!guestExist) {
                        boolean success = guestDAO.insert(guest);
                        if (!success) {
                            showMessage("Failed to insert new guest.");
                            return;
                        }
                    } else {
                        boolean success = guestDAO.update(guest);
                        if (!success) {
                            showMessage("Failed to update guest.");
                            return;
                        }
                    }

                    Room selectedRoom = currentRoomList.stream()
                            .filter(r -> r.getRoomNumber().equals(roomNumber))
                            .findFirst()
                            .orElse(null);
                    if (selectedRoom == null) {
                        showMessage("Room not found.");
                        return;
                    }

                    Reservation reservation = new Reservation(
                            0,
                            guestId,
                            selectedRoom.getRoomId(),
                            new java.sql.Date(checkInDate.getTime()),
                            new java.sql.Date(checkOutDate.getTime())
                    );

                    if (!isEditMode) {
                        boolean reservationSuccess = reservationDAO.insert(reservation);
                        boolean roomSuccess =  roomDAO.updateRoomAvailability(view.getRoomNumber(), false);

                        if (reservationSuccess && roomSuccess) {
                            showMessage("Reservation successful!");
                            ShowReservationView reservationView = new ShowReservationView();
                            new ShowReservationController(reservationView);
                            view.dispose();
                        } else {
                            showMessage("Failed to save reservation.");
                        }
                    } else {
                        reservation.setReservationId(reservationEdit.getReservationId());
                        reservation.setGuestId(reservationEdit.getGuestId());
                        roomDAO.updateRoomAvailability(reservationEdit.getRoomNumber(), true);
                        roomDAO.updateRoomAvailability(view.getRoomNumber(), false);

                        boolean success = reservationDAO.update(reservation);
                        if (success) {
                            showMessage("Reservation successfully edited");
                            ShowReservationView reservationView = new ShowReservationView();
                            new ShowReservationController(reservationView);
                            view.dispose();
                        } else {
                            showMessage("Failed to edit reservation.");
                        }
                    }

                } catch (NumberFormatException ex) {
                    showMessage("Invalid guest Id.");
                } catch (Exception ex) {
                    showMessage("Error: " + ex.getMessage());
                }
            }
        });
    }

    private void initRoomTypeChangeListener() {
        view.setRoomTypeChangeListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String roomType = view.getRoomType();
                int roomTypeId = new RoomTypeDAO().findByName(roomType).getRoomTypeId();

                String selectedRoomNumber = null;

                try {
                    if(isEditMode) {
                        currentRoomList = roomDAO.findAvailableRooms(roomTypeId, reservationEdit.getRoomNumber());
                        selectedRoomNumber = reservationEdit.getRoomNumber();
                    } else {
                        currentRoomList = roomDAO.findAvailableRooms(roomTypeId, "0");
                    }

                    String[] roomNumbers = currentRoomList.stream().map(Room::getRoomNumber).toArray(String[]::new);
                    view.setRoomNumberOptions(roomNumbers);

                    if (isEditMode && selectedRoomNumber != null) {
                        view.setSelectedRoomNumber(selectedRoomNumber);
                    }

                } catch (Exception ex) {
                    showMessage("Failed to load rooms: " + ex.getMessage());
                }
            }
        });
    }
    
    private void loadData() {
        List<RoomType> roomType = new RoomTypeDAO().findAll();
        String[] roomsTypeName = roomType.stream().map(RoomType::getTypeName).toArray(String[]::new);
        view.setRoomTypeOptions(roomsTypeName);

        if (isEditMode) {
            view.setFirstName(reservationEdit.getFirstName());
            view.setLastName(reservationEdit.getLastName());
            view.setEmail(reservationEdit.getEmail());
            view.setPhone(reservationEdit.getPhoneNumber());
            view.setCheckInDate(reservationEdit.getCheckInDate());
            view.setCheckOutDate(reservationEdit.getCheckOutDate());
            view.setGuestId(String.valueOf(reservationEdit.getGuestId()));
            view.setSelectedRoomType(reservationEdit.getRoomType());
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(view, message);
    }
}
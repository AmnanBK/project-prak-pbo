package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.Room;
import model.RoomDAO;
import model.RoomType;
import model.RoomTypeDAO;
import view.AddRoomView;
import view.RoomDetailsView;

public class AddRoomController {
    private AddRoomView view;
    private RoomDAO roomDAO;
    private RoomTypeDAO roomTypeDAO;
    private List<RoomType> roomTypeList;
    private Room existingRoom;
    private boolean isEditMode = false;

    public AddRoomController(AddRoomView view) {
        this(view, null);
    }
    
    public AddRoomController(AddRoomView view, Room existingRoom) {
        this.view = view;
        this.roomDAO = new RoomDAO();
        this.roomTypeDAO = new RoomTypeDAO();
        this.existingRoom = existingRoom;
        this.isEditMode = existingRoom != null;

        loadRoomTypes();

        if (isEditMode) {
            String selectedTypeName = roomTypeDAO.findById(existingRoom.getRoomTypeId()).getTypeName();
            view.setRoomNumber(existingRoom.getRoomNumber());
            view.setSelectedRoomType(selectedTypeName);
        }

        initController();
    }

    private void initController() {
        initBtnCancelListener();
        initBtnSubmitListener();
    }

    private void initBtnCancelListener() {
        view.setBtnCancelListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                navigateToRoomDetailsView();
            }
        });
    }

    private void initBtnSubmitListener() {
        view.setBtnSubmitListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String roomNumber = view.getRoomNumber();
                String selectedTypeName = view.getSelectedRoomType();

                if (roomNumber.isEmpty() || selectedTypeName == null) {
                    showMessage("Please complete all fields.");
                    return;
                }

                int roomTypeId = 0;
                for (RoomType type : roomTypeList) {
                    if (type.getTypeName().equals(selectedTypeName)) {
                        roomTypeId = type.getRoomTypeId();
                        break;
                    }
                }

                if (roomTypeId == 0) {
                    showMessage("Invalid room type.");
                    return;
                }

                if (isEditMode) {
                    updateRoom(roomNumber, roomTypeId);
                } else {
                    insertRoom(roomNumber, roomTypeId);
                }
            }
        });
    }

    private void updateRoom(String roomNumber, int roomTypeId) {
        existingRoom.setRoomNumber(roomNumber);
        existingRoom.setRoomTypeId(roomTypeId);

        boolean success = roomDAO.update(existingRoom);

        if (success) {
            showMessage("Room updated successfully.");
            navigateToRoomDetailsView();
        } else {
            showMessage("Failed to update room.");
        }
    }

    private void insertRoom(String roomNumber, int roomTypeId) {
        Room newRoom = new Room(0, roomTypeId, roomNumber, true);

        boolean success = roomDAO.insert(newRoom);

        if (success) {
            showMessage("Room added successfully.");
            navigateToRoomDetailsView();
        } else {
            showMessage("Failed to add room.");
        }
    }

    private void loadRoomTypes() {
        roomTypeList = roomTypeDAO.findAll();
        String[] typeNames = roomTypeList.stream()
                .map(RoomType::getTypeName)
                .toArray(String[]::new);

        view.setRoomTypeOptions(typeNames);
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(view, message);
    }

    private void navigateToRoomDetailsView() {
        RoomDetailsView roomDetailsView = new RoomDetailsView();
        new RoomDetailsController(roomDetailsView);
        view.dispose();
    }
}
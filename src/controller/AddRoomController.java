package controller;

import model.Room;
import model.RoomDAO;
import model.RoomType;
import model.RoomTypeDAO;
import view.AddRoomView;
import view.RoomDetailsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddRoomController {
    private AddRoomView view;
    private RoomDAO roomDAO;
    private RoomTypeDAO roomTypeDAO;
    private List<RoomType> roomTypeList;

    public AddRoomController(AddRoomView view) {
        this.view = view;
        this.roomDAO = new RoomDAO();
        this.roomTypeDAO = new RoomTypeDAO();

        loadRoomTypes();
        initController();
    }
    
    public AddRoomController(AddRoomView view, Room existingRoom) {
        this.view = view;
        this.roomDAO = new RoomDAO();
        this.roomTypeDAO = new RoomTypeDAO();

        loadRoomTypes();

        view.setRoomNumber(existingRoom.getRoomNumber());

        String selectedTypeName = roomTypeDAO.findById(existingRoom.getRoomTypeId()).getTypeName();
        view.setSelectedRoomType(selectedTypeName);

        view.setBtnCancelListener(e -> view.dispose());

        view.setBtnSubmitListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomNumber = view.getRoomNumber();
                String selectedType = view.getSelectedRoomType();

                if (roomNumber.isEmpty() || selectedType == null) {
                    JOptionPane.showMessageDialog(view, "Please complete all fields.");
                    return;
                }

                int roomTypeId = 0;
                for (RoomType type : roomTypeList) {
                    if (type.getTypeName().equals(selectedType)) {
                        roomTypeId = type.getRoomTypeId();
                        break;
                    }
                }

                if (roomTypeId == 0) {
                    JOptionPane.showMessageDialog(view, "Invalid room type.");
                    return;
                }

                Room updatedRoom = new Room(existingRoom.getRoomId(), roomTypeId, roomNumber, existingRoom.getIsAvailable());
                boolean updated = roomDAO.update(updatedRoom);

                if (updated) {
                    JOptionPane.showMessageDialog(view, "Room updated successfully.");
                    view.dispose();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update room.");
                }
            }
        });
    }

    private void initController() {
        view.setBtnCancelListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose(); // Tutup jendela
            }
        });

        view.setBtnSubmitListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitRoom();
            }
        });
    }

    private void loadRoomTypes() {
        roomTypeList = roomTypeDAO.findAll();
        String[] typeNames = roomTypeList.stream()
                .map(RoomType::getTypeName)
                .toArray(String[]::new);

        view.setRoomTypeOptions(typeNames);
    }

    private void submitRoom() {
        String roomNumber = view.getRoomNumber();
        String selectedTypeName = view.getSelectedRoomType();

        if (roomNumber.isEmpty() || selectedTypeName == null) {
            JOptionPane.showMessageDialog(view, "Please complete all fields.");
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
            JOptionPane.showMessageDialog(view, "Invalid room type.");
            return;
        }

        Room newRoom = new Room(0, roomTypeId, roomNumber, true);
        boolean inserted = roomDAO.insert(newRoom);

        if (inserted) {
            JOptionPane.showMessageDialog(view, "Room added successfully.");
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Failed to add room.");
        }
    }
}
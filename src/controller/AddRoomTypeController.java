package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.RoomType;
import model.RoomTypeDAO;
import view.AddRoomTypeView;
import view.RoomDetailsView;

public class AddRoomTypeController {
    private AddRoomTypeView view;
    private RoomTypeDAO roomTypeDAO;
    private RoomType existingRoomType;
    private boolean isEditMode = false;

    public AddRoomTypeController(AddRoomTypeView view) {
        this(view, null);
    }
    
    public AddRoomTypeController(AddRoomTypeView view, RoomType roomType) {
        this.view = view;
        this.roomTypeDAO = new RoomTypeDAO();
        this.existingRoomType = roomType;
        this.isEditMode = roomType != null;

        if (isEditMode) {
            view.setRoomTypeName(existingRoomType.getTypeName());
            view.setPrice(String.valueOf(existingRoomType.getPrice()));
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
                RoomDetailsView roomDetailsView = new RoomDetailsView();
                new RoomDetailsController(roomDetailsView);
                view.dispose();
            }
        });
    }

    private void initBtnSubmitListener() {
        view.setBtnSubmitListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typeName = view.getRoomTypeName();
                String priceText = view.getPrice();

                if (typeName.isEmpty() || priceText.isEmpty()) {
                    showMessage("Please fill in all fields");
                    return;
                }

                try {
                    int price = Integer.parseInt(priceText);

                    if (isEditMode) {
                        updateRoomtype(typeName, price);
                    } else {
                        insertRoomtype(typeName, price);
                    }

                } catch (NumberFormatException ex) {
                    showMessage("Price must be a valid number.");
                }
            }
        });
    }

    private void updateRoomtype(String typeName, int price) {
        existingRoomType.setTypeName(typeName);
        existingRoomType.setPrice(price);

        boolean success = roomTypeDAO.update(existingRoomType);

        if (success) {
            showMessage("Room type updated successfully.");
            navigateToRoomDetailsView();
        } else {
            showMessage("Failed to update room type.");
        }
    }

    private void insertRoomtype(String typeName, int price) {
        RoomType newType = new RoomType(0, typeName, price);
        boolean success = roomTypeDAO.insert(newType);

        if (success) {
            showMessage("Room type added successfully.");
            navigateToRoomDetailsView();
        } else {
            showMessage("Failed to add room type.");
        }
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
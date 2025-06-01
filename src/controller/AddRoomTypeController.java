package controller;

import model.RoomType;
import model.RoomTypeDAO;
import view.AddRoomTypeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddRoomTypeController {
    private AddRoomTypeView view;
    private RoomTypeDAO roomTypeDAO;
    private RoomType existingRoomType;

    public AddRoomTypeController(AddRoomTypeView view) {
        this.view = view;
        this.roomTypeDAO = new RoomTypeDAO();

        initController();
    }
    
    public AddRoomTypeController(AddRoomTypeView view, RoomType roomType) {
        this.view = view;
        this.roomTypeDAO = new RoomTypeDAO();
        this.existingRoomType = roomType;

        if (existingRoomType != null) {
            view.setRoomTypeName(existingRoomType.getTypeName());
            view.setPrice(String.valueOf(existingRoomType.getPrice()));
        }

        initController();
    }

    private void initController() {
        view.setBtnCancelListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose(); // Tutup form
            }
        });

        view.setBtnSubmitListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitRoomType();
            }
        });
    }

    private void submitRoomType() {
        String typeName = view.getRoomTypeName();
        String priceText = view.getPrice();

        if (typeName.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please fill in all fields.");
            return;
        }

        try {
            int price = Integer.parseInt(priceText);

            if (existingRoomType != null) {
                // Edit
                existingRoomType.setTypeName(typeName);
                existingRoomType.setPrice(price);
                boolean success = roomTypeDAO.update(existingRoomType);

                if (success) {
                    JOptionPane.showMessageDialog(view, "Room type updated successfully.");
                    view.dispose();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update room type.");
                }

            } else {
                // Insert
                RoomType newType = new RoomType(0, typeName, price);
                boolean success = roomTypeDAO.insert(newType);

                if (success) {
                    JOptionPane.showMessageDialog(view, "Room type added successfully.");
                    view.dispose();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to add room type.");
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Price must be a valid number.");
        }
    }

}
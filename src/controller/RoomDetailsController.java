package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import java.util.List;

import model.Room;
import model.RoomDAO;
import model.RoomType;
import model.RoomTypeDAO;
import view.DashboardView;
import view.RoomDetailsView;
import view.AddRoomView;
import view.AddRoomTypeView;

public class RoomDetailsController {
    private RoomDetailsView view;
    private RoomDAO roomDAO;
    private RoomTypeDAO roomTypeDAO;
    private List<Room> roomList;
    private List<RoomType> roomTypeList;
    
    public RoomDetailsController(RoomDetailsView view) {
        this.view = view;
        this.roomDAO = new RoomDAO();
        this.roomTypeDAO = new RoomTypeDAO();
        
        loadData();
        initController();
    }

    private void initController() {
        initBtnBackListener();
        initBtnAddTypeListener();
        initBtnEditTypeListener();
        initBtnDeleteTypeListener();
        initBtnAddRoomListener();
        initBtnEditRoomListener();
        initBtnDeleteRoomListener();
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

    private void initBtnAddTypeListener() {
        view.setBtnAddTypeListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddRoomTypeView addView = new AddRoomTypeView();
                new AddRoomTypeController(addView);
                view.dispose();
            }
        });
    }

    private void initBtnEditTypeListener() {
        view.setBtnEditTypeListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = view.getSelectedRoomTypeRow();

                if (selectedRow == -1) {
                    showMessage("Please select a room type to edit.");
                    return;
                }

                RoomType selectedRoomType = roomTypeList.get(selectedRow);
                AddRoomTypeView editView = new AddRoomTypeView();
                new AddRoomTypeController(editView, selectedRoomType);
                view.dispose();
            }
        });
    }

    private void initBtnDeleteTypeListener() {
        view.setBtnDeleteTypeListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRoomTypeRow = view.getSelectedRoomTypeRow();

                if (selectedRoomTypeRow == -1) {
                    showMessage("Please select room type row first");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(view, "Are you sure to delete this room type?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    int roomTypeId = roomList.get(selectedRoomTypeRow).getRoomTypeId();
                    boolean success = roomTypeDAO.delete(roomTypeId);

                    if (success) {
                        showMessage("Room type deleted successfully");
                        loadData();
                    } else {
                        showMessage("Failed to delete room");
                    }
                }
            }
        });
    }

    private void initBtnAddRoomListener() {
        view.setBtnAddRoomListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddRoomView addView = new AddRoomView();
                new AddRoomController(addView);
                view.dispose();
            }
        });
    }

    private void initBtnEditRoomListener() {
        view.setBtnEditRoomListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRoomRow = view.getSelectedRoomRow();

                if (selectedRoomRow == -1) {
                    showMessage("Please select a room to edit.");
                    return;
                }

                Room selectedRoom = roomList.get(selectedRoomRow);
                AddRoomView editView = new AddRoomView();
                new AddRoomController(editView, selectedRoom);
                view.dispose();
            }
        });
    }

    private void initBtnDeleteRoomListener() {
        view.setBtnDeleteRoomListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRoomRow = view.getSelectedRoomRow();

                if (selectedRoomRow == -1) {
                    showMessage("Please select room row first");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(view, "Are you sure to delete this room?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    int roomId = roomList.get(selectedRoomRow).getRoomId();
                    boolean success = roomDAO.delete(roomId);

                    if (success) {
                        showMessage("Room deleted successfully");
                        loadData();
                    } else {
                        showMessage("Failed to delete room");
                    }
                }
            }
        });
    }
    
    private void loadData() {
        view.clearRoomTable();
        view.clearRoomTypeTable();
        roomList = roomDAO.findAll();
        roomTypeList = roomTypeDAO.findAll();
        
        for (RoomType rt : roomTypeList) {
            Object[] row = {
                rt.getRoomTypeId(),
                rt.getTypeName(),
                rt.getPrice()
            };
            view.addRoomTypeRow(row);
        }
        
        for (Room r : roomList) {
            String status = r.getIsAvailable() ? "Free" : "Occupied";

            Object[] row = {
                r.getRoomId(),
                r.getRoomNumber(),
                r.getRoomTypeName(),
                status
            };
            view.addRoomRow(row);
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(view, message);
    }
}

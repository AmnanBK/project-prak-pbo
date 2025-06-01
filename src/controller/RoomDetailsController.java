package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.List;

import model.Room;
import model.RoomDAO;
import model.RoomType;
import model.RoomTypeDAO;
import view.DashboardView;
import view.RoomDetailsView;

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
        view.setBtnBackListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DashboardView dashboardView = new DashboardView();
                new DashboardController(dashboardView);
                view.dispose();
            }
        });
        
        view.setBtnAddListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        view.setBtnEditListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        view.setBtnDeleteListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRooomRow = view.getSelectedRoomRow();
                if (selectedRooomRow == -1) {
                    JOptionPane.showMessageDialog(view, "Please select room row first");
                    return;
                }
                
                int confirm = JOptionPane.showConfirmDialog(view, "Are you sure to delete this room?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int roomId = roomList.get(selectedRooomRow).getRoomId();
                    boolean deleted = roomDAO.delete(roomId);
                    
                    if (deleted) {
                        JOptionPane.showMessageDialog(view, "Room deleted successfully");
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(view, "Failed to delete room");
                    }
                }
            }
        });
        
    }
    
    private void loadData() {
        view.clearRoomTable();
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
            Object[] row = {
                r.getRoomId(),
                r.getRoomNumber(),
                getRoomTypeName(r.getRoomTypeId()),
                getRoomStatus(r.getIsAvailable())
            };
            view.addRoomRow(row);
        }
    }
    
    private String getRoomTypeName(int roomTypeId) {
        if (roomTypeId == 1) return "Presidential";
        else if (roomTypeId == 2) return "Suite";
        else if (roomTypeId == 3) return "Deluxe";
        else if (roomTypeId == 4) return "Standard";
        return "";
    }
    
    private String getRoomStatus(boolean status) {
        if (status) return "Free";
        else return "Ocuppied";
    }
}

package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.Reservation;
import model.ReservationDAO;
import model.RoomDAO;
import view.DashboardView;
import view.ShowReservationView;
import view.AddReservationView;

public class ShowReservationController {
    private ShowReservationView view;
    private ReservationDAO reservationDAO;
    private List<Reservation> reservationList;

    public ShowReservationController(ShowReservationView view) {
        this.view = view;
        this.reservationDAO = new ReservationDAO();

        loadData();
        initController();
    }

    private void initController() {
        initBtnBackListener();
        initBtnEditListener();
        initBtnCheckoutListener();
        initBtnDeleteListener();
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

    private void initBtnEditListener() {
        view.setBtnEditListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = view.getSelectedRow();

                if (selectedRow == -1) {
                    showMessage("Please select a reservation to edit.");
                    return;
                }

                int reservationId = reservationList.get(selectedRow).getReservationId();
                Reservation selectedReservation = reservationDAO.findById(reservationId);

                AddReservationView editView = new AddReservationView();
                new AddReservationController(editView, selectedReservation);
                view.dispose();
            }
        });
    }

    private void initBtnCheckoutListener() {
        view.setBtnCheckoutListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = view.getSelectedRow();

                if (selectedRow == -1) {
                    showMessage("Please select a reservation to checkout.");
                    return;
                }

                Reservation selectedReservation = reservationList.get(selectedRow);
                String roomNumber = selectedReservation.getRoomNumber();

                if (selectedReservation.isCheckedOut()) {
                    showMessage("Guest already checked out.");
                    return;
                }

                boolean reservationSuccess = reservationDAO.updateReservationStatus(selectedReservation.getReservationId());
                boolean roomSuccess = new RoomDAO().updateRoomAvailability(roomNumber, true);

                if (reservationSuccess && roomSuccess) {
                    showMessage("Guest checked out successfully.");
                    reloadData();
                } else {
                    showMessage("Failed to update status.");
                }
            }
        });
    }

    private void initBtnDeleteListener() {
        view.setBtnDeleteListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = view.getSelectedRow();

                if (selectedRow == -1) {
                    showMessage("Please select a reservation to delete.");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(view, "Are you sure to delete this reservation?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    int reservationId = reservationList.get(selectedRow).getReservationId();
                    boolean success = reservationDAO.delete(reservationId);

                    if (success) {
                        showMessage("Reservation deleted successfully.");
                        reloadData();
                    } else {
                        showMessage("Failed to delete reservation.");
                    }
                }
            }
        });
    }

    private void loadData() {
        reservationList = reservationDAO.findAll();
        for (Reservation res : reservationList) {
            Object[] row = {
                    res.getGuestName(),
                    res.getRoomNumber(),
                    res.getCheckInDate(),
                    res.getCheckOutDate(),
                    res.getTotalPrice(),
                    res.isCheckedOut() ? "Checked Out" : "Ongoing"
            };
            view.addRowReservation(row);
        }
    }

    private void reloadData() {
        view.clearTableReservation();
        loadData();
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(view, message);
    }
}

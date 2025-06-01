package controller;

import model.Reservation;
import model.ReservationDAO;
import view.DashboardView;
import view.ShowReservationView;
import view.AddReservationView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShowReservationController {

    private ShowReservationView view;
    private ReservationDAO reservationDAO;
    private List<Reservation> reservationList;

    public ShowReservationController(ShowReservationView view) {
        this.view = view;
        this.reservationDAO = new ReservationDAO();

        loadData();

        view.setBtnBackListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                DashboardView dashboardView = new DashboardView();
                new DashboardController(dashboardView);
            }
        });

        view.setBtnDeleteListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = view.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(view, "Please select a row first.");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(view, "Are you sure to delete this reservation?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int reservationId = reservationList.get(selectedRow).getReservationId();
                    boolean deleted = reservationDAO.delete(reservationId);

                    if (deleted) {
                        JOptionPane.showMessageDialog(view, "Reservation deleted successfully.");
                        reloadData();
                    } else {
                        JOptionPane.showMessageDialog(view, "Failed to delete reservation.");
                    }
                }
            }
        });

        view.setBtnCheckoutListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = view.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(view, "Please select a reservation.");
                    return;
                }

                Reservation reservation = reservationList.get(selectedRow);
                if (reservation.isCheckedOut()) {
                    JOptionPane.showMessageDialog(view, "Guest already checked out.");
                    return;
                }

                boolean updated = reservationDAO.checkout(reservation.getReservationId());
                if (updated) {
                    JOptionPane.showMessageDialog(view, "Guest checked out successfully.");
                    reloadData();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update status.");
                }
            }
        });

        view.setBtnEditListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = view.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(view, "Please select a reservation to edit.");
                    return;
                }
                
                int reservationId = reservationList.get(selectedRow).getReservationId();
                Reservation reservation = reservationDAO.searchReservation(reservationId);
                System.out.println(reservation.getGuestId());
                AddReservationView editView = new AddReservationView();
                new AddReservationController(editView, reservation);
                view.dispose();

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
}

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.AddReservationView;
import view.DashboardView;
import view.RoomDetailsView;
import view.ShowReservationView;

public class DashboardController {
    private DashboardView view;
    
    public DashboardController(DashboardView view) {
        this.view = view;
        initController();
    }

    private void initController() {
        initAddReservationListener();
        initShowReservationListener();
        initRoomDetailsListener();
    }

    private void initAddReservationListener() {
        view.setBtnAddReservationListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddReservationView addReservationView = new AddReservationView();
                new AddReservationController(addReservationView);
                view.dispose();
            }
        });
    }

    private void initShowReservationListener() {
        view.setBtnShowReservationListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowReservationView showReservationView = new ShowReservationView();
                new ShowReservationController(showReservationView);
                view.dispose();
            }
        });
    }

    private void initRoomDetailsListener() {
        view.setBtnRoomDetailsListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RoomDetailsView roomDetailsView = new RoomDetailsView();
                new RoomDetailsController(roomDetailsView);
                view.dispose();
            }
        });
    }
}

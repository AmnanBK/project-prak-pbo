package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.DashboardView;
import view.AddReservationView;
import view.ShowReservationView;
import view.RoomDetailsView;

public class DashboardController {
    private DashboardView view;
    
    public DashboardController(DashboardView view) {
        this.view = view;
        
        initController();
    }
    
    private void initController() {
        view.setBtnAddReservationListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddReservationView addView = new AddReservationView();
                new AddReservationController(addView);
                view.dispose();
            }
        });
        
        view.setBtnShowReservationListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowReservationView showView = new ShowReservationView();
                new ShowReservationController(showView);
                view.dispose();
            }
        });
        
        view.setBtnRoomDetailsListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RoomDetailsView RoomView = new RoomDetailsView();
                view.dispose();
            }
        });
    }
}

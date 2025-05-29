package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DashboardView extends BaseView {
    private JButton btnAddReservation;
    private JButton btnShowReservation;
    private JButton btnRoomDetails;
    
    public DashboardView() {
        super("Hotel Reservation", 400, 250);
        setLayout(new BorderLayout());
        
        // Judul atas
        JLabel lblTitle = new JLabel("Hotel Reservation ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(lblTitle, BorderLayout.NORTH);

        // Panel tombol di tengah
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(3, 1, 10, 10));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        btnAddReservation = new JButton("Add Reservation");
        btnShowReservation = new JButton("Show Reservations");
        btnRoomDetails = new JButton("Room Details");

        panelButtons.add(btnAddReservation);
        panelButtons.add(btnShowReservation);
        panelButtons.add(btnRoomDetails);

        add(panelButtons, BorderLayout.CENTER);

        setVisible(true);
    }
    
    // Setter for button listener
    public void setBtnAddReservationListener(ActionListener listener) {
        btnAddReservation.addActionListener(listener);
    }
    
    public void setBtnShowReservationListener (ActionListener listener) {
        btnShowReservation.addActionListener(listener);
    }
    
    public void setBtnRoomDetailsListener (ActionListener listener) {
        btnShowReservation.addActionListener(listener);
    }
}

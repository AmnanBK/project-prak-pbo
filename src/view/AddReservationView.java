package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.*;

import org.jdatepicker.impl.*;

import util.DateLabelFormatter;

public class AddReservationView extends BaseView {
    private JTextField tfId, tfFirstName, tfLastName, tfEmail, tfPhone;
    private JComboBox<String> cbRoomType, cbRoomNumber;
    private JDatePickerImpl dpCheckIn, dpCheckOut;
    private JLabel lblDuration;
    private JButton btnSubmit, btnBack, btnSearch;

    public AddReservationView() {
        super("Add Reservation", 500, 550);
        setLayout(null);

        // Title
        JLabel lblTitle = new JLabel("Add Reservation");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitle.setBounds(150, 10, 250, 30);
        add(lblTitle);

        // Guest Id
        JLabel lblId = new JLabel("Guest Id:");
        lblId.setBounds(30, 60, 100, 25);
        add(lblId);

        tfId = new JTextField();
        tfId.setBounds(130, 60, 200, 25);
        add(tfId);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(340, 60, 100, 25);
        add(btnSearch);

        // Guest first name
        JLabel lblFirst = new JLabel("First Name:");
        lblFirst.setBounds(30, 100, 100, 25);
        add(lblFirst);

        tfFirstName = new JTextField();
        tfFirstName.setBounds(130, 100, 250, 25);
        add(tfFirstName);

        // Guest last name
        JLabel lblLast = new JLabel("Last Name:");
        lblLast.setBounds(30, 140, 100, 25);
        add(lblLast);

        tfLastName = new JTextField();
        tfLastName.setBounds(130, 140, 250, 25);
        add(tfLastName);

        // Guest email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 180, 100, 25);
        add(lblEmail);

        tfEmail = new JTextField();
        tfEmail.setBounds(130, 180, 250, 25);
        add(tfEmail);

        // Guest phone number
        JLabel lblPhone = new JLabel("Phone Number:");
        lblPhone.setBounds(30, 220, 100, 25);
        add(lblPhone);

        tfPhone = new JTextField();
        tfPhone.setBounds(130, 220, 250, 25);
        add(tfPhone);

        // Choose room type
        JLabel lblRoomType = new JLabel("Room Type:");
        lblRoomType.setBounds(30, 260, 100, 25);
        add(lblRoomType);

        cbRoomType = new JComboBox<>(new String[]{"Standard", "Deluxe", "Suite", "Presidential"});
        cbRoomType.setBounds(130, 260, 250, 25);
        add(cbRoomType);

        // Choose room number
        JLabel lblRoomNumber = new JLabel("Room Number:");
        lblRoomNumber.setBounds(30, 300, 100, 25);
        add(lblRoomNumber);

        cbRoomNumber = new JComboBox<>();
        cbRoomNumber.setBounds(130, 300, 250, 25);
        add(cbRoomNumber);

        // Date Picker Setup
        UtilDateModel modelIn = new UtilDateModel();
        UtilDateModel modelOut = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        dpCheckIn = new JDatePickerImpl(new JDatePanelImpl(modelIn, p), new DateLabelFormatter());
        dpCheckOut = new JDatePickerImpl(new JDatePanelImpl(modelOut, p), new DateLabelFormatter());

        // Choose date check in
        JLabel lblCheckIn = new JLabel("Check-in Date:");
        lblCheckIn.setBounds(30, 340, 100, 25);
        add(lblCheckIn);
        dpCheckIn.setBounds(130, 340, 250, 30);
        add(dpCheckIn);

        // Choose date check out
        JLabel lblCheckOut = new JLabel("Check-out Date:");
        lblCheckOut.setBounds(30, 380, 100, 25);
        add(lblCheckOut);
        dpCheckOut.setBounds(130, 380, 250, 30);
        add(dpCheckOut);

        // Show duration
        JLabel lblDur = new JLabel("Duration (nights):");
        lblDur.setBounds(30, 420, 120, 25);
        add(lblDur);
        lblDuration = new JLabel("-");
        lblDuration.setBounds(160, 420, 100, 25);
        add(lblDuration);

        // Button back
        btnBack = new JButton("Back");
        btnBack.setBounds(130, 470, 100, 30);
        add(btnBack);

        // Button Submit
        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(250, 470, 100, 30);
        add(btnSubmit);

        // Calculate duration on check-out change
        dpCheckOut.addActionListener(e -> calculateDuration());

        cbRoomType.setSelectedItem("Standard");
        setVisible(true);
    }


    // Getters
    public String getGuestId() { return tfId.getText().trim(); }
    public String getFirstName() { return tfFirstName.getText().trim(); }
    public String getLastName() { return tfLastName.getText().trim(); }
    public String getEmail() { return tfEmail.getText().trim(); }
    public String getPhone() { return tfPhone.getText().trim(); }
    public String getRoomType() { return (String) cbRoomType.getSelectedItem(); }
    public String getRoomNumber() { return (String) cbRoomNumber.getSelectedItem(); }
    public Date getCheckInDate() {
        return (Date) dpCheckIn.getModel().getValue();
    }
    public Date getCheckOutDate() {
        return (Date) dpCheckOut.getModel().getValue();
    }

    // Setters
    public void setGuestId(String val) { tfId.setText(val); }
    public void setFirstName(String val) { tfFirstName.setText(val); }
    public void setLastName(String val) { tfLastName.setText(val); }
    public void setEmail(String val) { tfEmail.setText(val); }
    public void setPhone(String val) { tfPhone.setText(val); }
    public void setSelectedRoomType(String val) {
        cbRoomType.setSelectedItem(val);
    }
    public void setSelectedRoomNumber(String val) {
        cbRoomNumber.setSelectedItem(val);
    }

    public void setCheckInDate(Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            UtilDateModel model = (UtilDateModel) dpCheckIn.getModel();
            model.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            model.setSelected(true);
        }
    }

    public void setCheckOutDate(Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            UtilDateModel model = (UtilDateModel) dpCheckOut.getModel();
            model.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            model.setSelected(true);
        }
    }

    // Setter for room type option
    public void setRoomTypeOptions(String[] roomTypes) {
        cbRoomType.removeAllItems();
        for (String type : roomTypes) {
            cbRoomType.addItem(type);
        }
    }

    // Setter for room number option
    public void setRoomNumberOptions(String[] roomNumbers) {
        cbRoomNumber.removeAllItems();
        for (String number : roomNumbers) {
            cbRoomNumber.addItem(number);
        }
    }

    // Setters for action listener
    public void setBtnSubmitListener(ActionListener listener) {
        btnSubmit.addActionListener(listener);
    }
    public void setBtnBackListener(ActionListener listener) {
        btnBack.addActionListener(listener);
    }
    public void setBtnSearchListener(ActionListener listener) {
        btnSearch.addActionListener(listener);
    }
    public void setRoomTypeChangeListener(ActionListener listener) {
        cbRoomType.addActionListener(listener);
    }

    // Helper for calculate duration
    private void calculateDuration() {
        try {
            java.util.Date in = (java.util.Date) dpCheckIn.getModel().getValue();
            java.util.Date out = (java.util.Date) dpCheckOut.getModel().getValue();

            if (in != null && out != null) {
                long diff = out.getTime() - in.getTime();
                long nights = diff / (1000 * 60 * 60 * 24);
                lblDuration.setText(nights + " night(s)");
            } else {
                lblDuration.setText("-");
            }
        } catch (Exception e) {
            lblDuration.setText("-");
        }
    }
}

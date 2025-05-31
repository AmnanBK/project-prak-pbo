package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.Calendar;

import javax.swing.*;

import org.jdatepicker.impl.*;

import util.DateLabelFormatter;

public class AddReservationView extends BaseView {
    private JTextField tfNIK, tfFirstName, tfLastName, tfEmail, tfPhone;
    private JComboBox<String> cbRoomType, cbRoomNumber;
    private JDatePickerImpl dpCheckIn, dpCheckOut;
    private JLabel lblDuration;
    private JButton btnSubmit, btnBack, btnSearch;
    
    public AddReservationView() {
        super("Create Reservation", 500, 550);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        int y = 0;

        // Input NIK and Search Button
        gbc.gridy = y;
        formPanel.add(new JLabel("Guest NIK:"), gbc);
        tfNIK = new JTextField();
        gbc.gridx = 1; formPanel.add(tfNIK, gbc);
        btnSearch = new JButton("Search");
        gbc.gridx = 2; formPanel.add(btnSearch, gbc);
        y++; gbc.gridx = 0;

        // Guest Data
        gbc.gridy = y++;
        formPanel.add(new JLabel("First Name:"), gbc);
        tfFirstName = new JTextField();
        gbc.gridx = 1; gbc.gridwidth = 2; formPanel.add(tfFirstName, gbc);
        gbc.gridx = 0; gbc.gridwidth = 1;

        gbc.gridy = y++;
        formPanel.add(new JLabel("Last Name:"), gbc);
        tfLastName = new JTextField();
        gbc.gridx = 1; gbc.gridwidth = 2; formPanel.add(tfLastName, gbc);
        gbc.gridx = 0; gbc.gridwidth = 1;

        gbc.gridy = y++;
        formPanel.add(new JLabel("Email:"), gbc);
        tfEmail = new JTextField();
        gbc.gridx = 1; gbc.gridwidth = 2; formPanel.add(tfEmail, gbc);
        gbc.gridx = 0; gbc.gridwidth = 1;

        gbc.gridy = y++;
        formPanel.add(new JLabel("Phone Number:"), gbc);
        tfPhone = new JTextField();
        gbc.gridx = 1; gbc.gridwidth = 2; formPanel.add(tfPhone, gbc);
        gbc.gridx = 0; gbc.gridwidth = 1;

        // Room
        cbRoomType = new JComboBox<>(new String[]{"Standard", "Deluxe", "Suite", "Presidential"});
        cbRoomNumber = new JComboBox<>();

        gbc.gridy = y++;
        formPanel.add(new JLabel("Room Type:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; formPanel.add(cbRoomType, gbc);
        gbc.gridx = 0; gbc.gridwidth = 1;

        gbc.gridy = y++;
        formPanel.add(new JLabel("Room Number:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; formPanel.add(cbRoomNumber, gbc);
        gbc.gridx = 0; gbc.gridwidth = 1;

        // Date Picker
        UtilDateModel modelIn = new UtilDateModel();
        UtilDateModel modelOut = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        dpCheckIn = new JDatePickerImpl(new JDatePanelImpl(modelIn, p), new DateLabelFormatter());
        dpCheckOut = new JDatePickerImpl(new JDatePanelImpl(modelOut, p), new DateLabelFormatter());

        gbc.gridy = y++;
        formPanel.add(new JLabel("Check-in Date:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; formPanel.add(dpCheckIn, gbc);
        gbc.gridx = 0; gbc.gridwidth = 1;

        gbc.gridy = y++;
        formPanel.add(new JLabel("Check-out Date:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; formPanel.add(dpCheckOut, gbc);
        gbc.gridx = 0; gbc.gridwidth = 1;

        // Duration
        lblDuration = new JLabel("-");
        gbc.gridy = y++;
        formPanel.add(new JLabel("Duration (nights):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; formPanel.add(lblDuration, gbc);
        gbc.gridx = 0; gbc.gridwidth = 1;

        // Buttons
        btnBack = new JButton("Back");
        btnSubmit = new JButton("Submit");
        gbc.gridy = y++;
        formPanel.add(btnBack, gbc);
        gbc.gridx = 1; formPanel.add(btnSubmit, gbc);

        // Listener auto update duration
        dpCheckOut.addActionListener(e -> calculateDuration());

        add(formPanel, BorderLayout.CENTER);
        cbRoomType.setSelectedItem("Standard");
        setVisible(true);
    }
    
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

    // Getters
    public String getGuestNIK() { return tfNIK.getText().trim(); }
    public String getFirstName() { return tfFirstName.getText().trim(); }
    public String getLastName() { return tfLastName.getText().trim(); }
    public String getEmail() { return tfEmail.getText().trim(); }
    public String getPhone() { return tfPhone.getText().trim(); }

    public String getRoomType() { return (String) cbRoomType.getSelectedItem(); }
    public String getRoomNumber() { return (String) cbRoomNumber.getSelectedItem(); }

    public java.util.Date getCheckInDate() {
        return (java.util.Date) dpCheckIn.getModel().getValue();
    }

    public java.util.Date getCheckOutDate() {
        return (java.util.Date) dpCheckOut.getModel().getValue();
    }

    // Setters
    public void setFirstName(String val) { tfFirstName.setText(val); }
    public void setLastName(String val) { tfLastName.setText(val); }
    public void setEmail(String val) { tfEmail.setText(val); }
    public void setPhone(String val) { tfPhone.setText(val); }
    public void setGuestId(String val) { tfNIK.setText(val); }
    
    public void setRoomNumberOptions(String[] roomNumbers) {
        cbRoomNumber.removeAllItems();
        for (String number : roomNumbers) {
            cbRoomNumber.addItem(number);
        }
    }
    
    // Setters for button listener
    public void setBtnSubmitListener(ActionListener listener) {
        btnSubmit.addActionListener(listener);
    }
    public void setBtnBackListener(ActionListener listener) {
        btnBack.addActionListener(listener);
    }
    public void setBtnSearchListener(ActionListener listener) {
        btnSearch.addActionListener(listener);
    }
    // Setter for combobox roomtype change
    public void setRoomTypeChangeListener(ActionListener listener) {
        cbRoomType.addActionListener(listener);
    }
    
    public void setCheckInDate(java.util.Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            UtilDateModel model = (UtilDateModel) dpCheckIn.getModel();
            model.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            model.setSelected(true);
        }
    }

    public void setCheckOutDate(java.util.Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            UtilDateModel model = (UtilDateModel) dpCheckOut.getModel();
            model.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            model.setSelected(true);
        }
    }
    
    public void setSelectedRoomType(String type) {
        cbRoomType.setSelectedItem(type);
    }

    public void setSelectedRoomNumber(String number) {
        cbRoomNumber.setSelectedItem(number);
    }
}

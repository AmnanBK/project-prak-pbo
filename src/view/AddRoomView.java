package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class AddRoomView extends BaseView {
    private JTextField tfRoomNumber;
    private JComboBox<String> cbRoomType;
    private JButton btnSubmit;
    private JButton btnCancel;

    public AddRoomView() {
        super("Add Room", 400, 250);
        setLayout(null);

        // Room number
        JLabel lblRoomNumber = new JLabel("Room Number:");
        lblRoomNumber.setBounds(40, 30, 120, 25);
        add(lblRoomNumber);

        tfRoomNumber = new JTextField();
        tfRoomNumber.setBounds(170, 30, 170, 25);
        add(tfRoomNumber);

        // Room type
        JLabel lblRoomType = new JLabel("Room Type:");
        lblRoomType.setBounds(40, 70, 120, 25);
        add(lblRoomType);

        cbRoomType = new JComboBox<>();
        cbRoomType.setBounds(170, 70, 170, 25);
        add(cbRoomType);

        // Buttons
        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(80, 130, 100, 30);
        add(btnCancel);

        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(200, 130, 100, 30);
        add(btnSubmit);

        setVisible(true);
    }

    // Getters
    public String getRoomNumber() {
        return tfRoomNumber.getText().trim();
    }
    public String getSelectedRoomType() {
        return (String) cbRoomType.getSelectedItem();
    }

    // Setters
    public void setRoomNumber(String number) {
        tfRoomNumber.setText(number);
    }
    public void setSelectedRoomType(String type) {
        cbRoomType.setSelectedItem(type);
    }

    // Setters for room type options
    public void setRoomTypeOptions(String[] types) {
        cbRoomType.removeAllItems();
        for (String type : types) {
            cbRoomType.addItem(type);
        }
    }

    // Setters for action listener
    public void setBtnCancelListener(ActionListener listener) {
        btnCancel.addActionListener(listener);
    }
    public void setBtnSubmitListener(ActionListener listener) {
        btnSubmit.addActionListener(listener);
    }
}
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddRoomTypeView extends BaseView {

    private JTextField tfTypeName;
    private JTextField tfPrice;
    private JButton btnSubmit;
    private JButton btnCancel;

    public AddRoomTypeView() {
        super("Add Room Type", 400, 250);
        setLayout(null);

        JLabel lblTypeName = new JLabel("Room Type Name:");
        lblTypeName.setBounds(40, 30, 120, 25);
        add(lblTypeName);

        tfTypeName = new JTextField();
        tfTypeName.setBounds(170, 30, 170, 25);
        add(tfTypeName);

        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setBounds(40, 70, 120, 25);
        add(lblPrice);

        tfPrice = new JTextField();
        tfPrice.setBounds(170, 70, 170, 25);
        add(tfPrice);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(80, 130, 100, 30);
        add(btnCancel);

        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(200, 130, 100, 30);
        add(btnSubmit);

        setVisible(true);
    }

    public String getTypeName() {
        return tfTypeName.getText().trim();
    }

    public String getPrice() {
        return tfPrice.getText().trim();
    }

    public void setBtnCancelkListener(ActionListener listener) {
        btnCancel.addActionListener(listener);
    }

    public void setBtnSubmitListener(ActionListener listener) {
        btnSubmit.addActionListener(listener);
    }

    public void clearForm() {
        tfTypeName.setText("");
        tfPrice.setText("");
    }
}
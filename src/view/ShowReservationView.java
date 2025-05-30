package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class ShowReservationView extends BaseView {

    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;

    private JButton btnBack;
    private JButton btnEdit;
    private JButton btnCheckout;
    private JButton btnDelete;

    public ShowReservationView() {
        super("Reservation List", 750, 500);
        setLayout(null);

        // Title
        JLabel lblTitle = new JLabel("Reservation List");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(250, 10, 250, 30);
        add(lblTitle);

        // Table setup
        String[] columnNames = {
                "Guest Name", "Room Number", "Check-in Date",
                "Check-out Date", "Total Price", "Status"
        };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 60, 680, 300);
        add(scrollPane);

        // Buttons
        btnBack = new JButton("Back");
        btnEdit = new JButton("Edit");
        btnCheckout = new JButton("Checkout");
        btnDelete = new JButton("Delete");

        btnBack.setBounds(30, 380, 100, 30);
        btnEdit.setBounds(200, 380, 100, 30);
        btnCheckout.setBounds(370, 380, 100, 30);
        btnDelete.setBounds(540, 380, 100, 30);

        add(btnBack);
        add(btnEdit);
        add(btnCheckout);
        add(btnDelete);

        setVisible(true);
    }

    // Getters
    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    public void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    // Button listener setters
    public void setBtnBackListener(ActionListener listener) {
        btnBack.addActionListener(listener);
    }

    public void setBtnEditListener(ActionListener listener) {
        btnEdit.addActionListener(listener);
    }

    public void setBtnCheckoutListener(ActionListener listener) {
        btnCheckout.addActionListener(listener);
    }

    public void setBtnDeleteListener(ActionListener listener) {
        btnDelete.addActionListener(listener);
    }

    // Table getter
    public JTable getTable() {
        return table;
    }
}
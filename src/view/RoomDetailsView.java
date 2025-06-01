package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class RoomDetailsView extends BaseView {

    private JTable tableRoomTypes;
    private JTable tableRooms;
    private DefaultTableModel modelRoomTypes;
    private DefaultTableModel modelRooms;

    // Tombol Room Type
    private JButton btnAddType;
    private JButton btnEditType;
    private JButton btnDeleteType;

    // Tombol Room
    private JButton btnAddRoom;
    private JButton btnEditRoom;
    private JButton btnDeleteRoom;

    private JButton btnBack;

    private static class NonEditableTableModel extends DefaultTableModel {
        public NonEditableTableModel(String[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    public RoomDetailsView() {
        super("Room Details", 800, 650);
        setLayout(null);

        // Title
        JLabel lblTitle = new JLabel("Room Types and Room List", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitle.setBounds(200, 20, 400, 30); // Centered horizontally
        add(lblTitle);

        // Table Room Type
        String[] columnRoomTypes = {"Room Type ID", "Type Name", "Price"};
        modelRoomTypes = new NonEditableTableModel(columnRoomTypes, 0);
        tableRoomTypes = new JTable(modelRoomTypes);
        JScrollPane scrollType = new JScrollPane(tableRoomTypes);
        scrollType.setBorder(BorderFactory.createTitledBorder("Room Types"));
        scrollType.setBounds(50, 70, 700, 150);
        add(scrollType);

        // Buttons Room Type
        btnAddType = new JButton("Add Type");
        btnEditType = new JButton("Edit Type");
        btnDeleteType = new JButton("Delete Type");

        btnAddType.setBounds(200, 230, 120, 30);
        btnEditType.setBounds(340, 230, 120, 30);
        btnDeleteType.setBounds(480, 230, 120, 30);

        add(btnAddType);
        add(btnEditType);
        add(btnDeleteType);

        // Table Room
        String[] columnRooms = {"Room ID", "Room Number", "Room Type", "Status"};
        modelRooms = new NonEditableTableModel(columnRooms, 0);
        tableRooms = new JTable(modelRooms);
        JScrollPane scrollRoom = new JScrollPane(tableRooms);
        scrollRoom.setBorder(BorderFactory.createTitledBorder("Room List"));
        scrollRoom.setBounds(50, 280, 700, 200);
        add(scrollRoom);

        // Buttons Room
        btnBack = new JButton("Back");
        btnAddRoom = new JButton("Add Room");
        btnEditRoom = new JButton("Edit Room");
        btnDeleteRoom = new JButton("Delete Room");

        btnBack.setBounds(80, 500, 120, 30);
        btnAddRoom.setBounds(250, 500, 120, 30);
        btnEditRoom.setBounds(420, 500, 120, 30);
        btnDeleteRoom.setBounds(590, 500, 120, 30);

        add(btnBack);
        add(btnAddRoom);
        add(btnEditRoom);
        add(btnDeleteRoom);

        setVisible(true);
    }

    // Adder for table data
    public void addRoomTypeRow(Object[] rowData) {
        modelRoomTypes.addRow(rowData);
    }
    public void addRoomRow(Object[] rowData) {
        modelRooms.addRow(rowData);
    }

    // Reset table data
    public void clearRoomTypeTable() {
        modelRoomTypes.setRowCount(0);
    }
    public void clearRoomTable() {
        modelRooms.setRowCount(0);
    }

    // Get selected row
    public int getSelectedRoomTypeRow() {
        return tableRoomTypes.getSelectedRow();
    }
    public int getSelectedRoomRow() {
        return tableRooms.getSelectedRow();
    }

    // Setter for action listener
    public void setBtnBackListener(ActionListener listener) {
        btnBack.addActionListener(listener);
    }
    public void setBtnAddRoomListener(ActionListener listener) {
        btnAddRoom.addActionListener(listener);
    }
    public void setBtnEditRoomListener(ActionListener listener) {
        btnEditRoom.addActionListener(listener);
    }
    public void setBtnDeleteRoomListener(ActionListener listener) {
        btnDeleteRoom.addActionListener(listener);
    }
    public void setBtnAddTypeListener(ActionListener listener) {
        btnAddType.addActionListener(listener);
    }
    public void setBtnEditTypeListener(ActionListener listener) {
        btnEditType.addActionListener(listener);
    }
    public void setBtnDeleteTypeListener(ActionListener listener) {
        btnDeleteType.addActionListener(listener);
    }
}
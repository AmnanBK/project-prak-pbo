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
    private JButton btnBack;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;

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
        setLayout(new BorderLayout());

        // =========================== Title
        JLabel lblTitle = new JLabel("Room Types and Room List", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(lblTitle, BorderLayout.NORTH);

        // =========================== Center Section
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table Room Type
        String[] columnRoomTypes = {"Room Type ID", "Type Name", "Price"};
        modelRoomTypes = new NonEditableTableModel(columnRoomTypes, 0);
        tableRoomTypes = new JTable(modelRoomTypes);
        JScrollPane scrollType = new JScrollPane(tableRoomTypes);
        scrollType.setBorder(BorderFactory.createTitledBorder("Room Types"));
        centerPanel.add(scrollType);

        // Panel Tombol Room Type
        JPanel panelRoomTypeButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        btnAddType = new JButton("Add Type");
        btnEditType = new JButton("Edit Type");
        btnDeleteType = new JButton("Delete Type");
        panelRoomTypeButtons.add(btnAddType);
        panelRoomTypeButtons.add(btnEditType);
        panelRoomTypeButtons.add(btnDeleteType);
        centerPanel.add(panelRoomTypeButtons);

        // Table Rooms
        String[] columnRooms = {"Room ID", "Room Number", "Room Type", "Status"};
        modelRooms = new NonEditableTableModel(columnRooms, 0);
        tableRooms = new JTable(modelRooms);
        JScrollPane scrollRoom = new JScrollPane(tableRooms);
        scrollRoom.setBorder(BorderFactory.createTitledBorder("Room List"));
        centerPanel.add(scrollRoom);

        add(centerPanel, BorderLayout.CENTER);

        // =========================== Bottom Buttons (for Room)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnBack = new JButton("Back");
        btnAdd = new JButton("Add Room");
        btnEdit = new JButton("Edit Room");
        btnDelete = new JButton("Delete Room");

        buttonPanel.add(btnBack);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ======================== Table Manipulation
    public void addRoomTypeRow(Object[] rowData) {
        modelRoomTypes.addRow(rowData);
    }

    public void addRoomRow(Object[] rowData) {
        modelRooms.addRow(rowData);
    }

    public void clearRoomTypeTable() {
        modelRoomTypes.setRowCount(0);
    }

    public void clearRoomTable() {
        modelRooms.setRowCount(0);
    }

    public int getSelectedRoomTypeRow() {
        return tableRoomTypes.getSelectedRow();
    }

    public int getSelectedRoomRow() {
        return tableRooms.getSelectedRow();
    }

    public JTable getRoomTable() {
        return tableRooms;
    }

    public JTable getRoomTypeTable() {
        return tableRoomTypes;
    }

    // ======================== Button Listeners
    public void setBtnBackListener(ActionListener listener) {
        btnBack.addActionListener(listener);
    }

    public void setBtnAddListener(ActionListener listener) {
        btnAdd.addActionListener(listener);
    }

    public void setBtnEditListener(ActionListener listener) {
        btnEdit.addActionListener(listener);
    }

    public void setBtnDeleteListener(ActionListener listener) {
        btnDelete.addActionListener(listener);
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
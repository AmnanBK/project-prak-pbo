package app;

import controller.DashboardController;
import view.DashboardView;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater( () -> {
            DashboardView view = new DashboardView();
            new DashboardController(view);
        });
    }
}

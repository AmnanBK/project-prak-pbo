package project_prak_pbo;

import controller.DashboardController;
import view.DashboardView;

public class Project_prak_pbo {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater( () -> {
            DashboardView view = new DashboardView();
            new DashboardController(view);
        });
    }
}

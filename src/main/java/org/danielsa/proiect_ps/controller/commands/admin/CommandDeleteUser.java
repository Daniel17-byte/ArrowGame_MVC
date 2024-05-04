package org.danielsa.proiect_ps.controller.commands.admin;

import eu.hansolo.tilesfx.Command;
import org.danielsa.proiect_ps.controller.AdminController;

public class CommandDeleteUser implements Command {
    private final AdminController controller;

    public CommandDeleteUser(AdminController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        boolean success = controller.getModel().deleteUser(controller.getSelectedUserProperty().getValue().getUserName());

        if (!success) {
            System.out.println("User not deleted!");
            return;
        }

        controller.getUserTableViewProperty().getValue().getItems().remove(controller.getUserTableViewProperty().getValue().getSelectionModel().getSelectedItem());
    }
}

package org.danielsa.proiect_ps.controller.commands.admin;

import eu.hansolo.tilesfx.Command;
import org.danielsa.proiect_ps.model.UserModel;
import org.danielsa.proiect_ps.controller.AdminController;

public class CommandUpdateUser implements Command {
    private final AdminController controller;

    public CommandUpdateUser(AdminController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        UserModel updatedUser = controller.getModel().updateUser(controller.getSelectedUserProperty().getValue().getUserName(), controller.getUsernameProperty().getValue(), controller.getPasswordProperty().getValue(), controller.getUserTypeProperty().getValue());

        if (updatedUser == null) {
            System.out.println("User not updated!");
            return;
        }

        controller.getUserTableViewProperty().getValue().getItems().clear();
        controller.getUserTableViewProperty().getValue().getItems().addAll(controller.getModel().getUsers());
    }
}

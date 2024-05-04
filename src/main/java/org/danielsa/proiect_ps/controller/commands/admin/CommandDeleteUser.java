package org.danielsa.proiect_ps.controller.commands.admin;

import eu.hansolo.tilesfx.Command;
import org.danielsa.proiect_ps.controller.AdminController;

public class CommandDeleteUser implements Command {
    private final AdminController viewModel;

    public CommandDeleteUser(AdminController viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void execute() {
        boolean success = viewModel.getModel().deleteUser(viewModel.getSelectedUserProperty().getValue().getUserName());

        if (!success) {
            System.out.println("User not deleted!");
            return;
        }

        viewModel.getUserTableViewProperty().getValue().getItems().remove(viewModel.getUserTableViewProperty().getValue().getSelectionModel().getSelectedItem());
    }
}

package org.danielsa.proiect_ps.controller.commands.admin;

import eu.hansolo.tilesfx.Command;
import javafx.scene.control.TableView;
import org.danielsa.proiect_ps.model.UserModel;
import org.danielsa.proiect_ps.controller.AdminController;

public class CommandAddUser implements Command {
    private final AdminController viewModel;
    public CommandAddUser(AdminController viewModel){
        this.viewModel = viewModel;
    }

    @Override
    public void execute() {
        boolean success = viewModel.getModel().register(
                viewModel.getUsernameProperty().getValue(),
                viewModel.getPasswordProperty().getValue(),
                viewModel.getUserTypeProperty().getValue()
        );

        if (success) {
            TableView<UserModel> tableView = viewModel.getUserTableViewProperty().getValue();
            tableView.getItems().addAll(viewModel.getModel().getUserByUsername(viewModel.getUsernameProperty().getValue()));
        } else {
            System.out.println("User not added!");
        }
    }
}

package org.danielsa.proiect_ps.controller.commands.admin;

import eu.hansolo.tilesfx.Command;
import javafx.scene.control.TableView;
import org.danielsa.proiect_ps.model.UserModel;
import org.danielsa.proiect_ps.controller.AdminController;

public class CommandAddUser implements Command {
    private final AdminController controller;
    public CommandAddUser(AdminController controller){
        this.controller = controller;
    }

    @Override
    public void execute() {
        boolean success = controller.getModel().register(
                controller.getUsernameProperty().getValue(),
                controller.getPasswordProperty().getValue(),
                controller.getUserTypeProperty().getValue()
        );

        if (success) {
            TableView<UserModel> tableView = controller.getUserTableViewProperty().getValue();
            tableView.getItems().addAll(controller.getModel().getUserByUsername(controller.getUsernameProperty().getValue()));
        } else {
            System.out.println("User not added!");
        }
    }
}

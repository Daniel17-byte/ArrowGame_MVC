package org.danielsa.proiect_ps.controller.commands.game;

import eu.hansolo.tilesfx.Command;
import org.danielsa.proiect_ps.model.UserModel;
import org.danielsa.proiect_ps.controller.GameController;

import java.util.ArrayList;

public class CommandLoadUsers implements Command {
    private final GameController controller;

    public CommandLoadUsers(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        ArrayList<UserModel> users = controller.getModel().getUsers();
        StringBuilder stringBuilder = new StringBuilder();

        users.forEach( u -> stringBuilder.append(u).append("\n"));

        controller.getUsersPaneProperty().setValue(stringBuilder.toString());
    }
}

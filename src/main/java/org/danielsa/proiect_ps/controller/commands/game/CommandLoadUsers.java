package org.danielsa.proiect_ps.controller.commands.game;

import eu.hansolo.tilesfx.Command;
import org.danielsa.proiect_ps.model.UserModel;
import org.danielsa.proiect_ps.controller.GameController;

import java.util.ArrayList;

public class CommandLoadUsers implements Command {
    private final GameController viewModel;

    public CommandLoadUsers(GameController viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void execute() {
        ArrayList<UserModel> users = viewModel.getModel().getUsers();
        StringBuilder stringBuilder = new StringBuilder();

        users.forEach( u -> stringBuilder.append(u).append("\n"));

        viewModel.getUsersPaneProperty().setValue(stringBuilder.toString());
    }
}

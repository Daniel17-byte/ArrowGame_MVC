package org.danielsa.proiect_ps.controller.commands.game;

import org.danielsa.proiect_ps.model.UserModel;
import org.danielsa.proiect_ps.controller.GameController;

public class CommandGetUser {
    private final GameController viewModel;

    public CommandGetUser(GameController viewModel) {
        this.viewModel = viewModel;
    }

    public UserModel execute() {
        return viewModel.getModel().getUser();
    }
}

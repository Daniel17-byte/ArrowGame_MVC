package org.danielsa.proiect_ps.controller.commands.game;

import org.danielsa.proiect_ps.model.UserModel;
import org.danielsa.proiect_ps.controller.GameController;

public class CommandGetUser {
    private final GameController controller;

    public CommandGetUser(GameController controller) {
        this.controller = controller;
    }

    public UserModel execute() {
        return controller.getModel().getUser();
    }
}

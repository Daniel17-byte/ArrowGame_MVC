package org.danielsa.proiect_ps.controller.commands.game;

import eu.hansolo.tilesfx.Command;
import org.danielsa.proiect_ps.model.UserModel;
import org.danielsa.proiect_ps.model.UserType;
import org.danielsa.proiect_ps.controller.GameController;
import org.danielsa.proiect_ps.utils.LanguageManager;

public class CommandLoadWonGames implements Command {
    private final GameController controller;

    public CommandLoadWonGames(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        UserModel user = controller.getModel().getUser();
        int gamesWon = user.getGamesWon();
        if (user.getUserType().equals(UserType.PLAYER)) {
            controller.getGameswonProperty().setValue(LanguageManager.getString("gamesWonText") + gamesWon);
        }else {
            controller.getGameswonProperty().setValue(LanguageManager.getString("gamesWonText") + gamesWon);
        }
    }
}

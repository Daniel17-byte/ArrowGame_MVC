package org.danielsa.proiect_ps.controller.commands.game;

import eu.hansolo.tilesfx.Command;
import org.danielsa.proiect_ps.model.UserModel;
import org.danielsa.proiect_ps.model.UserType;
import org.danielsa.proiect_ps.controller.GameController;

public class CommandLoadWonGames implements Command {
    private final GameController viewModel;

    public CommandLoadWonGames(GameController viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void execute() {
        UserModel user = viewModel.getModel().getUser();
        int gamesWon = user.getGamesWon();
        if (user.getUserType().equals(UserType.PLAYER)) {
            viewModel.getGameswonProperty().setValue("Games won : " + gamesWon);
        }else {
            viewModel.getGameswonProperty().setValue("Games won : " + gamesWon);
        }
    }
}

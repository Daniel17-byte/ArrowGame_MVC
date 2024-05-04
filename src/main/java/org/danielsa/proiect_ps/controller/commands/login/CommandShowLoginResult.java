package org.danielsa.proiect_ps.controller.commands.login;

import eu.hansolo.tilesfx.Command;
import javafx.stage.Stage;
import org.danielsa.proiect_ps.view.GameView;
import org.danielsa.proiect_ps.controller.LoginController;

public class CommandShowLoginResult implements Command {
    private final LoginController viewModel;

    public CommandShowLoginResult(LoginController viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void execute() {
        boolean success = viewModel.getModel().authenticate(viewModel.getUsernameProperty().getValue(), viewModel.getPasswordProperty().getValue());

        if (success) {
            GameView view = new GameView();
            Stage gameStage = new Stage();

            gameStage.setScene(view);
            gameStage.setTitle("Arrow Game");
            gameStage.show();
        } else {
            viewModel.getResultLabelProperty().setValue("Login failed. Please try again.");
        }
    }
}

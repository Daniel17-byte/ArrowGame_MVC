package org.danielsa.proiect_ps.controller.commands.register;

import eu.hansolo.tilesfx.Command;
import javafx.stage.Stage;
import org.danielsa.proiect_ps.view.GameView;
import org.danielsa.proiect_ps.controller.RegisterController;

public class CommandShowRegisterResult implements Command {
    private final RegisterController viewModel;

    public CommandShowRegisterResult(RegisterController viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void execute() {
        boolean success = viewModel.getModel().register(viewModel.getUsernameProperty().getValue(), viewModel.getPasswordProperty().getValue(), viewModel.getUserTypeProperty().getValue());

        if (success) {
            GameView view = new GameView();
            Stage gameStage = new Stage();

            gameStage.setScene(view);
            gameStage.setTitle("Arrow Game");
            gameStage.show();
        } else {
            viewModel.getResultLabelProperty().setValue("Register failed. Please try again.");
        }
    }
}

package org.danielsa.proiect_ps.controller.commands.register;

import eu.hansolo.tilesfx.Command;
import javafx.stage.Stage;
import org.danielsa.proiect_ps.utils.LanguageManager;
import org.danielsa.proiect_ps.view.GameView;
import org.danielsa.proiect_ps.controller.RegisterController;

public class CommandShowRegisterResult implements Command {
    private final RegisterController controller;

    public CommandShowRegisterResult(RegisterController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        boolean success = controller.getModel().register(controller.getUsernameProperty().getValue(), controller.getPasswordProperty().getValue(), controller.getUserTypeProperty().getValue());

        if (success) {
            GameView view = new GameView();
            Stage gameStage = new Stage();

            gameStage.setScene(view);
            gameStage.setTitle(LanguageManager.getString("arrowGame"));
            gameStage.show();
        } else {
            controller.getResultLabelProperty().setValue(LanguageManager.getString("registerFailed"));
        }
    }
}

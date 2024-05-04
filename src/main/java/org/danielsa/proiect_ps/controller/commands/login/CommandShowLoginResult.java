package org.danielsa.proiect_ps.controller.commands.login;

import eu.hansolo.tilesfx.Command;
import javafx.stage.Stage;
import org.danielsa.proiect_ps.utils.LanguageManager;
import org.danielsa.proiect_ps.view.GameView;
import org.danielsa.proiect_ps.controller.LoginController;

public class CommandShowLoginResult implements Command {
    private final LoginController controller;

    public CommandShowLoginResult(LoginController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        boolean success = controller.getModel().authenticate(controller.getUsernameProperty().getValue(), controller.getPasswordProperty().getValue());

        if (success) {
            GameView view = new GameView();
            Stage gameStage = new Stage();

            gameStage.setScene(view);
            gameStage.setTitle(LanguageManager.getString("arrowGame"));
            gameStage.show();
        } else {
            controller.getResultLabelProperty().setValue(LanguageManager.getString("loginFailed"));
        }
    }
}

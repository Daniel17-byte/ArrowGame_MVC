package org.danielsa.proiect_ps.controller.commands.login;

import eu.hansolo.tilesfx.Command;
import javafx.stage.Stage;
import org.danielsa.proiect_ps.utils.LanguageManager;
import org.danielsa.proiect_ps.view.RegisterView;

public class CommandOpenRegisterWindow implements Command {

    public CommandOpenRegisterWindow() {
    }

    @Override
    public void execute() {
        RegisterView view = new RegisterView();
        Stage registerStage = new Stage();

        registerStage.setScene(view);
        registerStage.setTitle(LanguageManager.getString("registerButton"));
        registerStage.show();
    }
}

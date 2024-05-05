package org.danielsa.proiect_ps.controller;

import javafx.stage.Stage;
import lombok.Getter;
import org.danielsa.proiect_ps.model.RegisterModel;
import org.danielsa.proiect_ps.utils.LanguageManager;
import org.danielsa.proiect_ps.view.RegisterView;

@Getter
public class RegisterController {
    private final RegisterModel model;
    private final RegisterView view;

    public RegisterController() {
        this.model = new RegisterModel();
        this.view = new RegisterView();
        this.model.attach(this.view);
        initComponents();
    }

    private void initComponents() {
        view.getRegisterButton().setOnAction(event -> showRegisterResult());
    }

    public void showRegisterResult() {
        boolean success = model.register(view.getUsernameField().getText(), view.getPasswordField().getText(), view.getUserTypeComboBox().getValue());

        if (success) {
            LanguageManager.loadLanguage(LanguageManager.fromStringToLocale(view.getLanguageComboBox().getValue()));
            GameController controller = new GameController();
            Stage gameStage = new Stage();

            gameStage.setScene(controller.getView());
            gameStage.setTitle(LanguageManager.getString("arrowGame"));
            gameStage.show();
        }

    }

}

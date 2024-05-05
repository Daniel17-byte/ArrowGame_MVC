package org.danielsa.proiect_ps.controller;

import javafx.stage.Stage;
import lombok.Getter;
import org.danielsa.proiect_ps.model.LoginModel;
import org.danielsa.proiect_ps.utils.LanguageManager;
import org.danielsa.proiect_ps.view.LoginView;

@Getter
public class LoginController {
    private final LoginModel model;
    private final LoginView view;

    public LoginController() {
        this.model = new LoginModel();
        this.view = new LoginView();
        initComponents();
    }

    private void initComponents() {
        view.getLoginButton().setOnAction(event -> showLoginResult());
        view.getRegisterButton().setOnAction(event -> openRegisterWindow());
    }

    public void openRegisterWindow() {
        RegisterController controller = new RegisterController();
        Stage registerStage = new Stage();

        registerStage.setScene(controller.getView());
        registerStage.setTitle(LanguageManager.getString("registerButton"));
        registerStage.show();
    }

    public void showLoginResult() {
        LanguageManager.loadLanguage(LanguageManager.fromStringToLocale(view.getLanguageComboBox().getValue()));
        boolean success = getModel().authenticate(view.getUsernameField().getText(), view.getPasswordField().getText());

        if (success) {
            GameController controller = new GameController();
            Stage gameStage = new Stage();

            gameStage.setScene(controller.getView());
            gameStage.setTitle(LanguageManager.getString("arrowGame"));
            gameStage.show();
        } else {
            view.getResultLabel().setText(LanguageManager.getString("loginFailed"));
        }
    }

}

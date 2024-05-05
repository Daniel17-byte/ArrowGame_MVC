package org.danielsa.proiect_ps.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;
import lombok.Getter;
import org.danielsa.proiect_ps.model.LoginModel;
import org.danielsa.proiect_ps.utils.LanguageManager;
import org.danielsa.proiect_ps.view.GameView;
import org.danielsa.proiect_ps.view.RegisterView;

@Getter
public class LoginController {
    private final LoginModel model;
    private final StringProperty resultLabelProperty = new SimpleStringProperty();
    private final StringProperty usernameProperty = new SimpleStringProperty();
    private final StringProperty passwordProperty = new SimpleStringProperty();
    private final ObjectProperty<String> languageProperty = new SimpleObjectProperty<>();

    public LoginController() {
        model = new LoginModel();
    }

    public void openRegisterWindow() {
        RegisterView view = new RegisterView();
        Stage registerStage = new Stage();

        registerStage.setScene(view);
        registerStage.setTitle(LanguageManager.getString("registerButton"));
        registerStage.show();
    }

    public void showLoginResult() {
        LanguageManager.loadLanguage(LanguageManager.fromStringToLocale(getLanguageProperty().getValue()));
        boolean success = getModel().authenticate(getUsernameProperty().getValue(), getPasswordProperty().getValue());

        if (success) {
            GameView view = new GameView();
            Stage gameStage = new Stage();

            gameStage.setScene(view);
            gameStage.setTitle(LanguageManager.getString("arrowGame"));
            gameStage.show();
        } else {
            getResultLabelProperty().setValue(LanguageManager.getString("loginFailed"));
        }
    }

}

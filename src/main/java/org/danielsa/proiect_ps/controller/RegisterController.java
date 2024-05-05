package org.danielsa.proiect_ps.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;
import lombok.Getter;
import org.danielsa.proiect_ps.model.RegisterModel;
import org.danielsa.proiect_ps.utils.LanguageManager;
import org.danielsa.proiect_ps.view.GameView;

@Getter
public class RegisterController {
    private final RegisterModel model;
    private final StringProperty resultLabelProperty = new SimpleStringProperty();
    private final StringProperty usernameProperty = new SimpleStringProperty();
    private final StringProperty passwordProperty = new SimpleStringProperty();
    private final ObjectProperty<String> userTypeProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<String> languageProperty = new SimpleObjectProperty<>();

    public RegisterController() {
        this.model = new RegisterModel();
    }

    public void showRegisterResult() {
        boolean success = model.register(getUsernameProperty().getValue(), getPasswordProperty().getValue(), getUserTypeProperty().getValue());

        if (success) {
            LanguageManager.loadLanguage(LanguageManager.fromStringToLocale(getLanguageProperty().getValue()));
            GameView view = new GameView();
            Stage gameStage = new Stage();

            gameStage.setScene(view);
            gameStage.setTitle(LanguageManager.getString("arrowGame"));
            gameStage.show();
        } else {
            getResultLabelProperty().setValue(LanguageManager.getString("registerFailed"));
        }
    }

}

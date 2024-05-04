package org.danielsa.proiect_ps.view;

import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.danielsa.proiect_ps.controller.RegisterController;
import org.danielsa.proiect_ps.utils.LanguageManager;

public class RegisterView extends Scene implements Observer {
    private final RegisterController controller;

    public RegisterView() {
        super(new VBox(), 300, 200);
        this.controller = new RegisterController();
        initComponents();
    }

    public void initComponents() {
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button registerButton = new Button(LanguageManager.getString("registerButton"));
        Label resultLabel = new Label();
        ComboBox<String> userTypeComboBox = new ComboBox<>();
        ComboBox<String> languageComboBox = new ComboBox<>();

        VBox root = (VBox) getRoot();
        root.setSpacing(10);

        usernameField.setPromptText(LanguageManager.getString("usernameField"));
        passwordField.setPromptText(LanguageManager.getString("passwordField"));
        userTypeComboBox.getItems().addAll(LanguageManager.getStringForKey("userTypeComboBox", "valueAdmin"), LanguageManager.getStringForKey("userTypeComboBox", "valuePlayer"));
        userTypeComboBox.setPromptText(LanguageManager.getStringForKey("userTypeComboBox", "label"));
        languageComboBox.getItems().addAll("ENGLISH", "ROMANIAN", "DEUTSCH", "ITALIAN", "FRENCH");
        languageComboBox.setPromptText("Language ");

        registerButton.setOnAction(event -> controller.showRegisterResult());

        Bindings.bindBidirectional(resultLabel.textProperty(), controller.getResultLabelProperty());
        Bindings.bindBidirectional(usernameField.textProperty(), controller.getUsernameProperty());
        Bindings.bindBidirectional(passwordField.textProperty(), controller.getPasswordProperty());
        Bindings.bindBidirectional(userTypeComboBox.valueProperty(), controller.getUserTypeProperty());
        Bindings.bindBidirectional(languageComboBox.valueProperty(), controller.getLanguageProperty());

        root.getChildren().addAll(usernameField, passwordField, userTypeComboBox, languageComboBox, registerButton);
    }

    @Override
    public void update() {

    }
}

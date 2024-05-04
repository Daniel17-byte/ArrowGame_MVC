package org.danielsa.proiect_ps.view;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.danielsa.proiect_ps.controller.LoginController;
import org.danielsa.proiect_ps.utils.LanguageManager;

public class LoginView extends Scene implements Observer {
    private final LoginController controller;

    public LoginView() {
        super(new VBox(), 300, 200);
        this.controller = new LoginController();
        initComponents();
    }

    public void initComponents() {
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button(LanguageManager.getString("loginButton"));
        Label resultLabel = new Label();
        ComboBox<String> languageComboBox = new ComboBox<>();
        Button registerButton = new Button(LanguageManager.getString("registerButton"));

        VBox root = (VBox) getRoot();
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        usernameField.setPromptText(LanguageManager.getString("usernameField"));
        passwordField.setPromptText(LanguageManager.getString("passwordField"));
        languageComboBox.getItems().addAll("ENGLISH", "ROMANIAN", "DEUTSCH", "ITALIAN", "FRENCH");
        languageComboBox.setPromptText("Language ");

        loginButton.setOnAction(event -> controller.showLoginResult());
        registerButton.setOnAction(event -> controller.openRegisterWindow());

        Bindings.bindBidirectional(resultLabel.textProperty(), controller.getResultLabelProperty());
        Bindings.bindBidirectional(usernameField.textProperty(), controller.getUsernameProperty());
        Bindings.bindBidirectional(passwordField.textProperty(), controller.getPasswordProperty());
        Bindings.bindBidirectional(languageComboBox.valueProperty(), controller.getLanguageProperty());

        root.getChildren().addAll(usernameField, passwordField, languageComboBox, loginButton, resultLabel, registerButton);
    }

    @Override
    public void update() {

    }
}

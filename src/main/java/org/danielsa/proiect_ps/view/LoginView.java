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
        TextField usernameField;
        PasswordField passwordField;
        Button loginButton;
        Label resultLabel = new Label();
        Button registerButton;

        VBox root = (VBox) getRoot();
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        usernameField = new TextField();
        usernameField.setPromptText(LanguageManager.getString("usernameField"));

        passwordField = new PasswordField();
        passwordField.setPromptText(LanguageManager.getString("passwordField"));

        loginButton = new Button(LanguageManager.getString("loginButton"));

        registerButton = new Button(LanguageManager.getString("registerButton"));

        loginButton.setOnAction(event -> controller.showLoginResult());

        registerButton.setOnAction(event -> controller.openRegisterWindow());

        Bindings.bindBidirectional(resultLabel.textProperty(), controller.getResultLabelProperty());
        Bindings.bindBidirectional(usernameField.textProperty(), controller.getUsernameProperty());
        Bindings.bindBidirectional(passwordField.textProperty(), controller.getPasswordProperty());

        root.getChildren().addAll(usernameField, passwordField, loginButton, resultLabel, registerButton);
    }

    @Override
    public void update() {

    }
}

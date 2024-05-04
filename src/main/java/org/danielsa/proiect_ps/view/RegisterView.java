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
        TextField usernameField;
        PasswordField passwordField;
        Button registerButton;
        Label resultLabel = new Label();
        ComboBox<String> userTypeComboBox = new ComboBox<>();

        VBox root = (VBox) getRoot();
        root.setSpacing(10);
        usernameField = new TextField();
        usernameField.setPromptText(LanguageManager.getString("usernameField"));

        passwordField = new PasswordField();
        passwordField.setPromptText(LanguageManager.getString("passwordField"));

        userTypeComboBox.getItems().addAll(LanguageManager.getStringForKey("userTypeComboBox", "valueAdmin"), LanguageManager.getStringForKey("userTypeComboBox", "valuePlayer"));
        userTypeComboBox.setPromptText(LanguageManager.getStringForKey("userTypeComboBox", "label"));

        registerButton = new Button(LanguageManager.getString("registerButton"));

        registerButton.setOnAction(event -> controller.showRegisterResult());

        Bindings.bindBidirectional(resultLabel.textProperty(), controller.getResultLabelProperty());
        Bindings.bindBidirectional(usernameField.textProperty(), controller.getUsernameProperty());
        Bindings.bindBidirectional(passwordField.textProperty(), controller.getPasswordProperty());
        Bindings.bindBidirectional(userTypeComboBox.valueProperty(), controller.getUserTypeProperty());

        root.getChildren().addAll(usernameField, passwordField, userTypeComboBox, registerButton);
    }

    @Override
    public void update() {

    }
}

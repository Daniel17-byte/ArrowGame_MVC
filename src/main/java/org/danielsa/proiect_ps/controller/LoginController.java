package org.danielsa.proiect_ps.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import org.danielsa.proiect_ps.model.LoginModel;
import org.danielsa.proiect_ps.model.LoginModelInterface;
import org.danielsa.proiect_ps.controller.commands.login.CommandOpenRegisterWindow;
import org.danielsa.proiect_ps.controller.commands.login.CommandShowLoginResult;

@Getter
public class LoginController {
    private final LoginModelInterface model;
    private final StringProperty resultLabelProperty = new SimpleStringProperty();
    private final StringProperty usernameProperty = new SimpleStringProperty();
    private final StringProperty passwordProperty = new SimpleStringProperty();
    private final ObjectProperty<String> languageProperty = new SimpleObjectProperty<>();
    private final CommandShowLoginResult commandShowLoginResult;
    private final CommandOpenRegisterWindow commandOpenRegisterWindow;

    public LoginController() {
        model = new LoginModel();
        this.commandShowLoginResult = new CommandShowLoginResult(this);
        this.commandOpenRegisterWindow = new CommandOpenRegisterWindow();
    }

    public void openRegisterWindow() {
        commandOpenRegisterWindow.execute();
    }

    public void showLoginResult() {
        commandShowLoginResult.execute();
    }

}

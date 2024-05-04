package org.danielsa.proiect_ps.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableView;
import lombok.Getter;
import lombok.Setter;
import org.danielsa.proiect_ps.model.AdminModel;
import org.danielsa.proiect_ps.model.AdminModelInterface;
import org.danielsa.proiect_ps.model.UserModel;
import org.danielsa.proiect_ps.controller.commands.admin.CommandAddUser;
import org.danielsa.proiect_ps.controller.commands.admin.CommandDeleteUser;
import org.danielsa.proiect_ps.controller.commands.admin.CommandGetUsers;
import org.danielsa.proiect_ps.controller.commands.admin.CommandUpdateUser;

import java.util.ArrayList;

@Getter
public class AdminController {
    private final AdminModelInterface model;
    private final StringProperty usernameProperty = new SimpleStringProperty();
    private final StringProperty passwordProperty = new SimpleStringProperty();
    private final ObjectProperty<String> userTypeProperty = new SimpleObjectProperty<>();
    @Setter
    private ObjectProperty<UserModel> selectedUserProperty = new SimpleObjectProperty<>();
    @Setter
    private ObjectProperty<TableView<UserModel>> userTableViewProperty = new SimpleObjectProperty<>();
    private final CommandAddUser commandAddUser;
    private final CommandUpdateUser commandUpdateUser;
    private final CommandDeleteUser commandDeleteUser;
    private final CommandGetUsers commandGetUsers;

    public AdminController() {
        this.model = new AdminModel();
        this.commandAddUser = new CommandAddUser(this);
        this.commandUpdateUser = new CommandUpdateUser(this);
        this.commandDeleteUser = new CommandDeleteUser(this);
        this.commandGetUsers = new CommandGetUsers(this);
    }

    public void addUser() {
        commandAddUser.execute();
    }

    public void updateUser() {
        commandUpdateUser.execute();
    }

    public void deleteUser() {
        commandDeleteUser.execute();
    }

    public ArrayList<UserModel> getUsers(){
        return commandGetUsers.execute();
    }
}

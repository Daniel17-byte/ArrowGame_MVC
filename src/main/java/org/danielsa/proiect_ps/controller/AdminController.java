package org.danielsa.proiect_ps.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableView;
import lombok.Getter;
import lombok.Setter;
import org.danielsa.proiect_ps.model.AdminModel;
import org.danielsa.proiect_ps.model.UserModel;

import java.util.ArrayList;

@Getter
public class AdminController {
    private final AdminModel model;
    private final StringProperty usernameProperty = new SimpleStringProperty();
    private final StringProperty passwordProperty = new SimpleStringProperty();
    private final ObjectProperty<String> userTypeProperty = new SimpleObjectProperty<>();
    @Setter
    private ObjectProperty<UserModel> selectedUserProperty = new SimpleObjectProperty<>();
    @Setter
    private ObjectProperty<TableView<UserModel>> userTableViewProperty = new SimpleObjectProperty<>();

    public AdminController() {
        this.model = new AdminModel();
    }

    public void addUser() {
        boolean success = model.register(
                getUsernameProperty().getValue(),
                getPasswordProperty().getValue(),
                getUserTypeProperty().getValue()
        );

        if (success) {
            TableView<UserModel> tableView = getUserTableViewProperty().getValue();
            tableView.getItems().addAll(model.getUserByUsername(getUsernameProperty().getValue()));
        } else {
            System.out.println("User not added!");
        }
    }

    public void updateUser() {
        UserModel updatedUser = model.updateUser(getSelectedUserProperty().getValue().getUserName(), getUsernameProperty().getValue(), getPasswordProperty().getValue(), getUserTypeProperty().getValue());

        if (updatedUser == null) {
            System.out.println("User not updated!");
            return;
        }

        getUserTableViewProperty().getValue().getItems().clear();
        getUserTableViewProperty().getValue().getItems().addAll(model.getUsers());
    }

    public void deleteUser() {
        boolean success = model.deleteUser(getSelectedUserProperty().getValue().getUserName());

        if (!success) {
            System.out.println("User not deleted!");
            return;
        }

        getUserTableViewProperty().getValue().getItems().remove(getUserTableViewProperty().getValue().getSelectionModel().getSelectedItem());
    }

    public ArrayList<UserModel> getUsers(){
        ArrayList<UserModel> users = model.getUsers();

        if (users.isEmpty()){
            System.out.println("Couldn't load users!");
            return null;
        }

        return users;
    }
}

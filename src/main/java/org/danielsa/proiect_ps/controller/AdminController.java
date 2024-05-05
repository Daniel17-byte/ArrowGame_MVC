package org.danielsa.proiect_ps.controller;

import javafx.scene.control.TableView;
import lombok.Getter;
import org.danielsa.proiect_ps.model.AdminModel;
import org.danielsa.proiect_ps.model.UserModel;
import org.danielsa.proiect_ps.view.AdminView;

import java.util.ArrayList;

@Getter
public class AdminController {
    private final AdminModel model;
    private final AdminView view;

    public AdminController() {
        this.model = new AdminModel();
        this.view = new AdminView();
        this.model.attach(this.view);
        initComponents();
    }

    private void initComponents() {
        view.getAddButton().setOnAction(event -> addUser());
        view.getUpdateButton().setOnAction(event -> {
            if (view.getUserTableView().getSelectionModel().getSelectedItem() != null) {
                updateUser();
            }
        });
        view.getDeleteButton().setOnAction(event -> {
            if (view.getUserTableView().getSelectionModel().getSelectedItem() != null) {
                deleteUser();
            }
        });
        view.getUserTableView().getItems().addAll(getUsers());
        view.getUserTableView().getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> view.setSelectedUser(newVal));

    }

    public void addUser() {
        boolean success = model.register(
                view.getUserNameField().getText(),
                view.getPasswordField().getText(),
                view.getUserTypeComboBox().getValue()
        );

        if (success) {
            TableView<UserModel> tableView = view.getUserTableView();
            tableView.getItems().addAll(model.getUserByUsername(view.getUserNameField().getText()));
        } else {
            System.out.println("User not added!");
        }
    }

    public void updateUser() {
        UserModel updatedUser = model.updateUser(view.getSelectedUser().getUserName(), view.getUserNameField().getText(), view.getPasswordField().getText(), view.getUserTypeComboBox().getValue());

        if (updatedUser == null) {
            System.out.println("User not updated!");
            return;
        }

        view.getUserTableView().getItems().clear();
        view.getUserTableView().getItems().addAll(model.getUsers());
    }

    public void deleteUser() {
        boolean success = model.deleteUser(view.getSelectedUser().getUserName());

        if (!success) {
            System.out.println("User not deleted!");
            return;
        }

        view.getUserTableView().getItems().remove(view.getUserTableView().getSelectionModel().getSelectedItem());
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

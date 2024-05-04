package org.danielsa.proiect_ps.view;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.danielsa.proiect_ps.model.UserModel;
import org.danielsa.proiect_ps.controller.AdminController;
import org.danielsa.proiect_ps.utils.LanguageManager;

import java.util.Arrays;

public class AdminView extends Scene implements Observer {
    private final AdminController controller;

    public AdminView() {
        super(new VBox(), 500, 500);
        this.controller = new AdminController();
        initComponents();
    }

    public void initComponents() {
        TextField userNameField = new TextField();
        PasswordField passwordField = new PasswordField();
        ComboBox<String> userTypeComboBox = new ComboBox<>();
        userTypeComboBox.getItems().addAll(LanguageManager.getStringForKey("userTypeComboBox", "valueAdmin"), LanguageManager.getStringForKey("userTypeComboBox", "valuePlayer"));
        Button addButton = new Button(LanguageManager.getString("addButton"));
        Button updateButton = new Button(LanguageManager.getString("updateButton"));
        Button deleteButton = new Button(LanguageManager.getString("deleteButton"));
        TableView<UserModel> userTableView = new TableView<>();

        TableColumn<UserModel, String> userNameColumn = new TableColumn<>(LanguageManager.getString("userNameColumn"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        TableColumn<UserModel, String> userTypeColumn = new TableColumn<>(LanguageManager.getString("userTypeColumn"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
        TableColumn<UserModel, Integer> gamesWonColumn = new TableColumn<>(LanguageManager.getString("gamesWonColumn"));
        gamesWonColumn.setCellValueFactory(new PropertyValueFactory<>("gamesWon"));
        userTableView.getColumns().addAll(Arrays.asList(userNameColumn, userTypeColumn, gamesWonColumn));

        userTableView.getItems().addAll(controller.getUsers());

        controller.getUserTableViewProperty().set(userTableView);

        userTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> controller.getSelectedUserProperty().set(newVal));

        addButton.setOnAction(event -> controller.addUser());

        updateButton.setOnAction(event -> {
            if (userTableView.getSelectionModel().getSelectedItem() != null) {
                controller.updateUser();
            }
        });

        deleteButton.setOnAction(event -> {
            if (userTableView.getSelectionModel().getSelectedItem() != null) {
                controller.deleteUser();
            }
        });

        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
                new HBox(new Label(LanguageManager.getString("userNameColumn") + " "), userNameField),
                new HBox(new Label(LanguageManager.getString("passwordField") + " "), passwordField),
                new HBox(new Label(LanguageManager.getString("userTypeColumn") + " "), userTypeComboBox),
                new HBox(addButton, updateButton, deleteButton),
                userTableView
        );

        Bindings.bindBidirectional(userNameField.textProperty(), controller.getUsernameProperty());
        Bindings.bindBidirectional(passwordField.textProperty(), controller.getPasswordProperty());
        Bindings.bindBidirectional(userTypeComboBox.valueProperty(), controller.getUserTypeProperty());

        setRoot(root);
    }

    @Override
    public void update() {

    }
}

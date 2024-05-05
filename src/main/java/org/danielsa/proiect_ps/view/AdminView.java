package org.danielsa.proiect_ps.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.danielsa.proiect_ps.model.UserModel;
import org.danielsa.proiect_ps.utils.LanguageManager;

import java.util.Arrays;

@Getter
public class AdminView extends Scene implements Observer {
    private final TextField userNameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final ComboBox<String> userTypeComboBox = new ComboBox<>();
    private final Button addButton = new Button(LanguageManager.getString("addButton"));
    private final Button updateButton = new Button(LanguageManager.getString("updateButton"));
    private final Button deleteButton = new Button(LanguageManager.getString("deleteButton"));
    private final TableView<UserModel> userTableView = new TableView<>();
    @Setter
    private UserModel selectedUser;


    public AdminView() {
        super(new VBox(), 500, 500);
        initComponents();
    }

    public void initComponents() {

        userTypeComboBox.getItems().addAll(LanguageManager.getStringForKey("userTypeComboBox", "valueAdmin"), LanguageManager.getStringForKey("userTypeComboBox", "valuePlayer"));

        TableColumn<UserModel, String> userNameColumn = new TableColumn<>(LanguageManager.getString("userNameColumn"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        TableColumn<UserModel, String> userTypeColumn = new TableColumn<>(LanguageManager.getString("userTypeColumn"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
        TableColumn<UserModel, Integer> gamesWonColumn = new TableColumn<>(LanguageManager.getString("gamesWonColumn"));
        gamesWonColumn.setCellValueFactory(new PropertyValueFactory<>("gamesWon"));
        userTableView.getColumns().addAll(Arrays.asList(userNameColumn, userTypeColumn, gamesWonColumn));

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

        setRoot(root);
    }

    @Override
    public void update(boolean success) {

    }
}

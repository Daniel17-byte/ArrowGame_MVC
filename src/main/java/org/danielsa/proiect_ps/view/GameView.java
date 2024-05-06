package org.danielsa.proiect_ps.view;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.utils.LanguageManager;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Getter
public class GameView extends Scene implements ObserverGame {
    private final Button startGameButton = new Button(LanguageManager.getString("startGameButton"));
    private final Button restartButton = new Button(LanguageManager.getString("restartButton"));
    private final Button undoButton = new Button(LanguageManager.getString("undoButton"));
    private final Button manageUsersButton = new Button(LanguageManager.getString("manageUsersButton"));
    private final HashMap<String, Button> buttons = new LinkedHashMap<>();
    private final TextField gamesWonText = new TextField();
    private final TextField selectedDirection = new TextField();
    private final ChoiceBox<String> levelSelectChoiceBox  = new ChoiceBox<>();
    private final TextArea usersPane = new TextArea("");
    private final BorderPane borderPane = new BorderPane();
    private final BorderPane rightPane = new BorderPane();
    private final Label greetingLabel = new Label();
    @Setter
    private GridPane board = new GridPane();
    @Setter
    private GridPane gridLargeBoard = new GridPane();
    @Setter
    private GridPane gridSmallBoard = new GridPane();

    public GameView() {
        super(new VBox(), 900, 500);
        initComponents();
    }

    private void initComponents() {
        VBox topPane;
        VBox leftPane;
        VBox centerPane;
        VBox rightPane;
        VBox bottomPane;

        gridSmallBoard = initBoard("small");
        gridLargeBoard = initBoard("large");

        borderPane.setStyle("-fx-background-color: #9db98a;");
        borderPane.setVisible(true);

        String[] directions = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        for (String direction : directions) {
            buttons.put(direction, new Button(direction));
        }

        buttons.forEach( (k, b) -> initializeButton(b));

        VBox root = (VBox) getRoot();

        topPane = createTopPanel();
        borderPane.setTop(topPane);

        leftPane = createLeftPane();
        borderPane.setLeft(leftPane);

        rightPane = createRightPane();
        borderPane.setRight(rightPane);

        centerPane = createCenterPane();
        borderPane.setCenter(centerPane);

        bottomPane = createBottomPane();
        borderPane.setBottom(bottomPane);

        root.getChildren().add(borderPane);
    }

    private VBox createTopPanel() {
        VBox vBox = new VBox();
        vBox.setStyle(("-fx-background-color: #60c760;"));

        vBox.getChildren().add(greetingLabel);

        return vBox;
    }

    private VBox createLeftPane() {
        VBox leftPanel = new VBox();

        AnchorPane leftPane = new AnchorPane();
        leftPane.setPrefSize(200, 500);
        leftPane.setStyle("-fx-background-color: grey;");

        AnchorPane.setTopAnchor(startGameButton, 113.0);
        AnchorPane.setLeftAnchor(startGameButton, 22.0);

        AnchorPane.setTopAnchor(restartButton, 230.0);
        AnchorPane.setLeftAnchor(restartButton, 22.0);

        levelSelectChoiceBox.getItems().addAll("4x4", "8x8");

        AnchorPane.setTopAnchor(levelSelectChoiceBox, 151.0);
        AnchorPane.setLeftAnchor(levelSelectChoiceBox, 68.0);

        Label boardLabel = new Label(LanguageManager.getString("boardLabel"));
        AnchorPane.setTopAnchor(boardLabel, 153.0);
        AnchorPane.setLeftAnchor(boardLabel, 22.0);

        AnchorPane.setTopAnchor(undoButton, 191.0);
        AnchorPane.setLeftAnchor(undoButton, 22.0);

        leftPane.getChildren().addAll(startGameButton, restartButton, boardLabel, levelSelectChoiceBox, undoButton);

        BorderPane.setAlignment(leftPane, Pos.CENTER_LEFT);
        leftPanel.getChildren().add(leftPane);

        return leftPanel;
    }

    private VBox createRightPane() {
        VBox rightPanel = new VBox();

        rightPane.setPrefSize(200, 500);
        rightPane.setStyle("-fx-background-color: grey;");

        gamesWonText.setText(LanguageManager.getString("gamesWonText"));
        gamesWonText.setEditable(false);
        gamesWonText.setPrefSize(180, 30);
        BorderPane.setMargin(gamesWonText, new Insets(20, 0, 0, 10));
        rightPane.setTop(gamesWonText);

        rightPanel.getChildren().add(rightPane);
        VBox.setMargin(rightPane, new Insets(0, 0, 0, 0));
        BorderPane.setAlignment(rightPane, Pos.CENTER_RIGHT);

        return rightPanel;
    }

    private VBox createBottomPane() {
        VBox bottomPanel = new VBox();

        HBox buttonRow = new HBox();
        buttonRow.setSpacing(2);

        buttons.forEach((key, value) -> buttonRow.getChildren().add(value));

        AnchorPane bottomPane = new AnchorPane();
        bottomPane.setStyle("-fx-background-color: grey;");
        bottomPane.getChildren().add(buttonRow);
        AnchorPane.setBottomAnchor(buttonRow, 0.0);
        AnchorPane.setLeftAnchor(buttonRow, 250.0);
        AnchorPane.setRightAnchor(buttonRow, 100.0);

        bottomPanel.getChildren().add(bottomPane);

        bottomPanel.setAlignment(Pos.CENTER);

        return bottomPanel;
    }

    private VBox createCenterPane() {
        VBox centerPanel = new VBox();
        BorderPane centerPane = new BorderPane();
        centerPane.setPrefSize(500, 500);
        board.setVisible(true);
        board = gridLargeBoard;
        centerPane.setCenter(board);
        centerPane.getCenter().setStyle("-fx-background-color: #9db98a;");
        centerPane.setVisible(true);
        centerPanel.getChildren().add(centerPane);

        return centerPanel;
    }

    public void initializeButton(Button button) {
        button.setBackground(setBgImage(button.getText() + ".png"));
        button.setVisible(true);
        button.setAlignment(javafx.geometry.Pos.BOTTOM_RIGHT);
        button.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        button.setLayoutX(113.0);
        button.setLayoutY(13.0);
        button.setMnemonicParsing(false);
        button.setPrefHeight(50.0);
        button.setPrefWidth(50.0);
        button.setPrefWidth(50.0);
        button.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
    }

    private Background setBgImage(String name) {
        BackgroundImage b = new BackgroundImage(new Image(new File(Main.path + "g" + name).toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        return new Background(b);
    }

    public void doButtonEffect(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), button);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(0.8);
        scaleTransition.setToY(0.8);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(2);

        scaleTransition.play();
    }

    public void signalEndgame(String winner) {
        final Stage dialog = new Stage();
        dialog.setTitle(LanguageManager.getString("endGame"));
        dialog.setX(950);
        dialog.setY(300);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.centerOnScreen();
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(new Text(winner.toUpperCase() + LanguageManager.getString("wins")));
        Scene dialogScene = new Scene(dialogVbox, 150, 100);
        dialogVbox.setOnMouseClicked(e -> dialog.close());
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public void setUsersPanel() {
        usersPane.setEditable(false);
        usersPane.setPrefSize(180, 300);
        BorderPane.setMargin(usersPane, new Insets(10, 0, 0, 10));
        rightPane.setCenter(usersPane);
        rightPane.setBottom(manageUsersButton);
        BorderPane.setMargin(manageUsersButton, new Insets(10, 0, 0, 10));
    }

    public GridPane initBoard(String sizeS) {
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(500, 500);

        int size = 4;

        if (sizeS.equals("large")){
            size = 8;
        }

        for (int i = 0; i < size; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / size);
            gridPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < size; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / size);
            gridPane.getRowConstraints().add(row);
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ImageView imageView = new ImageView(new File(Main.path + "img.png").toURI().toString());
                imageView.setFitWidth(41.0);
                imageView.setFitHeight(38.0);
                GridPane.setMargin(imageView, new Insets(2));
                gridPane.add(imageView, j, i);
            }
        }

        return gridPane;
    }

    @Override
    public void update(boolean success, String op) {
        if (!success && op.equals("MOVE")){
            greetingLabel.setText(LanguageManager.getString("invalidMove"));
        }

        if (!success && op.equals("ENDGAME")){
            greetingLabel.setText(LanguageManager.getString("endGame"));
        }
    }
}

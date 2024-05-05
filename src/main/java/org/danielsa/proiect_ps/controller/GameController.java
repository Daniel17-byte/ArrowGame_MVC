package org.danielsa.proiect_ps.controller;

import javafx.animation.ScaleTransition;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.model.*;
import org.danielsa.proiect_ps.utils.LanguageManager;
import org.danielsa.proiect_ps.view.AdminView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
public class GameController {
    private final GameModel model;
    private final StringProperty selectedDirectionProperty = new SimpleStringProperty();
    private final StringProperty gameswonProperty = new SimpleStringProperty();
    private final StringProperty usersPaneProperty = new SimpleStringProperty();
    private final StringProperty levelSelectChoiceBoxProperty = new SimpleStringProperty("8x8");
    @Setter
    private ObjectProperty<GridPane> boardProperty = new SimpleObjectProperty<>();
    @Setter
    private ObjectProperty<GridPane> gridLargeBoardProperty = new SimpleObjectProperty<>();
    @Setter
    private ObjectProperty<GridPane> gridSmallBoardProperty = new SimpleObjectProperty<>();
    @Setter
    private ObjectProperty<HashMap<String, Button>> buttonsProperty = new SimpleObjectProperty<>();

    public GameController() {
        model = new GameModel();
        model.getComputer().setStrategy(new MinMaxStrategy(4, 10));
    }

    public void userRegisterMove(int row, int column) {
        boolean valid = model.makeUserMove(new MoveModel(row, column, new ArrowModel(model.getPlayer().getColor(), getSelectedDirectionProperty().getValue())));

        if(!valid) {
            signalInvalidMove(LanguageManager.getString("invalidMove"));
            return;
        }

        if(getSelectedDirectionProperty().getValue() == null){
            signalInvalidMove(LanguageManager.getString("directionNotSelected"));
            return;
        }

        placeArrow(model.getPlayer().getColor(), getSelectedDirectionProperty().getValue(), row, column);

        if(model.isEndgame()) {
            signalEndgame(LanguageManager.getString("user"));
            return;
        }

        MoveModel moveModel = model.getSystemMove();
        if (moveModel != null){
            placeArrow(model.getComputer().getColor(), moveModel.getArrowModel().getDirection(), moveModel.getX(), moveModel.getY());
        }

        if(model.isEndgame()){
            signalEndgame(LanguageManager.getString("computer"));
        }
    }

    public void undoMove() {
        MoveModel sysMoveModel = model.undo();
        MoveModel usrMoveModel = model.undo();
        if(sysMoveModel != null) removeArrow(sysMoveModel.getX(), sysMoveModel.getY());
        if(usrMoveModel != null) removeArrow(usrMoveModel.getX(), usrMoveModel.getY());
    }

    public void loadUserList() {
        ArrayList<UserModel> users = model.getUsers();
        StringBuilder stringBuilder = new StringBuilder();

        users.forEach( u -> stringBuilder.append(u).append("\n"));

        getUsersPaneProperty().setValue(stringBuilder.toString());
    }

    public void clickedManageUsersButton() {
        AdminView view = new AdminView();
        Stage adminStage = new Stage();

        adminStage.setScene(view);
        adminStage.setTitle(LanguageManager.getString("adminPanel"));
        adminStage.show();
    }

    public void loadWonGames() {
        UserModel user = model.getUser();
        int gamesWon = user.getGamesWon();
        if (user.getUserType().equals(UserType.PLAYER)) {
            getGameswonProperty().setValue(LanguageManager.getString("gamesWonText") + gamesWon);
        }else {
            getGameswonProperty().setValue(LanguageManager.getString("gamesWonText") + gamesWon);
        }
    }

    public void clickedStartGame() {
        clearBoard();
        String color = "g";
        if(!model.getPlayer().getColor().equals(color)) {
            model.getComputer().setColor(model.getPlayer().getColor());
            model.getComputer().setColor(color);
            model.changePlayerColor(model.getComputer(), color);
        }

        String selectedBoard = getLevelSelectChoiceBoxProperty().getValue();
        if(selectedBoard.equals("4x4")) {
            getBoardProperty().setValue(getGridSmallBoardProperty().getValue());
            model.changeBoardSize(4);
            adjustInterButtons(false);
        } else {
            getBoardProperty().setValue(getGridLargeBoardProperty().getValue());
            model.changeBoardSize(8);
            adjustInterButtons(true);
        }
    }

    public void clearBoard(){
        if(getBoardProperty().getValue() != null){
            model.clearBoard();
            getBoardProperty().getValue().getChildren().stream()
                    .filter(node -> node instanceof ImageView)
                    .map(node -> (ImageView) node)
                    .forEach(imageView -> imageView.setImage(new Image(new File(Main.path + "img.png").toURI().toString())));
        }
        if(getLevelSelectChoiceBoxProperty().getValue().equals("4x4")){
            getBoardProperty().setValue(getGridSmallBoardProperty().getValue());
        }else {
            getBoardProperty().setValue(getGridLargeBoardProperty().getValue());
        }
    }

    public UserModel getUser() {
        return model.getUser();
    }

    public void initializeButton(Button button){
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

        button.setOnAction( e ->{
            doButtonEffect(button);
            clickedArrowButton(e);
        });
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
                imageView.setOnMouseClicked(this::clickedBoard);
                imageView.setFitWidth(41.0);
                imageView.setFitHeight(38.0);
                GridPane.setMargin(imageView, new Insets(2));
                gridPane.add(imageView, j, i);
            }
        }

        return gridPane;
    }

    private void placeArrow(String color, String direction, int row, int column){
        Image image = new Image(new File(Main.path + color + direction + ".png").toURI().toString());

        getBoardProperty().getValue().getChildren().stream()
                .filter(node -> node instanceof ImageView)
                .map(node -> (ImageView) node)
                .filter(imageView -> {
                    Integer rowIdx = GridPane.getRowIndex(imageView);
                    Integer colIdx = GridPane.getColumnIndex(imageView);
                    return rowIdx != null && colIdx != null && rowIdx == row && colIdx == column;
                })
                .findFirst()
                .ifPresent(imageView -> imageView.setImage(image));
    }

    private void signalInvalidMove(String message) {
        final Stage dialog = new Stage();
        dialog.setTitle(LanguageManager.getString("error"));
        dialog.setX(950);
        dialog.setY(300);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.centerOnScreen();
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(new Text(message));
        Scene dialogScene = new Scene(dialogVbox, 150, 100);
        dialogVbox.setOnMouseClicked(mouseEvent -> dialog.close());
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void signalEndgame(String winner) {
        final Stage dialog = new Stage();
        dialog.setTitle(LanguageManager.getString("endGame"));
        dialog.setX(950);
        dialog.setY(300);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.centerOnScreen();
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        if(winner.equals(LanguageManager.getString("user"))){
            winner = model.getUser().getUserName();
        }
        dialogVbox.getChildren().add(new Text(winner.toUpperCase() + LanguageManager.getString("wins")));
        Scene dialogScene = new Scene(dialogVbox, 150, 100);
        dialogVbox.setOnMouseClicked(e -> {
            dialog.close();
            clearBoard();
        });
        dialog.setScene(dialogScene);
        dialog.show();
        if (!winner.equalsIgnoreCase(LanguageManager.getString("computer"))){
            getModel().updateUserScore();
        }
        loadWonGames();
    }

    private void removeArrow(int row, int column) {
        getBoardProperty().getValue().getChildren().stream()
                .filter(node -> node instanceof ImageView)
                .map(node -> (ImageView) node)
                .filter(imageView -> {
                    Integer rowIdx = GridPane.getRowIndex(imageView);
                    Integer colIdx = GridPane.getColumnIndex(imageView);
                    return rowIdx != null && colIdx != null && rowIdx == row && colIdx == column;
                })
                .findFirst()
                .ifPresent(imageView -> imageView.setImage(new Image(new File(Main.path + "img.png").toURI().toString())));
    }

    private void adjustInterButtons(boolean isVisible) {
        getButtonsProperty().getValue().get("nE").setVisible(isVisible);
        getButtonsProperty().getValue().get("nW").setVisible(isVisible);
        getButtonsProperty().getValue().get("sE").setVisible(isVisible);
        getButtonsProperty().getValue().get("sW").setVisible(isVisible);
    }

    private void clickedArrowButton(ActionEvent actionEvent){
        Button button = (Button)actionEvent.getSource();
        getSelectedDirectionProperty().setValue(button.getText());
    }

    private Background setBgImage(String name){
        BackgroundImage b = new BackgroundImage(new Image(new File(Main.path + "g" + name).toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        return new Background(b);
    }

    private void doButtonEffect(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), button);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(0.8);
        scaleTransition.setToY(0.8);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(2);

        scaleTransition.play();
    }

    private void clickedBoard(MouseEvent mouseEvent){
        EventTarget source = mouseEvent.getTarget();

        if(!(source instanceof ImageView selectedImage)){
            return;
        }

        int row = GridPane.getRowIndex(selectedImage);
        int col = GridPane.getColumnIndex(selectedImage);

        userRegisterMove(row, col);
    }
}

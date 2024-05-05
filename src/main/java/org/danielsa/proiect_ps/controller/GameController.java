package org.danielsa.proiect_ps.controller;

import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.model.*;
import org.danielsa.proiect_ps.utils.LanguageManager;
import org.danielsa.proiect_ps.utils.MinMaxStrategy;
import org.danielsa.proiect_ps.view.GameView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

@Getter
public class GameController {
    private final GameModel model;
    private final GameView view;

    public GameController() {
        this.model = new GameModel();
        this.view = new GameView();
        this.model.getComputer().setStrategy(new MinMaxStrategy(4, 10));
        this.model.attach(this.view);
        initComponents();
    }

    private void initComponents() {
        view.getRestartButton().setOnAction(e -> clearBoard());
        view.getStartGameButton().setOnAction(e -> {
            clickedStartGame();
            view.getBorderPane().setCenter(view.getBoard());
        });
        view.getUndoButton().setOnAction(e -> undoMove());
        view.getManageUsersButton().setOnAction(event -> clickedManageUsersButton());
        view.getButtons().forEach((key, value) -> value.setOnAction( e -> {
            view.getSelectedDirection().setText(value.getText());
            view.doButtonEffect(value);
        }));
        loadUserList();
        loadWonGames();
        view.setGridSmallBoard(initBoard("small"));
        view.setGridLargeBoard(initBoard("large"));
        view.getGreetingLabel().setText(LanguageManager.getString("greetingLabel") + " " + model.getUser().getUserName().toUpperCase());
        if (model.getUser().getUserType().equals(UserType.ADMIN)) {
            view.getUsersPane().setEditable(false);
            view.getUsersPane().setPrefSize(180, 300);
            BorderPane.setMargin(view.getUsersPane(), new Insets(10, 0, 0, 10));
            view.getRightPane().setCenter(view.getUsersPane());
            view.getRightPane().setBottom(view.getManageUsersButton());
            BorderPane.setMargin(view.getManageUsersButton(), new Insets(10, 0, 0, 10));
        }

    }

    public void userRegisterMove(int row, int column) {
        boolean valid = model.makeUserMove(new MoveModel(row, column, new ArrowModel(model.getPlayer().getColor(), view.getSelectedDirection().getText())));

        if(!valid) {
            view.signalInvalidMove(LanguageManager.getString("invalidMove"));
            return;
        }

        if(view.getSelectedDirection().getText() == null){
            view.signalInvalidMove(LanguageManager.getString("directionNotSelected"));
            return;
        }

        placeArrow(model.getPlayer().getColor(), view.getSelectedDirection().getText(), row, column);

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

        view.getUsersPane().setText(stringBuilder.toString());
    }

    public void clickedManageUsersButton() {
        AdminController controller = new AdminController();
        Stage adminStage = new Stage();

        adminStage.setScene(controller.getView());
        adminStage.setTitle(LanguageManager.getString("adminPanel"));
        adminStage.show();
    }

    public void loadWonGames() {
        UserModel user = model.getUser();
        int gamesWon = user.getGamesWon();
        if (user.getUserType().equals(UserType.PLAYER)) {
            view.getGamesWonText().setText(LanguageManager.getString("gamesWonText") + gamesWon);
        }else {
            view.getGamesWonText().setText(LanguageManager.getString("gamesWonText") + gamesWon);
        }
    }

    public void clickedStartGame() {
        clearBoard();
        String color = "g";

        if(!model.getPlayer().getColor().equals(color)) {
            model.getComputer().setColor(model.getPlayer().getColor());
            model.getComputer().setColor(color);
        }

        String selectedBoard = view.getLevelSelectChoiceBox().getValue();
        String[] directions = {"NE", "SE", "SW", "NW"};

        if(selectedBoard.equals("4x4")) {
            view.setBoard(view.getGridSmallBoard());
            model.changeBoardSize(4);
            Arrays.stream(directions).forEach( d -> view.getButtons().get(d).setVisible(false));
        } else {
            view.setBoard(view.getGridLargeBoard());
            model.changeBoardSize(8);
            Arrays.stream(directions).forEach( d -> view.getButtons().get(d).setVisible(true));
        }
    }

    public void clearBoard() {
        if(view.getBoard() != null){
            model.clearBoard();
            view.getBoard().getChildren().stream()
                    .filter(node -> node instanceof ImageView)
                    .map(node -> (ImageView) node)
                    .forEach(imageView -> imageView.setImage(new Image(new File(Main.path + "img.png").toURI().toString())));
        }
        if(view.getLevelSelectChoiceBox().getValue().equals("4x4")){
            view.setBoard(view.getGridSmallBoard());
        }else {
            view.setBoard(view.getGridLargeBoard());
        }
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

    private void placeArrow(String color, String direction, int row, int column) {
        Image image = new Image(new File(Main.path + color + direction + ".png").toURI().toString());

        view.getBoard().getChildren().stream()
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
        view.getBoard().getChildren().stream()
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

    private void clickedBoard(MouseEvent mouseEvent) {
        EventTarget source = mouseEvent.getTarget();

        if(!(source instanceof ImageView selectedImage)){
            return;
        }

        int row = GridPane.getRowIndex(selectedImage);
        int col = GridPane.getColumnIndex(selectedImage);

        userRegisterMove(row, col);
    }
}

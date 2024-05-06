package org.danielsa.proiect_ps.controller;

import javafx.event.EventTarget;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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
        view.getBoard().getChildren().stream()
                .filter(node -> node instanceof ImageView)
                .map(node -> (ImageView) node)
                .forEach(imageView -> imageView.setOnMouseClicked(this::clickedBoard));
        view.getGridLargeBoard().getChildren().stream()
                .filter(node -> node instanceof ImageView)
                .map(node -> (ImageView) node)
                .forEach(imageView -> imageView.setOnMouseClicked(this::clickedBoard));
        view.getGridSmallBoard().getChildren().stream()
                .filter(node -> node instanceof ImageView)
                .map(node -> (ImageView) node)
                .forEach(imageView -> imageView.setOnMouseClicked(this::clickedBoard));
        view.getGreetingLabel().setText(LanguageManager.getString("greetingLabel") + " " + model.getUser().getUserName().toUpperCase());
        if (model.getUser().getUserType().equals(UserType.ADMIN)) {
            view.setUsersPanel();
        }
    }

    public void userRegisterMove(int row, int column) {
        boolean valid = model.makeUserMove(new MoveModel(row, column, new ArrowModel(model.getPlayer().getColor(), view.getSelectedDirection().getText())));

        if(!valid || view.getSelectedDirection().getText() == null){
            return;
        }

        placeArrow(model.getPlayer().getColor(), view.getSelectedDirection().getText(), row, column);

        if(model.isEndgame()) {
            view.signalEndgame(model.getUser().getUserName());
            model.updateUserScore();
            loadWonGames();
            return;
        }

        MoveModel moveModel = model.getSystemMove();
        if (moveModel != null){
            placeArrow(model.getComputer().getColor(), moveModel.getArrowModel().getDirection(), moveModel.getX(), moveModel.getY());
        }

        if(model.isEndgame()){
            view.signalEndgame(LanguageManager.getString("computer"));
            loadWonGames();
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

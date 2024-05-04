package org.danielsa.proiect_ps.controller.commands.game;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.model.ArrowModel;
import org.danielsa.proiect_ps.model.MoveModel;
import org.danielsa.proiect_ps.controller.GameController;
import org.danielsa.proiect_ps.utils.LanguageManager;

import java.io.File;

public class CommandUserRegisterMove {
    private final GameController controller;

    public CommandUserRegisterMove(GameController controller) {
        this.controller = controller;
    }

    public void execute(int row, int column) {
        boolean valid = controller.getModel().makeUserMove(new MoveModel(row, column, new ArrowModel(controller.getModel().getUserPlayer().getColor(), controller.getSelectedDirectionProperty().getValue())));

        if(!valid) {
            signalInvalidMove(LanguageManager.getString("invalidMove"));
            return;
        }

        if(controller.getSelectedDirectionProperty().getValue() == null){
            signalInvalidMove(LanguageManager.getString("directionNotSelected"));
            return;
        }

        placeArrow(controller.getModel().getUserPlayer().getColor(), controller.getSelectedDirectionProperty().getValue(), row, column);

        if(controller.getModel().isEndgame()) {
            signalEndgame(LanguageManager.getString("user"));
            return;
        }

        MoveModel moveModel = controller.getModel().getSystemMove();
        if (moveModel != null){
            placeArrow(controller.getModel().getComputer().getColor(), moveModel.getArrowModel().getDirection(), moveModel.getX(), moveModel.getY());
        }

        if(controller.getModel().isEndgame()){
            signalEndgame(LanguageManager.getString("computer"));
        }
    }

    private void placeArrow(String color, String direction, int row, int column){
        Image image = new Image(new File(Main.path + color + direction + ".png").toURI().toString());

        controller.getBoardProperty().getValue().getChildren().stream()
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
            winner = controller.getModel().getUser().getUserName();
        }
        dialogVbox.getChildren().add(new Text(winner.toUpperCase() + LanguageManager.getString("wins")));
        Scene dialogScene = new Scene(dialogVbox, 150, 100);
        dialogVbox.setOnMouseClicked(e -> {
            dialog.close();
            controller.clearBoard();
        });
        dialog.setScene(dialogScene);
        dialog.show();
        if (!winner.equalsIgnoreCase(LanguageManager.getString("computer"))){
            controller.getModel().updateUserScore();
        }
        controller.loadWonGames();
    }
}

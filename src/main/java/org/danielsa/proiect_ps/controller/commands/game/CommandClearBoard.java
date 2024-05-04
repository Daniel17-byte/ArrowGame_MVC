package org.danielsa.proiect_ps.controller.commands.game;

import eu.hansolo.tilesfx.Command;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.controller.GameController;

import java.io.File;

public class CommandClearBoard implements Command {
    private final GameController controller;

    public CommandClearBoard(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        if(controller.getBoardProperty().getValue() != null){
            controller.getModel().clearBoard();
            controller.getBoardProperty().getValue().getChildren().stream()
                    .filter(node -> node instanceof ImageView)
                    .map(node -> (ImageView) node)
                    .forEach(imageView -> imageView.setImage(new Image(new File(Main.path + "img.png").toURI().toString())));
        }
        if(controller.getLevelSelectChoiceBoxProperty().getValue().equals("4x4")){
            controller.getBoardProperty().setValue(controller.getGridSmallBoardProperty().getValue());
        }else {
            controller.getBoardProperty().setValue(controller.getGridLargeBoardProperty().getValue());
        }
    }
}

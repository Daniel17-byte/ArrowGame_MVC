package org.danielsa.proiect_ps.controller.commands.game;

import eu.hansolo.tilesfx.Command;
import org.danielsa.proiect_ps.controller.GameController;

public class CommandStartGame implements Command {
    private final GameController controller;

    public CommandStartGame(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.clearBoard();
        String color = "g";
        if(!controller.getModel().getUserPlayer().getColor().equals(color)) {
            controller.getModel().getComputer().setColor(controller.getModel().getUserPlayer().getColor());
            controller.getModel().getUserPlayer().setColor(color);
            controller.getModel().changePlayerColor(controller.getModel().getUserPlayer(), color);
        }

        String selectedBoard = controller.getLevelSelectChoiceBoxProperty().getValue();
        if(selectedBoard.equals("4x4")) {
            controller.getBoardProperty().setValue(controller.getGridSmallBoardProperty().getValue());
            controller.getModel().changeBoardSize(4);
            adjustInterButtons(false);
        } else {
            controller.getBoardProperty().setValue(controller.getGridLargeBoardProperty().getValue());
            controller.getModel().changeBoardSize(8);
            adjustInterButtons(true);
        }

    }

    private void adjustInterButtons(boolean isVisible) {
        controller.getButtonsProperty().getValue().get("nE").setVisible(isVisible);
        controller.getButtonsProperty().getValue().get("nW").setVisible(isVisible);
        controller.getButtonsProperty().getValue().get("sE").setVisible(isVisible);
        controller.getButtonsProperty().getValue().get("sW").setVisible(isVisible);
    }
}

package org.danielsa.proiect_ps.model;

import lombok.Getter;
import org.danielsa.proiect_ps.utils.DatabaseService;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.view.ObserverGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameModel implements SubjectGame {
    @Getter
    private final ComputerModel computer;
    @Getter
    private final PlayerModel player;
    private GameBoardModel board;
    private final Stack<MoveModel> moveModelStack;
    private final DatabaseService databaseService;
    private final List<ObserverGame> observerGames = new ArrayList<>();

    public GameModel() {
        this.databaseService = Main.context.getBean(DatabaseService.class);
        this.computer = new ComputerModel("r");
        this.player = new PlayerModel("g");
        this.board = new GameBoardModel(8);
        this.moveModelStack = new Stack<>();
    }

    public boolean makeUserMove(MoveModel moveModel) {
        if (this.board.makeMove(moveModel)) {
            moveModelStack.push(moveModel);
            return true;
        }
        notifyObservers(false, "MOVE");
        return false;
    }

    public MoveModel getSystemMove() {
        MoveModel moveModel = computer.makeMove(new GameBoardModel(this.board));
        if (moveModel != null) {
            moveModelStack.push(moveModel);
            board.makeMove(moveModel);
            return moveModel;
        }
        return null;
    }

    public MoveModel undo() {
        if(moveModelStack.isEmpty()) return null;
        MoveModel moveModel = moveModelStack.pop();
        this.board.undoMove(moveModel);
        return moveModel;
    }

    public boolean isEndgame() {
        boolean success = this.board.noValidMoves() == 0;
        notifyObservers(success, "ENDGAME");
        return success;
    }

    public void clearBoard() {
        this.board.clearBoard();
        moveModelStack.clear();
    }

    public void changeBoardSize(int size) {
        if(this.board.getSize() == size)
            return;
        this.board = new GameBoardModel(size);
    }

    public UserModel getUser() {
        return databaseService.getUser();
    }

    public void updateUserScore() {
        databaseService.updateUserScore();
    }

    public ArrayList<UserModel> getUsers() {
        return databaseService.getUsers();
    }

    @Override
    public void attach(ObserverGame o) {
        observerGames.add(o);
    }

    @Override
    public void detach(ObserverGame o) {
        observerGames.remove(o);
    }

    @Override
    public void notifyObservers(boolean success, String op) {
        for (ObserverGame observerGame : observerGames) {
            observerGame.update(success, op);
        }
    }
}

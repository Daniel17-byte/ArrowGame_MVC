package org.danielsa.proiect_ps.model;

import lombok.Getter;
import org.danielsa.proiect_ps.utils.DatabaseService;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.view.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameModel implements Subject {
    @Getter
    private final ComputerModel computer;
    @Getter
    private final PlayerModel player;
    private GameBoardModel board;
    private final Stack<MoveModel> moveModelStack;
    private final DatabaseService databaseService;
    private final List<Observer> observers = new ArrayList<>();

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
            notifyObservers(true);
            return true;
        }
        return false;
    }

    public MoveModel getSystemMove() {
        MoveModel moveModel = computer.makeMove(new GameBoardModel(this.board));
        if (moveModel != null) {
            moveModelStack.push(moveModel);
            board.makeMove(moveModel);
            notifyObservers(true);
            return moveModel;
        }
        return null;
    }

    public MoveModel undo() {
        if(moveModelStack.isEmpty()) return null;
        MoveModel moveModel = moveModelStack.pop();
        this.board.undoMove(moveModel);
        notifyObservers(true);
        return moveModel;
    }

    public boolean isEndgame() {
        boolean success = this.board.noValidMoves() == 0;
        notifyObservers(success);
        return success;
    }

    public void clearBoard() {
        this.board.clearBoard();
        moveModelStack.clear();
        notifyObservers(true);
    }

    public void changeBoardSize(int size) {
        if(this.board.getSize() == size)
            return;
        this.board = new GameBoardModel(size);
        notifyObservers(true);
    }

    public UserModel getUser() {
        UserModel user = databaseService.getUser();
        boolean success = user != null;
        notifyObservers(success);
        return user;
    }

    public void updateUserScore() {
        databaseService.updateUserScore();
        notifyObservers(true);
    }

    public ArrayList<UserModel> getUsers() {
        ArrayList<UserModel> users = databaseService.getUsers();
        boolean success = !users.isEmpty();
        notifyObservers(success);
        return users;
    }

    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(boolean success) {
        for (Observer observer : observers) {
            observer.update(success);
        }
    }
}

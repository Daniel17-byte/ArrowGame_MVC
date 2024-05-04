package org.danielsa.proiect_ps.model;

import org.danielsa.proiect_ps.DatabaseService;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.view.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameModel implements GameModelInterface, Subject {
    private final ComputerPlayerModel computer;
    private final UserPlayerModel player;
    private GameBoardInterface board;
    private final Stack<MoveModel> moveModelStack;
    private final DatabaseService databaseService;
    private final List<Observer> observers = new ArrayList<>();

    public GameModel() {
        this.databaseService = Main.context.getBean(DatabaseService.class);
        this.computer = new ComputerPlayerModel("r");
        this.player = new UserPlayerModel("g");
        this.board = new GameBoardModel(8);
        this.moveModelStack = new Stack<>();
    }

    public boolean makeUserMove(MoveModel moveModel) {
        if (this.board.makeMove(moveModel)) {
            moveModelStack.push(moveModel);
            return true;
        }
        return false;
    }

    @Override
    public void changePlayerColor(UserPlayerModel player, String color) {
        if (!player.getColor().equals(color)) {
            computer.setColor(player.getColor());
            player.setColor(color);
        }
    }

    @Override
    public MoveModel getSystemMove() {
        MoveModel moveModel = computer.makeMove(new GameBoardModel((GameBoardModel) (this.board)));
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

    @Override
    public UserPlayerModel getUserPlayer() {
        return this.player;
    }

    @Override
    public ComputerPlayerModel getComputer() {
        return this.computer;
    }

    @Override
    public boolean isEndgame() {
        return this.board.noValidMoves() == 0;
    }

    @Override
    public void clearBoard() {
        this.board.clearBoard();
        moveModelStack.clear();
    }

    public void changeBoardSize(int size) {
        if(this.board.getSize() == size)
            return;
        this.board = new GameBoardModel(size);
    }

    @Override
    public UserModel getUser() {
        return databaseService.getUser();
    }

    @Override
    public void updateUserScore() {
        databaseService.updateUserScore();
    }

    @Override
    public ArrayList<UserModel> getUsers() {
        return databaseService.getUsers();
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
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}

package org.danielsa.proiect_ps.model;

import lombok.Setter;
import org.danielsa.proiect_ps.view.Observer;

import java.util.ArrayList;
import java.util.List;

@Setter
public class ComputerPlayerModel extends UserPlayerModel implements Subject {
    private MinMaxStrategy strategy;
    private final List<Observer> observers = new ArrayList<>();

    public ComputerPlayerModel(String color) {
        super(color);
        this.strategy = new MinMaxStrategy(8,16);
    }

    public MoveModel makeMove(GameBoardModel board) {
        return strategy.makeMove(board);
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

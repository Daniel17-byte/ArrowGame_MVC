package org.danielsa.proiect_ps.model;

import lombok.Getter;
import org.danielsa.proiect_ps.view.Observer;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MoveModel implements Subject {
    private final int x;
    private final int y;
    private final ArrowModel arrowModel;
    private final List<Observer> observers = new ArrayList<>();

    public MoveModel(int x, int y, ArrowModel arrowModel) {
        this.x = x;
        this.y = y;
        this.arrowModel = arrowModel;
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

package org.danielsa.proiect_ps.model;

import lombok.*;
import org.danielsa.proiect_ps.view.Observer;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
public class ArrowModel implements Subject {
    @Setter
    private String color;
    private final String direction;
    private final List<Observer> observers = new ArrayList<>();

    public ArrowModel(String color, String direction) {
        this.color = color;
        this.direction = direction;
    }
    public ArrowModel(String direction) {
        this.direction = direction;
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

package org.danielsa.proiect_ps.model;

import lombok.Getter;
import lombok.Setter;
import org.danielsa.proiect_ps.view.Observer;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class PlayerModel implements Subject {
    private String color;
    private UserModel user;
    private final List<Observer> observers = new ArrayList<>();

    public PlayerModel(String color) {
        this.color = color;
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

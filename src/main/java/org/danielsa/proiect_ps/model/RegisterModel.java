package org.danielsa.proiect_ps.model;

import org.danielsa.proiect_ps.utils.DatabaseService;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.view.Observer;

import java.util.ArrayList;
import java.util.List;

public class RegisterModel implements Subject {
    private final DatabaseService databaseService;
    private final List<Observer> observers = new ArrayList<>();

    public RegisterModel() {
        this.databaseService = Main.context.getBean(DatabaseService.class);
    }

    public boolean register(String username, String password, String usertype) {
        return databaseService.register(username, password, usertype);
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

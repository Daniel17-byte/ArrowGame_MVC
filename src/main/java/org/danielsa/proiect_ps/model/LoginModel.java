package org.danielsa.proiect_ps.model;

import org.danielsa.proiect_ps.DatabaseService;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.view.Observer;

import java.util.ArrayList;
import java.util.List;

public class LoginModel implements LoginModelInterface, Subject {
    private final DatabaseService databaseService;
    private final List<Observer> observers = new ArrayList<>();

    public LoginModel() {
        this.databaseService = Main.context.getBean(DatabaseService.class);
    }

    @Override
    public boolean authenticate(String username, String password) {
        return databaseService.authenticate(username, password);
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

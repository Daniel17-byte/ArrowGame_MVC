package org.danielsa.proiect_ps.model;

import org.danielsa.proiect_ps.utils.DatabaseService;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.view.Observer;

import java.util.ArrayList;
import java.util.List;

public class LoginModel implements Subject {
    private final DatabaseService databaseService;
    private final List<Observer> observers = new ArrayList<>();

    public LoginModel() {
        this.databaseService = Main.context.getBean(DatabaseService.class);
    }

    public boolean authenticate(String username, String password) {
        boolean success = databaseService.authenticate(username, password);
        notifyObservers(success);
        return success;
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

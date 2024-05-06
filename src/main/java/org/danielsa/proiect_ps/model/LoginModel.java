package org.danielsa.proiect_ps.model;

import org.danielsa.proiect_ps.utils.DatabaseService;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.view.ObserverAuthenticate;

import java.util.ArrayList;
import java.util.List;

public class LoginModel implements SubjectAuthenticate {
    private final DatabaseService databaseService;
    private final List<ObserverAuthenticate> observerAuthenticates = new ArrayList<>();

    public LoginModel() {
        this.databaseService = Main.context.getBean(DatabaseService.class);
    }

    public boolean authenticate(String username, String password) {
        boolean success = databaseService.authenticate(username, password);
        notifyObservers(success);
        return success;
    }

    @Override
    public void attach(ObserverAuthenticate o) {
        observerAuthenticates.add(o);
    }

    @Override
    public void detach(ObserverAuthenticate o) {
        observerAuthenticates.remove(o);
    }

    @Override
    public void notifyObservers(boolean success) {
        for (ObserverAuthenticate observerAuthenticate : observerAuthenticates) {
            observerAuthenticate.update(success);
        }
    }
}

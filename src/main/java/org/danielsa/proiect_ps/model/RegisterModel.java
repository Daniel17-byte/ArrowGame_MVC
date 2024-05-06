package org.danielsa.proiect_ps.model;

import org.danielsa.proiect_ps.utils.DatabaseService;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.view.ObserverAuthenticate;

import java.util.ArrayList;
import java.util.List;

public class RegisterModel implements SubjectAuthenticate {
    private final DatabaseService databaseService;
    private final List<ObserverAuthenticate> observerAuthenticates = new ArrayList<>();

    public RegisterModel() {
        this.databaseService = Main.context.getBean(DatabaseService.class);
    }

    public boolean register(String username, String password, String usertype) {
        boolean success = databaseService.register(username, password, usertype);
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

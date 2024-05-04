package org.danielsa.proiect_ps.model;

import org.danielsa.proiect_ps.utils.DatabaseService;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.view.Observer;

import java.util.ArrayList;
import java.util.List;

public class AdminModel implements AdminModelInterface, Subject {
    private final DatabaseService databaseService;

    private final List<Observer> observers = new ArrayList<>();

    public AdminModel() {
        this.databaseService = Main.context.getBean(DatabaseService.class);
    }

    @Override
    public ArrayList<UserModel> getUsers() {
        return databaseService.getUsers();
    }

    @Override
    public UserModel updateUser(String username, String newUsername, String newPassword, String newUserType) {
        return databaseService.updateUser(username, newUsername, newPassword, newUserType);
    }

    @Override
    public boolean deleteUser(String username) {
        return databaseService.deleteUser(username);
    }

    @Override
    public UserModel getUserByUsername(String username) {
        return databaseService.getUserByUsername(username);
    }

    @Override
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

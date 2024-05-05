package org.danielsa.proiect_ps.model;

import org.danielsa.proiect_ps.utils.DatabaseService;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.view.Observer;

import java.util.ArrayList;
import java.util.List;

public class AdminModel implements Subject {
    private final DatabaseService databaseService;

    private final List<Observer> observers = new ArrayList<>();

    public AdminModel() {
        this.databaseService = Main.context.getBean(DatabaseService.class);
    }

    public ArrayList<UserModel> getUsers() {
        ArrayList<UserModel> users = databaseService.getUsers();
        boolean success = !users.isEmpty();
        notifyObservers(success);
        return users;
    }

    public UserModel updateUser(String username, String newUsername, String newPassword, String newUserType) {
        UserModel user = databaseService.updateUser(username, newUsername, newPassword, newUserType);
        boolean success = user.getUserName().equals(newUsername);
        notifyObservers(success);
        return user;
    }

    public boolean deleteUser(String username) {
        boolean success = databaseService.deleteUser(username);
        notifyObservers(success);
        return success;
    }

    public UserModel getUserByUsername(String username) {
        UserModel user = databaseService.getUserByUsername(username);
        boolean success = user.getUserName().equals(username);
        notifyObservers(success);
        return user;
    }

    public boolean register(String username, String password, String usertype) {
        boolean success = databaseService.register(username, password, usertype);
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

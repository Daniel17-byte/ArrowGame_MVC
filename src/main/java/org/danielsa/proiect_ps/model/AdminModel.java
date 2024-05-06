package org.danielsa.proiect_ps.model;

import org.danielsa.proiect_ps.utils.DatabaseService;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.view.ObserverAdmin;

import java.util.ArrayList;
import java.util.List;

public class AdminModel implements SubjectAdmin {
    private final DatabaseService databaseService;

    private final List<ObserverAdmin> observerAdmins = new ArrayList<>();

    public AdminModel() {
        this.databaseService = Main.context.getBean(DatabaseService.class);
    }

    public ArrayList<UserModel> getUsers() {
        return databaseService.getUsers();
    }

    public UserModel updateUser(String username, String newUsername, String newPassword, String newUserType) {
        UserModel user = databaseService.updateUser(username, newUsername, newPassword, newUserType);
        boolean success = user != null && user.getUserName().equals(newUsername);
        notifyObservers(success, "UPDATE");
        return user;
    }

    public boolean deleteUser(String username) {
        boolean success = databaseService.deleteUser(username);
        notifyObservers(success, "DELETE");
        return success;
    }

    public UserModel getUserByUsername(String username) {
        return databaseService.getUserByUsername(username);
    }

    public boolean register(String username, String password, String usertype) {
        boolean success = databaseService.register(username, password, usertype);
        notifyObservers(success, "ADD");
        return success;
    }

    @Override
    public void attach(ObserverAdmin o) {
        observerAdmins.add(o);
    }

    @Override
    public void detach(ObserverAdmin o) {
        observerAdmins.remove(o);
    }

    @Override
    public void notifyObservers(boolean success, String op) {
        for (ObserverAdmin observer : observerAdmins) {
            observer.update(success, op);
        }
    }
}

package org.danielsa.proiect_ps.controller.commands.admin;

import org.danielsa.proiect_ps.model.UserModel;
import org.danielsa.proiect_ps.controller.AdminController;

import java.util.ArrayList;

public class CommandGetUsers {
    private final AdminController controller;

    public CommandGetUsers(AdminController controller) {
        this.controller = controller;
    }

    public ArrayList<UserModel> execute() {
        ArrayList<UserModel> users = controller.getModel().getUsers();

        if (users.isEmpty()){
            System.out.println("Couldn't load users!");
            return null;
        }

        return users;
    }
}

package org.danielsa.proiect_ps.controller.commands.admin;

import org.danielsa.proiect_ps.model.UserModel;
import org.danielsa.proiect_ps.controller.AdminController;

import java.util.ArrayList;

public class CommandGetUsers {
    private final AdminController viewModel;

    public CommandGetUsers(AdminController viewModel) {
        this.viewModel = viewModel;
    }

    public ArrayList<UserModel> execute() {
        ArrayList<UserModel> users = viewModel.getModel().getUsers();

        if (users.isEmpty()){
            System.out.println("Couldn't load users!");
            return null;
        }

        return users;
    }
}

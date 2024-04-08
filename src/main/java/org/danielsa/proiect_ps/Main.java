package org.danielsa.proiect_ps;

import jakarta.inject.Inject;
import javafx.application.Application;
import javafx.stage.Stage;
import org.danielsa.proiect_ps.view.LoginViewInterface;
import org.danielsa.proiect_ps.view.LoginView;

public class Main extends Application {
    @Inject
    private final DatabaseService databaseService = new DatabaseService();

    @SuppressWarnings("CastCanBeRemovedNarrowingVariableType")
    @Override
    public void start(Stage primaryStage) {
        LoginViewInterface view = new LoginView(databaseService);

        primaryStage.setScene((LoginView) view);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

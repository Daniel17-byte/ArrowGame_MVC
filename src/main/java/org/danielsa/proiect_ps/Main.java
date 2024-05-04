package org.danielsa.proiect_ps;

import javafx.application.Application;
import javafx.stage.Stage;
import org.danielsa.proiect_ps.utils.CustomLocale;
import org.danielsa.proiect_ps.utils.LanguageManager;
import org.danielsa.proiect_ps.view.LoginView;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

public class Main extends Application {
    public static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    public static String path = context.getBean("myProperties", Properties.class).getProperty("path");

    @Override
    public void start(Stage primaryStage) {
        LanguageManager.loadLanguage(CustomLocale.ITALIAN);

        LoginView view = new LoginView();

        primaryStage.setScene(view);
        primaryStage.setTitle(LanguageManager.getString("loginButton"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

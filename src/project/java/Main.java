package project.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.java.datamodel.Position;
import project.java.datamodel.Roads;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.*;

public class Main extends Application {
    public static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void start(Stage primaryStage) throws Exception{
        setupLogger();
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Java 2021");
        primaryStage.setScene(new Scene(root, 1000, 1000));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void setupLogger(){
        logger.setLevel(Level.ALL);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.SEVERE);
        logger.addHandler(ch);
        try{
            FileHandler fh = new FileHandler("logger.log", true);
            fh.setFormatter(new SimpleFormatter());
            fh.setLevel(Level.FINE);
            logger.addHandler(fh);
        }catch (IOException e){
            logger.log(Level.SEVERE, "FileHandler error", e);
        }

    }
}

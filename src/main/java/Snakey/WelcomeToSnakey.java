package Snakey;

import Snakey.Controller.MusicPlayer;
import Snakey.View.ViewManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

import static Snakey.View.ViewManager.getMainStage;

public class WelcomeToSnakey extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        ViewManager manager = new ViewManager();
        primaryStage = getMainStage();
        MusicPlayer.GetMusicPlay("src/main/resources/music/Feel-Good.mp3");
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
                getMainStage().close();
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
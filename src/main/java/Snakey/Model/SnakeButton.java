package Snakey.Model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.text.Font;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;

public class SnakeButton extends Button {

    private final String FONT_PATH = "src/main/resources/image/kenvector_future.ttf";
    private final String BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('yellow_button04.png');";
    private final String BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('grey_button03.png');";

    private MediaPlayer mediaPlayer;

    public SnakeButton(String name) {
        setText(name);
        setButtonFont();
        setPrefHeight(45);
        setPrefWidth(190);
        setStyle(BUTTON_FREE);
        initializeButtonListeners();
    }

    private void setButtonFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana",23));
        }
    }

    private void setBUTTON_PRESSED(){
        setStyle(BUTTON_PRESSED);
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);
    }

    private void setBUTTON_FREE(){
        setStyle(BUTTON_FREE);
        setPrefHeight(45);
        setLayoutY(getLayoutY() - 4);
    }

//    public void playSound() {
//        try {
//            String soundPath = "src/main/resources/music/clicksoundeffect.mp3";
//            Media sound = new Media(new File(soundPath).toURI().toString());
//            MediaPlayer mediaPlayer = new MediaPlayer(sound);
//            mediaPlayer.play();
//        } catch (Exception e) {
//            e.printStackTrace(); // Handle exceptions
//        }
//    }

    private void initializeButtonListeners(){
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
//                playSound();
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    setBUTTON_PRESSED();
                    if ( mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.play();
                    }
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    setBUTTON_FREE();
                }
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(new DropShadow());
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(null);
            }
        });
    }
}

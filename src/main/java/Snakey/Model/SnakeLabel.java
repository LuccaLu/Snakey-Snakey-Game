package Snakey.Model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SnakeLabel extends Label {
    public final static String FONT_PATH = "src/main/resources/image/kenvector_future.ttf";

    public final static String BACKGROUND_IMAGE = "yellow_button03.png";
    public SnakeLabel(String name){
        setPrefWidth(380);
        setPrefHeight(50);
        setText(name);
        setWrapText(true);
        setLabelFont();
        setAlignment(Pos.CENTER);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(
                BACKGROUND_IMAGE,380,50, false,true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        setBackground(new Background(backgroundImage));

    }

    private void setLabelFont(){
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 23));
        }
    }
}

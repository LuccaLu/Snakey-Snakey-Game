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
import java.security.PrivateKey;

public class InfoLabel extends Label {

    private final static String FONT_PATH = "src/main/resources/image/kenvector_future.ttf";


    public InfoLabel(String name){
        setPrefWidth(130);
        setPrefHeight(50);

        BackgroundImage backgroundImage = new BackgroundImage(new Image("green_button13.png", 130,50,false,true),
                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10,10,10,10));
        setFont();
        setText(name);
    }

    private void setFont(){
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),15));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 15));
        }
    }
}

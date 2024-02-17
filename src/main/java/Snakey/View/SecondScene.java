package Snakey.View;

import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.scene.layout.AnchorPane;

public class SecondScene extends SubScene {

    private final String FONT_PATH = "src/main/resources/image/kenvector_future.ttf";
    private final static String BACKGROUND_IMAGE = "grey_panel.png";

    private boolean isHidden;

    public SecondScene() {
        super(new AnchorPane(),800,400);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE,800, 400,false,true),
                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

        AnchorPane root = (AnchorPane)this.getRoot();
        root.setBackground(new Background(image));

        isHidden = true;

        setLayoutX(1024);
        setLayoutY(180);
    }

    public void moveToSecScene(){
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.2));
        transition.setNode(this);

        if (isHidden) {
            transition.setToX(-900);
            isHidden = false;
        } else{
            transition.setToX(0);
            isHidden = true;
        }
        transition.play();
    }

    public AnchorPane getPane(){
        return (AnchorPane) this.getRoot();
    }
}

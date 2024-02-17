package Snakey.Controller;

import Snakey.Model.SNAKE;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class SelectController extends VBox {

    private ImageView selectImage;
    private ImageView snakeImage;

    private String notSelected = "grey_circle.png";
    private String selected = "yellow_boxTick.png";

    private SNAKE snake;

    private boolean isSelected;

    public SelectController(SNAKE snake){
        selectImage = new ImageView(notSelected);
        snakeImage = new ImageView(snake.getUrl());

        snakeImage.setFitHeight(100);
        snakeImage.setFitWidth(100);
        snakeImage.setPreserveRatio(true);

        this.snake = snake;
        isSelected = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(50);
        this.getChildren().addAll(selectImage,snakeImage);
    }

    public SNAKE getSnake(){
        return snake;
    }

    public boolean getIsSelected(){
        return isSelected;
    }

    public void setIsSelected(boolean isSelected){
        this.isSelected = isSelected;
        String imageSelected = this.isSelected ? selected: notSelected;
        selectImage.setImage(new Image(imageSelected));
    }

}

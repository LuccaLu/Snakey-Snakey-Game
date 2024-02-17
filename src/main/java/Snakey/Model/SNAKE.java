package Snakey.Model;

public enum SNAKE {
    WHITE("1.png", "1.png"),
    BLUE("2.png", "1.png"),
    BLACK("3.png", "1.png");

    private String urlSnake;
    private String snakeLife;

    private SNAKE(String urlSnake, String snakeLife){
        this.urlSnake = urlSnake;
        this.snakeLife = snakeLife;
    }

    public String getUrl(){
        return this.urlSnake;
    }

    public String getSnakeLife(){
        return snakeLife;
    }
}

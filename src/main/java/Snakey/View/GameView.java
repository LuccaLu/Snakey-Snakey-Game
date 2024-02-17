package Snakey.View;
import Snakey.Model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import static Snakey.View.ViewManager.getMainStage;


public class GameView extends SnakeObject {
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 750;

    private Stage menuStage;

    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    private static final int SQUARE_SIZE = 25;
    private static final int COLUMNS = WINDOW_HEIGHT / SQUARE_SIZE;
    private static final int ROWS = WINDOW_WIDTH / SQUARE_SIZE;

    private static final String[] FOODS_IMAGE = new String[]{"food-apple.png", "food-blueberry.png", "food-banana.png",
            "food-cherry.png", "food-durian.png", "food-grape.png", "food-grapefruit.png", "food-lemon.png", "food-mango.png",
            "food-kiwi.png", "food-litchi.png", "food-peach.png", "food-pear.png", "food-orange.png", "food-pineapple.png",
            "food-pitaya.png", "food-strawberry.png", "food-watermelon.png"};

    private GraphicsContext gc;
    private List<Point> snakeBody = new ArrayList();
    private Image originalSnakeHeadImage =  getSelectedHead();

    private Image snakeHeadImage = getSelectedHead();

    private Image snakeBodyImage = new Image("snake-body.png");

    private Point snakeHead;
    private Image foodImage;
    private int foodX;
    private int foodY;
    private int currentDirection;
    private boolean gameOver;
    private int score = 0;
    private InfoLabel infoLabel;
    private ImageView[] snakeLifes;
    private int snakeLife = 3;

    private boolean gamePaused;

    public final static String FONT_PATH = "src/main/resources/image/kenvector_future.ttf";

    private SnakeButton gameMenu = new SnakeButton("MENU");
    private SnakeButton gamePause = new SnakeButton("PAUSE");
    private SnakeButton gameReplay = new SnakeButton("REPLAY");



    public GameView() {
        intializeStage();
        setKeyListeners();
        Draw(gc);
        gameStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
                getMainStage().close();
            }
        });
    }

    public Image getSelectedHead(){
        ImageView selectedHead = new ImageView(ViewManager.getSelectedSnake().getUrl());
        selectedHead.setFitWidth(30);
        selectedHead.setFitHeight(30);

        SnapshotParameters params = new SnapshotParameters();
        Image snakeHeadImage = selectedHead.snapshot(params, null);
        snakeHeadImage = rotatedImage(snakeHeadImage,90);

        return snakeHeadImage;
    }
    private void setKeyListeners() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode code = keyEvent.getCode();
                if (code == KeyCode.RIGHT || code == KeyCode.D) {
                    if (currentDirection != LEFT) {
                        currentDirection = RIGHT;
//                        snakeHeadImage.setRotate(-20);
                        snakeHeadImage = rotatedImage(originalSnakeHeadImage, 0);
                    }
                } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                    if (currentDirection != RIGHT) {
                        currentDirection = LEFT;
//                        snakeHeadImage.setRotate(20);
                        snakeHeadImage = rotatedImage(originalSnakeHeadImage, 180);
                    }
                } else if (code == KeyCode.UP || code == KeyCode.W) {
                    if (currentDirection != DOWN) {
                        currentDirection = UP;
//                        snakeHeadImage.setRotate(-15);
                        snakeHeadImage = rotatedImage(originalSnakeHeadImage, -90);

                    }
                } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                    if (currentDirection != UP) {
                        currentDirection = DOWN;
//                        snakeHeadImage.setRotate(15);
                        snakeHeadImage = rotatedImage(originalSnakeHeadImage, 90);
                    }
                } else if (code == KeyCode.SPACE) {
                    setGameMenuButton(gameMenu);
                    setPauseButton(gamePause);
                    setReplayButton(gameReplay);
                    togglePause();
                }
            }
        });
    }

    private void setGameMenuButton(SnakeButton button){
        button.setLayoutX(2);
        button.setLayoutY(2);
        gamePane.getChildren().add(button);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameStage.close();
                menuStage.show();
            }
        });
    }

    private void setPauseButton(SnakeButton button){
        button.setLayoutX(200);
        button.setLayoutY(2);
        gamePane.getChildren().add(button);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                togglePause();
            }
        });
    }

    private void setReplayButton(SnakeButton button){
        button.setLayoutX(400);
        button.setLayoutY(2);
        gamePane.getChildren().add(button);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (snakeLife < 0 ){
                    gc.setFill(Color.RED);
                    gc.setFont(new Font(FONT_PATH, 80));
                    gc.fillText("All your lives are gone!", WINDOW_WIDTH / 8.5, WINDOW_HEIGHT / 2);
                    System.out.println("All your lives are gone!");
                } else{
                    rePlayGame();
                }
            }
        });
    }

    private void togglePause() {
        setGamePaused(!isGamePaused());
        if(isGamePaused()){
            gamePause.setText("UNPAUSE");
            gc.setFill(Color.RED);
            gc.setFont(new Font(FONT_PATH, 80));
            gc.fillText("Game is Paused!", WINDOW_WIDTH / 4, WINDOW_HEIGHT / 2);
        }else{
            gamePause.setText("PAUSE");
        }
    }

    public boolean isGamePaused(){
        return gamePaused;
    }

    private void setGamePaused(boolean gamePaused){
        this.gamePaused = gamePaused;
    }


    public Image rotatedImage(Image originalImage, double angle) {
        ImageView imageView = new ImageView(originalImage);

        Rotate rotate = new Rotate(angle, originalImage.getWidth() / 2, originalImage.getHeight() / 2);
        imageView.getTransforms().add(rotate);

        SnapshotParameters params = new SnapshotParameters();
        Image rotatedImage = imageView.snapshot(params, null);

        return rotatedImage;
    }

    public void Draw(GraphicsContext gc) {
        for (int i = 0; i < 1; i++) {
            snakeBody.add(new Point(5, ROWS / 2));
        }
        snakeHead = snakeBody.get(0);
        generateFood();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(130), e -> run(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    private void run(GraphicsContext gc) {
        if (!gamePaused){
            if (gameOver) {
                gc.setFill(Color.RED);
                gc.setFont(new Font(FONT_PATH, 80));
                gc.fillText("Game Over!", WINDOW_WIDTH / 3.5, WINDOW_HEIGHT / 2);
                gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        KeyCode code = keyEvent.getCode();
                        if (code == KeyCode.SPACE) {
                            gameStage.close();
                            menuStage.show();
                        }
                    }
                });
                return;
            }
        drawBackground(gc);
        drawFood(gc);
        drawSnake(gc);
        drawScore();

        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }

        switch (currentDirection) {
            case RIGHT:
                moveRight();
                break;
            case LEFT:
                moveLeft();
                break;
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
        }

        gameOver();
        eatFood();
    }}

    private void drawBackground(GraphicsContext gc) {
        Image backgroundImage = new Image("cute-snake.png");
        gc.drawImage(backgroundImage, 0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }


    private void generateFood() {
        start:
        while (true) {
            foodX = (int) (Math.random() * ROWS);
            foodY = (int) (Math.random() * COLUMNS);

            if (foodX < 0 || foodX >= ROWS || foodY < 0 || foodY >= COLUMNS) {
                System.out.println("Food coordinates are out of bounds!");
            }

            for (Point snake : snakeBody) {
                if (snake.getX() == foodX && snake.getY() == foodY) {
                    continue start;
                }
            }

            foodImage = new Image(FOODS_IMAGE[(int) (Math.random() * FOODS_IMAGE.length)]);
            if (foodImage.isError()) {
                System.out.println("Food is error!");
            }
            if (foodImage == null) {
                System.out.println("Food is not there!");
            }
            break;
        }
    }

    private void drawFood(GraphicsContext gc) {
        if (foodImage.isError()) {
            System.out.println("Food is error!");
        }
        if (foodImage == null) {
            System.out.println("Food is not there!");
        }
        if (foodX < 0 || foodX >= ROWS || foodY < 0 || foodY >= COLUMNS) {
            System.out.println("Food coordinates are out of bounds!");
        }
        gc.drawImage(foodImage, foodX * SQUARE_SIZE, foodY * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    private void drawSnake(GraphicsContext gc) {
        gc.drawImage(snakeHeadImage, snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE);
        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeBodyImage != null) {
                gc.drawImage(snakeBodyImage, snakeBody.get(i).getX() * SQUARE_SIZE, snakeBody.get(i).getY() * SQUARE_SIZE);
            }
        }
    }


    private void moveRight() {
        snakeHead.x++;
    }

    private void moveLeft() {
        snakeHead.x--;
    }

    private void moveUp() {
        snakeHead.y--;
    }

    private void moveDown() {
        snakeHead.y++;
    }

    public void gameOver() {
        if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x * SQUARE_SIZE >= WINDOW_WIDTH || snakeHead.y * SQUARE_SIZE >= WINDOW_HEIGHT) {
            removeOneLife();
            if (snakeLife >= 0) {
                gamePane.getChildren().remove(snakeLifes[snakeLife]);
                rePlayGame();
            } else{
                gameOver = true;
            }
        }
        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.x == snakeBody.get(i).getX() && snakeHead.getY() == snakeBody.get(i).getY()) {
                removeOneLife();
                if (snakeLife >= 0) {
                    gamePane.getChildren().remove(snakeLifes[snakeLife]);
                    rePlayGame();
                } else{
                    gameOver = true;
                }
                break;
                }
            }
    }

    private void eatFood() {
        if (snakeHead.getX() == foodX && snakeHead.getY() == foodY) {
            snakeBody.add(new Point(-1, -1));
            generateFood();
            score += 5;
        }
    }

    private void drawScore() {
        infoLabel = new InfoLabel("Score: " + score);
        infoLabel.setLayoutX(850);
        infoLabel.setLayoutY(20);
        gamePane.getChildren().add(infoLabel);
    }


    private void intializeStage() {
        gamePane = new AnchorPane();
        gameStage = new Stage();
        Group root = new Group();
        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        gamePane.getChildren().add(canvas);
        root.getChildren().add(gamePane);
        gameScene = new Scene(root);
        gameStage.setScene(gameScene);
        gc = canvas.getGraphicsContext2D();
    }


    public void setGame(Stage menuStage, SNAKE selectedSnake) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        gameStage.setTitle("Snakey Snakey");
        setLife(selectedSnake);
        gameStage.show();
    }


    private void setLife(SNAKE selectedSnake) {
        snakeLifes = new ImageView[3];
        for (int i = 0; i < snakeLifes.length; i++) {
            snakeLifes[i] = new ImageView(selectedSnake.getUrl());
            snakeLifes[i].setFitWidth(30);
            snakeLifes[i].setFitHeight(30);
            snakeLifes[i].setLayoutX(850 + (i * 50));
            snakeLifes[i].setLayoutY(80);
            gamePane.getChildren().add(snakeLifes[i]);
        }
    }

    public int getSnakeLife(){
        return snakeLife;
    }

    public void removeOneLife() {
        snakeLife--;
        if (snakeLife >= 0 && snakeLife < snakeLifes.length) {
            gc.setFill(Color.RED);
            gc.setFont(new Font(FONT_PATH, 80));
            gc.fillText("Lives Remaining " + snakeLife, WINDOW_WIDTH / 4, WINDOW_HEIGHT / 2);
            System.out.println("Lives Remaining " + snakeLife);
        } else {
            System.out.println("Game Over!");
            gameOver = true;
        }
    }

    public void rePlayGame(){
        snakeBody.clear();
        setKeyListeners();
        Draw(gc);
    }

//    public static void startCountdown() {
//        final int[] countdownTime = {3};
//
//        Timeline countdown = new Timeline();
//        countdown.setCycleCount(3);
//        countdown.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
//            if (countdownTime[0] > 0) {
//                countdownLabel.setText(String.valueOf(countdownTime[0]));
//                countdownTime[0]--;
//            }
//        }));
//
//        countdown.setOnFinished(e -> {
//            countdownLabel.setText("Game Begin!");
//        });
//        countdown.play();
//    }

}



package Snakey.View;

import Snakey.Controller.SelectController;
import Snakey.Model.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ViewManager {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 750;
    private AnchorPane mainPane;
    private Scene mainScene;
    private static Stage mainStage;
    private final static int MENU_START_X = 100;
    private final static int MENU_START_Y = 150;

    private SecondScene tipsSubScene;
    private SecondScene playSubScene;
    private SecondScene scoreSubScene;
    private SecondScene inputSubScene;

    private SecondScene changeTheScene;

    private List<SnakeButton> menuButtons;

    List<SelectController> snakeOptions;

    public static SNAKE getSelectedSnake() {
        return selectedSnake;
    }

    private static SNAKE selectedSnake;


    public ViewManager(){
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane,WIDTH,HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        this.menuButtons = new ArrayList<>();
        setSecondScenes();
        setButtons();
        setBackground();
        mainStage.setTitle("Snakey Snakey");
//        setLogo();
    }

    private void mainSubScene(SecondScene secondScene){
        if (changeTheScene != null){
            changeTheScene.moveToSecScene();
        }
        secondScene.moveToSecScene();
        changeTheScene = secondScene;
    }

    public void setMainPaneOpacity(double opacity) {
        mainPane.setOpacity(opacity);
    }

    private void setSecondScenes(){
        setPlaySubScene();
        setScoreSubScene();
        setInputSubScene();
        setTipsSubScene();
    }

    private void  setInputSubScene(){
        inputSubScene = new SecondScene();
        mainPane.getChildren().add(inputSubScene);

        SnakeLabel nameInput = new SnakeLabel("ENTER YOUR NAME");
        nameInput.setLayoutX(200);
        nameInput.setLayoutY(25);
        nameInput.setAlignment(Pos.CENTER);
        inputSubScene.getPane().getChildren().add(nameInput);

        TextField nameInputField = new TextField();
        nameInputField.setLayoutX(350);
        nameInputField.setLayoutY(200);
        nameInputField.setAlignment(Pos.CENTER);

        SnakeButton submitButton = new SnakeButton("Submit");
        submitButton.setOnAction(event -> {
            String playerName = nameInputField.getText();
            System.out.println("Player Name: " + playerName);
        });
        submitButton.setLayoutX(450);
        submitButton.setLayoutY(300);
        submitButton.setAlignment(Pos.CENTER);
        inputSubScene.getPane().getChildren().add(submitButton);
        inputSubScene.getPane().getChildren().add(nameInputField);

    }

    private void setTipsSubScene(){
        tipsSubScene = new SecondScene();
        mainPane.getChildren().add(tipsSubScene);

        Label movementInstructions = new Label("Use ASDW or Arrow Keys to move the snake.");
        Label pauseInstructions = new Label("Press Space to pause during the game.");
        Label lifeInstructions = new Label("Every player has three snake lives per game.");
        movementInstructions.setLayoutX(20);
        movementInstructions.setLayoutY(200);
        movementInstructions.setPrefWidth(1000);
        movementInstructions.setStyle("-fx-font-size: 14px;-fx-font-weight: bold;-fx-font-style: italic; -fx-text-fill: black;");
        movementInstructions.setAlignment(Pos.CENTER);
        pauseInstructions.setLayoutX(30);
        pauseInstructions.setLayoutY(300);
        pauseInstructions.setPrefWidth(1000);
        pauseInstructions.setStyle("-fx-font-size: 14px;-fx-font-weight: bold;-fx-font-style: italic; -fx-text-fill: black;");
        pauseInstructions.setAlignment(Pos.CENTER);
        lifeInstructions.setLayoutX(10);
        lifeInstructions.setLayoutY(100);
        lifeInstructions.setPrefWidth(1000);
        lifeInstructions.setStyle("-fx-font-size: 14px;-fx-font-weight: bold;-fx-font-style: italic; -fx-text-fill: black;");
        lifeInstructions.setAlignment(Pos.CENTER);
        tipsSubScene.getPane().getChildren().addAll(lifeInstructions,movementInstructions,pauseInstructions);
    }
    private void setScoreSubScene() {
        scoreSubScene = new SecondScene();
        mainPane.getChildren().add(scoreSubScene);
        SnakeLabel selectLabel = new SnakeLabel("HIGHSCORE PLAYER");
        selectLabel.setLayoutX(200);
        selectLabel.setLayoutY(25);
        selectLabel.setAlignment(Pos.CENTER);
        scoreSubScene.getPane().getChildren().add(selectLabel);
        scoreSubScene.getPane().getChildren().add(highScoreList());
    }

    public class PlayerScore {
        private final SimpleStringProperty playerName;
        private final SimpleIntegerProperty score;

        public PlayerScore(String playerName, int score) {
            this.playerName = new SimpleStringProperty(playerName);
            this.score = new SimpleIntegerProperty(score);
        }

        public String getPlayerName() {
            return playerName.get();
        }

        public int getScore() {
            return score.get();
        }
    }

    private VBox highScoreList(){
        VBox box = new VBox();
        box.setSpacing(10);
        TableView<PlayerScore> tableView = new TableView<>();
        ObservableList<PlayerScore> data = generateRandomScores(10);

        TableColumn<PlayerScore, String> nameColumn = new TableColumn<>("Player Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));

        TableColumn<PlayerScore, Number> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        tableView.getColumns().addAll(nameColumn,scoreColumn);
        tableView.setItems(data);

        tableView.setPrefHeight(270);
        tableView.setPrefWidth(140);

        box.getChildren().add(tableView);

        box.setLayoutX(300);
        box.setLayoutY(100);
        return box;
    }

    private ObservableList<PlayerScore> generateRandomScores(int numberOfScores) {
        ObservableList<PlayerScore> scores = FXCollections.observableArrayList();
        Random random = new Random();

        for (int i = 1; i <= numberOfScores; i++) {
            String playerName = "Player " + i;
            int playerScore = random.nextInt(100); // Random score between 0 and 99
            scores.add(new PlayerScore(playerName, playerScore));
        }
        return scores;
    }

    private void setPlaySubScene(){
        playSubScene = new SecondScene();
        mainPane.getChildren().add(playSubScene);

        SnakeLabel selectLabel = new SnakeLabel("CHOOSE YOUR SNAKE");
        selectLabel.setLayoutX(200);
        selectLabel.setLayoutY(25);
        selectLabel.setAlignment(Pos.CENTER);
        playSubScene.getPane().getChildren().add(selectLabel);
        playSubScene.getPane().getChildren().add(setSnakeToSelect());
        playSubScene.getPane().getChildren().add(setStartButton());
    }

    private HBox setSnakeToSelect(){
        HBox box = new HBox();
        box.setSpacing(50);
        snakeOptions = new ArrayList<>();
        for (SNAKE snake: SNAKE.values()){
            SelectController snakeToChoose= new SelectController(snake);
            snakeOptions.add(snakeToChoose);
            box.getChildren().add(snakeToChoose);
            snakeToChoose.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    for (SelectController ship: snakeOptions){
                        ship.setIsSelected(false);
                    }
                    snakeToChoose.setIsSelected(true);
                    selectedSnake = snakeToChoose.getSnake();
                }
            });
        }
        box.setLayoutX(280);
        box.setLayoutY(100);
        return box;
    }

    private SnakeButton setStartButton(){
        SnakeButton startButton = new SnakeButton("START");
        startButton.setLayoutX(350);
        startButton.setLayoutY(300);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(selectedSnake != null){
                    GameView gameView = new GameView();
                    gameView.setGame(mainStage, selectedSnake);
                }
            }
        });
        return startButton;
    }

    public static Stage getMainStage(){
        return mainStage;
    }

    private void addMenuButton(SnakeButton button){
        button.setLayoutX(MENU_START_X);
        button.setLayoutY(MENU_START_Y + menuButtons.size() * 100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void setButtons(){
        setPlayButton();
        setScoreButton();
        setInputButton();
        setTipsButton();
        setExitButton();
    }

    private void setPlayButton(){
        SnakeButton startButton = new SnakeButton("PLAY");
        addMenuButton(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainSubScene(playSubScene);
            }
        });
    }

    private void setScoreButton(){
        SnakeButton scoreButton = new SnakeButton("SCORES");
        addMenuButton(scoreButton);

        scoreButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainSubScene(scoreSubScene);
            }
        });
    }

    private void setInputButton(){
        SnakeButton helpButton = new SnakeButton("INPUT");
        addMenuButton(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainSubScene(inputSubScene);
            }
        });
    }

    private void setTipsButton(){
        SnakeButton inFoButton = new SnakeButton("TIPS");
        addMenuButton(inFoButton);

        inFoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainSubScene(tipsSubScene);
            }
        });
    }

    private void setExitButton(){
        SnakeButton exitButton = new SnakeButton("EXIT");
        addMenuButton(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
                mainStage.close();
            }
        });
    }


    private void setBackground(){
        Image backgroundImage = new Image("background.png", WIDTH,HEIGHT,false,true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,null);
        mainPane.setBackground(new Background(background));
    }
}

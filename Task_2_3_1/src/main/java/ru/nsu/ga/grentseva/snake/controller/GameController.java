package ru.nsu.ga.grentseva.snake.controller;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import ru.nsu.ga.grentseva.snake.config.GameConfig;
import ru.nsu.ga.grentseva.snake.config.LocalizationManager;
import ru.nsu.ga.grentseva.snake.model.Direction;
import ru.nsu.ga.grentseva.snake.model.GameModel;
import ru.nsu.ga.grentseva.snake.model.GameState;
import ru.nsu.ga.grentseva.snake.render.GameRenderer;
import ru.nsu.ga.grentseva.snake.render.RenderCommand;
import ru.nsu.ga.grentseva.snake.render.RenderType;

import java.util.ArrayList;

public class GameController {

    @FXML private Canvas canvas;
    @FXML private Label scoreLabel;
    @FXML private Label targetLabel;
    @FXML private Label levelLabel;
    @FXML private Label legendLabel;

    private GameModel model;
    private GameRenderer renderer;
    private GraphicsContext gc;
    private AnimationTimer timer;

    private int level = 1;
    private boolean gameEnded = false;
    private boolean isEndless = false;


    public void setEndlessMode(boolean isEndless) {
        this.isEndless = isEndless;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    @FXML
    public void initialize() {
        legendLabel.setText(LocalizationManager.get("food.legend"));

        setupCanvas();

        canvas.setFocusTraversable(true);
        canvas.requestFocus();
    }


    private void setupCanvas() {
        GameConfig config = getConfigForLevel();

        int cellSize = Math.min(
                1200 / config.width,
                800 / config.height
        );

        canvas.setWidth(config.width * cellSize);
        canvas.setHeight(config.height * cellSize);

        gc = canvas.getGraphicsContext2D();
        renderer = new GameRenderer(cellSize);
    }

    public void start() {
        startGame();
    }

    private void startGame() {
        gameEnded = false;

        model = new GameModel(getConfigForLevel(), isEndless);
        updateUI();

        renderer.drawBackground(gc,
                (int) canvas.getWidth(),
                (int) canvas.getHeight());

        renderInitialState();

        restartTimer();
    }


    private void restartTimer() {
        if (timer != null) timer.stop();
        timer = createGameLoop();
        timer.start();
    }


    private AnimationTimer createGameLoop() {
        return new AnimationTimer() {

            private double accumulator = 0;
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                if (deltaTime > 0.1) deltaTime = 0.1;

                accumulator += deltaTime;

                double tickTime = 1.0 / getTicksPerSecond();

                while (accumulator >= tickTime) {
                    updateGame();
                    accumulator -= tickTime;
                }

                handleGameState();
            }
        };
    }


    private double getTicksPerSecond() {
        double base = model.getConfig().ticksPerSecond;
        return base + model.getScore() / 20.0;
    }


    private void updateGame() {
        var commands = model.update();
        renderer.render(gc, commands);
        updateUI();
    }


    private void handleGameState() {

        if (!gameEnded && model.getState() == GameState.GAME_OVER) {
            gameEnded = true;
            timer.stop();
            Platform.runLater(() -> showEndScreen(false));
            return;
        }

        if (!isEndless && !gameEnded && model.getState() == GameState.WIN) {
            gameEnded = true;
            timer.stop();
            Platform.runLater(this::handleWin);
        }
    }


    private void handleWin() {
        if (level == 1) {
            level = 2;
            startGame();
        } else {
            showEndScreen(true);
        }
    }


    private void updateUI() {
        scoreLabel.setText(LocalizationManager.get("score") + model.getScore());

        if (isEndless) {
            targetLabel.setText(LocalizationManager.get("target") + "∞");
            levelLabel.setText(LocalizationManager.get("level") + "∞");
        } else {
            targetLabel.setText(LocalizationManager.get("target") + model.getConfig().targetLength);
            levelLabel.setText(LocalizationManager.get("level") + level);
        }
    }


    private void renderInitialState() {
        ArrayList<RenderCommand> initial = new ArrayList<>();

        for (var c : model.getSnake().getBody()) {
            initial.add(new RenderCommand(c, RenderType.SNAKE, null));
        }

        for (var f : model.getFoods()) {
            initial.add(new RenderCommand(f.position(), RenderType.FOOD, f.type()));
        }

        for (var c : model.getField().getObstacles()) {
            initial.add(new RenderCommand(c, RenderType.OBSTACLE, null));
        }

        renderer.render(gc, initial);
    }


    @FXML
    public void onKeyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case UP -> model.getSnake().setDirection(Direction.UP);
            case DOWN -> model.getSnake().setDirection(Direction.DOWN);
            case LEFT -> model.getSnake().setDirection(Direction.LEFT);
            case RIGHT -> model.getSnake().setDirection(Direction.RIGHT);
        }
    }


    private void showEndScreen(boolean win) {
        try {
            FXMLLoader loader = loadFXML("/end_screen.fxml");

            Scene scene = new Scene(loader.load());

            EndController controller = loader.getController();
            controller.init(win, model.getScore(), isEndless, level);

            getStage().setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Stage getStage() {
        return (Stage) canvas.getScene().getWindow();
    }

    private FXMLLoader loadFXML(String path) {
        return new FXMLLoader(getClass().getResource(path));
    }


    private GameConfig getConfigForLevel() {
        if (isEndless) return GameConfig.endless();

        return switch (level) {
            case 1 -> GameConfig.level1();
            case 2 -> GameConfig.level2();
            default -> GameConfig.level2();
        };
    }
}
package ru.nsu.ga.grentseva.snake.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ru.nsu.ga.grentseva.snake.config.LocalizationManager;

import java.io.IOException;

public class EndController {

    @FXML private Label titleLabel;
    @FXML private Label scoreLabel;
    @FXML private Button restartButton;
    @FXML private Button menuButton;

    private boolean isEndless;
    private int level;


    public void init(boolean isWin, int score, boolean isEndless, int level) {
        this.isEndless = isEndless;
        this.level = level;

        titleLabel.setText(isWin
                ? LocalizationManager.get("game.win")
                : LocalizationManager.get("game.over"));

        scoreLabel.setText(LocalizationManager.get("score") + score);

        restartButton.setText(LocalizationManager.get("button.restart"));
        menuButton.setText(LocalizationManager.get("button.menu"));
    }


    @FXML
    private void onRestart() throws IOException {
        Stage stage = getStage();

        FXMLLoader loader = loadFXML("/main.fxml");
        Scene scene = new Scene(loader.load());

        GameController controller = loader.getController();
        controller.setEndlessMode(isEndless);
        controller.setLevel(level);
        controller.start();

        stage.setScene(scene);
    }


    @FXML
    private void onMenu() throws IOException {
        Stage stage = getStage();

        FXMLLoader loader = loadFXML("/menu.fxml");
        stage.setScene(new Scene(loader.load()));
    }


    private Stage getStage() {
        return (Stage) titleLabel.getScene().getWindow();
    }

    private FXMLLoader loadFXML(String path) {
        return new FXMLLoader(getClass().getResource(path));
    }
}
package ru.nsu.ga.grentseva.snake.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import ru.nsu.ga.grentseva.snake.config.LocalizationManager;

import java.io.IOException;
import java.util.Locale;

public class MenuController {

    @FXML private Label titleLabel;
    @FXML private Button playButton;
    @FXML private Button playEndlessButton;
    @FXML private Button exitButton;
    @FXML private ChoiceBox<String> languageBox;
    @FXML private Label languageLabel;


    @FXML
    public void initialize() {
        setupLanguageBox();
        updateTexts();
    }


    @FXML
    private void onPlay() throws IOException {
        startGame(false);
    }

    @FXML
    private void onPlayEndless() throws IOException {
        startGame(true);
    }

    @FXML
    private void onExit() {
        System.exit(0);
    }


    private void startGame(boolean endless) throws IOException {
        Stage stage = (Stage) playButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/main.fxml")
        );

        Scene scene = new Scene(loader.load());

        GameController controller = loader.getController();
        controller.setEndlessMode(endless);
        controller.start();

        stage.setScene(scene);
    }


    private void setupLanguageBox() {
        languageBox.getItems().addAll("English", "Русский");
        languageBox.setValue("English");

        languageBox.setOnAction(e -> {
            if (languageBox.getValue().equals("Русский")) {
                LocalizationManager.setLocale(new Locale("ru"));
            } else {
                LocalizationManager.setLocale(Locale.ENGLISH);
            }
            updateTexts();
        });
    }


    private void updateTexts() {
        titleLabel.setText(LocalizationManager.get("game.title"));
        playButton.setText(LocalizationManager.get("menu.play"));
        playEndlessButton.setText(LocalizationManager.get("menu.play.endless"));
        exitButton.setText(LocalizationManager.get("menu.exit"));
        languageLabel.setText(LocalizationManager.get("language"));
    }


    @FXML
    private void onHover(MouseEvent e) {
        Button b = (Button) e.getSource();
        b.setStyle("-fx-background-color: #385E22; -fx-text-fill: white; -fx-background-radius: 15; -fx-font-size: 20px; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 4);");
    }

    @FXML
    private void onExitHover(MouseEvent e) {
        Button b = (Button) e.getSource();
        b.setStyle("-fx-background-color: #4A752C; -fx-text-fill: white; -fx-background-radius: 15; -fx-font-size: 20px; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 2);");
    }

    @FXML
    private void onHoverExit(MouseEvent e) {
        Button b = (Button) e.getSource();
        b.setStyle("-fx-background-color: #B23414; -fx-text-fill: white; -fx-background-radius: 15; -fx-font-size: 20px; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 4);");
    }

    @FXML
    private void onExitHoverExit(MouseEvent e) {
        Button b = (Button) e.getSource();
        b.setStyle("-fx-background-color: #E7471D; -fx-text-fill: white; -fx-background-radius: 15; -fx-font-size: 20px; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 2);");
    }
}
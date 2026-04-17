package ru.nsu.ga.grentseva.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = loadScene();

        stage.setScene(scene);
        stage.sizeToScene();
        stage.setOnShown(e -> scene.getRoot().requestFocus());
        stage.setTitle("Snake");
        stage.setResizable(false);

        stage.show();
    }


    private Scene loadScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/menu.fxml")
        );

        return new Scene(loader.load());
    }
}
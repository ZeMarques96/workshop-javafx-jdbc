package javaf.workshop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Scene mainScene;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));


            ScrollPane scrollPane = loader.load();

            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);

            stage.setTitle("Main Scene");
            mainScene = new Scene(scrollPane);
            stage.setScene(mainScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Scene getMainScene(){
        return mainScene;
    }

    public static void main(String[] args) {
        launch();
    }
}
package UserInterface;

import DataBase.DbHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;



import java.io.IOException;


public class Main extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        primaryStage.show();
        showWindow();
    }


    public void showWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/UserInterface/fxml/sample.fxml"));
            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.setTitle("DataBase");
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();

            Controller controller = fxmlLoader.getController();
            controller.provideApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
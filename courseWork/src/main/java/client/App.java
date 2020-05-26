package client;

import client.api.*;
import client.controller.*;
import client.model.UserModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import client.api.ApiComponent;
import client.api.DaggerApiComponent;


import java.io.IOException;


public class App extends Application {
    Stage primaryStage;
    private BookApi bookApi;
    private BookTypeApi bookTypeApi;
    private ClientApi clientApi;
    private JournalApi journalApi;

    private Stage bookTypeStage;
    private Stage bookStage;
    private Stage clientStage;
    private Stage journalStage;
    private ApiComponent component = DaggerApiComponent.create();

    private Auth auth;
    private UserModel user;
    private String token;
    private MainWindowController mainWindowController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        journalApi = component.provideJournal();
        clientApi = component.provideClient();
        bookApi = component.provideBook();
        bookTypeApi = component.provideType();
        auth = component.provideAuthApi();
        showAuthWindow();
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public void showAuthWindow() {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/resources/Auth.fxml"));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.setTitle("Authorization");

            AuthController authController = loader.getController();
            authController.provideApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void showMainWindow() {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/resources/scene.fxml"));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.setTitle("Library");

            mainWindowController = loader.getController();
            mainWindowController.provideApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showBookTypeWindow(Actions action) {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/resources/BookType.fxml"));
            bookTypeStage = new Stage();
            bookTypeStage.setScene(new Scene(loader.load()));
            bookTypeStage.setTitle("Type");
            bookTypeStage.show();

            BookTypeController controller = loader.getController();
            controller.provideApp(this, action);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void closeBookTypeWindow() {
        bookTypeStage.close();
    }

    public void showBookWindow(Actions action) {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/resources/Book.fxml"));
            bookStage = new Stage();
            bookStage.setScene(new Scene(loader.load()));
            bookStage.setTitle("Book");
            bookStage.show();

            BookController controller = loader.getController();
            controller.provideApp(this, action);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void closeBookWindow() {
        bookStage.close();
    }

    public void showClientWindow(Actions action) {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/resources/Client.fxml"));
            clientStage = new Stage();
            clientStage.setScene(new Scene(loader.load()));
            clientStage.setTitle("Client");
            clientStage.show();

            ClientController controller = loader.getController();
            controller.provideApp(this, action);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void closeClientWindow() {
        clientStage.close();
    }

    public void showJournalWindow(Actions action) {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/resources/Journal.fxml"));
            journalStage = new Stage();
            journalStage.setScene(new Scene(loader.load()));
            journalStage.setTitle("Journal");
            journalStage.show();

            JournalController controller = loader.getController();
            controller.provideApp(this, action);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void closeJournalWindow() {
        journalStage.close();
    }

    public void reload() {
        mainWindowController.reload();
    }

    public BookApi getBookApi() {
        return bookApi;
    }

    public BookTypeApi getBookTypeApi() {
        return bookTypeApi;
    }

    public ClientApi getClientApi() {
        return clientApi;
    }

    public JournalApi getJournalApi() {
        return journalApi;
    }

    public Auth getAuth() {
        return auth;
    }

    public UserModel getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void createAlertError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(primaryStage);
        alert.setTitle("Error");
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void createAlertWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(primaryStage);
        alert.setTitle("Warning");
        alert.setHeaderText("Check your input");
        alert.setContentText(message);
        alert.showAndWait();
    }
}


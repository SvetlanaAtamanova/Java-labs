package client.controller;

import client.App;
import client.model.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AuthController {


    @FXML
    private TextField fieldUser;
    @FXML
    private TextField fieldPassword;

    private App app;

    public void provideApp(App app) {
    this.app = app;
    }

    @FXML
    public void onClickAdd() {
        if (fieldUser.getText().isEmpty() || fieldPassword.getText().isEmpty()) {
            app.createAlertWarning("Fields cannot be empty");
        } else {
            UserModel userModel = new UserModel(fieldUser.getText(), fieldPassword.getText());
            app.getAuth().singIn(userModel).subscribe(response -> {
                if (response.isSuccessful()) {
                    app.setToken("Bearer " + response.body());
                    app.getPrimaryStage().close();
                    app.showMainWindow();
                    app.getPrimaryStage().setResizable(false);
                    app.getPrimaryStage().show();
                } else {
                    app.createAlertError("Error!. Code = " + response.code());
                }
            });

        }
    }

    @FXML
    public void onClickCancel() {
        app.getPrimaryStage().close();
    }

    @FXML
    void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onClickAdd();
        } else if (event.getCode() == KeyCode.ESCAPE) {
            onClickCancel();
        }
    }

}

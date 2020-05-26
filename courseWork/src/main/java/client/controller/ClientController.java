package client.controller;

import client.App;
import client.model.ClientModel;
import client.model.JournalModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientController {
    private App app;
    private Actions action;

    @FXML
    private Label labelId;
    @FXML
    private Label labelFirstName;
    @FXML
    private Label labelLastName;
    @FXML
    private Label labelFatherName;
    @FXML
    private Label labelPassportSer;
    @FXML
    private Label labelPassportNum;

    @FXML
    private TextField fieldId;
    @FXML
    private TextField fieldFirstName;
    @FXML
    private TextField fieldLastName;
    @FXML
    private TextField fieldFatherName;
    @FXML
    private TextField fieldPassportSer;
    @FXML
    private TextField fieldPassportNum;

    public void provideApp(App app, Actions action) {
        this.app = app;
        this.action = action;

        fieldId.textProperty().addListener((observable, oldValue, newValue) -> {
            if (action == Actions.Delete) return;
            try {
                app.getClientApi().getClientById(app.getToken(), Long.parseLong(newValue)).subscribe(clientModelResponse -> {
                    if (clientModelResponse.isSuccessful() && clientModelResponse.body() != null) {
                        fieldFirstName.setText(clientModelResponse.body().getFirstName());
                        fieldLastName.setText(clientModelResponse.body().getLastName());
                        fieldFatherName.setText(clientModelResponse.body().getFatherName());
                        fieldPassportSer.setText(clientModelResponse.body().getPassportSeria());
                        fieldPassportNum.setText(clientModelResponse.body().getPassportNum());
                    } else {
                        fieldFirstName.setText("");
                        fieldLastName.setText("");
                        fieldFatherName.setText("");
                        fieldPassportSer.setText("");
                        fieldPassportNum.setText("");
                    }
                });
            } catch (Exception ignored) {
            }
        });

        labelId.setVisible(true);
        fieldId.setVisible(true);
        setVisibleForContent(true);
        switch (action) {
            case Add:
                labelId.setVisible(false);
                fieldId.setVisible(false);
                break;
            case Delete:
                setVisibleForContent(false);
            case Update:
                break;
        }
    }

    public void onClickAdd() {
        switch (action) {
            case Add:
                if (fieldFirstName.getText().isEmpty() || fieldLastName.getText().isEmpty() || fieldFatherName.getText().isEmpty() || fieldPassportSer.getText().isEmpty() || fieldPassportNum.getText().isEmpty()) {
                    app.createAlertWarning("Fields cannot be empty");
                } else {
                    if (checkClient()){
                        app.getClientApi().createClient(app.getToken(), new ClientModel(fieldFirstName.getText(), fieldLastName.getText(), fieldFatherName.getText(), fieldPassportSer.getText(), fieldPassportNum.getText())).subscribe(response -> {
                            if (response.isSuccessful()) {
                                app.reload();
                                app.closeClientWindow();
                            } else {
                                app.createAlertError("Something went wrong. Code = " + response.code());
                            }
                        });
                    } else {
                        app.createAlertWarning("Check symbols");
                    }

                }
                break;
            case Delete:
                if (fieldId.getText().isEmpty()) {
                    app.createAlertWarning("Id cannot be empty");
                    return;
                }
                try {
                    long id = Long.parseLong(fieldId.getText());
                    app.getJournalApi().getAllJournal(app.getToken()).subscribe(clientResponse -> {
                        if (clientResponse.isSuccessful()) {
                            List<JournalModel> list = clientResponse.body();
                            AtomicBoolean check = new AtomicBoolean(false);
                            list.forEach(it -> {
                                if (it.getClient().getId() == id) {
                                    app.createAlertError("There is a journal with this client. You can't delete it");
                                    app.closeClientWindow();
                                    check.set(true);
                                    return;
                                }
                            });
                            if (check.get()) return;
                            app.getClientApi().deleteClient(app.getToken(), id).subscribe(response -> {
                                if (response.isSuccessful()) {
                                    app.reload();
                                    app.closeClientWindow();
                                } else {
                                    app.createAlertError("Something went wrong. Code = " + response.code());
                                }
                            });
                        } else {
                            app.createAlertError("Something went wrong. Code = " + clientResponse.code());
                        }
                    });
                } catch (NumberFormatException e) {
                    app.createAlertError("Wrong id");
                }
                break;
            case Update:
                String idString = fieldId.getText();
                if (idString.isEmpty() || fieldLastName.getText().isEmpty() || fieldFatherName.getText().isEmpty() || fieldPassportSer.getText().isEmpty() || fieldPassportNum.getText().isEmpty()) {
                    app.createAlertWarning("Fields cannot be empty");
                } else {
                    try {
                        long id = Long.parseLong(idString);
                        app.getClientApi().updateClient(app.getToken(), id, new ClientModel(id, fieldFirstName.getText(), fieldLastName.getText(), fieldFatherName.getText(), fieldPassportSer.getText(), fieldPassportNum.getText())).subscribe(response -> {
                            if (response.isSuccessful()) {
                                app.reload();
                                app.closeClientWindow();
                            } else {
                                app.createAlertError("Something went wrong. Code = " + response.code());
                            }
                        });
                    } catch (NumberFormatException e) {
                        app.createAlertError("Wrong id");
                    }
                }
                break;
        }

    }

    @FXML
    public void onClickCancel() {
        app.closeClientWindow();
    }

    @FXML
    void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onClickAdd();
        } else if (event.getCode() == KeyCode.ESCAPE) {
            onClickCancel();
        }
    }

    private boolean checkLetters(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) < 'A' || (string.charAt(i) > 'z' && string.charAt(i) < 'А') ||  string.charAt(i) > 'я') {
                return false;
            }
        }
        return true;
    }

    private boolean checkNum(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) < '0' || string.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    private boolean checkClient() {
        if (checkLetters(fieldFirstName.getText()) && checkLetters(fieldLastName.getText()) && checkLetters(fieldFatherName.getText()) && checkNum(fieldPassportSer.getText()) && checkNum(fieldPassportNum.getText())) {
            return true;
        }
        return false;
    }


    private void setVisibleForContent(boolean bool) {
        labelFirstName.setVisible(bool);
        fieldFirstName.setVisible(bool);
        labelLastName.setVisible(bool);
        fieldLastName.setVisible(bool);
        labelFatherName.setVisible(bool);
        fieldFatherName.setVisible(bool);
        labelPassportSer.setVisible(bool);
        fieldPassportSer.setVisible(bool);
        labelPassportNum.setVisible(bool);
        fieldPassportNum.setVisible(bool);
    }

}


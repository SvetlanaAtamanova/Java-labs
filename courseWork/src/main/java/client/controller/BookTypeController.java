package client.controller;

import client.App;
import client.model.BookModel;
import client.model.BookTypeModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;



public class BookTypeController {
    private App app;
    private Actions action;

    @FXML
    private Label labelId;
    @FXML
    private Label labelName;
    @FXML
    private Label labelFine;
    @FXML
    private Label labelDayCount;
    @FXML
    private Label labelCount;

    @FXML
    private TextField fieldId;
    @FXML
    private TextField fieldName;
    @FXML
    private TextField fieldCount;
    @FXML
    private TextField fieldFine;
    @FXML
    private TextField fieldDayCount;

    public void provideApp(App app, Actions action) {
        this.app = app;
        this.action = action;

        fieldId.textProperty().addListener((observable, oldValue, newValue) -> {
            if (action == Actions.Delete) return;
            try {
                app.getBookTypeApi().getTypeById(app.getToken(), Long.parseLong(newValue)).subscribe(typeModelResponse -> {
                    if (typeModelResponse.isSuccessful() && typeModelResponse.body() != null) {
                        fieldName.setText(typeModelResponse.body().getName());
                        fieldCount.setText(Long.toString(typeModelResponse.body().getCount()));
                        fieldFine.setText(Double.toString(typeModelResponse.body().getFine()));
                        fieldDayCount.setText(Long.toString(typeModelResponse.body().getDayCount()));
                    } else {
                        fieldName.setText("");
                        fieldCount.setText("");
                        fieldFine.setText("");
                        fieldDayCount.setText("");
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
                labelCount.setVisible(false);
                fieldCount.setVisible(false);
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
                if (fieldName.getText().isBlank() || fieldFine.getText().isBlank() || fieldDayCount.getText().isBlank()) {
                    app.createAlertWarning("Fields cannot be empty");
                } else {
                    app.getBookTypeApi().createType(app.getToken(), new BookTypeModel(fieldName.getText(), 0, extractDouble(fieldFine), extractInteger(fieldDayCount))).subscribe(response -> {
                        if (response.isSuccessful()) {
                            app.reload();
                            app.closeBookTypeWindow();
                        } else {
                            app.createAlertError("Something went wrong. Code = " + response.code());
                        }
                    });
                }
                break;
            case Delete:
                if (fieldId.getText().isBlank()) {
                    app.createAlertWarning("Id cannot be empty");
                    return;
                }
                try {
                    long id = Long.parseLong(fieldId.getText());
                    app.getBookApi().getAllBooks(app.getToken()).subscribe(typeResponse -> {
                        if (typeResponse.isSuccessful()) {
                            List<BookModel> list = typeResponse.body();
                            AtomicBoolean check = new AtomicBoolean(false);
                            list.forEach(it -> {
                                if (it.getType().getId() == id) {
                                    app.createAlertError("There is a book with this type. You can't delete it");
                                    app.closeBookTypeWindow();
                                    check.set(true);
                                    return;
                                }
                            });
                            if (check.get()) return;
                            app.getBookTypeApi().deleteType(app.getToken(), id).subscribe(response -> {
                                if (response.isSuccessful()) {
                                    app.reload();
                                    app.closeBookTypeWindow();
                                } else {
                                    app.createAlertError("Something went wrong. Code = " + response.code());
                                }
                            });
                        } else {
                            app.createAlertError("Something went wrong. Code = " + typeResponse.code());
                        }
                    });
                } catch (NumberFormatException e) {
                    app.createAlertError("Wrong id");
                }
                break;
            case Update:
                String idString = fieldId.getText();
                String newName = fieldName.getText();
                if (idString.isBlank() || newName.isBlank() || fieldCount.getText().isBlank() || fieldFine.getText().isBlank() || fieldDayCount.getText().isBlank()) {
                    app.createAlertWarning("Fields cannot be empty");
                } else {
                    try {
                        long id = Long.parseLong(idString);
                        app.getBookTypeApi().updateType(app.getToken(), id, new BookTypeModel(id, newName, extractInteger(fieldCount), extractDouble(fieldFine), extractInteger(fieldDayCount))).subscribe(response -> {
                            if (response.isSuccessful()) {
                                app.reload();
                                app.closeBookTypeWindow();
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
        app.closeBookTypeWindow();
    }

    @FXML
    void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onClickAdd();
        } else if (event.getCode() == KeyCode.ESCAPE) {
            onClickCancel();
        }
    }
    private int extractInteger(TextField field) {
        if (field.getText().isEmpty()) {
            throw new RuntimeException();
        }
        int result = Integer.parseInt(field.getText());
        if (result < 0) {
            field.requestFocus();
            throw new IllegalArgumentException("Number can't be negative.");
        }
        return result;
    }
    private double extractDouble(TextField field) {
        if (field.getText().isEmpty()) {
            throw new RuntimeException();
        }
        double result = Double.parseDouble(field.getText());
        if (result < 0) {
            field.requestFocus();
            throw new IllegalArgumentException("Number can't be negative.");
        }
        return result;
    }

    private void setVisibleForContent(boolean bool){
        labelName.setVisible(bool);
        fieldName.setVisible(bool);
        labelCount.setVisible(bool);
        fieldCount.setVisible(bool);
        labelFine.setVisible(bool);
        fieldFine.setVisible(bool);
        labelDayCount.setVisible(bool);
        fieldDayCount.setVisible(bool);
    }
}

package client.controller;

import client.App;
import client.model.BookModel;
import client.model.BookTypeModel;
import client.model.JournalModel;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookController {
    private App app;
    private Actions action;
    private BookTypeModel currType;

    @FXML
    private Label labelId;
    @FXML
    private Label labelName;
    @FXML
    private Label labelCount;
    @FXML
    private Label labelType;

    @FXML
    private TextField fieldId;
    @FXML
    private TextField fieldName;
    @FXML
    private TextField fieldCount;
    @FXML
    private ChoiceBox<BookTypeModel> typeBox;

    private BookTypeModel type;
    private long count;
    private long oldCount;

    public void provideApp(App app, Actions action) {
        this.app = app;
        this.action = action;


        app.getBookTypeApi().getAllTypes(app.getToken()).subscribe(typeResponse -> {
            if (typeResponse.isSuccessful()) {
                typeBox.getItems().addAll(typeResponse.body());
            } else {
                app.createAlertError("Something went wrong. Code = " + typeResponse.code());
            }
        });

        fieldId.textProperty().addListener((observable, oldValue, newValue) -> {
            if (action == Actions.Delete) return;
            try {
                app.getBookApi().getBookById(app.getToken(), Long.parseLong(newValue)).subscribe(bookModelResponse -> {
                    if (bookModelResponse.isSuccessful() && bookModelResponse.body() != null) {
                        fieldName.setText(bookModelResponse.body().getName());
                        fieldCount.setText(Long.toString(bookModelResponse.body().getCount()));
                        typeBox.setValue(bookModelResponse.body().getType());

                    } else {
                        fieldName.setText("");
                        fieldCount.setText("");
                        typeBox.setValue(null);
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
                if (fieldName.getText().isEmpty() || fieldCount.getText().isEmpty() || fieldCount.getText().isEmpty() || typeBox.getValue() == null) {
                    app.createAlertWarning("Fields cannot be empty");
                } else {
                    long count = extractInteger(fieldCount);
                    BookTypeModel bookTypeModel = typeBox.getValue();
                    app.getBookApi().createBook(app.getToken(), new BookModel(fieldName.getText(), count , bookTypeModel)).subscribe(response -> {
                        if (response.isSuccessful()) {
                            app.reload();
                            app.closeBookWindow();
                            app.getBookTypeApi().updateType(app.getToken(), bookTypeModel.getId(), new BookTypeModel(bookTypeModel.getId(), bookTypeModel.getName(), bookTypeModel.getCount() + count, bookTypeModel.getFine(), bookTypeModel.getDayCount())).subscribe(type -> {
                                if (type.isSuccessful()) {
                                    app.reload();
                                }
                            });
                        } else {
                            app.createAlertError("Something went wrong. Code = " + response.code());
                        }
                    });
                }
                break;
            case Delete:
                if (fieldId.getText().isEmpty()) {
                    app.createAlertWarning("Id cannot be empty");
                    return;
                }
                try {
                    long id = Long.parseLong(fieldId.getText());
                    app.getJournalApi().getAllJournal(app.getToken()).subscribe(bookResponse -> {
                        if (bookResponse.isSuccessful()) {
                            List<JournalModel> list = bookResponse.body();
                            AtomicBoolean check = new AtomicBoolean(false);
                            list.forEach(it -> {
                                if (it.getBook().getId() == id) {
                                    app.createAlertError("There is a journal with this book. You can't delete it");
                                    app.closeBookWindow();
                                    check.set(true);
                                    return;
                                }
                            });
                            if (check.get()) return;

                            app.getBookApi().getBookById(app.getToken(), id).subscribe(bookModelResponse -> {
                                if (bookModelResponse.isSuccessful() && bookModelResponse.body() != null) {
                                    type = bookModelResponse.body().getType();
                                    count = -(bookModelResponse.body().getCount());
                                }
                            });

                            app.getBookApi().deleteBook(app.getToken(), id).subscribe(response -> {
                                if (response.isSuccessful()) {
                                    app.reload();
                                    app.closeBookWindow();
                                    app.getBookTypeApi().updateType(app.getToken(), type.getId(), new BookTypeModel(type.getId(), type.getName(), type.getCount() + count, type.getFine(), type.getDayCount())).subscribe(type -> {
                                        if (type.isSuccessful()) {
                                            app.reload();
                                        }
                                    });
                                } else {
                                    app.createAlertError("Something went wrong. Code = " + response.code());
                                }
                            });
                        } else {
                            app.createAlertError("Something went wrong. Code = " + bookResponse.code());
                        }
                    });
                } catch (NumberFormatException e) {
                    app.createAlertError("Wrong id");
                }
                break;
            case Update:
                String idString = fieldId.getText();
                String newName = fieldName.getText();
                if (idString.isEmpty() || newName.isEmpty() || fieldCount.getText().isEmpty() || typeBox.getValue() == null) {
                    app.createAlertWarning("Fields cannot be empty");
                } else {
                    try {
                        long id = Long.parseLong(idString);
                        // find old count
                        app.getBookApi().getBookById(app.getToken(), id).subscribe(bookModelResponse -> {
                            if (bookModelResponse.isSuccessful() && bookModelResponse.body() != null) {
                                oldCount = bookModelResponse.body().getCount();
                            }
                        });
                        long diffCount = extractInteger(fieldCount) - oldCount;
                        BookTypeModel bookTypeModel = typeBox.getValue();

                        app.getBookApi().updateBook(app.getToken(), id, new BookModel(id, newName, extractInteger(fieldCount), typeBox.getValue())).subscribe(response -> {
                            if (response.isSuccessful()) {
                                app.reload();
                                app.closeBookWindow();
                                app.getBookTypeApi().updateType(app.getToken(), bookTypeModel.getId(), new BookTypeModel(bookTypeModel.getId(), bookTypeModel.getName(), bookTypeModel.getCount() + diffCount, bookTypeModel.getFine(), bookTypeModel.getDayCount())).subscribe(type -> {
                                    if (type.isSuccessful()) {
                                        app.reload();
                                    }
                                });
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
        app.closeBookWindow();
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

    private void setVisibleForContent(boolean bool){
        labelName.setVisible(bool);
        fieldName.setVisible(bool);
        labelCount.setVisible(bool);
        fieldCount.setVisible(bool);
        labelType.setVisible(bool);
        typeBox.setVisible(bool);

    }

}

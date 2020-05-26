package client.controller;

import client.App;
import client.model.BookModel;
import client.model.ClientModel;
import client.model.JournalModel;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;



import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;


public class JournalController {
    private App app;
    private Actions action;
    private Long days;

    @FXML
    private Label labelId;
    @FXML
    private Label labelBook;
    @FXML
    private Label labelClient;
    @FXML
    private Label labelBeg;
    @FXML
    private Label labelEnd;
    @FXML
    private Label labelRet;

    @FXML
    private TextField fieldId;
    @FXML
    private ChoiceBox<BookModel> bookBox;
    @FXML
    private ChoiceBox<ClientModel> clientBox;
    @FXML
    private DatePicker dateBeg;
    @FXML
    private DatePicker dateEnd;
    @FXML
    private DatePicker dateRet;
    private Locale locale;

    public void provideApp(App app, Actions action) {
        this.app = app;
        this.action = action;

        app.getBookApi().getAllBooks(app.getToken()).subscribe(bookResponse -> {
            if (bookResponse.isSuccessful()) {
                bookBox.getItems().addAll(bookResponse.body());
            } else {
                app.createAlertError("Something went wrong. Code = " + bookResponse.code());
            }
        });
        app.getClientApi().getAllClients(app.getToken()).subscribe(clientResponse -> {
            if (clientResponse.isSuccessful()) {
                clientBox.getItems().addAll(clientResponse.body());
            } else {
                app.createAlertError("Something went wrong. Code = " + clientResponse.code());
            }
        });

        fieldId.textProperty().addListener((observable, oldValue, newValue) -> {
            if (action == Actions.Delete) return;
            try {

                app.getJournalApi().getJournalById(app.getToken(), Long.parseLong(newValue)).subscribe(journalModelResponse -> {
                    if (journalModelResponse.isSuccessful() && journalModelResponse.body() != null) {
                        bookBox.setValue(journalModelResponse.body().getBook());
                        clientBox.setValue(journalModelResponse.body().getClient());

                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                        String date = journalModelResponse.body().getDateBeg();
                        LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
                        dateBeg.setValue(localDate);

                        date = journalModelResponse.body().getDateEnd();
                        localDate = LocalDate.parse(date, dateTimeFormatter);
                        dateEnd.setValue(localDate);
                        dateRet.setValue(LocalDate.now());


                    } else {
                        bookBox.setValue(null);
                        clientBox.setValue(null);

                        dateBeg.setValue(LocalDate.of(1998, 10, 8));
                        //dateBeg.setValue(LocalDate.now());
                        System.out.println("4");
                        dateEnd.setValue(LocalDate.now());
                        dateRet.setValue(LocalDate.now());
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
                labelEnd.setVisible(false);
                dateEnd.setVisible(false);
                labelRet.setVisible(false);
                dateRet.setVisible(false);
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
                if (bookBox.getValue() == null || clientBox.getValue() == null || this.dateBeg.getValue() == null) {
                    app.createAlertWarning("Fields cannot be empty");
                } else {
                    String dateBegStr = new SimpleDateFormat("yyyy.MM.dd").format(Date.from(dateBeg.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

                    long days =  bookBox.getValue().getType().getDayCount();
                    LocalDate date = dateBeg.getValue().plusDays(days);
                    String dateEndStr = new SimpleDateFormat("yyyy.MM.dd").format(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));

                    app.getJournalApi().createJournal(app.getToken(), new JournalModel(bookBox.getValue(), clientBox.getValue(), dateBegStr, dateEndStr)).subscribe(response -> {
                        if (response.isSuccessful()) {
                            app.reload();
                            app.closeJournalWindow();
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
                } else {
                    long id = Long.parseLong(fieldId.getText());
                    app.getJournalApi().deleteJournal(app.getToken(), id).subscribe(response -> {
                        if (response.isSuccessful()) {
                            app.reload();
                            app.closeJournalWindow();
                        } else {
                            app.createAlertError("Something went wrong. Code = " + response.code());
                        }
                    });
                }
                break;
            case Update:
                String idString = fieldId.getText();
                if (idString.isEmpty() || bookBox.getValue() == null || clientBox.getValue() == null || this.dateBeg.getValue() == null || dateEnd.getValue() == null) {
                    app.createAlertWarning("Fields cannot be empty");
                } else {
                    try {
                        long id = Long.parseLong(idString);
                        String dateBegStr = new SimpleDateFormat("yyyy.MM.dd").format(Date.from(dateBeg.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                        String dateEndStr = new SimpleDateFormat("yyyy.MM.dd").format(Date.from(dateEnd.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

                        if (dateRet.getValue() == null) {
                            app.getJournalApi().updateJournal(app.getToken(), id, new JournalModel(id, bookBox.getValue(), clientBox.getValue(),dateBegStr, dateEndStr)).subscribe(response -> {
                                if (response.isSuccessful()) {
                                    app.reload();
                                    app.closeJournalWindow();
                                } else {
                                    app.createAlertError("Something went wrong. Code = " + response.code());
                                }
                            });
                        } else {
                            String dateRetStr = new SimpleDateFormat("yyyy.MM.dd").format(Date.from(dateRet.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                            app.getJournalApi().updateJournal(app.getToken(), id, new JournalModel(id, bookBox.getValue(), clientBox.getValue(), dateBegStr, dateEndStr, dateRetStr)).subscribe(response -> {
                                if (response.isSuccessful()) {
                                    app.reload();
                                    app.closeJournalWindow();
                                } else {
                                    app.createAlertError("Something went wrong. Code = " + response.code());
                                }
                            });
                        }

                    } catch (NumberFormatException e) {
                        app.createAlertError("Wrong id");
                    }
                }
                break;
        }

    }

    @FXML
    public void onClickCancel() {
        app.closeJournalWindow();
    }

    @FXML
    void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onClickAdd();
        } else if (event.getCode() == KeyCode.ESCAPE) {
            onClickCancel();
        }
    }


    private void setVisibleForContent(boolean bool) {
        labelBook.setVisible(bool);
        bookBox.setVisible(bool);
        labelClient.setVisible(bool);
        clientBox.setVisible(bool);
        labelBeg.setVisible(bool);
        dateBeg.setVisible(bool);
        labelEnd.setVisible(bool);
        dateEnd.setVisible(bool);
        labelRet.setVisible(bool);
        dateRet.setVisible(bool);

    }

}

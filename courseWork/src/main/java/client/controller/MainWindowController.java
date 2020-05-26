package client.controller;

import client.model.BookModel;
import client.model.BookTypeModel;
import client.model.ClientModel;
import client.model.JournalModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import client.App;


public class MainWindowController {
    private App app;

    @FXML
    private TableView<JournalModel> tableJournal;
    @FXML
    private TableColumn<?, ?> idJournal;
    @FXML
    private TableColumn<?, ?> bookIdColumn;
    @FXML
    private TableColumn<?, ?> clientIdColumn;
    @FXML
    private TableColumn<?, ?> dateBegColumn;
    @FXML
    private TableColumn<?, ?> dateEndColumn;
    @FXML
    private TableColumn<?, ?> dateRetColumn;


    @FXML
    private TableView<ClientModel> tableClients;
    @FXML
    private TableColumn<?, ?> idClient;
    @FXML
    private TableColumn<?, ?> firstNameColumn;
    @FXML
    private TableColumn<?, ?> lastNameColumn;
    @FXML
    private TableColumn<?, ?> fatherNameColumn;
    @FXML
    private TableColumn<?, ?> pSeria;
    @FXML
    private TableColumn<?, ?> pNumber;

    @FXML
    private TableView<BookModel> tableBooks;
    @FXML
    private TableColumn<?, ?> bookId;
    @FXML
    private TableColumn<?, ?> nameColumn;
    @FXML
    private TableColumn<?, ?> countColumn;
    @FXML
    private TableColumn<?, ?> typeIdColumn;

    @FXML
    private TableView<BookTypeModel> tableTypes;
    @FXML
    private TableColumn<?, ?> typeId;
    @FXML
    private TableColumn<?, ?> typeNameColumn;
    @FXML
    private TableColumn<?, ?> typeCountColumn;
    @FXML
    private TableColumn<?, ?> fineColumn;
    @FXML
    private TableColumn<?, ?> dayCount;

    public void provideApp(App app) {
        this.app = app;
        typeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        fineColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));
        dayCount.setCellValueFactory(new PropertyValueFactory<>("dayCount"));

        bookId.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        typeIdColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        idClient.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        fatherNameColumn.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
        pSeria.setCellValueFactory(new PropertyValueFactory<>("passportSeria"));
        pNumber.setCellValueFactory(new PropertyValueFactory<>("passportNum"));


        idJournal.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("book"));
        clientIdColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        dateBegColumn.setCellValueFactory(new PropertyValueFactory<>("dateBeg"));
        dateEndColumn.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        dateRetColumn.setCellValueFactory(new PropertyValueFactory<>("dateRet"));

        reload();
    }


    public void reload() {
        tableJournal.getItems().clear();
        tableBooks.getItems().clear();
        tableClients.getItems().clear();
        tableTypes.getItems().clear();
        loadJournals();
        loadBookTypes();
        loadBooks();
        loadClients();
    }

    private void loadJournals() {
        app.getJournalApi().getAllJournal(app.getToken()).subscribe(journal -> {
            if (journal.isSuccessful() && journal.body() != null) {
                tableJournal.getItems().addAll(journal.body());
            } else {
                app.createAlertError(journal.errorBody().string());
            }
        });
    }

    private void loadBooks() {
        app.getBookApi().getAllBooks(app.getToken()).subscribe(book -> {
            if (book.isSuccessful() && book.body() != null) {
                tableBooks.getItems().addAll(book.body());
            } else {
                app.createAlertError(book.errorBody().string());
            }
        });
    }

    private void loadBookTypes() {
        app.getBookTypeApi().getAllTypes(app.getToken()).subscribe(bookType -> {
            if (bookType.isSuccessful() && bookType.body() != null) {
                tableTypes.getItems().addAll(bookType.body());
            } else {
                app.createAlertError(bookType.errorBody().string());
            }
        });
    }

    private void loadClients() {
        app.getClientApi().getAllClients(app.getToken()).subscribe(client -> {
            if (client.isSuccessful() && client.body() != null) {
                tableClients.getItems().addAll(client.body());
            } else {
                app.createAlertError(client.errorBody().string());
            }
        });
    }

    @FXML
    private void JournalReload() {
        tableJournal.getItems().clear();
        loadJournals();
    }
    @FXML
    private void JournalAdd() {
        app.showJournalWindow(Actions.Add);
    }
    @FXML
    private void JournalDelete() {
        app.showJournalWindow(Actions.Delete);
    }
    @FXML
    private void JournalUpdate() {
        app.showJournalWindow(Actions.Update);
    }


    @FXML
    private void ClientReload() {
        tableClients.getItems().clear();
        loadClients();
    }
    @FXML
    private void ClientAdd() {
        app.showClientWindow(Actions.Add);
    }
    @FXML
    private void ClientDelete() {
        app.showClientWindow(Actions.Delete);
    }
    @FXML
    private void ClientUpdate() {
        app.showClientWindow(Actions.Update);
    }



    @FXML
    private void BookReload() {
        tableBooks.getItems().clear();
        loadBooks();
    }
    @FXML
    private void BookAdd() {
        app.showBookWindow(Actions.Add);
    }
    @FXML
    private void BookDelete() {
        app.showBookWindow(Actions.Delete);
    }
    @FXML
    private void BookUpdate() {
        app.showBookWindow(Actions.Update);
    }

    @FXML
    private void TypeReload() {
        tableTypes.getItems().clear();
        loadBookTypes();
    }
    @FXML
    private void TypeAdd() {
        app.showBookTypeWindow(Actions.Add);
    }
    @FXML
    private void TypeDelete() {
        app.showBookTypeWindow(Actions.Delete);
    }
    @FXML
    private void TypeUpdate() {
        app.showBookTypeWindow(Actions.Update);
    }



}

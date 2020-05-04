package UserInterface;

import DataBase.Product;
import DataBase.DbHandler;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;

public class Controller {
    private Main mainApp;
    private DbHandler dbHandler;
    private ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    private TableView<Product> tableView;

    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableColumn<Product, String> prodIdColumn;

    @FXML
    private TableColumn<Product, String> titleColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TextField fieldNumber;

    @FXML
    private TextField fieldFrom;

    @FXML
    private TextField fieldTo;

    @FXML
    private TextField fieldCost;

    @FXML
    private Text textLogin;

    @FXML
    private Button btnGenerate;

    @FXML
    private Button btnUpdatePrice;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnFilter;

    @FXML
    private Button btnShowAll;

    @FXML
    private Button btnSignOut;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        prodIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProdId()));
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getCost()).asObject());
        try {
            DbHandler dbHandler = DbHandler.getInstance();
            this.dbHandler = dbHandler;
        } catch (SQLException e){
            e.printStackTrace();
        }
        dbHandler.createTable();
        dbHandler.clearTable();
        tableView.setItems(products);
    }

    @FXML
    void onClickAdd() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(mainApp.getClass().getResource("/UserInterface//fxml/NewProduct.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            dialogStage.setScene(new Scene(fxmlLoader.load()));
            dialogStage.setTitle("New Product");
            dialogStage.setResizable(false);

            NewProductController controller = fxmlLoader.getController();
            controller.setProduct(new Product());
            controller.setStage(dialogStage);
            dialogStage.showAndWait();

            if (controller.isWasAdded()) {
                //dbHandler.addProduct(controller.getProduct());
                if (!dbHandler.addProduct(controller.getProduct())){
                    createAlertError("Такой товар уже есть");
                }
                products.setAll(dbHandler.getAllProducts());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            createAlertError(e.getMessage());
        }
    }

    @FXML
    void onClickUpdatePrice() {
        try {
            Product product = tableView.getSelectionModel().getSelectedItem();
            if (product == null) {
                return;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(mainApp.getClass().getResource("/UserInterface//fxml/UpdatePrice.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            dialogStage.setScene(new Scene(fxmlLoader.load()));
            dialogStage.setTitle("Update Price");
            dialogStage.setResizable(false);

            UpdatePriceController controller = fxmlLoader.getController();
            controller.setProduct(product);
            controller.setStage(dialogStage);
            dialogStage.showAndWait();

            if (controller.isWasUpdated()) {
                dbHandler.updatePrice(controller.getProduct().getTitle(), controller.getProduct().getCost());
                products.setAll(dbHandler.getAllProducts());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            createAlertError(e.getMessage());
        }
    }

    @FXML
    void onClickDelete() {
        Product product = tableView.getSelectionModel().getSelectedItem();
        if (product == null) {
            return;
        }
        dbHandler.deleteProduct(product.getTitle());
        products.setAll(dbHandler.getAllProducts());
    }

    @FXML
    void onClickFilter() {
        double from;
        double to;
        try {
            from = extractDouble(fieldFrom);
            to = extractDouble(fieldTo);
        } catch (IllegalArgumentException e) {
            createAlertWarning(e.getMessage());
            fieldFrom.requestFocus();
            return;
        } catch (RuntimeException e) {
            fieldFrom.requestFocus();
            return;
        }

        if (from > to) {
            createAlertWarning("From must be lower then To.");
            fieldFrom.requestFocus();
            return;
        }

        try {
            products.setAll(dbHandler.getProductsFromInterval(from, to));
            fieldFrom.clear();
            fieldTo.clear();
        } catch (RuntimeException e) {
            createAlertError(e.getMessage() + "\n" + e.getCause().getMessage());
        }
    }

    @FXML
    void onClickGenerate() {
        try {
            int amount = extractInteger(fieldNumber);
            dbHandler.generateTable(amount);
            products.setAll(dbHandler.getAllProducts());
            fieldNumber.clear();
        } catch (IllegalArgumentException e) {
            createAlertWarning(e.getMessage());
            fieldNumber.requestFocus();
        } catch (RuntimeException e) {
            fieldNumber.requestFocus();
        }
    }
    @FXML
    void onClickGetCost(){
        try {
            if (fieldCost.getText().isEmpty()) {
                throw new RuntimeException();
            }
            String title = fieldCost.getText();
            products.setAll(dbHandler.getCost(title));
            fieldCost.clear();
            if (dbHandler.getCost(title).equals(Collections.emptyList())){
                createAlertWarning("Такого товара нет");
            }
        } catch (IllegalArgumentException e) {
            createAlertWarning(e.getMessage());
            fieldCost.requestFocus();
        } catch (RuntimeException e) {
            fieldCost.requestFocus();
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


    @FXML
    void onClickShowAll() {
        products.setAll(dbHandler.getAllProducts());
    }

    public void provideApp(Main mainApp) {
        this.mainApp = mainApp;
    }


    private void createAlertError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Error");
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void createAlertWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Warning");
        alert.setHeaderText("Check your input");
        alert.setContentText(message);
        alert.showAndWait();
    }
}


package DataBase;
import org.sqlite.JDBC;
import org.sqlite.SQLiteException;
import java.sql.*;
import java.util.*;

public class DbHandler {

    private static final String CON_STR = "jdbc:sqlite:products.db";
    private static DbHandler instance = null;

    public static synchronized DbHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DbHandler();
        return instance;
    }

    private Connection connection;

    private DbHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
    }


    public void createTable() {
        try (Statement statement = this.connection.createStatement())  {
            statement.execute("CREATE TABLE IF NOT EXISTS  " + "Products" + "(" +
                    "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "prodid VARCHAR(70) NOT NULL, " +
                    "title VARCHAR(70) NOT NULL, " +
                    "cost DOUBLE NOT NULL, " +
                    "UNIQUE (id), UNIQUE(prodid), UNIQUE(title))");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create table.", e);
        }
    }

    public void clearTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM Products");
            statement.execute("DELETE FROM sqlite_sequence WHERE NAME = 'Products'");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear table.", e);
        }
    }

    public void generateTable(int n) {
        try (Statement statement = this.connection.createStatement()) {
            for (int i = 0; i < n; i++){
                addProduct(new Product("товар" + (1 + (int) (Math.random() * 10000)), 1 + (int) (Math.random() * 1000)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        try (Statement statement = this.connection.createStatement()) {
            List<Product> products = new ArrayList<Product>();
            ResultSet resultSet = statement.executeQuery("SELECT id, prodid, title, cost FROM products");
            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt("id"),
                        resultSet.getString("prodid"),
                        resultSet.getString("title"),
                        resultSet.getDouble("cost")));
            }
            return products;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean addProduct(Product product) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO Products(`prodid`, `title`, `cost`) " +
                        "VALUES(?, ?, ?)")) {
            statement.setObject(1, product.prodid);
            statement.setObject(2, product.title);
            statement.setObject(3, product.cost);
            statement.execute();
            return true;
        } catch (SQLiteException e) {
            //System.out.println("Такой товар уже есть");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public void deleteProduct(String title) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM Products WHERE title = ?")) {
            statement.setObject(1, title);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getCost(String title) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "SELECT * FROM " + "Products" + " WHERE title = ?")) {
            statement.setString(1, title);
            List<Product> products = new ArrayList<Product>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt("id"),
                        resultSet.getString("prodid"),
                        resultSet.getString("title"),
                        resultSet.getDouble("cost")));
            }
            //if (resultSet.next()) {
                //int cost = resultSet.getInt("cost");
                //System.out.println("Cost = " + cost);
           // } else {
                //System.out.println("Такого товара нет");
            //}
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void updatePrice(String title, double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Incorrect cost, cost should be positive!");
        }
        try (PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE " + "Products" + " SET cost = ? WHERE title = ?")){
            statement.setDouble(1, price);
            statement.setString(2, title);
            if (statement.executeUpdate() == 0) {
                throw new IllegalArgumentException("Такого товара нет");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update table.", e);
        }
    }

    public List<Product> getProductsFromInterval(double priceFrom, double priceTo) {
        try (Statement statement = this.connection.createStatement()) {
            List<Product> products = new ArrayList<Product>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + "Products" +
                    " WHERE cost BETWEEN " + priceFrom +" AND " + priceTo);
            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt("id"),
                        resultSet.getString("prodid"),
                        resultSet.getString("title"),
                        resultSet.getDouble("cost")));
            }
            return products;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get data from table.", e);
        }
    }
}
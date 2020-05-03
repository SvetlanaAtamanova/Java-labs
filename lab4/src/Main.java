import java.sql.SQLException;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Enter the number of products");
            Scanner scanner = new Scanner(System.in);
            int n;
            do {
                try {
                    n = scanner.nextInt();
                    if (n < 0) {
                        System.out.println("Enter positive number");
                        continue;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input");
                    scanner.nextLine();
                    continue;
                }
                break;
            } while (true);

            DbHandler dbHandler = DbHandler.getInstance();
            dbHandler.createTable();
            dbHandler.clearTable();
            dbHandler.generateTable(n);
            List<Product> products = dbHandler.getAllProducts();
            for (Product product : products) {
                System.out.println(product.toString());
            }
            boolean condition = true;
            while (condition) {
                System.out.println("Enter the command");
                String command = scanner.next();
                try {
                    switch (command) {
                        case "/add": {
                            String title = scanner.next();
                            double cost = scanner.nextDouble();
                            if (cost <= 0) {
                                System.out.println("Incorrect cost, cost should be positive!");
                                break;
                            }
                            dbHandler.addProduct(new Product(title, cost));
                            break;
                        }
                        case "/delete": {
                            String title = scanner.next();
                            dbHandler.deleteProduct(title);
                            break;
                        }
                        case "/show_all": {
                            products = dbHandler.getAllProducts();
                            for (Product product : products) {
                                System.out.println(product.toString());
                            }
                            break;
                        }
                        case "/price": {
                            String title = scanner.next();
                            dbHandler.getCost(title);
                            break;
                        }
                        case "/change_price": {
                            String title = scanner.next();
                            double cost = scanner.nextDouble();
                            if (cost < 0) {
                                System.out.println("Incorrect cost, cost should be positive!");
                                break;
                            }
                            dbHandler.updatePrice(title, cost);
                            break;
                        }
                        case "/filter_by_price": {
                            double min = scanner.nextDouble();
                            double max = scanner.nextDouble();
                            if (max < 0 || min < 0) {
                                System.out.println("Incorrect cost, cost should be positive!");
                                break;
                            }
                            if (max < min) {
                                System.out.println("The right border should be greater than the left");
                                break;
                            }
                            products = dbHandler.getProductsFromInterval(min, max);
                            for (Product product : products) {
                                System.out.println(product.toString());
                            }
                            break;
                        }
                        case "/finish":
                            System.out.println("The end");
                            condition = false;
                            break;
                        default:
                            System.out.println("Invalid command, try again");
                            break;
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid input");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
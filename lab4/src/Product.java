import java.util.UUID;

public class Product {
    public int id;
    public String prodid;
    public String title;
    public double cost;

    public Product(int id, String prodid, String title, double cost) {
        if (cost <= 0) {
            throw new IllegalArgumentException("Incorrect cost, cost should be positive!");
        }
        this.id = id;
        this.prodid = prodid;
        this.title = title;
        this.cost = cost;
    }

    public Product(String title, double cost) {
        this(1, UUID.randomUUID().toString(), title, cost);
    }


    @Override
    public String toString() {
        return String.format("ID: %s | ProdID: %s | Title:  %s | Cost: %s",
                this.id, this.prodid, this.title, this.cost);
    }
}
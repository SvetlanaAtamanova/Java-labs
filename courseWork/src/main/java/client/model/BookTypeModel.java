package client.model;

import java.util.Objects;

public class BookTypeModel {
    private long id;
    private String name;
    private long count;
    private double fine;
    private long dayCount;

    public BookTypeModel() {
    }

    public BookTypeModel(String name, long count, double fine, long dayCount) {
        this.name = name;
        this.count = count;
        this.fine = fine;
        this.dayCount = dayCount;
    }

    public BookTypeModel(long id, String name, long count, double fine, long dayCount) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.fine = fine;
        this.dayCount = dayCount;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getCount() {
        return count;
    }

    public double getFine() {
        return fine;
    }

    public long getDayCount() {
        return dayCount;
    }

    public void updateCount(long countOfBook) {
        this.count += countOfBook;
    }

    @Override
    public String toString() {
        return  name;
    }


}

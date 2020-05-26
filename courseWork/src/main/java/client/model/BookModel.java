package client.model;

import java.util.Objects;

public class BookModel {
    private long id;
    private String name;
    private long count;
    private BookTypeModel type;

    public BookModel() {
    }

    public BookModel(String name, long count, BookTypeModel type) {
        this.name = name;
        this.count = count;
        this.type = type;
    }

    public BookModel(long id, String name, long count, BookTypeModel type) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.type = type;
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

    public BookTypeModel getType() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }


}

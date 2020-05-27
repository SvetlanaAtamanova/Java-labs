package client.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

public class JournalModel {
    private long id;
    private BookModel book;
    private ClientModel client;
    private String dateBeg;
    private String dateEnd;
    private String dateRet;

    public JournalModel() {
    }

    public JournalModel(BookModel book, ClientModel client, String dateBeg, String dateEnd, String dateRet) {
        this.book = book;
        this.client = client;
        this.dateBeg = dateBeg;
        this.dateEnd = dateEnd;
        this.dateRet = dateRet;
    }

    public JournalModel(BookModel book, ClientModel client, String dateBeg, String dateEnd) {
        this.book = book;
        this.client = client;
        this.dateBeg = dateBeg;
        this.dateEnd = dateEnd;
    }
    public JournalModel(long id, BookModel book, ClientModel client, String dateBeg, String dateEnd) {
        this.id = id;
        this.book = book;
        this.client = client;
        this.dateBeg = dateBeg;
        this.dateEnd = dateEnd;
    }

    public JournalModel(long id, BookModel book, ClientModel client, String dateBeg, String dateEnd, String dateRet) {
        this.id = id;
        this.book = book;
        this.client = client;
        this.dateBeg = dateBeg;
        this.dateEnd = dateEnd;
        this.dateRet = dateRet;
    }

    public long getId() {
        return id;
    }

    public BookModel getBook() {
        return book;
    }

    public ClientModel getClient() {
        return client;
    }

    public String getDateBeg() {
        return dateBeg;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getDateRet() {
        return dateRet;
    }

    @Override
    public String toString() {
        return "JournalModel{" +
                "id=" + id +
                ", book=" + book +
                ", client=" + client +
                ", dateBeg=" + dateBeg +
                ", dateEnd=" + dateEnd +
                ", dateRet=" + dateRet +
                '}';
    }

  /*  @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JournalModel)) return false;
        JournalModel that = (JournalModel) o;
        return getId() == that.getId() &&
                Objects.equals(getBook(), that.getBook()) &&
                Objects.equals(getClient(), that.getClient()) &&
                Objects.equals(getDateBeg(), that.getDateBeg()) &&
                Objects.equals(getDateEnd(), that.getDateEnd()) &&
                Objects.equals(getDateRet(), that.getDateRet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBook(), getClient(), getDateBeg(), getDateEnd(), getDateRet());
    }*/
}

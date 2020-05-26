package main.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
@Table(name = "journal")
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Book id is compulsory")
    @ManyToOne(targetEntity = Books.class)
    private Books book;

    @NotBlank(message = "Client id is compulsory")
    @ManyToOne(targetEntity = Clients.class)
    private Clients client;

    @NotBlank(message = "Date is compulsory")
    private String dateBeg;

    private String dateEnd;

    private String dateRet;

    public Journal(Books book, Clients client, String dateBeg, String dateEnd){
        this.book = book;
        this.client = client;
        this.dateBeg = dateBeg;
        this.dateEnd = dateEnd;
    }

    public Journal(Books book, Clients client, String dateBeg, String dateEnd, String dateRet){
        this.book = book;
        this.client = client;
        this.dateBeg = dateBeg;
        this.dateEnd = dateEnd;
        this.dateRet = dateRet;
    }


}


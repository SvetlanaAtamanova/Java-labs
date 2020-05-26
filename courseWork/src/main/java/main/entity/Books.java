package main.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 50, unique = true)
    @NotBlank(message = "Book name is compulsory")
    @Size(max = 50, message = "Book name can't be longer than 50 characters")
    private String name;

    @Min(value = 0, message = "Count mustn't be negative")
    private long count;

    @ManyToOne (targetEntity = BookTypes.class)
    private BookTypes type;

    public Books(String name, long count, BookTypes type){
        this.name = name;
        this.count = count;
        this.type = type;
    }


}

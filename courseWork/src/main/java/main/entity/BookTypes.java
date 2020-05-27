package main.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@Data
@Table(name = "bookTypes")
public class BookTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 50, unique = true)
    //@NotBlank(message = "Book type is compulsory")
    @Size(max = 50, message = "Book type can't be longer than 50 characters")
    private String name;

    @Min(value = 0, message = "Count mustn't be negative")
    private long count;

    @Min(value = 0, message = "Fine mustn't be negative")
    private double fine;

    @Min(value = 0, message = "Day count mustn't be negative")
    private long dayCount;


    public BookTypes(String name, long count,double fine, long dayCount){
        this.name = name;
        this.count = count;
        this.fine = fine;
        this.dayCount = dayCount;
    }


}

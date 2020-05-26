package main.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@Data
@Table(name = "Clients")
public class Clients {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 20)
    @NotBlank(message = "First name is compulsory")
    @Size(max = 20, message = "First name can't be longer than 20 characters")
    private String firstName;

    @Column(length = 20)
    @NotBlank(message = "Last name is compulsory")
    @Size(max = 20, message = "Last name can't be longer than 20 characters")
    private String lastName;

    @Column(length = 20)
    @NotBlank(message = "Father name is compulsory")
    @Size(max = 20, message = "Father name can't be longer than 20 characters")
    private String fatherName;

    @Column(length = 20)
    @NotBlank(message = "Passport seria is compulsory")
    @Size(max = 20, message = "Passport seria can't be longer than 20 characters")
    private String passportSeria;

    @Column(length = 20)
    @NotBlank(message = "Passport number is compulsory")
    @Size(max = 20, message = "Passport number can't be longer than 20 characters")
    private String passportNum;

    public Clients(String firstName, String lastName, String fatherName, String passportSeria, String passportNum){
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.passportSeria = passportSeria;
        this.passportNum = passportNum;
    }



}

package client.model;
import java.util.Objects;

public class ClientModel {
    private long id;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String passportSeria;
    private String passportNum;

    public ClientModel(){
    }
    public ClientModel(String firstName, String lastName, String fatherName, String passportSeria, String passportNum){
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.passportSeria = passportSeria;
        this.passportNum = passportNum;
    }

    public ClientModel(long id, String firstName, String lastName, String fatherName, String passportSeria, String passportNum){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.passportSeria = passportSeria;
        this.passportNum = passportNum;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getPassportSeria() {
        return passportSeria;
    }

    public String getPassportNum() {
        return passportNum;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + fatherName ;
    }


}

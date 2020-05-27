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

  /*  @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientModel)) return false;
        ClientModel that = (ClientModel) o;
        return getId() == that.getId() &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getFatherName(), that.getFatherName()) &&
                Objects.equals(getPassportSeria(), that.getPassportSeria()) &&
                Objects.equals(getPassportNum(), that.getPassportNum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getFatherName(), getPassportSeria(), getPassportNum());
    }*/
}

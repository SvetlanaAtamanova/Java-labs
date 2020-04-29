public class Student {
    private  String subject;
    private int numberOfStudent;
    private int labsCount;
    private static int counter = 0;


    public void decreaseLabs() {

        labsCount -= 5;
    }

    public String getSubject()
    {
        return subject;
    }

    public int getLabsCount()
    {
        return labsCount;
    }

    public int getNumberOfStudent() {

        return numberOfStudent;
    }

    public Student (String subject, int labsCount) {
        this.subject = subject;
        this.labsCount = labsCount;
        ++counter;
        this.numberOfStudent = counter;
    }
}

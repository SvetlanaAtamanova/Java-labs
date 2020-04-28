public class Student {
    private String subjectName;
    private int numberOfStudent;
    private int labsCount;
    private static int number = 0;


    public void decreaseLabs() {
        labsCount -= 5;
    }

    public String getSubject() {
        return subjectName;
    }

    public int getLabsCount() {
        return labsCount;
    }

    public int getNumberOfStudent() {
        return numberOfStudent;
    }

    public Student(String subject, int labsCount) {
        this.subjectName = subject;
        this.labsCount = labsCount;
        ++number;
        this.numberOfStudent = number;
    }
}
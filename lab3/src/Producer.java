import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private final BlockingQueue<Student> queueOfStudents;
    private int amount;

    @Override
    public void run() {

        try {
            for (int i = 0; i < amount; ++i) {
                generate(i);
            }
            queueOfStudents.put(new Student("end", 0));
            queueOfStudents.put(new Student("end", 0));
            queueOfStudents.put(new Student("end", 0));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private void generate(int i) throws InterruptedException {

        int labsCount_ = (int) (Math.random() * 3);
        int subjectName_ = (int) (Math.random() * 3);
        int labsCount;
        String subjectName;
        switch (labsCount_) {
            case 0:
                labsCount = 10;
                break;
            case 1:
                labsCount = 20;
                break;
            default:
                labsCount = 100;
        }

        switch (subjectName_) {
            case 0:
                subjectName = "ООП";
                break;
            case 1:
                subjectName = "Физика";
                break;
            default:
                subjectName = "Математика";
        }
        try {
            System.out.println("Студент № " + (i+1) + " " + subjectName + " " + labsCount + " сгенерирован");
            queueOfStudents.put(new Student(subjectName, labsCount));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    public Producer(BlockingQueue<Student> queueOfStudents, int amount) {
        this.queueOfStudents = queueOfStudents;
        this.amount = amount;
    }
}
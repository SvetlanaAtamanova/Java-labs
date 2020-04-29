import java.util.concurrent.BlockingQueue;
import java.util.HashMap;


public class Producer implements Runnable {

    private final HashMap<String, BlockingQueue<Student>>  queueOfStudents;
    private int amount;
    private final Object monitor;


    @Override
    public void run() {
        try {
            for (int i = 0; i < amount; ++i) {
                synchronized (monitor) {
                    while (queueOfStudents.get("ООП").size() + queueOfStudents.get("Физика").size() + queueOfStudents.get("Математика").size() >= 10) {
                        System.out.println(" waiting");
                        monitor.wait();
                    }
                    generate(i);

                }
            }
            Student student1 = new Student("Математика", -1);
            queueOfStudents.get(student1.getSubject()).put(student1);
            Student student2 = new Student("Физика", -1);
            queueOfStudents.get(student2.getSubject()).put(student2);
            Student student3 = new Student("ООП", -1);
            queueOfStudents.get(student3.getSubject()).put(student3);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void generate(int i) {
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
                subjectName = "Математика";
                break;
            case 1:
                subjectName = "ООП";
                break;
            default:
                subjectName = "Физика";
        }
        try {
            System.out.println("Студент № " + (i+1) + " " + subjectName + " " + labsCount + " сгенерирован");
            Student student = new Student(subjectName, labsCount);
            queueOfStudents.get(student.getSubject()).put(student);
            monitor.notifyAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Producer(HashMap<String, BlockingQueue<Student>> queueOfStudents, int amount, Object mon) {
        this.queueOfStudents = queueOfStudents;
        this.amount = amount;
        this.monitor = mon;
    }
}

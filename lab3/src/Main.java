import java.util.InputMismatchException;
import java.util.concurrent.*;
import java.util.HashMap;


public class Main {

    public static void main(String[] args) {
        int numberOfStudents;
        Object monitor = new Object();

        do {
            try {
                numberOfStudents = Integer.parseInt(args[0]);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                continue;
            }
            break;
        } while (true);

        HashMap<String, BlockingQueue<Student>> queueOfStudents= new HashMap<>();
        queueOfStudents.put("ООП", new LinkedBlockingQueue<>());
        queueOfStudents.put("Физика", new LinkedBlockingQueue<>());
        queueOfStudents.put("Математика", new LinkedBlockingQueue<>());
        new Thread(new Producer(queueOfStudents, numberOfStudents, monitor)).start();
        new Thread(new Robot(queueOfStudents.get("ООП"), "ООП",monitor)).start();
        new Thread(new Robot(queueOfStudents.get("Физика"), "Физика", monitor)).start();
        new Thread(new Robot(queueOfStudents.get("Математика"), "Математика", monitor)).start();
    }
}


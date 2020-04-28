import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import java.util.InputMismatchException;

public class Main {

    public static void main(String[] args) {
        int numberOfStudents;

        do {
            try {
                 numberOfStudents = Integer.parseInt(args[0]);
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input");
                continue;
            }
            break;
        } while (true);
        BlockingQueue<Student> queueOfStudents = new LinkedBlockingQueue<>(10);
        new Thread(new Producer(queueOfStudents, numberOfStudents)).start();
        new Thread(new Robot(queueOfStudents, "Физика")).start();
        new Thread(new Robot(queueOfStudents, "ООП")).start();
        new Thread(new Robot(queueOfStudents, "Математика")).start();
    }
}
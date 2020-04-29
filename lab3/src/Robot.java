import java.util.concurrent.BlockingQueue;

public class Robot implements Runnable {

    public BlockingQueue<Student> queueOfStudents;
    private  String subjectName;
    private final Object monitor;


    @Override
    public void run() {
        try {
            while (true) {
                System.out.println(Thread.currentThread().getName());
                Student currStudent;
                synchronized (monitor) {
                    currStudent = queueOfStudents.peek();
                    if (currStudent == null){
                        monitor.wait();
                    }
                    else {
                        currStudent = queueOfStudents.take();
                        monitor.notifyAll();
                        if (currStudent.getLabsCount() == -1) {
                            System.out.println("Робот по " + subjectName + " закончил проверку");
                            break;
                        }
                    }
                }
                process(currStudent);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void process(Student currStudent) throws InterruptedException {
        while( currStudent.getLabsCount() > 0) {
            currStudent.decreaseLabs();
            System.out.println("Робот по " + subjectName + " проверил 5 работ у студента №:" + currStudent.getNumberOfStudent()
                    + " осталось:" + currStudent.getLabsCount() + " работ");
            Thread.sleep(100);
        }
    }

    public Robot(BlockingQueue<Student> queueOfStudents, String subjectName, Object mon) {
        this.queueOfStudents = queueOfStudents;
        this.subjectName = subjectName;
        this.monitor = mon;
    }


}

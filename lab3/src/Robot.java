import java.util.concurrent.BlockingQueue;

public class Robot implements Runnable {

    private final BlockingQueue<Student> queueOfStudents;
    private String subjectName;

    @Override
    public void run() {

        try {
            while (true) {
                Student currStudent = queueOfStudents.peek();
                if (currStudent != null) {
                    if (currStudent.getSubject().equals(this.subjectName)) {
                        Student tmp = queueOfStudents.take();
                        System.out.println("Робот по " + subjectName + " проверяет студента №:" + tmp.getNumberOfStudent());
                        while (tmp.getLabsCount() != 0) {
                            process(tmp);
                        }
                    }
                    else if (currStudent.getSubject().equals("end")){
                        System.out.println("Робот по " + subjectName + " закончил проверку");
                        break;
                    }
                }

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    private void process(Student currStudent) throws InterruptedException {
        for (int i = 0; i < currStudent.getLabsCount() / 5; i++) {
            System.out.println("Робот по " + subjectName + " проверил 5 работ");
            currStudent.decreaseLabs();
            Thread.sleep(100);
        }
    }

    public Robot(BlockingQueue<Student> queueOfStudents, String subjectName) {
        this.queueOfStudents = queueOfStudents;
        this.subjectName = subjectName;
    }
}
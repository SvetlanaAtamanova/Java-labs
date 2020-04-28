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
                        currStudent = queueOfStudents.take();
                        System.out.println("Робот по " + subjectName + " начинает проверять студента №:" + currStudent.getNumberOfStudent());
                        while (currStudent.getLabsCount() != 0) {
                            process(currStudent);
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
            currStudent.decreaseLabs();
            System.out.println("Робот по " + subjectName + " проверил 5 работ у студента №:" + currStudent.getNumberOfStudent()
                    + " осталось:" + currStudent.getLabsCount() + " работ");
            Thread.sleep(100);
        }
    }

    public Robot(BlockingQueue<Student> queueOfStudents, String subjectName) {
        this.queueOfStudents = queueOfStudents;
        this.subjectName = subjectName;
    }
}
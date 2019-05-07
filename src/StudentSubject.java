public class StudentSubject {

    private Integer markValue;

    private Integer interestValue;

    private Student student;

    private Subject subject;

    private Double userValue;

    public StudentSubject(Integer markValue, Integer interestValue, Student student, Subject subject) {
        this.markValue = markValue;
        this.interestValue = interestValue;
        this.student = student;
        this.subject = subject;
    }

    public StudentSubject(Integer interestValue, Student student, Subject subject) {
        this.interestValue = interestValue;
        this.student = student;
        this.subject = subject;
    }

    public Integer getMarkValue() {
        return markValue;
    }

    public void setMarkValue(Integer markValue) {
        this.markValue = markValue;
    }

    public Integer getInterestValue() {
        return interestValue;
    }

    public void setInterestValue(Integer interestValue) {
        this.interestValue = interestValue;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Double getUserValue() {
        return userValue;
    }

    public void setUserValue(Double userValue) {
        this.userValue = userValue;
    }

    @Override
    public String toString() {
        return getSubject().getTitle();
    }
}
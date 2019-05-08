package entity;

public class StudentSubject {

    private Integer markValue;

    private Integer interestValue = 2;

    private String studentName;

    private Subject subject;

    private Double userValue;

    public StudentSubject(Integer markValue, Integer interestValue, Student student, Subject subject) {
        this.markValue = markValue;
        this.interestValue = interestValue;
        this.studentName = student.getName();
        this.subject = subject;
        student.getEduGraph().getSubjects().add(this);
    }

    public StudentSubject(Integer interestValue, Student student, Subject subject) {
        this.interestValue = interestValue;
        this.studentName = student.getName();
        this.subject = subject;

        student.getEduGraph().getPossibleSubjects(subject.getBlock()).add(this);
        student.getEduGraph().getSubjects().add(this);
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

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        String value = getSubject().getTitle();
        if(userValue != null) {
            value += " with userValue " + String.format("%.2f", userValue);;
        }
        return value;
    }


}
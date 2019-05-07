public class Requirement {

    private String name;

    private Subject subject;

    public Requirement(String name, Subject subject) {
        this.name = name;
        this.subject = subject;
        subject.getRequirements().add(this);
    }
}
package entity;

import java.util.Objects;

public class Requirement {

    private String name;

    private String subjectName;

    public Requirement(String name, Subject subject) {
        this.name = name;
        this.subjectName = subject.getTitle();
        subject.getRequirements().add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Requirement)) {
            return false;
        }
        Requirement requirement = (Requirement) o;
        return Objects.equals(name, requirement.name) &&
                Objects.equals(subjectName, requirement.subjectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
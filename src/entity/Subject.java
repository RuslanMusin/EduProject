package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Subject {

    private String title;

    private Integer semestr;

    private List<Requirement> requirements = new ArrayList<>();

    private List<Requirement> pastRequirements = new ArrayList<>();

    private List<Subject> nextSubjects = new ArrayList<>();

    private List<Subject> pastSubjects = new ArrayList<>();

    public Subject(String title) {
        this.title = title;
    }

    public Subject(String title, Integer semestr) {
        this.title = title;
        this.semestr = semestr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    public List<Requirement> getPastRequirements() {
        return pastRequirements;
    }

    public void setPastRequirements(List<Requirement> pastRequirements) {
        this.pastRequirements = pastRequirements;
    }

    public List<Subject> getNextSubjects() {
        return nextSubjects;
    }

    public void setNextSubjects(List<Subject> nextSubjects) {
        this.nextSubjects = nextSubjects;
    }

    public List<Subject> getPastSubjects() {
        return pastSubjects;
    }

    public void setPastSubjects(List<Subject> pastSubjects) {
        this.pastSubjects = pastSubjects;
    }

    public Integer getSemestr() {
        return semestr;
    }

    public void setSemestr(Integer semestr) {
        this.semestr = semestr;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Subject)) {
            return false;
        }
        Subject subject = (Subject) o;
        return Objects.equals(title, subject.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

}


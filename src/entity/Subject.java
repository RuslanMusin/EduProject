package entity;

import util.SimpleStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Subject {

    private String title;

    private Integer semestr;

    private String block;

    private List<Requirement> requirements = new ArrayList<>();

    private List<Requirement> pastRequirements = new ArrayList<>();

    private List<String> nextSubjects = new ArrayList<>();

    private List<Subject> pastSubjects = new ArrayList<>();

    public Subject(String title) {
        this.title = title;
    }

    public Subject(String title, Integer semestr, String block) {
        this.title = title;
        this.semestr = semestr;
        this.block = block;
        SimpleStorage.eduStandart.getSubjects().add(this);
        if(title.equals(block) && !SimpleStorage.eduStandart.getBlocks().contains(block)) {
            SimpleStorage.eduStandart.getBlocks().add(block);
        }
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
        return SimpleStorage.eduStandart
                .getSubjects()
                .stream()
                .filter(it -> nextSubjects.contains(it.getTitle()))
                .collect(Collectors.toList());
    }

    public void setNextSubjects(List<Subject> nextSubjects) {
        for(Subject subject: nextSubjects) {
            addNextSubject(subject);
        }
    }

    public void addNextSubject(Subject subject) {
        this.nextSubjects.add(subject.getTitle());
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public List<Subject> getPastSubjects() {
        return pastSubjects;
    }

    public List<Subject> getAllPastSubjects() {
        List<Subject> list = new ArrayList<>(getPastSubjects());
        List<Subject> addList;
        for(Subject subject: getPastSubjects()) {
            addList = subject.getAllPastSubjects();
            for(Subject sub: addList) {
                if(!list.contains(sub)) {
                    list.add(sub);
                }
            }
        }
        return list;
    }

    public void setPastSubjects(List<Subject> pastSubjects) {
        this.pastSubjects = pastSubjects;
    }

    public void addToPastSubjects(List<Subject> subjects) {
        this.pastSubjects.addAll(subjects);
        for(Subject sub: subjects) {
            for(Requirement rec: sub.getRequirements()) {
                if(!pastRequirements.contains(rec)) {
                    pastRequirements.add(rec);
                }
            }
            sub.addNextSubject(this);
        }
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


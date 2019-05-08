package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EduGraph {

    private List<StudentSubject> subjects = new ArrayList<>();

    private List<StudentSubject> startedSubjects = new ArrayList<>();

    private Map<String, List<StudentSubject>> possibleSubjects = new HashMap<>();

    public List<StudentSubject> getSubjects() {
        /*List<StudentSubject> list = new ArrayList<>(getStartedSubjects());
        list.addAll(getPossibleSubjects());*/
        return subjects;
    }

    public void setSubjects(List<StudentSubject> subjects) {
        this.subjects.addAll(subjects);
    }

    public List<StudentSubject> getStartedSubjects() {
        return startedSubjects;
    }

    public void setStartedSubjects(List<StudentSubject> startedSubjects) {
        this.startedSubjects = startedSubjects;
    }

    public Map<String, List<StudentSubject>> getPossibleSubjects() {
        return possibleSubjects;
    }

    public List<StudentSubject> getPossibleSubjects(String block) {
        if(!possibleSubjects.containsKey(block)) {
            possibleSubjects.put(block, new ArrayList<>());
        }
        return possibleSubjects.get(block);
    }

    public void setPossibleSubjects(Map<String, List<StudentSubject>> possibleSubjects) {
        this.possibleSubjects = possibleSubjects;
    }

    public void changeToStarted(StudentSubject subject) {
        possibleSubjects.remove(subject.getSubject().getBlock());
        startedSubjects.add(subject);
    }
}
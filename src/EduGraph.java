import java.util.ArrayList;
import java.util.List;

public class EduGraph {

    private List<StudentSubject> subjects = new ArrayList<>();

    private List<StudentSubject> startedSubjects = new ArrayList<>();

    private List<StudentSubject> possibleSubjects = new ArrayList<>();

    public List<StudentSubject> getSubjects() {
        List<StudentSubject> list = new ArrayList<>(getStartedSubjects());
        list.addAll(getPossibleSubjects());
        return list;
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

    public List<StudentSubject> getPossibleSubjects() {
        return possibleSubjects;
    }

    public void setPossibleSubjects(List<StudentSubject> possibleSubjects) {
        this.possibleSubjects = possibleSubjects;
    }
}
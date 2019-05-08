package entity;

import util.SimpleStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EduStandart {

    private List<Subject> subjects = new ArrayList<>();

    private List<String> blocks = new ArrayList<>();

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<String> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<String> blocks) {
        this.blocks = blocks;
    }

    public List<String> getCurrentBlocks() {
        List<String> blocks = SimpleStorage.eduStandart.getSubjects()
                .stream()
                .filter(it -> it.getSemestr() == SimpleStorage.student.getSemestr())
                .map(it -> it.getBlock())
                .distinct()
                .collect(Collectors.toList());
        return blocks;
    }
}

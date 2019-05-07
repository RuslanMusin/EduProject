package entity;

import entity.EduGraph;

public class Student {

    private String name;

    private EduGraph eduGraph;

    private Integer semestr = 1;

    public Student(String name, EduGraph eduGraph) {
        this.name = name;
        this.eduGraph = eduGraph;
    }

    public Student() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EduGraph getEduGraph() {
        return eduGraph;
    }

    public void setEduGraph(EduGraph eduGraph) {
        this.eduGraph = eduGraph;
    }

    public Integer getSemestr() {
        return semestr;
    }

    public void setSemestr(Integer semestr) {
        this.semestr = semestr;
    }
}

import java.util.ArrayList;
import java.util.List;

public class Job {

    private String name;

    private Integer count;

    private List<Requirement> requirements = new ArrayList<>();

    public Job(String name, Integer count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    @Override
    public String toString() {
        return name;
    }
}

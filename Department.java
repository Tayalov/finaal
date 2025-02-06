import java.util.ArrayList;
import java.util.List;

public class Department {
    private int departmentId;
    private String name;
    private Library library;
    private List<Edition> editions;

    public Department(String name, Library library) {
        this.name = name;
        this.library = library;
        this.editions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addEdition(Edition edition) {
        editions.add(edition);
    }

    public void removeEdition(Edition edition) {
        editions.remove(edition);
    }

    public int getNumberOfEditions() {
        return editions.size();
    }

    public List<Edition> getEditions() {
        return editions;
    }
}


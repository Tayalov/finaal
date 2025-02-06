import java.util.ArrayList;

public class Department {
    private String name;
    private Library library;
    private ArrayList<Edition> editions;

    // Constructor это специальный метод, который вызывается при создании объекта класса.
    public Department(String name, Library library) {
        this.name = name;
        this.library = library;
        this.editions = new ArrayList<>();
    }

    public void addEdition(Edition edition) {
        editions.add(edition);
    }
    public void removeEdition(Edition edition) {
        editions.remove(edition);
    }

    public ArrayList<Edition> getEditions() {
        return editions;
    }
    public void displayDepartmentInfo() {
        System.out.println("  Department: " + name);
        System.out.println("  Number of publications: " + editions.size());
        for (Edition edition : editions) {
            System.out.println("    - " + edition.getTitle() + " (" + edition.getYear() + ")");
        }
    }
    public Library getLibrary() {
        return library;
    }
}

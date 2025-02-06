import java.util.List;

public class Main {
    public static void main(String[] args) {
        Library library = new Library("Kazakh national Library", 100);

        Department dept1 = new Department("Roman ", library);
        Department dept2 = new Department("Kazakh Adebi", library);
        library.addDepartment(dept1);
        library.addDepartment(dept2);

        Edition pb1 = new Edition("Bakytsiz Zhamal", 1910, dept1);
        Edition pb3 = new Edition("Abai", 1935, dept1);
        Edition pb2 = new Edition("Bir kem dunie", 1998, dept2);
        Edition pb4 = new Edition("Ai men Aisha",1998,dept2);

        dept1.addEdition(pb1);
        dept1.addEdition(pb2);
        dept2.addEdition(pb3);
        dept2.addEdition(pb4);

        library.displayLibraryInfo();
        library.removeDepartment(dept2);

        System.out.println("\nAfter removing Kazakh Adebi Department:");
        library.displayLibraryInfo();

        List<Edition> searchResults = library.searchPublicationsByYear(1998);
        System.out.println("\nSearch results for publications from year 1998:");
        for (Edition edition : searchResults) {
            System.out.println(edition.getTitle() + " in " + edition.getDepartment().getLibrary().getDepartments().get(0).getLibrary().getDepartments());
        }
    }
}

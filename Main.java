public class Main {
    public static void main(String[] args) {
        Library library = new Library("Central Library");

        Department sciFi = new Department("Science Fiction", library);
        Department history = new Department("History", library);

        library.addDepartment(sciFi);
        library.addDepartment(history);

        Edition dune = new Edition("Dune", "Frank Herbert", 1965, sciFi);
        Edition neuromancer = new Edition("Neuromancer", "William Gibson", 1984, sciFi);
        Edition historyBook = new Edition("History of Rome", "T. J. Cornell", 1995, history);

        sciFi.addEdition(dune);
        sciFi.addEdition(neuromancer);
        history.addEdition(historyBook);

        // Выводим данные о библиотеках и их изданиях
        for (Department dept : library.getDepartments()) {
            System.out.println("Department: " + dept.getName());
            System.out.println("Number of editions: " + dept.getNumberOfEditions());
        }

        // Поиск публикаций по году
        library.searchEditionsByYear(1984);
    }
}

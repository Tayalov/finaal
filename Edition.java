public class Edition {
    private int editionId;
    private String name;
    private String author;
    private int publicationYear;
    private Department department;

    public Edition(String name, String author, int publicationYear, Department department) {
        this.name = name;
        this.author = author;
        this.publicationYear = publicationYear;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public Department getDepartment() {
        return department;
    }
}

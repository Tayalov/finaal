public class Edition {
    private String title;
    private int year;
    private Department department;

    public Edition(String title, int year, Department department) {
        this.title = title;
        this.year = year;
        this.department = department;
    }

    public String getTitle() {
        return title;
    }
    public int getYear() {
        return year;
    }
    public Department getDepartment() {
        return department;
    }
}


import java.util.Objects;
public class Category implements Comparable<Category> {
    private String id;
    private String name;
    private String type; // "INCOME" or "EXPENSE"

    public Category(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Category other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return "Category{id='" + id + "', name='" + name + "', type='" + type + "'}";
    }
}

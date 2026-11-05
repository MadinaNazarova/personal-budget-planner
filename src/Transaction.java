
import java.time.LocalDate;
import java.util.Objects;

public class Transaction implements Comparable<Transaction> {
    private String id;
    private String description;
    private double amount;
    private LocalDate date;
    private Category category;
    public Transaction(String id, String description, double amount, LocalDate date, Category category) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    public String getId() { return id; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }
    public Category getCategory() { return category; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Natural ordering: sorted by date (oldest to newest)
    @Override
    public int compareTo(Transaction other) {
        return this.date.compareTo(other.date);
    }

    @Override
    public String toString() {
        return String.format("Transaction{id='%s', desc='%s', amount=%.2f, date=%s, category=%s}",
                id, description, amount, date, category.getName());
    }
}

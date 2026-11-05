import java.util.Comparator;

public class TransactionDescriptionComparator implements Comparator<Transaction> {
    @Override
    public int compare(Transaction t1, Transaction t2) {
        return t1.getDescription().compareToIgnoreCase(t2.getDescription());
    }
}
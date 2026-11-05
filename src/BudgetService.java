
import java.util.*;
public class BudgetService {
    private final List<Transaction> transactionsList = new ArrayList<>();
    private final Set<Category> categoriesSet = new HashSet<>();

    private final Map<String, Transaction> idIndexMap = new HashMap<>(); // O(1) access by id
    private final Map<String, List<Transaction>> categoryIndexMap = new HashMap<>(); // Grouping by category name

    private final Queue<FinancialRequest> requestQueue = new ArrayDeque<>();

    //  CATEGORY CRUD
    public boolean addCategory(Category category) {
        boolean added = categoriesSet.add(category);
        if (added) {
            categoryIndexMap.putIfAbsent(category.getName(), new ArrayList<>());
        }
        return added;
    }

    public Set<Category> getAllCategories() {
        return categoriesSet;
    }

    //  TRANSACTION CRUD WITH INDEX SYNCHRONIZATION
    public boolean addTransaction(Transaction transaction) {
        if (idIndexMap.containsKey(transaction.getId())) {
            return false; // Duplicate primary key
        }

        // Check if category exists, if not — add it
        if (!categoriesSet.contains(transaction.getCategory())) {
            addCategory(transaction.getCategory());
        }

        // Add to main list
        transactionsList.add(transaction);

        // Index for Map
        idIndexMap.put(transaction.getId(), transaction);
        categoryIndexMap.get(transaction.getCategory().getName()).add(transaction);

        return true;
    }

    public boolean removeTransaction(String id) {
        Transaction tx = idIndexMap.get(id);
        if (tx == null) return false;

        // Synchronized removal from all structures
        transactionsList.remove(tx);
        idIndexMap.remove(id);

        List<Transaction> catTransactions = categoryIndexMap.get(tx.getCategory().getName());
        if (catTransactions != null) {
            catTransactions.remove(tx);
        }

        return true;
    }

    // SEARCH AND FILTERING
// Fast O(1) lookup by primary key
    public Transaction findById(String id) {
        return idIndexMap.get(id);
    }

    // Filtering by category name
    public List<Transaction> filterByCategory(String categoryName) {
        return categoryIndexMap.getOrDefault(categoryName, Collections.emptyList());
    }

    // Get all transactions
    public List<Transaction> getAllTransactions() {
        return transactionsList;
    }

    // QUEUE AND ROLES MANAGEMENT
    public void submitRequest(FinancialRequest request) {
        requestQueue.offer(request);
    }

    public FinancialRequest processNextRequest() {
        return requestQueue.poll(); // FIFO extraction
    }

    public int getQueueSize() {
        return requestQueue.size();
    }

    // ANALYTICS AND REPORTS
    public double getTotalIncome() {
        return transactionsList.stream()
                .filter(t -> t.getCategory().getType().equalsIgnoreCase("INCOME"))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalExpense() {
        return transactionsList.stream()
                .filter(t -> t.getCategory().getType().equalsIgnoreCase("EXPENSE"))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getNetBalance() {
        return getTotalIncome() - getTotalExpense();
    }
}
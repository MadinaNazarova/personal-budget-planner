
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BudgetService service = new BudgetService();
        Scanner scanner = new Scanner(System.in);

    // Prepopulate with initial demo data
    Category salary = new Category("C1", "Salary", "INCOME");
    Category food = new Category("C2", "Food", "EXPENSE");
    Category transport = new Category("C3", "Transport", "EXPENSE");

    service.addCategory(salary);
    service.addCategory(food);
    service.addCategory(transport);

    service.addTransaction(new Transaction("T1", "Project advance", 45000.0, LocalDate.of(2026, 11, 1), salary));
    service.addTransaction(new Transaction("T2", "Grocery shopping", 3200.5, LocalDate.of(2026, 11, 2), food));
    service.addTransaction(new Transaction("T3", "Subway fare", 60.0, LocalDate.of(2026, 11, 3), transport));

    while (true) {
        System.out.println("\n PERSONAL BUDGET PLANNER (JCF PROJECT) ");
        System.out.println("1. Show all transactions (natural order by date)");
        System.out.println("2. Add new transaction");
        System.out.println("3. Remove transaction by ID (synchronized)");
        System.out.println("4. Fast lookup by ID in O(1) [Map]");
        System.out.println("5. Dynamic sorting (Comparators)");
        System.out.println("6. Manage requests via Queue (Roles: Client/Manager)");
        System.out.println("7. Financial analytics and reports");
        System.out.println("0. Exit");
        System.out.print("Select menu option: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.println("\nAll Transactions ");
                List<Transaction> all = service.getAllTransactions();
                Collections.sort(all); // Uses Comparable (by date)
                all.forEach(System.out::println);
                break;

            case "2":
                System.out.print("Enter transaction ID (e.g., T4): ");
                String id = scanner.nextLine();
                System.out.print("Enter description: ");
                String desc = scanner.nextLine();
                System.out.print("Enter amount: ");
                double amount = Double.parseDouble(scanner.nextLine());
                System.out.print("Enter category name (Food, Salary, etc.): ");
                String catName = scanner.nextLine();
                System.out.print("Enter category type (INCOME / EXPENSE): ");
                String catType = scanner.nextLine();

                Category category = new Category("C_" + catName.hashCode(), catName, catType);
                Transaction newTx = new Transaction(id, desc, amount, LocalDate.now(), category);

                if (service.addTransaction(newTx)) {
                    System.out.println("Transaction added successfully!");
                } else {
                    System.out.println("Error: Transaction with this ID already exists.");
                }
                break;

            case "3":
                System.out.print("Enter transaction ID to remove: ");
                String removeId = scanner.nextLine();
                if (service.removeTransaction(removeId)) {
                    System.out.println("Transaction successfully removed from lists and indexes.");
                } else {
                    System.out.println("Transaction not found.");
                }
                break;

            case "4":
                System.out.print("Enter ID for fast O(1) lookup: ");
                String searchId = scanner.nextLine();
                Transaction found = service.findById(searchId);
                if (found != null) {
                    System.out.println("Found: " + found);
                } else {
                    System.out.println("Transaction not found.");
                }
                break;

            case "5":
                System.out.println("Select sorting type:");
                System.out.println("1. By amount (ascending)");
                System.out.println("2. By amount (descending)");
                System.out.println("3. By description (alphabetical)");
                String sortChoice = scanner.nextLine();
                List<Transaction> sortedList = service.getAllTransactions();

                if (sortChoice.equals("1")) {
                    sortedList.sort(new TransactionAmountAscComparator());
                } else if (sortChoice.equals("2")) {
                    sortedList.sort(new TransactionAmountDescComparator());
                } else if (sortChoice.equals("3")) {
                    sortedList.sort(new TransactionDescriptionComparator());
                }
                System.out.println("\nSorted Result ");
                sortedList.forEach(System.out::println);
                break;

            case "6":
                System.out.println("\nFinancial Requests Queue (Queue) ");
                System.out.println("1. Submit request (Role: Client)");
                System.out.println("2. Process next request (Role: Manager)");
                System.out.print("Choice: ");
                String qChoice = scanner.nextLine();
                if (qChoice.equals("1")) {
                    System.out.print("Enter request ID: ");
                    String reqId = scanner.nextLine();
                    System.out.print("Enter request details: ");
                    String details = scanner.nextLine();
                    service.submitRequest(new FinancialRequest(reqId, "CLIENT", details));
                    System.out.println("Request added to Queue. Current queue size: " + service.getQueueSize());
                } else if (qChoice.equals("2")) {
                    FinancialRequest processed = service.processNextRequest();
                    if (processed != null) {
                        System.out.println("Manager processed and extracted request via .poll(): " + processed);
                    } else {
                        System.out.println("Queue is empty.");
                    }
                }
                break;

            case "7":
                System.out.println("\nAnalytics and Reports ");
                System.out.printf("Total Income: %.2f\n", service.getTotalIncome());
                System.out.printf("Total Expense: %.2f\n", service.getTotalExpense());
                System.out.printf("Net Balance: %.2f\n", service.getNetBalance());
                break;

            case "0":
                System.out.println("Exiting program. Goodbye!");
                scanner.close();
                return;

            default:
                System.out.println("Invalid choice, please try again.");
        }
    }
    }
}

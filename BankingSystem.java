// //  -------------------- BANKING SYSTEM PROJECT --------------------
//     Features:
//     BankAccount (Base Class)
//     Savings + Current accounts (Inheritance)
//     Loan Interface + HomeLoan + CarLoan
//     Fully User+Admin-Interactive MENU 
//     Every operation updates totalTransactions
//     Users can:
//         - Deposit
//         - Withdraw
//         - Transfer
//         - Apply Loan
//         - Display Account
//         - Exit
//     Accounts are saved in a file "accounts.txt" 


import java.util.*;
// ------------------ MAIN CLASS WITH MENU SYSTEM ------------------
public class BankingSystem {
    public static void main(String[] args) {
        final String ADMIN_PIN = "admin123";
        Scanner sc = new Scanner(System.in);

        List<BankAccount> accounts = BankAccount.loadAccounts();
        BankAccount currentUser = null;

        int startOption = -1;

        while (startOption != 2) {

            System.out.println("\n====== SYSTEM STARTUP ======");
            System.out.println("1. Admin: Create New Bank Account");
            System.out.println("2. User: Login to Bank System");
            System.out.print("Enter choice: ");
            startOption = sc.nextInt();

            // ---------------- ADMIN PANEL ----------------
            if (startOption == 1) {
                System.out.print("Enter Admin PIN: ");
                String ap = sc.next();

                if (!ap.equals(ADMIN_PIN)) {
                    System.out.println("Wrong Admin PIN!");
                    continue;
                }

                System.out.println("\n--- ACCOUNT CREATION ---");
                System.out.print("Enter Account Holder Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Account ID: ");
                int accId = sc.nextInt();

                System.out.print("Enter Starting Balance: ");
                double bal = sc.nextDouble();

                System.out.print("Set Account PIN: ");
                String pin = sc.next();

                System.out.println("Select Account Type:");
                System.out.println("1. Savings Account");
                System.out.println("2. Current Account");
                System.out.print("Choose: ");
                int accType = sc.nextInt();

                BankAccount newAcc = null;

                if (accType == 1) {
                    System.out.print("Enter Minimum Required Balance: ");
                    int min = sc.nextInt();

                    System.out.print("Enter Withdrawal Limit: ");
                    int wl = sc.nextInt();

                    newAcc = new Savings(name, accId, bal, pin, min, wl);
                } else if (accType == 2) {
                    System.out.print("Enter Company Name: ");
                    String comp = sc.nextLine();

                    System.out.print("Enter Monthly Fee: ");
                    double fee = sc.nextDouble();

                    newAcc = new Current(name, comp, accId, bal, pin, fee);
                }

                if (newAcc != null) {
                    accounts.add(newAcc);
                    newAcc.saveToFile();
                    System.out.println("Account Created Successfully!");
                }
            }

            // ---------------- USER LOGIN ----------------
            if (startOption == 2) {
                if (accounts.isEmpty()) {
                    System.out.println("No accounts found! Ask admin to create an account first!");
                    startOption = -1;
                    continue;
                }

                System.out.print("Enter Account ID: ");
                int accId = sc.nextInt();
                System.out.print("Enter PIN: ");
                String pin = sc.next();

                currentUser = null;
                for (BankAccount acc : accounts) {
                    if (acc.getId() == accId && acc.verifyPin(pin)) {
                        currentUser = acc;
                        break;
                    }
                }

                if (currentUser == null) {
                    System.out.println("Invalid ID or PIN!");
                    startOption = -1;
                    continue;
                }

                System.out.println("Login Successful!");
                break;
            }
        }

        // ------------------- MAIN USER MENU -------------------
        int choice = -1;

        while (choice != 7) {
            System.out.println("\n=========== BANK MENU ===========");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Apply Loan");
            System.out.println("5. Display Account");
            System.out.println("6. Show Total Transactions");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter deposit amount: ");
                    currentUser.deposit(sc.nextDouble());
                    currentUser.saveToFile();
                    break;

                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    currentUser.withdraw(sc.nextDouble());
                    currentUser.saveToFile();
                    break;

                case 3:
                    System.out.print("Enter receiver account ID: ");
                    int rid = sc.nextInt();
                    System.out.print("Enter amount: ");
                    double amount = sc.nextDouble();

                    BankAccount receiver = null;
                    for (BankAccount acc : accounts) {
                        if (acc.getId() == rid) {
                            receiver = acc;
                            break;
                        }
                    }
                    if (receiver != null) {
                        currentUser.transfer(receiver, amount);
                        currentUser.saveToFile();
                        receiver.saveToFile();
                    } else {
                        System.out.println("Receiver not found!");
                    }
                    break;

                case 4:
                    System.out.println("\n1. Home Loan");
                    System.out.println("2. Car Loan");
                    System.out.print("Select option: ");
                    int l = sc.nextInt();

                    if (l == 1) {
                        System.out.print("Enter loan amount: ");
                        double lam = sc.nextDouble();
                        System.out.print("Enter monthly income: ");
                        double inc = sc.nextDouble();

                        Loan h = new HomeLoan(currentUser, lam, inc);
                        h.approveLoan();
                    } else {
                        System.out.print("Enter loan amount: ");
                        double lam = sc.nextDouble();
                        System.out.print("Do you have license? (true/false): ");
                        boolean lic = sc.nextBoolean();

                        Loan c = new CarLoan(currentUser, lam, lic);
                        c.approveLoan();
                    }
                    currentUser.saveToFile();
                    break;

                case 5:
                    currentUser.display();
                    break;

                case 6:
                    System.out.println("Total Transactions: " + BankAccount.totalTransactions);
                    break;

                case 7:
                    System.out.println("Exited Succesfully...");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }
        }

        sc.close();
    }
}

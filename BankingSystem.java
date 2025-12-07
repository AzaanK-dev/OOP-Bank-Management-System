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
import java.io.*;
// ------------------ LOAN INTERFACE ------------------
interface Loan {
    void approveLoan();
}

class HomeLoan implements Loan {
    BankAccount account;
    double loanAmount;
    double income;
    public HomeLoan(BankAccount account, double loanAmount, double income) {
        this.account = account;
        this.loanAmount = loanAmount;
        this.income = income;
    }
    @Override
    public void approveLoan() {
        if (loanAmount <= 100000 && income >= 50000) {
            account.setBalance(account.getBalance() + loanAmount);
            BankAccount.totalTransactions++;
            System.out.println("Home Loan Approved!");
        } else {
            System.out.println("Home Loan Denied!");
        }
    }
}

class CarLoan implements Loan {
    BankAccount account;
    double loanAmount;
    boolean license;
    public CarLoan(BankAccount account, double loanAmount, boolean license) {
        this.account = account;
        this.loanAmount = loanAmount;
        this.license = license;
    }
    @Override
    public void approveLoan() {
        if (loanAmount <= 50000 && license) {
            account.setBalance(account.getBalance() + loanAmount);
            BankAccount.totalTransactions++;
            System.out.println("Car Loan Approved!");
        } else {
            System.out.println("Car Loan Denied!");
        }
    }
}

// ------------------ BASE CLASS ------------------
class BankAccount {
    private String accountName;
    private int id;
    private double balance;
    private String pin;

    static int totalTransactions = 0;

    public BankAccount(String accountName, int id, double balance, String pin) {
        this.accountName = accountName;
        this.id = id;
        this.balance = balance;
        this.pin = pin;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return accountName;
    }

    public int getId() {
        return id;
    }

    public String getPin() {
        return pin;
    }

    public boolean verifyPin(String p) {
        return p.equals(pin);
    }

    public void deposit(double amount) {
        balance += amount;
        totalTransactions++;
        System.out.println("Deposit Successful!");
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdraw Successful!");
        } else {
            System.out.println("Insufficient Balance!");
        }
        totalTransactions++;
    }

    public void transfer(BankAccount receiver, double amount) {
        if (amount <= balance) {
            balance -= amount;
            receiver.setBalance(receiver.getBalance() + amount);
            System.out.println("Transfer Successful!");
        } else {
            System.out.println("Insufficient Balance!");
        }
        totalTransactions++;
    }

    protected void display() {
        System.out.println("\n-----------| ACCOUNT DETAILS |-------------");
        System.out.println("Name: " + accountName);
        System.out.println("Account ID: " + id);
        System.out.println("Balance: " + balance);
    }

    // Save account data in file
    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("accounts.txt", true))) {
            if (this instanceof Savings) {
                Savings s = (Savings)this;
                bw.write("SAVINGS," + getName() + "," + getId() + "," + getBalance() + "," + getPin() + "," + s.minAmount + "," + s.noOfWithdrawal + "\n");
            } else if (this instanceof Current) {
                Current c = (Current)this;
                bw.write("CURRENT," + getName() + "," + getId() + "," + getBalance() + "," + getPin() + "," + c.companyName + "," + c.fee + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving account to file: " + e.getMessage());
        }
    }

    // readng accounts from file 
    public static List<BankAccount> loadAccounts() {
        List<BankAccount> accounts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals("SAVINGS")) {
                    accounts.add(new Savings(parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3]),
                            parts[4], Integer.parseInt(parts[5]), Integer.parseInt(parts[6])));
                } else if (parts[0].equals("CURRENT")) {
                    accounts.add(new Current(parts[1], parts[5], Integer.parseInt(parts[2]), Double.parseDouble(parts[3]),
                            parts[4], Double.parseDouble(parts[6])));
                }
            }
        } catch (FileNotFoundException e) {
            // may not exist 
        } catch (IOException e) {
            System.out.println("Error loading accounts from file: " + e.getMessage());
        }
        return accounts;
    }
}

// ------------------ SAVINGS ACCOUNT ------------------
class Savings extends BankAccount {
    int minAmount;
    int noOfWithdrawal;
    int currWithdrawals = 0;

    public Savings(String name, int id, double balance, String pin, int minAmount, int noOfWithdrawal) {
        super(name, id, balance, pin);
        this.minAmount = minAmount;
        this.noOfWithdrawal = noOfWithdrawal;
    }

    public void penaltyCharge() {
        if (getBalance() < minAmount) {
            setBalance(getBalance() - 1000);
            System.out.println("⚠ Penalty Applied! Low Balance!");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (currWithdrawals < noOfWithdrawal) {
            currWithdrawals++;
            super.withdraw(amount);
        } else {
            System.out.println("Withdrawal Limit Exceeded!");
        }
    }

    @Override
    protected void display() {
        super.display();
        System.out.println("Account Type: Savings");
        System.out.println("Min Balance: " + minAmount);
        System.out.println("Withdrawals Allowed: " + noOfWithdrawal);
        System.out.println("Used Withdrawals: " + currWithdrawals);
    }
}

// ------------------ CURRENT ACCOUNT ------------------
class Current extends BankAccount {
    String companyName;
    double fee;

    public Current(String name, String companyName, int id, double balance, String pin, double fee) {
        super(name, id, balance, pin);
        this.companyName = companyName;
        this.fee = fee;
    }

    public void deductMonthlyFee() {
        setBalance(getBalance() - fee);
        System.out.println("Monthly Fee Deducted: " + fee);
    }

    @Override
    protected void display() {
        super.display();
        System.out.println("Account Type: Current");
        System.out.println("Company: " + companyName);
        System.out.println("Monthly Fee: " + fee);
    }
}

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
                String name = sc.next();

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
                    String comp = sc.next();

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
                    System.out.println("Exiting... Goodbye!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }
        }

        sc.close();
    }
}
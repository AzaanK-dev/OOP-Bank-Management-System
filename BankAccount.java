import java.util.*;
import java.io.*;
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
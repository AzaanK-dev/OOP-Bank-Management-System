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


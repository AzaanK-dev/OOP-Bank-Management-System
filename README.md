# OOP-Based Banking System | Java Project

Welcome to my Banking System project, built entirely in Java using Object-Oriented Programming principles.  
This project demonstrates practical implementation of classes, inheritance, interfaces, file handling, and interactive console-based menus.
---

🚀 Technologies & Concepts Used
Languages & Tools:
- Java SE
- Scanner for user input
- File I/O (`accounts.txt`) for data persistence

OOP Concepts:
- Classes & Objects (`BankAccount`, `Savings`, `Current`)
- Inheritance (`Savings` and `Current` extend `BankAccount`)
- Interfaces (`Loan` interface with `HomeLoan` and `CarLoan`)
- Method Overriding (`withdraw()`, `display()`)
- Encapsulation (private fields, getters/setters)
- Static Members (`totalTransactions` for all accounts)
- Polymorphism (using `Loan` interface references)
- Conditional Logic & Loops (for menu-driven operations)
---

💻 Features
Admin Features:
- Create new bank accounts (Savings or Current)
- Set account holder name, account ID, PIN, and initial balance
- Configure account-specific parameters:
  - Minimum balance & withdrawal limit for Savings
  - Company name & monthly fee for Current

User Features:
- Login securely with account PIN
- Deposit funds
- Withdraw funds with limits & penalty checks
- Transfer funds to other accounts
- Apply for Home or Car Loan (with eligibility checks)
- Display account details
- View total system-wide transactions

Additional Features:
- Tracks total transactions across all accounts
- Penalty charged for low balance in Savings accounts
- Monthly fee deduction for Current accounts
- Account data saved and loaded from a file (`accounts.txt`) for persistence

---

This project is ideal for demonstrating core OOP concepts, file handling, and interactive console-based applications in Java.

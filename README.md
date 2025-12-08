# OOP Banking Management System (Java)

A complete console-based banking system built using **Object Oriented Programming concepts in JAVA**.  
It includes **Admin account creation**, **User banking operations**, **Loan system**, and **Account rules** for both *Savings* and *Current* accounts.

---

## 🏛️ Project Overview
This project simulates a real-world bank using:
- Classes & Objects  
- Inheritance  
- Method Overriding  
- Interfaces  
- Encapsulation  
- Polymorphism  
- File Handling (for account log storage)  
- Menu-Driven User Interaction  

---

## 👨‍💼 Admin Features
Admin has **exclusive access** to:
- Create **Savings Account**
- Create **Current Account**
- Enter all required details:
  - Account Holder Name  
  - Account ID  
  - Initial Balance  
  - PIN  
  - (Savings Only) Minimum Balance + Withdrawal Limit  
  - (Current Only) Company Name + Monthly Fee  
- Only Admin can register new bank accounts  
- Normal users **cannot** access this section  

Admin access is secured with:
- **Admin Username**
- **Admin Password**

---

## 🧑‍💻 User Features
After account creation, users can:
- Login using **Account ID + PIN**
- Deposit Money  
- Withdraw Money  
- Transfer Amount to any existing account  
- Apply for Loan  
- Display Account Details  
- Logout safely  

All actions update:
- **Account balance**
- **Total transactions counter**

---

## 🏦 Account Types

### 1️⃣ Savings Account
Savings account requires:
- Minimum Balance Requirement  
- Limited Number of Withdrawals  
- Penalty charged if balance drops below minimum  
- Withdrawal limit automatically tracked  

**Conditions:**  
- Withdrawal allowed only if limit not exceeded  
- Penalty = Rs.1000 when below minimum  

---

### 2️⃣ Current Account
Current account requires:
- Company Name  
- Monthly Maintenance Fee  
- Fee is deducted from balance when applied  

**Conditions:**  
- No withdrawal limits  
- No minimum balance requirements  
- Monthly fee must be paid from balance  

---

## 💰 Loan System (Interface-Based)
Loan approval uses the `Loan` interface and two implementations:

### 🏠 Home Loan
**Approved if:**  
- Loan ≤ 10,000  
- User Income ≥ 40,000  

### 🚗 Car Loan
**Approved if:**  
- Loan ≤ 10,000  
- User has a Valid Driving License  

If approved:
- Loan amount is added to user balance  
- Transaction count increases  

If denied:
- Shows the reason  

---

## 🧩 Topics Covered
This project demonstrates mastery of:
- **Class Structure & Object-Oriented Design**
- **Abstract Coding with Interfaces**
- **Inheritance & Super Constructors**
- **Encapsulation using getters/setters**
- **Method Overriding**
- **Polymorphism (Loan processing)**
- **Static Variables (Total Transactions)**
- **Conditional Logic & Menu Systems**
- **Realistic Banking Rules Implementation**

---

## 🎮 How the User Interacts
1. Admin creates account(s)  
2. User logs in with ID + PIN  
3. User selects operations from menu  
4. System prints results in real-time  
5. User logs out  

This makes the console behave like an actual ATM or bank terminal.

---

## 📌 Summary
This project is a complete **Java OOP case study** combining:
- Banking rules  
- Loan rules  
- User interaction  
- Secure admin control  
- Modular class-based architecture  


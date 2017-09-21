package com.example.arathi.balancesheet.expenses;

import com.example.arathi.balancesheet.Accounts.AccountDetails;

/**
 * Created by arathi on 8/3/17.
 * This file deals with creating the class for expense entry.
 *
 */

public class ExpenseDetail {
    AccountDetails accountDetails;
    private int expenseIndex;
    private String expenseName;
    private float amount;
    private String expenseCategory;
    private int expenseAccount;
    private String expenseDate;
    private int expenseOrIncome;

    public ExpenseDetail(int eIndex, String eName, float eAmount, String eCategory,
                         int eMode, String eDate, int eOrIn) {
        expenseIndex = eIndex;
        expenseName = eName;
        amount = eAmount;
        expenseCategory = eCategory;
        expenseAccount = eMode;
        expenseDate = eDate;
        expenseOrIncome = eOrIn;
    }

    public void setAccountDetails(AccountDetails accountDetails) {
        this.accountDetails = accountDetails;
    }

    public int getExpenseIndex() {
        return expenseIndex;
    }

    String getExpenseName() {
        return expenseName;
    }

    String getExpenseCategory(){
        return expenseCategory;
    }

    float getAmount(){
        return amount;
    }

    int getExpenseAccount() {
        return expenseAccount;
    }

    String getExpenseDate() {
        return expenseDate;
    }

    int getCheckIncomeOrExpense(){ return expenseOrIncome; }

    String getAccountName() {
        return accountDetails.getAccountName();
    }
}

package com.example.arathi.balancesheet.expenses;

/**
 * Created by arathi on 8/3/17.
 * This file deals with creating the class for expense entry.
 *
 */

public class ExpenseDetail {
    private int expenseIndex;
    private String expenseName;
    private float amount;
    private String expenseCategory;
    private String expenseMode;
    private String expenseDate;
    private int expenseOrIncome;

    public ExpenseDetail( int eIndex, String eName, float eAmount, String eCategory,
                          String eMode, String eDate, int eOrIn){
        expenseIndex = eIndex;
        expenseName = eName;
        amount = eAmount;
        expenseCategory = eCategory;
        expenseMode = eMode;
        expenseDate = eDate;
        expenseOrIncome = eOrIn;
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

    String getExpenseMode(){
        return expenseMode;
    }

    String getExpenseDate() {
        return expenseDate;
    }

    int getCheckIncomeOrExpense(){ return expenseOrIncome; }
}

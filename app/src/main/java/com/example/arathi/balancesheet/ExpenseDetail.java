package com.example.arathi.balancesheet;

/**
 * Created by arathi on 8/3/17.
 */

public class ExpenseDetail {
    private int expenseIndex;
    private String expenseName;
    private float amount;
    private String expenseCategory;
    private String expenseMode;
    private String expenseDate;

    public ExpenseDetail( int eIndex, String eName, float eAmount, String eCategory,
                          String eMode, String eDate){
        expenseIndex = eIndex;
        expenseName = eName;
        amount = eAmount;
        expenseCategory = eCategory;
        expenseMode = eMode;
        expenseDate = eDate;
    }

    public int getExpenseIndexndex() {
        return expenseIndex;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public String getExpenseCategory(){
        return expenseCategory;
    }

    public float getAmount(){
        return amount;
    }

    public String getExpenseMode(){
        return expenseMode;
    }

    public String getExpenseDate() {
        return expenseDate;
    }
}

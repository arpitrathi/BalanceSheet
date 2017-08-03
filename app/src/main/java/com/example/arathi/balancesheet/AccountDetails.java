package com.example.arathi.balancesheet;

/**
 * Created by arathi on 8/3/17.
 */

public class AccountDetails {
    private static int aIndex;
    private String accountName;
    private String accountNumber;
    private float accountBalance;

    public AccountDetails(int index, String name, String accNum, float balance ) {
        aIndex = index;
        accountName = name;
        accountNumber = accNum;
        accountBalance = balance;
    }
    public int getTotalBalance(){
        return 10000;
    }
    public int getaIndex(){
        return aIndex;
    }
    public String getAccountName(){
        return accountName;
    }
    public String getAccountNumber(){
        return accountNumber;
    }
    public float getAccountBalance(){
        return accountBalance;
    }

}

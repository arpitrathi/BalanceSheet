package com.example.arathi.balancesheet.Accounts;

/**
 * Created by arathi on 8/3/17.
 * This file deals with creating the class for account details.
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
        return 0;
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

    public static class AccountId {

        int accountId;
        String accountName;

        public AccountId(int id, String name) {
            accountId = id;
            accountName = name;
        }

        public int getAccountId() {
            return accountId;
        }

        public String getAccountName() {
            return accountName;
        }

    }
}

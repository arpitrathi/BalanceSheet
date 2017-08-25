package com.example.arathi.balancesheet;

import android.provider.BaseColumns;

/**
 * Created by arathi on 8/11/17.
 * This file has column name of expense and account table for database.
 */

final class FeedReaderContract {
    private FeedReaderContract(){}

    static class ExpenseEntry implements BaseColumns {
        static final String TABLE_NAME = "expense";
        static final String COLUMN_ID_NAME = "expenseId";
        static final String COLUMN_NAME1 = "expenseName";
        static final String COLUMN_NAME2 = "expenseAmount";
        static final String COLUMN_NAME3 = "expenseCategory";
        static final String COLUMN_NAME4 = "expenseMode";
        static final String COLUMN_NAME5 = "expenseDate";
        static final String COLUMN_NAME6 = "expenseOrIncome";

    }
    static class AccountEntry implements BaseColumns{
        static final String TABLE_NAME = "account";
        static final String COLUMN_ID_NAME = "accountId";
        static final String COLUMN_NAME1 = "accountName";
        static final String COLUMN_NAME2 = "accountNumber";
        static final String COLUMN_NAME3 = "accountBalance";
    }


}

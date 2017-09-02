package com.example.arathi.balancesheet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.arathi.balancesheet.Accounts.AccountDetails;
import com.example.arathi.balancesheet.expenses.ExpenseDetail;

import java.util.ArrayList;
import java.util.List;

import static com.example.arathi.balancesheet.FeedReaderContract.AccountEntry;
import static com.example.arathi.balancesheet.FeedReaderContract.ExpenseEntry;


public class DBHelper extends SQLiteOpenHelper {
    private static final String EXPENSE_TABLE =
            "CREATE TABLE " + ExpenseEntry.TABLE_NAME + " ( " +
                    ExpenseEntry.COLUMN_ID_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ExpenseEntry.COLUMN_NAME1 + " TEXT, " +
                    ExpenseEntry.COLUMN_NAME2 + " TEXT, " +
                    ExpenseEntry.COLUMN_NAME3 + " TEXT, " +
                    ExpenseEntry.COLUMN_NAME4 + " TEXT, " +
                    ExpenseEntry.COLUMN_NAME5 + " TEXT, " +
                    ExpenseEntry.COLUMN_NAME6 + " INTEGER NOT NULL CHECK ("+ ExpenseEntry.COLUMN_NAME6 +" IN (0,1) ) )";
    private static final String ACCOUNTS_TABLE =
            "CREATE TABLE " + AccountEntry.TABLE_NAME + " ( " +
                    AccountEntry.COLUMN_ID_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    AccountEntry.COLUMN_NAME1 + " TEXT UNIQUE, " +
                    AccountEntry.COLUMN_NAME2 + " TEXT, " +
                    AccountEntry.COLUMN_NAME3 + " TEXT ) ";
    private static final String SQL_DELETE_EXPENSE_TABLE =
            "DROP TABLE IF EXISTS " + ExpenseEntry.TABLE_NAME;
    private static final String SQL_DELETE_ACCOUNTS_TABLE =
            "DROP TABLE IF EXISTS " + AccountEntry.TABLE_NAME;
    private static String DATABASE_NAME = "expenseDatabase.db";
    private static int DATABASE_VERSION = 1;
    //private static Context context;
    private static DBHelper instance;
    private static SQLiteDatabase db;



    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //DBHelper.context = context;
    }

    public static synchronized DBHelper getInstance(Context context) {
        if(instance==null){
            instance = new DBHelper(context);
        }
        db = instance.getWritableDatabase();

        return instance;
    }

    public void closeDB(){

        if(instance!=null){
            db.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Database","Reached onCreate for DBHelper");
        db.execSQL(EXPENSE_TABLE);
        db.execSQL(ACCOUNTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_EXPENSE_TABLE);
        db.execSQL(SQL_DELETE_ACCOUNTS_TABLE);
        onCreate(db);
    }

    /*
    * Insert Data in database
    *
    * */
    public boolean insertExpense(String name, float amount, String category, String mode, String date, int expinc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(ExpenseEntry.COLUMN_NAME1, name);
        value.put(ExpenseEntry.COLUMN_NAME2, amount);
        value.put(ExpenseEntry.COLUMN_NAME3, category);
        value.put(ExpenseEntry.COLUMN_NAME4, mode);
        value.put(ExpenseEntry.COLUMN_NAME5,date);
        value.put(ExpenseEntry.COLUMN_NAME6, expinc);
        long newRowId = db.insert(ExpenseEntry.TABLE_NAME,null,value);
        return (newRowId != 0);
    }

    /*
    * Insert Account Details in Database
    * */
    public boolean insertAccount(String name,  String number, float amount ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value  = new ContentValues();
        value.put(AccountEntry.COLUMN_NAME1,name);
        value.put(AccountEntry.COLUMN_NAME2, number);
        value.put(AccountEntry.COLUMN_NAME3, amount);
        long newRowId = db.insert(AccountEntry.TABLE_NAME,null,value);
        return (newRowId != 0);
    }


    /*
    * Return the list of expense
    * */
    List<ExpenseDetail> getExpenseList(){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ExpenseEntry.COLUMN_ID_NAME,
                ExpenseEntry.COLUMN_NAME1,
                ExpenseEntry.COLUMN_NAME2,
                ExpenseEntry.COLUMN_NAME3,
                ExpenseEntry.COLUMN_NAME4,
                ExpenseEntry.COLUMN_NAME5,
                ExpenseEntry.COLUMN_NAME6
        };
        String sortOrder = ExpenseEntry.COLUMN_NAME5 + " DESC ";

        Cursor cursor = db.query(
                ExpenseEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List<ExpenseDetail> items = new ArrayList<>();

        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COLUMN_NAME1));
            float amount =  cursor.getFloat(cursor.getColumnIndexOrThrow(ExpenseEntry.COLUMN_NAME2));
            String category = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COLUMN_NAME3));
            String mode = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COLUMN_NAME4));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COLUMN_NAME5));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ExpenseEntry.COLUMN_ID_NAME));
            int checkinc = cursor.getInt(cursor.getColumnIndexOrThrow(ExpenseEntry.COLUMN_NAME6));


            ExpenseDetail item = new ExpenseDetail(id, name, amount, category, mode, date, checkinc) ;
            items.add(item);
        }
        cursor.close();
        return items;
    }

    /*
    * Return List of Account Details
    *
    * */
    public List<AccountDetails> getAccountDetailsList(){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                AccountEntry.COLUMN_ID_NAME,
                AccountEntry.COLUMN_NAME1,
                AccountEntry.COLUMN_NAME2,
                AccountEntry.COLUMN_NAME3
        };

        String sortedOrder =AccountEntry.COLUMN_NAME1 +" ASC";
        Cursor cursor = db.query(
                AccountEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortedOrder
        );

        List<AccountDetails> items = new ArrayList<>();
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndexOrThrow(AccountEntry.COLUMN_NAME1));
            float amount = cursor.getFloat(cursor.getColumnIndexOrThrow(AccountEntry.COLUMN_NAME3));
            String number = cursor.getString(cursor.getColumnIndexOrThrow(AccountEntry.COLUMN_NAME2));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(AccountEntry.COLUMN_ID_NAME));
            AccountDetails item = new AccountDetails(id, name, number, amount);
            items.add(item);
        }
        cursor.close();
        return items;
    }
    /**
     * Update the Expense to some new value.
     */

    public boolean updateExpense(int id, String name, float amount, String category, String mode, String date, int incomeOrNot){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(ExpenseEntry.COLUMN_NAME1, name);
        value.put(ExpenseEntry.COLUMN_NAME2, amount);
        value.put(ExpenseEntry.COLUMN_NAME3, category);
        value.put(ExpenseEntry.COLUMN_NAME4, mode);
        value.put(ExpenseEntry.COLUMN_NAME5,date);
        value.put(ExpenseEntry.COLUMN_NAME6, incomeOrNot);

        String selection = ExpenseEntry.COLUMN_ID_NAME + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        int count = db.update(
                ExpenseEntry.TABLE_NAME,
                value,
                selection,
                selectionArgs
        );

        return (count != 0);
    }

    public boolean updateAccount(int id, String name, String number, float balance){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(AccountEntry.COLUMN_NAME1, name);
        value.put(AccountEntry.COLUMN_NAME2, number);
        value.put(AccountEntry.COLUMN_NAME3, balance);

        String selection = AccountEntry.COLUMN_ID_NAME + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        int count = db.update(
                AccountEntry.TABLE_NAME,
                value,
                selection,
                selectionArgs
        );

        return (count != 0);
    }

    public List<String> getAccountName(){
        List<String> accountsName = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                AccountEntry.COLUMN_NAME1,
        };

        Cursor cursor = db.query(
                AccountEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int len = cursor.getCount();

            while (cursor.moveToNext()) {
                String name = cursor.getString(0);
                accountsName.add(name);
            }
        }catch (Exception e){
            Log.d("DBHelper",e.getMessage());
        }

        return accountsName;
    }

    public AccountDetails getAccountEntry(String name){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                AccountEntry.COLUMN_ID_NAME,
                AccountEntry.COLUMN_NAME1,
                AccountEntry.COLUMN_NAME2,
                AccountEntry.COLUMN_NAME3
        };

        String selection =  AccountEntry.COLUMN_NAME1 + " LIKE ?";
        String[] selectionArgs = { name };

        Cursor cursor = db.query(
                AccountEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        AccountDetails item = null;
        if(cursor.moveToNext()){
            float amount = cursor.getFloat(cursor.getColumnIndexOrThrow(AccountEntry.COLUMN_NAME3));
            String num = cursor.getString(cursor.getColumnIndexOrThrow(AccountEntry.COLUMN_NAME2));
            int id1 = cursor.getInt(cursor.getColumnIndexOrThrow(AccountEntry.COLUMN_ID_NAME));
            item = new AccountDetails(id1, name, num, amount);

        }

        return  item;
    }

    public float getAccountsBalance(){
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor rs = db.rawQuery("select sum( "+ AccountEntry.COLUMN_NAME3+" ) as balance from "
                + AccountEntry.TABLE_NAME, null );

        float balance = 0;
        if(rs.moveToFirst()) {
        balance = rs.getFloat(0);
        }

        return balance;
    }

    public float getTotalExpense(){
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor rs = db.rawQuery("select sum ( "+ExpenseEntry.COLUMN_NAME2+ " ) as totalE from "
                +ExpenseEntry.TABLE_NAME + " where "+ExpenseEntry.COLUMN_NAME6+" = 0", null);
        float totalExpense = 0;
        if(rs.moveToFirst()){
            totalExpense = rs.getFloat(0);
        }
        return totalExpense;
    }

    public float getTotalIncome(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rs = db.rawQuery("select sum ( "+ExpenseEntry.COLUMN_NAME2+ " ) as totalE from "
                +ExpenseEntry.TABLE_NAME + "where "+ExpenseEntry.COLUMN_NAME6+" = 1", null);
        float totalExpense = 0;
        if(rs.moveToFirst()){
            totalExpense = rs.getFloat(0);
        }
        return totalExpense;

    }
    public float getExpenseAmount(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rs = db.rawQuery("select "+ExpenseEntry.COLUMN_NAME2+" from "+ExpenseEntry.TABLE_NAME+
                " where "+ExpenseEntry.COLUMN_ID_NAME+ " = "+ Integer.toString(id), null);
        float expense = 0;
        if(rs.moveToFirst()){
            expense = rs.getFloat(0);
        }
        return expense;

    }
    public ExpenseDetail getExpenseDetail(int id){
        float amount;
        String name;
        String category, mode;
        int incornot;
        String date;

        String[] projection = {
                ExpenseEntry.COLUMN_ID_NAME,
                ExpenseEntry.COLUMN_NAME1,
                ExpenseEntry.COLUMN_NAME2,
                ExpenseEntry.COLUMN_NAME3,
                ExpenseEntry.COLUMN_NAME4,
                ExpenseEntry.COLUMN_NAME5,
                ExpenseEntry.COLUMN_NAME6
        };
       String selection = ExpenseEntry.COLUMN_ID_NAME + " = ?";
        String[] selectionArgs = {
                Integer.toString(id)
        };

        Cursor cursor = db.query(
                ExpenseEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        ExpenseDetail expenseDetail = null;
        if(cursor.moveToFirst()){
            amount= cursor.getFloat(cursor.getColumnIndexOrThrow(ExpenseEntry.COLUMN_NAME2));
            name = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COLUMN_NAME1));
            category = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COLUMN_NAME3));
            mode = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COLUMN_NAME4));
            date = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COLUMN_NAME5));
            incornot = cursor.getInt(cursor.getColumnIndexOrThrow(ExpenseEntry.COLUMN_NAME6));
            expenseDetail = new ExpenseDetail(id,name,amount,category,mode,date,incornot);
        }
        return expenseDetail;
    }

}

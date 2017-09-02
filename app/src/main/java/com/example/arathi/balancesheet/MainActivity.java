package com.example.arathi.balancesheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arathi.balancesheet.Accounts.AccountDetailsImplement;
import com.example.arathi.balancesheet.expenses.ExpenseDetail;
import com.example.arathi.balancesheet.expenses.ExpenseDetailAdapter;
import com.example.arathi.balancesheet.expenses.ExpenseEdit;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{


    public static final String EXPENSE_TYPE = "expense_type";
    public static final int  EXPENSE_UPDATE = 2;
    public static final int EXPENSE_ADD = 1;
    public static final String EXPENSE_DATA = "expense_data";

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public  DBHelper dbHelper;
    TextView bankBalance;
    TextView expenseBalance;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_display);

        Log.d("Main","Reached Main Activity");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(MainActivity.this, ExpenseEdit.class);
                intent.putExtra(EXPENSE_TYPE,EXPENSE_ADD);
                startActivity(intent);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        dbHelper = DBHelper.getInstance(getApplicationContext());

        bankBalance = (TextView)findViewById(R.id.bank_balance);
        expenseBalance = (TextView) findViewById(R.id.expense_balance);

        bankBalance.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Intent intent;
                                               intent = new Intent(MainActivity.this, AccountDetailsImplement.class);
                                               startActivity(intent);
                                           }
                                       } );
        lv = (ListView)findViewById(R.id.expense_item_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExpenseDetail expenseDetail = (ExpenseDetail) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, ExpenseEdit.class);
                String expenseString = (new Gson()).toJson(expenseDetail);
                intent.putExtra(EXPENSE_TYPE,EXPENSE_UPDATE);
                intent.putExtra(EXPENSE_DATA,expenseString);
                startActivity(intent);

            }
        });
    //tv.setText(stringFromJNI());
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("DEBUG", "Reached onResume Main Activity"+bankBalance.getText().toString());
        dbHelper = DBHelper.getInstance(getApplicationContext());
        float totalExpense = dbHelper.getTotalExpense();
        float accBal = dbHelper.getAccountsBalance();

        String accbalance = "Accounts Balance: " + String.format(Locale.getDefault(),"%.2f",accBal);
        String expenseBal = "Total Expense: " +String.format(Locale.getDefault(),"%.2f",totalExpense);
        expenseBalance.setText( expenseBal );
        bankBalance.setText( accbalance );

        List<ExpenseDetail> expenseDetailList = dbHelper.getExpenseList();
        ExpenseDetailAdapter expenseDetailAdapter = new ExpenseDetailAdapter(expenseDetailList,this);
        lv.setAdapter(expenseDetailAdapter);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.empty_text_view);
        int lenexpense = expenseDetailList.size();

        if(lenexpense==0){
            tv.setVisibility(View.VISIBLE);
            tv.setTextSize(30);
        }
        else{
            tv.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.account_all){
            Intent intent = new Intent(MainActivity.this, AccountDetailsImplement.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}

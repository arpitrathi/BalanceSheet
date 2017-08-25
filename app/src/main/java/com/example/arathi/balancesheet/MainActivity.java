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
import android.widget.ListView;
import android.widget.TextView;

import com.example.arathi.balancesheet.accounts.AccountDetailsImplement;
import com.example.arathi.balancesheet.expense.ExpenseDetail;
import com.example.arathi.balancesheet.expense.ExpenseDetailAdapter;
import com.example.arathi.balancesheet.expense.ExpenseEdit;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    public static final int READABLE_DB = 2;
    public static final int WRITABLE_DB = 1;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public  DBHelper dbHelper;
    TextView bankBalance;

    public static StringBuilder getCurrentDate(){
        final Calendar c = Calendar.getInstance();
        int date = c.get(Calendar.DATE);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        StringBuilder str = new StringBuilder();
        str.append(date);
        str.append("/");
        str.append(month+1);
        str.append("/");
        str.append(year);
        return str;
    }

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
                startActivity(intent);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        dbHelper = DBHelper.getInstance(getApplicationContext());

        bankBalance = (TextView)findViewById(R.id.bank_balance);
        String accBal = dbHelper.getAccountsBalance();

        if( accBal == null)
            accBal = "0";
        String balance = "Balance: " + accBal;
        bankBalance.setText( balance );

        bankBalance.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Intent intent;
                                               intent = new Intent(MainActivity.this, AccountDetailsImplement.class);
                                               startActivity(intent);
                                           }
                                       } );
    //tv.setText(stringFromJNI());
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("DEBUG", "Reached onResume Main Activity"+bankBalance.getText().toString());
        dbHelper = DBHelper.getInstance(getApplicationContext());
        String accBal = dbHelper.getAccountsBalance();
        if( accBal == null)
            accBal = "0";
        String balance = "Balance: " + accBal;
        bankBalance.setText( balance );

        List<ExpenseDetail> expenseDetailList = dbHelper.getExpenseList();
        ExpenseDetailAdapter expenseDetailAdapter = new ExpenseDetailAdapter(expenseDetailList,this);
        ListView lv = (ListView)findViewById(R.id.expense_item_list);
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

        return super.onOptionsItemSelected(item);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}

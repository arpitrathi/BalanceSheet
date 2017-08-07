package com.example.arathi.balancesheet;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    public static StringBuilder getCurrentDate(){
        final Calendar c = Calendar.getInstance();
        int date = c.get(Calendar.DATE);
        int month = c.get(Calendar.MONTH);
        StringBuilder str = new StringBuilder();
        str.append(date);
        str.append("/");
        str.append(month+1);
        return str;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        TextView bankBalance = (TextView)findViewById(R.id.bank_balance);
        String balance = "Balance: 100000";

        bankBalance.setText( balance );
        bankBalance.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Intent intent;
                                               intent = new Intent(MainActivity.this, AccountDetailsImplement.class);
                                               startActivity(intent);
                                           }
                                       } );
        String dateToday = getCurrentDate().toString();

        List<ExpenseDetail> expenseDetailList = new ArrayList<>();
        expenseDetailList.add(new ExpenseDetail(1,"Temple",10,"Personal","Cash",dateToday));
        expenseDetailList.add(new ExpenseDetail(2,"Food",250,"Personal","HDFC CC", dateToday));

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
    //tv.setText(stringFromJNI());
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

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}

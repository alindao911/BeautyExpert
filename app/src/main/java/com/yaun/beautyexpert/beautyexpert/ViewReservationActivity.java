package com.yaun.beautyexpert.beautyexpert;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ViewReservationActivity extends AppCompatActivity {
    TableLayout tbl;
        SQLiteOpenHelper openHelper;
        SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reservation);
        tbl = findViewById(R.id.tblId);
        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();
        Bundle extras = getIntent().getExtras();
        int ID = extras.getInt("ID");
        display(ID);
    }
    public void display(int ID){
        final Cursor c;
        String query = "SELECT * FROM appointments WHERE userid =" + ID +"";
        c = db.rawQuery(query, null);
        c.moveToFirst();
        if(!c.isAfterLast()){
            Toast.makeText(getApplication(),"Records found!", Toast.LENGTH_SHORT).show();
            int count = 2;
            do{
                TableRow trow = new TableRow(getApplication());
                if(count%2==0) {
                    trow.setBackgroundColor(Color.LTGRAY);
                }
                count++;
                final TextView key = new TextView((getApplication()));
                key.setText("" + c.getString(c.getColumnIndex("time")));
                key.setVisibility(View.GONE);

                final TextView key1 = new TextView((getApplication()));
                key1.setText("" + c.getString(c.getColumnIndex("ID")));
                key1.setVisibility(View.GONE);

                final TextView idd = new TextView(getApplication());
                idd.setText("" + c.getString(c.getColumnIndex("service")));
                idd.setTextSize(15);
                idd.setGravity(Gravity.CENTER);
                idd.setTextColor(Color.BLACK);
                idd.setPadding(10,20,10,10);
                trow.addView(idd);

                final TextView firstName = new TextView(getApplication());
                firstName.setText("" + c.getString(c.getColumnIndex("beautician")));
                firstName.setTextSize(15);
                firstName.setGravity(Gravity.CENTER);
                firstName.setTextColor(Color.BLACK);
                firstName.setPadding(20,30,20,20);
                trow.addView(firstName);

                final TextView lastName = new TextView(getApplication());
                lastName.setText("" + c.getString(c.getColumnIndex("date")));
                lastName.setTextSize(15);
                lastName.setGravity(Gravity.CENTER);
                lastName.setTextColor(Color.BLACK);
                lastName.setPadding(10,30,20,20);
                trow.addView(lastName);

                final TextView Course = new TextView(getApplication());
                Course.setText("" + c.getString(c.getColumnIndex("status")));
                Course.setTextSize(15);
                Course.setGravity(Gravity.CENTER);
                Course.setTextColor(Color.BLACK);
                Course.setPadding(10,30,20,20);
                trow.addView(Course);

                trow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), BookingDetails.class);
                        intent.putExtra("service", idd.getText().toString());
                        intent.putExtra("beautician", firstName.getText().toString());
                        intent.putExtra("date", lastName.getText().toString());
                        intent.putExtra("status", Course.getText().toString());
                        intent.putExtra("ID", Integer.parseInt(key1.getText().toString()));
                        intent.putExtra("time", key.getText().toString());
                        intent.putExtra("type", "view");
                        startActivity(intent);
                    }
                });

                tbl.addView(trow);

            }while(c.moveToNext());
            c.close();
        }
        else{
            Toast.makeText(getApplication(), "No Records Found!", Toast.LENGTH_SHORT).show();

        }
    }

}

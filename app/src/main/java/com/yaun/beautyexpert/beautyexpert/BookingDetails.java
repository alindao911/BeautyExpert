package com.yaun.beautyexpert.beautyexpert;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BookingDetails extends AppCompatActivity {
    private TextView service;
    private TextView beautician;
    private TextView time;
    private TextView date;
    private Button cancelBtn;

    private int key, ID;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        refIDs();
        getExtras();
        openHelper = new DatabaseHelper(this);
        db = openHelper.getWritableDatabase();
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.COL_6, "CANCELLED");
                try {
                    db.update(DatabaseHelper.APPOINTMENT, contentValues, "ID=" + key, null);
                    Toast.makeText(BookingDetails.this, "Booking Cancelled", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), CancelReservationActivity.class);
                    intent.putExtra("ID", ID);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                catch (SQLException e) {
                    Toast.makeText(BookingDetails.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void getExtras() {
        Bundle bundle = getIntent().getExtras();
        String Service = bundle.getString("service");
        String Beautician = bundle.getString("beautician");
        String Time = bundle.getString("time");
        String Date = bundle.getString("date");
        String Status = bundle.getString("status");
        key = bundle.getInt("ID");
        ID = bundle.getInt("uid");
        String type = bundle.getString("type");

        if(type.equalsIgnoreCase("cancel")) {
            cancelBtn.setVisibility(View.VISIBLE);
        }

        service.setText("" + Service);
        beautician.setText("" + Beautician);
        time.setText("" + Time);
        date.setText("" + Date);

    }
    private void refIDs() {
        service = findViewById(R.id.service);
        beautician = findViewById(R.id.beautician);
        time = findViewById(R.id.times);
        date = findViewById(R.id.dates);
        cancelBtn = findViewById(R.id.cancelBtn);
    }
}

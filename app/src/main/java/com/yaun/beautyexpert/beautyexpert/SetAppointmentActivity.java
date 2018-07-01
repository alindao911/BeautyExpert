package com.yaun.beautyexpert.beautyexpert;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.util.Calendar;

public class SetAppointmentActivity extends AppCompatActivity {
    private Spinner serviceSpinner;
    private Spinner beauticianSpinner;
    private TextView schedDate;
    private TextView schedTime;
    private TimePickerDialog.OnTimeSetListener resTimeListener;
    private DatePickerDialog.OnDateSetListener startDateListener, endDateListener, resDateListner;
    private int hourOfDay;
    private int minOfDay;
    private int start_year, start_month, start_day;
    private Button bookBtn;
    //SQLITE CHUCHU
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_appointment);
        openHelper = new DatabaseHelper(this);
        db = openHelper.getWritableDatabase();
        setItems();
        Calendar calendar = Calendar.getInstance();
        hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        minOfDay = calendar.get(Calendar.MINUTE);

        Calendar startDate1 = Calendar.getInstance();
        start_year = startDate1.get(Calendar.YEAR);
        start_month = startDate1.get(Calendar.MONTH);
        start_day = startDate1.get(Calendar.DAY_OF_MONTH);
        init();

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBooking();
            }
        });

    }
    private void saveBooking() {
        String service = serviceSpinner.getSelectedItem().toString();
        String beautician = beauticianSpinner.getSelectedItem().toString();
        String date = schedDate.getText().toString();
        String time = schedTime.getText().toString();

        if(date.equalsIgnoreCase("date") && time.equalsIgnoreCase("time")) {
            Toast.makeText(this, "Input all fields", Toast.LENGTH_SHORT).show();
        }
        else {
            Bundle extras = getIntent().getExtras();
            int ID = extras.getInt("ID");
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.COL_2, ID);
            contentValues.put(DatabaseHelper.COL_3, service);
            contentValues.put(DatabaseHelper.COL_4, date);
            contentValues.put(DatabaseHelper.COL_5, time);
            contentValues.put(DatabaseHelper.COL_7, beautician);
            contentValues.put(DatabaseHelper.COL_6, "ACTIVE");
            long id = db.insert(DatabaseHelper.APPOINTMENT, null, contentValues);
            if(id != -1) {
                Toast.makeText(this, "Successfully Booked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("ID", ID);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(this, "Something Wrong!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void init() {
        final Calendar c = Calendar.getInstance();
        schedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dpd = TimePickerDialog.newInstance(resTimeListener,
                        hourOfDay,
                        minOfDay,
                        0,
                        false);
                String currentDateString = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
                if(currentDateString.equalsIgnoreCase(schedDate.getText().toString())) {
                    dpd.setMinTime(hourOfDay, minOfDay, 0);
                }
                dpd.show(getFragmentManager(), "Time picker dialog");
            }
        });

        resTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                boolean isPM = (hourOfDay >= 12);
                schedTime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
            }
        };

        schedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = DatePickerDialog.newInstance(startDateListener,
                        start_year,
                        start_month,
                        start_day);
                dpd.setMinDate(c);
                dpd.show(getFragmentManager(), "Date picker dialog");
            }
        });

        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                start_year = year;
                start_month = monthOfYear;
                start_day = dayOfMonth;
                String currentDateString = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());

                schedDate.setText(currentDateString);
            }
        };

    }
    private void setItems() {
        bookBtn = findViewById(R.id.bookbtn);
        schedDate = findViewById(R.id.schedDate);
        schedTime = findViewById(R.id.schedTime);
        String[] arraySpinner = new String[] {
                "Hair creambath", "Hair coloring", "Make up", "Facial vit C", "Eyelash extension", "Body massage", "Waxing"
        };
        serviceSpinner = findViewById(R.id.Spinner01);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        serviceSpinner.setAdapter(adapter);

        String[] arraySpinner1 = new String[] {
                "Joan", "Stephanie", "Mike", "John", "Ericka"
        };
        beauticianSpinner = findViewById(R.id.Spinner02);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner1);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        beauticianSpinner.setAdapter(adapter1);
    }
}

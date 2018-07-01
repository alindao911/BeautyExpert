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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    private EditText email;
    private EditText pass;
    private EditText fullName;
    private EditText addr;
    private EditText phoneNum;
    private EditText age;
    private Button registerBtn;
    private Spinner genderSpinner;

    //SQLITE CHUCHU
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        refId();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = openHelper.getWritableDatabase();
                String em = email.getText().toString();
                String ps = pass.getText().toString();
                String fn = fullName.getText().toString();
                String ad = addr.getText().toString();
                String pn = phoneNum.getText().toString();
                String ag = age.getText().toString();
                String gn = genderSpinner.getSelectedItem().toString();
                if(em.isEmpty() && ps.isEmpty() && fn.isEmpty() && ad.isEmpty() &&
                        pn.isEmpty() && ag.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Input all fields!", Toast.LENGTH_SHORT).show();
                }
                else {
                    insertData(em, ps, fn, ad, pn, ag, gn);
                }
            }
        });
    }
    private void insertData(String email, String password, String fullname, String address, String phone, String age, String gender) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.USERS_2, email);
        contentValues.put(DatabaseHelper.USERS_3, password);
        contentValues.put(DatabaseHelper.USERS_4, fullname);
        contentValues.put(DatabaseHelper.USERS_5, address);
        contentValues.put(DatabaseHelper.USERS_6, phone);
        contentValues.put(DatabaseHelper.USERS_7, age);
        contentValues.put(DatabaseHelper.USERS_8, gender);
        long id = db.insert(DatabaseHelper.USERS, null, contentValues);
        if(id != -1) {
            Toast.makeText(SignupActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, "Something Wrong!", Toast.LENGTH_SHORT).show();
        }

    }
    private void refId() {
        String[] arraySpinner1 = new String[] {
                "Male", "Female"
        };
        genderSpinner = findViewById(R.id.Spinner02);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner1);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter1);

        openHelper = new DatabaseHelper(this);
        email = findViewById(R.id.etRegEmail);
        pass = findViewById(R.id.etRegPassword);
        fullName = findViewById(R.id.etFullName);
        addr = findViewById(R.id.etRegAddress);
        phoneNum = findViewById(R.id.etRegContactNumber);
        age = findViewById(R.id.etAge);
        registerBtn = findViewById(R.id.registerBtn);
    }
}

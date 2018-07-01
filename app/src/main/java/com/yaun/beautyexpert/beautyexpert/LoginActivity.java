package com.yaun.beautyexpert.beautyexpert;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button signinBtn;
    private Button signupBtn;

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        refId();

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString();
                String pass = password.getText().toString();
                if(em.isEmpty() && pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Input all fields!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String whereClause = " WHERE email=? AND password =?";
                    cursor = db.rawQuery("SELECT * FROM " +
                                    DatabaseHelper.USERS+
                                    whereClause,
                            new String[]{em, pass});
                    if(cursor != null) {
                        if(cursor.getCount() > 0) {
                            cursor.moveToFirst();
                            int userid = cursor.getInt(cursor.getColumnIndex("ID"));
                            Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("ID", userid);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }
    private void refId() {
        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();
        email = findViewById(R.id.etUserName);
        password = findViewById(R.id.etPassword);
        signinBtn = findViewById(R.id.btnSignin);
        signupBtn = findViewById(R.id.btnSignup);
    }
}

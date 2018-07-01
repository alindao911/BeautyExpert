package com.yaun.beautyexpert.beautyexpert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private CardView btnSet;
    private CardView btnView;
    private CardView btnCancel;
    private CardView btnLogout;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        refIDs();

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SetAppointmentActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewReservationActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CancelReservationActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void refIDs() {
        Bundle exras = getIntent().getExtras();
        id = exras.getInt("ID");
        btnSet = findViewById(R.id.setAppointmentBtn);
        btnView = findViewById(R.id.viewReservationBtn);
        btnCancel = findViewById(R.id.cancelReservationBtn);
        btnLogout = findViewById(R.id.logout);
    }
}

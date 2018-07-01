package com.yaun.beautyexpert.beautyexpert;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sharls on 5/31/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="beautyexpert.db";
    public static final String USERS="users";
    public static final String USERS_1 ="ID";
    public static final String USERS_2 ="email";
    public static final String USERS_3 ="password";
    public static final String USERS_4 ="fullname";
    public static final String USERS_5 ="address";
    public static final String USERS_6 ="contact";
    public static final String USERS_7 ="age";
    public static final String USERS_8 ="gender";

    public static final String APPOINTMENT ="appointments";
    public static final String COL_1 ="ID";
    public static final String COL_2 ="userid";
    public static final String COL_3 ="service";
    public static final String COL_4 ="date";
    public static final String COL_5 ="time";
    public static final String COL_6 ="status";
    public static final String COL_7 ="beautician";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " +USERS+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,email TEXT,password TEXT,fullname TEXT,address TEXT,contact TEXT,age TEXT,gender TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE " +APPOINTMENT+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,userid INTEGER,service TEXT,date TEXT,time TEXT,status TEXT,beautician TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ USERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ APPOINTMENT);
        onCreate(sqLiteDatabase);
    }
}

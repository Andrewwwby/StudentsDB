package com.Andrew.StudentsDB;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper {
    //номер версии для обновления версии базы данных
    //каждый раз, если добавлять данные, редактировать таблицу, необходимо изменить
    //номер версии.
    private static final int DATABASE_VERSION = 5;

    // имя базы данных
    private static final String DATABASE_NAME = "stud.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //создание таблицы

        String CREATE_TABLE_STUDENT = "CREATE TABLE " + Student.TABLE  + "("
                + Student.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Student.KEY_name + " TEXT, "
                + Student.KEY_mark + " DOUBLE, "
                + Student.KEY_contact + " TEXT, "
                + Student.KEY_comment + " TEXT, "
                + Student.KEY_group + " TEXT )";

        db.execSQL(CREATE_TABLE_STUDENT);

        String CREATE_TABLE_MARK = "CREATE TABLE " + Mark.TABLE  + "("
                + Mark.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Mark.KEY_STUDENT_ID + " INTEGER, "
                + Mark.KEY_value + " INTEGER, "
                + Mark.KEY_date + " TEXT )";

        db.execSQL(CREATE_TABLE_MARK);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // удаление старых данных таблицы
        db.execSQL("DROP TABLE IF EXISTS " + Student.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Mark.TABLE);

        onCreate(db);

    }

}
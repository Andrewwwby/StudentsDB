package com.Andrew.StudentsDB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;

public class StudentRepo {
    private DBHelper dbHelper;

    public StudentRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Student student) {

        //соединение для записи данных
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Student.KEY_mark, student.mark);
        values.put(Student.KEY_group,student.group);
        values.put(Student.KEY_name, student.name);
        values.put(Student.KEY_contact, student.contact);
        values.put(Student.KEY_comment, student.comment);

        // Вставка строки
        long student_Id = db.insert(Student.TABLE, null, values);
        db.close();
        return (int) student_Id;
    }
    public int insert(Mark mark) {

        //соединение для записи данных
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Mark.KEY_STUDENT_ID, mark.mark_student_ID);
        values.put(Mark.KEY_value,mark.value);
        values.put(Mark.KEY_date, mark.date);


        // Вставка строки
        long mark_Id = db.insert(Mark.TABLE, null, values);
        db.close();
        return (int) mark_Id;
    }

    public void delete(int student_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(Student.TABLE, Student.KEY_ID + "= ?", new String[] { String.valueOf(student_Id) });
        db.close();
    }
    public void deleteMark(int mark_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(Mark.TABLE, Mark.KEY_ID + "= ?", new String[] { String.valueOf(mark_Id) });
        db.close();
    }

    public void update(Student student) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Student.KEY_mark, student.mark);
        values.put(Student.KEY_group,student.group);
        values.put(Student.KEY_name, student.name);
        values.put(Student.KEY_contact, student.contact);
        values.put(Student.KEY_comment, student.comment);


        db.update(Student.TABLE, values, Student.KEY_ID + "= ?", new String[] { String.valueOf(student.student_ID) });
        db.close();
    }

    public void update(Mark mark) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Mark.KEY_STUDENT_ID, mark.mark_student_ID);
        values.put(Mark.KEY_value,mark.value);
        values.put(Mark.KEY_date, mark.date);



        db.update(Mark.TABLE, values, Mark.KEY_ID + "= ?", new String[] { String.valueOf(mark.mark_ID) });
        db.close();
    }

    public ArrayList<HashMap<String, String>>  getStudentList() {
        //чтение данных
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Student.KEY_ID + "," +
                Student.KEY_name + "," +
                Student.KEY_group + "," +
                Student.KEY_contact + "," +
                Student.KEY_comment + "," +
                Student.KEY_mark +
                " FROM " + Student.TABLE;


        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // добавляем строки к списку

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<String, String>();
                student.put("id", cursor.getString(cursor.getColumnIndex(Student.KEY_ID)));
                student.put("name", cursor.getString(cursor.getColumnIndex(Student.KEY_name)));
                student.put("group", cursor.getString(cursor.getColumnIndex(Student.KEY_group)));
                studentList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }

    // вывод списка групп

    public ArrayList<HashMap<String, String>>  getGroupList() {
        //чтение данных
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Student.KEY_ID + "," +
                Student.KEY_name + "," +
                Student.KEY_group + "," +
                Student.KEY_contact + "," +
                Student.KEY_comment + "," +
                Student.KEY_mark +
                " FROM " + Student.TABLE;


        ArrayList<HashMap<String, String>> groupList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // добавляем строки к списку

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> group = new HashMap<String, String>();
                group.put("id", cursor.getString(cursor.getColumnIndex(Student.KEY_ID)));
                group.put("name", cursor.getString(cursor.getColumnIndex(Student.KEY_group)));
                group.put("group", "");
                groupList.add(group);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return groupList;

    }



    public Student getStudentById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Student.KEY_ID + "," +
                Student.KEY_name + "," +
                Student.KEY_group + "," +
                Student.KEY_contact + "," +
                Student.KEY_comment + "," +
                Student.KEY_mark +
                " FROM " + Student.TABLE
                + " WHERE " +
                Student.KEY_ID + "=?";

        int iCount =0;
        Student student = new Student();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                student.student_ID =cursor.getInt(cursor.getColumnIndex(Student.KEY_ID));
                student.name =cursor.getString(cursor.getColumnIndex(Student.KEY_name));
                student.group  =cursor.getString(cursor.getColumnIndex(Student.KEY_group));
                student.mark =cursor.getDouble(cursor.getColumnIndex(Student.KEY_mark));
                student.contact=cursor.getString(cursor.getColumnIndex(Student.KEY_contact));
                student.comment=cursor.getString(cursor.getColumnIndex(Student.KEY_comment));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return student;
    }
    public ArrayList<HashMap<String, String>> getMarksByStudentId(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Mark.KEY_ID + "," +
                Mark.KEY_STUDENT_ID + "," +
                Mark.KEY_value + "," +
                Mark.KEY_date +
                " FROM " + Mark.TABLE
                + " WHERE " +
                Mark.KEY_STUDENT_ID + "=?";

        int iCount =0;
        ArrayList<HashMap<String, String>> marksList = new ArrayList<HashMap<String, String>>();


        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> mark = new HashMap<String, String>();
                mark.put("id", cursor.getString(cursor.getColumnIndex(Mark.KEY_ID)));
                mark.put("studentid", cursor.getString(cursor.getColumnIndex(Mark.KEY_STUDENT_ID)));
                mark.put("value", cursor.getString(cursor.getColumnIndex(Mark.KEY_value)));
                mark.put("date", cursor.getString(cursor.getColumnIndex(Mark.KEY_date)));
                marksList.add(mark);


            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return marksList;
    }

}
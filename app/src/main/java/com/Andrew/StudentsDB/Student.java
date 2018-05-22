package com.Andrew.StudentsDB;



public class Student {
    // имя таблицы
    public static final String TABLE = "Student";

    // имена столбцов таблицы
    public static final String KEY_ID = "id";
    public static final String KEY_name = "name";
    public static final String KEY_group = "email";
    public static final String KEY_mark = "age";
    public static final String KEY_contact = "contact";
    public static final String KEY_comment = "comment";

    // сохранение данных
    public int student_ID;
    public String name;
    public String group;
    public double mark;
    public String contact;
    public String comment;
}
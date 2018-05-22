package com.Andrew.StudentsDB;


public class Mark {
    // имя таблицы
    public static final String TABLE = "Marks";

    // имена столбцов таблицы
    public static final String KEY_ID = "id";

    public static final String KEY_STUDENT_ID = "studentid";
    public static final String KEY_value = "value";
    public static final String KEY_date = "date";


    // сохранение данных
    public int mark_ID;
    public int mark_student_ID;

    public int value;
    public String date;


}

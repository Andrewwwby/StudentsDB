package com.Andrew.StudentsDB;

import android.app.AlertDialog;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class StudentDetail extends ActionBarActivity implements android.view.View.OnClickListener{

    Button btnSave ,  btnDelete;
    Button btnClose;
    EditText editTextName;
    EditText editTextGroupNumber;
    EditText editTextMark;
    EditText editTextContact;
    EditText editTextComment;
    EditText markDateEdit;
    Spinner markSpinner;
    Button btnMark;
    Button btnMarkOk;
    LinearLayout markBlock;
    ListView listMarks;

    private int _Student_Id=0;
    private boolean addMark = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);
        btnMark = (Button)findViewById(R.id.btnMark);
        btnMarkOk = (Button)findViewById(R.id.buttonMarkOk);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextGroupNumber = (EditText) findViewById(R.id.editTextGroupNumber);
        editTextMark = (EditText) findViewById(R.id.editTextMark);
        editTextContact = (EditText) findViewById(R.id.editTextContact);
        editTextComment = (EditText) findViewById(R.id.editTextComment);
        markDateEdit = (EditText)findViewById(R.id.editTextMarkDate);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnMark.setOnClickListener(this);
        btnMarkOk.setOnClickListener(this);

        markSpinner = (Spinner)findViewById(R.id.markSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.marks, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        markSpinner.setAdapter(adapter);




        markBlock = (LinearLayout)findViewById(R.id.addMarkBlock);


        _Student_Id =0;
        Intent intent = getIntent();
        _Student_Id =intent.getIntExtra("student_Id", 0);
        StudentRepo repo = new StudentRepo(this);
        Student student = new Student();
        student = repo.getStudentById(_Student_Id);

        editTextMark.setText(String.valueOf(student.mark));
        editTextName.setText(student.name);
        editTextGroupNumber.setText(student.group);
        editTextContact.setText(student.contact);
        editTextComment.setText(student.comment);

        listMarks = (ListView)findViewById(R.id.listMarks);
        ArrayList<HashMap<String,String>> marks = repo.getMarksByStudentId(_Student_Id);

        ListAdapter marksAdapter = new SimpleAdapter( this,marks, R.layout.view_student_entry, new String[] { "id","value","date"}, new int[] {R.id.student_Id, R.id.student_name,R.id.student_group});
        listMarks.setAdapter(marksAdapter);



    }



    public void onClick(View view) {
        if (view == findViewById(R.id.btnSave)){
            StudentRepo repo = new StudentRepo(this);
            Student student = new Student();
            student.mark= Double.parseDouble(editTextMark.getText().toString());
            student.group=editTextGroupNumber.getText().toString();
            student.name=editTextName.getText().toString();
            student.student_ID=_Student_Id;
            student.contact=editTextContact.getText().toString();
            student.comment=editTextComment.getText().toString();

            if(editTextGroupNumber.getText().toString().trim().length()==0||
                    editTextName.getText().toString().trim().length()==0||
                    editTextMark.getText().toString().trim().length()==0)
            {
                showMessage("Ошибка", "Пожалуйста заполните все поля");
                return;
            }



            if (_Student_Id==0){
                _Student_Id = repo.insert(student);

                Toast.makeText(this,"Новая запись внесена",Toast.LENGTH_SHORT).show();
            }else{

                repo.update(student);
                Toast.makeText(this,"Запись обновлена",Toast.LENGTH_SHORT).show();
            }
        }else if (view== findViewById(R.id.btnDelete)){
            StudentRepo repo = new StudentRepo(this);
            repo.delete(_Student_Id);
            Toast.makeText(this, "Запись удалена", Toast.LENGTH_SHORT);
            finish();
        }else if (view== findViewById(R.id.btnMark)){
            if(addMark == false){
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) listMarks.getLayoutParams();
                params.addRule(RelativeLayout.ABOVE, R.id.addMarkBlock);
                listMarks.setLayoutParams(params);
                markBlock.setVisibility(LinearLayout.VISIBLE);
                GregorianCalendar date = new GregorianCalendar();
                markDateEdit.setText(date.get(GregorianCalendar.YEAR)+"-"+(date.get(GregorianCalendar.MONTH)+1)+"-"+date.get(GregorianCalendar.DAY_OF_MONTH));
                addMark = true;
            }else{
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) listMarks.getLayoutParams();
                params.addRule(RelativeLayout.ABOVE, R.id.btnSave);
                listMarks.setLayoutParams(params);
                markBlock.setVisibility(LinearLayout.GONE);
                addMark = false;
            }

        }else if (view.getId()== R.id.buttonMarkOk){
            String date = markDateEdit.getText().toString();
            int value = markSpinner.getSelectedItemPosition();
            Mark mark = new Mark();
            mark.date = date;
            mark.value = value;
            mark.mark_student_ID = _Student_Id;
            StudentRepo repo = new StudentRepo(this);
            int res = repo.insert(mark);
            Toast.makeText(this, "Оценка сохранена id="+res, Toast.LENGTH_LONG);

            ArrayList<HashMap<String,String>> marks = repo.getMarksByStudentId(_Student_Id);

            ListAdapter marksAdapter = new SimpleAdapter( this,marks, R.layout.view_student_entry, new String[] { "id","value","date"}, new int[] {R.id.student_Id, R.id.student_name,R.id.student_group});
            listMarks.setAdapter(marksAdapter);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) listMarks.getLayoutParams();
            params.addRule(RelativeLayout.ABOVE, R.id.btnSave);
            listMarks.setLayoutParams(params);
            markBlock.setVisibility(LinearLayout.GONE);
            addMark = false;

        }else if (view== findViewById(R.id.btnClose)){
            finish();
        }


    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}

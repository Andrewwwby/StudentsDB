package com.Andrew.StudentsDB;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog.Builder;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity  implements android.view.View.OnClickListener{

    Button btnAdd,btnGetAll, btnInfo, btnGetGroups;
    TextView student_Id;

    @Override
    public void onClick(View view) {

        btnInfo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                showMessage("Система управления студентами", "Разработано Гайко А.И.");
            }
        });

        if (view== findViewById(R.id.btnAdd)){

            Intent intent = new Intent(this,StudentDetail.class);
            intent.putExtra("student_Id",0);
            startActivity(intent);

        }

        else {

            StudentRepo repo = new StudentRepo(this);

            ArrayList<HashMap<String, String>> studentList =  repo.getStudentList();
            if(studentList.size()!=0) {
                ListView lv = getListView();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                        student_Id = (TextView) view.findViewById(R.id.student_Id);
                        String studentId = student_Id.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(),StudentDetail.class);
                        objIndent.putExtra("student_Id", Integer.parseInt( studentId));
                        startActivity(objIndent);
                    }
                });
                ListAdapter adapter = new SimpleAdapter( MainActivity.this,studentList, R.layout.view_student_entry, new String[] { "id","name","group"}, new int[] {R.id.student_Id, R.id.student_name,R.id.student_group});
                setListAdapter(adapter);
            }
            else{
                Toast.makeText(this,"Список пуст",Toast.LENGTH_SHORT).show();
            }

        }

        // выводим список всех групп
         if (view== findViewById(R.id.btnGetGroups)){

            StudentRepo repo = new StudentRepo(this);


            ArrayList<HashMap<String, String>> groupList =  repo.getGroupList();

            if(groupList.size()!=0) {
                ListView lv = getListView();

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    }
                });
                ListAdapter adapter = new SimpleAdapter( MainActivity.this,groupList, R.layout.view_student_entry, new String[] { "id","name","group"}, new int[] {R.id.student_Id, R.id.student_name,R.id.student_group});
                setListAdapter(adapter);
            }
            else{
                Toast.makeText(this,"Список групп пуст",Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        btnGetGroups = (Button) findViewById(R.id.btnGetGroups);
        btnGetGroups.setOnClickListener(this);

        btnInfo=(Button)findViewById(R.id.btnInfo);

    }

    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}
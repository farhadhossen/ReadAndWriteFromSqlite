package com.example.readandwritefromsqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyDatabaseahelper myDatabaseahelper;

    EditText edName, edAge, edgender, edId;
    Button btnSubmit, btnShow, btnUpdate, btnDelete;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDatabaseahelper = new MyDatabaseahelper(this);
        SQLiteDatabase sqLiteDatabase = myDatabaseahelper.getWritableDatabase();

        edName = findViewById(R.id.idName);
        edAge = findViewById(R.id.idAge);
        edgender = findViewById(R.id.idGender);
        btnSubmit = findViewById(R.id.idUpload);
        btnShow = findViewById(R.id.idShowData);
        tv = findViewById(R.id.idtvResult);
        edId = findViewById(R.id.idEditId);
        btnUpdate = findViewById(R.id.idUpdate);
        btnDelete = findViewById(R.id.idDelete);


        btnSubmit.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        String name = edName.getText().toString();
        String age = edAge.getText().toString();
        String gender = edgender.getText().toString();
        String id = edId.getText().toString();

        if (view.getId()==R.id.idUpload)
        {
          long rowId =  myDatabaseahelper.insertData(name,age,gender);

          if (rowId==-1)
          {
              Toast.makeText(this, "failed ", Toast.LENGTH_SHORT).show();

          }
          else {
              Toast.makeText(this, "ssfl "+rowId, Toast.LENGTH_SHORT).show();
          }
        }

        else if (view.getId()==R.id.idShowData){
            Cursor resultSet = myDatabaseahelper.displayAllData();

            if (resultSet.getCount()==0){
                //if no data exists
               // showData("Error","No data found");

                return;
            }

            StringBuffer stringBuffer = new StringBuffer();
            while (resultSet.moveToNext()){

                stringBuffer.append("ID : "+resultSet.getString(0)+"\n");
                stringBuffer.append("NAME : "+resultSet.getString(1)+"\n");
                stringBuffer.append("AGE : "+resultSet.getString(2)+"\n");
                stringBuffer.append("GENDER : "+resultSet.getString(3)+"\n");
            }

            //showData("Results",stringBuffer.toString());

            tv.setText(""+stringBuffer.toString());
        }

        else if(view.getId()==R.id.idUpdate){

           Boolean isUpdated = myDatabaseahelper.updateData(id,name,age,gender);
           if (isUpdated==true)
               Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show();
           else
               Toast.makeText(this, "Not Updated", Toast.LENGTH_SHORT).show();
        }

        else if (view.getId()==R.id.idDelete){

            int value = myDatabaseahelper.deleteData(id);
            if (value>0)
                Toast.makeText(this, "deletd", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Not Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    private void showData(String title, String data) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(data);
        builder.setMessage(title);
        builder.setCancelable(true);
        builder.show();

    }
}
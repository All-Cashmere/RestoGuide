package com.splash.ws.restoguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class AddResto extends AppCompatActivity {

    DatabaseHandler db;

    RatingBar ratingBar;
    EditText id, name, address, phone, desc, tags;
    Button add, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resto);

        db = new DatabaseHandler(this);

        ratingBar = findViewById(R.id.ratingBar);
        add = findViewById(R.id.btnAdd2);
        back = findViewById(R.id.btnBack);

        addResto();
        btnBack();

    }

    public void addResto(){

        id = findViewById(R.id.etID);
        name = findViewById(R.id.etName);
        address = findViewById(R.id.etAddress);
        phone = findViewById(R.id.etPhone);
        desc = findViewById(R.id.etDesc);
        tags = findViewById(R.id.etTags);

        // add resto button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String restoName = name.getText().toString();
                String restoAdd = address.getText().toString();
                String rPhone = phone.getText().toString();
                String rDesc = desc.getText().toString();
                String rTags = tags.getText().toString();
                String rRate = String.valueOf(ratingBar.getRating());

                if (name.length() !=0 || address.length() !=0 || phone.length() !=0 || desc.length() !=0 || tags.length() !=0){
                    AddData(restoName, restoAdd, rPhone, rDesc, rTags, rRate);
                    Intent intent = new Intent(AddResto.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(AddResto.this, "missing data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void AddData(String name, String add, String phone, String desc, String tag, String rate) {
        boolean insertData = db.addData(name, add, phone, desc, tag, rate);

        if (insertData) {
            Toast.makeText(AddResto.this, "Data Successfully Inserted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddResto.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnBack(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddResto.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

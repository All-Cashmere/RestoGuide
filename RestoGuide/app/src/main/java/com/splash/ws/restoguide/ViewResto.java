package com.splash.ws.restoguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ViewResto extends AppCompatActivity {

    DatabaseHandler db;

    TextView name, address, phone, description, tags;
    RatingBar ratingBar;
    Button back, edit, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_resto);

        setTextView();
        setBtnBack();
        setBtnDelete();
    }

    public void setTextView(){
        //set all textview
        name = findViewById(R.id.etName);
        address = findViewById(R.id.etAddress);
        phone = findViewById(R.id.etPhone);
        description = findViewById(R.id.etDesc);
        tags = findViewById(R.id.etTags);
        ratingBar = findViewById(R.id.ratingBar);
//        String.valueOf(value);

        Intent i = getIntent();

        String n = i.getStringExtra("name");
        String a = i.getStringExtra("add");
        String p = i.getStringExtra("phone");
        String d = i.getStringExtra("desc");
        String t = i.getStringExtra("tags");
        String r = i.getStringExtra("rate");

        name.setText(n);
        address.setText(a);
        phone.setText(p);
        description.setText(d);
        tags.setText(t);
        ratingBar.setRating(Float.parseFloat(r));
    }

    public void setBtnBack(){
        //back button

        back = findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent list = new Intent(ViewResto.this, MainActivity.class);
                startActivity(list);
                finish();
            }
        });
    }

    public void setBtnDelete(){
        //back button
        Intent i = getIntent();
        final int itemId = i.getIntExtra("id", -1);
        final String name = i.getStringExtra("name");
        Log.d("id", "ID: "+itemId);

        delete = findViewById(R.id.btnDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean deleted = db.deleteItem(name);
                Intent list = new Intent(ViewResto.this, MainActivity.class);
                if(deleted)
                {
                    startActivity(list);
                    finish();
                }else {
                    Toast.makeText(ViewResto.this,"Unable To Delete",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

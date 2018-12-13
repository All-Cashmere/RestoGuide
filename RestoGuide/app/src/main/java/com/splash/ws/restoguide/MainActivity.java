package com.splash.ws.restoguide;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    Button home, search, about, add;

    //database handler
    DatabaseHandler db;
    private static final String TAG = "ListDataActivity";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SystemClock.sleep(4000);

        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        home = findViewById(R.id.btnHome);
        search = findViewById(R.id.btnSearch);
        about = findViewById(R.id.btnAbout);
        add = findViewById(R.id.btnAdd);

        // database
        db = new DatabaseHandler(this);
        listView = findViewById(R.id.listview);

        viewList();
        btnAdd();
        btnHome();
        btnAbout();
        btnSearch();
    }




    public void btnAdd(){
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent addResto = new Intent(MainActivity.this, AddResto.class);
                startActivity(addResto);
                finish();
            }
        });
    }

    public void btnHome(){
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void btnAbout(){
        about.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, About.class);
                startActivity(i);
            }
        });
    }


    public void btnSearch(){
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });
    }

    public void viewList(){
        //get the data and append to a list
        Cursor data = db.getData();
        ArrayList<String> listData = new ArrayList<>();

        // create the list adapter and set the adapter
        final ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);

        while(data.moveToNext()){
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(1));
            listView.setAdapter(adapter);
        }

        //set on click listener for every item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Cursor all = db.getItemAll(position+1);

                //error handling
                //if not exist
                int id = -1;
                String name="",
                        add="",
                        phone="",
                        desc="",
                        tags="",
                        rate="";

                while(all.moveToNext()){
                    // get value of each column
                    id = all.getInt(all.getColumnIndex("id"));
                    name = all.getString(all.getColumnIndex("name"));
                    add = all.getString(all.getColumnIndex("address"));
                    phone = all.getString(all.getColumnIndex("phone"));
                    desc = all.getString(all.getColumnIndex("description"));
                    tags = all.getString(all.getColumnIndex("tags"));
                    rate = all.getString(all.getColumnIndex("rate"));
                }

                if (id > -1){
                    Intent restoView = new Intent(MainActivity.this, ViewResto.class);
                    restoView.putExtra("id", id);
                    restoView.putExtra("name", name);
                    restoView.putExtra("add", add);
                    restoView.putExtra("phone", phone);
                    restoView.putExtra("desc", desc);
                    restoView.putExtra("tags", tags);
                    restoView.putExtra("rate", rate);
                    startActivity(restoView);
                }
                else {
                    Toast.makeText(MainActivity.this, "no item found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

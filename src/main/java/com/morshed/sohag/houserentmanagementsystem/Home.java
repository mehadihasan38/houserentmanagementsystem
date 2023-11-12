package com.morshed.sohag.houserentmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {
Spinner sp,location;


String d[]=new String[]
        {"1","2","3","4","5","6","7","8","9","10"};
String e[]=new String[]
            {"Rajshahi","Chapai Nawabganj","Natore","Naogaon","Bogura","Pabna","Joypurhat","Dhaka","Khulna","Sirajganj"};

LinearLayout gender;

String gen="Male",people="Single";

String room=null,locate=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Toolbar tb=findViewById(R.id.home_toolbar);
        setSupportActionBar(tb);


        DrawerLayout drawerLayout = findViewById(R.id.drawer_home);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,tb, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
        NavigationView nv=findViewById(R.id.nav_home);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.about_us:
                        startActivity(new Intent(Home.this,AboutUs.class));
                        break;
                    case R.id.exit:
                        finishAffinity();
                        break;
                }
                return true;
            }
        });

        sp=findViewById(R.id.number_of_room_room_view);
        location=findViewById(R.id.location_home);
        location.setAdapter(new ArrayAdapter<String>(Home.this, android.R.layout.simple_list_item_1,e));
        gender=findViewById(R.id.gender_upload_room);
location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        locate=e[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
});


        sp.setAdapter(new ArrayAdapter<String>(Home.this, android.R.layout.simple_list_item_1,d));

sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        room=d[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
});
        ImageView single=findViewById(R.id.room_for_single_room_view);
        ImageView multiple=findViewById(R.id.room_for_multiple_room_view);
       single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender.setVisibility(View.VISIBLE);
                people="Single";
                single.setAlpha(1f);
                multiple.setAlpha(0.5f);

            }
        });
        multiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender.setVisibility(View.GONE);
                people="Family";
                single.setAlpha(0.5f);
                multiple.setAlpha(1f);
            }
        });
        ImageView man=findViewById(R.id.gender_male_home);
        ImageView women=findViewById(R.id.gender_woman_room_view);
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gen="Man";
                women.setAlpha(0.5f);
                man.setAlpha(1f);
            }
        });
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gen="Woman";
                man.setAlpha(0.5f);
                women.setAlpha(1f);
            }
        });

        findViewById(R.id.search_button_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Home.this,SearchList.class);
                in.putExtra("Location",locate);
                in.putExtra("RoomNumber",room);
                in.putExtra("Gender",gen);
                in.putExtra("People",people);
                startActivity(new Intent(in));
            }
        });
        findViewById(R.id.upload_button_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,UploadRoom.class));
            }
        });
    }
}
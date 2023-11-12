package com.morshed.sohag.houserentmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RoomView extends AppCompatActivity {
    String image,descri,gender,room,people,locat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_view);
        image=getIntent().getStringExtra("Image");
        descri=getIntent().getStringExtra("Description");
        gender =getIntent().getStringExtra("Gender");
        room=getIntent().getStringExtra("RoomNumber");
        people=getIntent().getStringExtra("People");
        locat=getIntent().getStringExtra("Location");
        ImageView iv=findViewById(R.id.photo_upload_room);

        Picasso.get().load(image).into(iv);
        ImageView single,multiple,man,woman;
        single=findViewById(R.id.room_for_single_room_view);
        multiple=findViewById(R.id.room_for_multiple_room_view);
        man=findViewById(R.id.gender_male_home);
        woman=findViewById(R.id.gender_woman_room_view);
        if(people.equals("Single")){
            multiple.setVisibility(View.INVISIBLE);
            single.setVisibility(View.VISIBLE);
            if(gender.equals("Woman")){
                woman.setVisibility(View.VISIBLE);
                man.setVisibility(View.INVISIBLE);
            }else if(gender.equals("Woman")){
                woman.setVisibility(View.INVISIBLE);
                man.setVisibility(View.VISIBLE);
            }
        }else{
            LinearLayout gn=findViewById(R.id.gender_upload_room);
            multiple.setVisibility(View.VISIBLE);
            single.setVisibility(View.INVISIBLE);
            gn.setVisibility(View.GONE);
        }

        TextView noofroom=findViewById(R.id.number_of_room_room_view);
        TextView location,description;
        location=findViewById(R.id.location_room_view);
        description=findViewById(R.id.discription_room_view);
        noofroom.setText(room);
        location.setText(locat);
        description.setText(descri);




    }
}
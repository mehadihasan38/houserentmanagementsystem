package com.morshed.sohag.houserentmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchList extends AppCompatActivity {
    ListView lv;
    BaseAdapter ba;
    ArrayList<RoomItem>list;
    String loc,room,gen,people;
    DatabaseReference dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        lv=findViewById(R.id.search_list);
        dr=FirebaseDatabase.getInstance().getReference("AllRoom");
        list=new ArrayList<>();
        loc=getIntent().getStringExtra("Location");
        room=getIntent().getStringExtra("RoomNumber");
        gen=getIntent().getStringExtra("Gender");
        people=getIntent().getStringExtra("People");


        ba=new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int i) {
                return i;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if(view==null){
                    view=getLayoutInflater().inflate(R.layout.search_item_view,null);
                }
                RoomItem ri=list.get(i);
                ImageView iv=view.findViewById(R.id.image_search_item_view);
                Picasso.get().load(ri.getImageuri()).into(iv);
                TextView tv=view.findViewById(R.id.search_item_details);
                tv.setText(ri.getDescription().length()<20?ri.getDescription():ri.getDescription().substring(0,20)+"...");

                return view;
            }
        };


        lv.setAdapter(ba);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in=new Intent(SearchList.this,RoomView.class);
                in.putExtra("Image",list.get(i).getImageuri());
                in.putExtra("Description",list.get(i).getDescription());
                in.putExtra("Gender",list.get(i).getGender());
                in.putExtra("RoomNumber",list.get(i).getRoomnumber());
                in.putExtra("People",list.get(i).getPeople());
                in.putExtra("Location",list.get(i).getLocation());
                startActivity(in);
            }
        });


        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                list=new ArrayList<>();
                for(DataSnapshot d:ds.getChildren()){
                    RoomItem ri=d.getValue(RoomItem.class);
                    if(room.equals(ri.getRoomnumber())&&people.equals(ri.getPeople())&&loc.equals(ri.getLocation())){
                        if(people.equals("Multiple")){
                            list.add(ri);
                        }else if(gen.equals(ri.getGender())){
                            list.add(ri);
                        }
                    }
                }
                ba.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
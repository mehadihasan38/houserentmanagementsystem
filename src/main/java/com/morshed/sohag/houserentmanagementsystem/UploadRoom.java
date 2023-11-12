package com.morshed.sohag.houserentmanagementsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class UploadRoom extends AppCompatActivity {
    Spinner sp, location;

    String d[] = new String[]
            {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    String e[] = new String[]
            {"Rajshahi", "Chapai Nawabganj", "Natore", "Naogaon", "Bogura", "Pabna", "Joypurhat", "Dhaka", "Khulna", "Sirajganj"};

    LinearLayout gender;
    ImageView iv;
    Uri link=null;
    String gen = "Male", people = "Single";

    String room = null, locate = null;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_room);
        sp = findViewById(R.id.number_of_room_room_view);
        et=findViewById(R.id.description_upload_room);
        location = findViewById(R.id.location_room_view);
        location.setAdapter(new ArrayAdapter<String>(UploadRoom.this, android.R.layout.simple_list_item_1, e));
        gender = findViewById(R.id.gender_upload_room);
        iv=findViewById(R.id.photo_upload_room);
        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                locate = e[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        sp.setAdapter(new ArrayAdapter<String>(UploadRoom.this, android.R.layout.simple_list_item_1, d));

sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                room = d[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
});

        ImageView single=findViewById(R.id.room_for_single_upload_room);
        ImageView multiple=findViewById(R.id.room_for_multiple_upload_room);
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
        ImageView man=findViewById(R.id.gender_male_upload_room);
        ImageView women=findViewById(R.id.gender_woman_upload_room);
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
        findViewById(R.id.gender_male_upload_room).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gen = "Man";
            }
        });
        findViewById(R.id.gender_woman_upload_room).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gen = "Woman";
            }
        });

        findViewById(R.id.upload_upload_room).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et.getText().toString().isEmpty()) {
                    et.setError("Fill it before upload");
                    return;
                }

                StorageReference sr = FirebaseStorage.getInstance().getReference();
                String name = "img" + System.currentTimeMillis() + ".jpg";
                sr.child(name).putFile(link).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        sr.child(name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                DatabaseReference dr = FirebaseDatabase.getInstance().getReference("AllRoom");
                                String key = dr.push().getKey();

                                RoomItem ri = new RoomItem(key, gen, people, room, et.getText().toString(), uri.toString(), locate);

                                dr.child(key).setValue(ri).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(UploadRoom.this, "Your data upload.", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(UploadRoom.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        });
                    }
                });




            }
        });


        findViewById(R.id.choose_upload_room).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Intent.ACTION_PICK);
                in.setType("image/*");
                startActivityForResult(Intent.createChooser(in, "Select your app:"), 100);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!= Activity.RESULT_OK)return;

        if(requestCode==100){
            Bitmap bit= null;
            try {
                link=data.getData();
                bit = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                iv.setImageBitmap(bit);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }


    }
}
package com.morshed.sohag.houserentmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Signup extends AppCompatActivity {
    EditText email,pass,cpass,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email=findViewById(R.id.email_signup);
        pass=findViewById(R.id.password_signup);
        cpass=findViewById(R.id.cpassword_signup);
        name=findViewById(R.id.name_signup);



        findViewById(R.id.signup_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty()){
                    name.setError("Fill it");
                    return ;
                }
                if(email.getText().toString().isEmpty()){
                    email.setError("Fill it");
                    return;
                }
                if(pass.getText().toString().isEmpty()){
                    pass.setError("Fill it");
                    return;
                }
                if(cpass.getText().toString().isEmpty()){
                    cpass.setError("Fill it");
                    return;
                }
                if(pass.getText().toString().equals(cpass)){
                    pass.setError("Password doesn't matched with confirm password");
                    cpass.setError("Password doesn't matched with confirm password");
                    return;
                }

                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name.getText().toString())
                                            .build();
                                    task.getResult().getUser().updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                startActivity(new Intent(Signup.this,Home.class));
                                                finish();
                                            }else{
                                                Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else{
                                    Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
package com.example.womensafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivty extends AppCompatActivity {
    private EditText email;

    private EditText password;
    private Button loginBtn;
    private ImageView backBtn;
    private ImageView manu;
    private TextView registerTxt;
    private FirebaseAuth firebaseAuth;

    private String emailPattern="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activty);

        email = findViewById(R.id.login_email_txt);
        registerTxt = findViewById(R.id.login_register_txt);
        password = findViewById(R.id.login_password_txt);
        loginBtn= findViewById(R.id.login_btn);
        firebaseAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInputs();
            }
        });

        registerTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(LoginActivty.this,RegisterActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });



    }



    private void checkInputs() {
        if(TextUtils.isEmpty(email.getText().toString())){

            email.setError("Email is required!");
            return;

        }
        if(TextUtils.isEmpty(password.getText().toString())){

            password.setError("Password is required!");
            return;

        }
        if(password.getText().length() < 8){
            password.setError("Password should at least 8 char");
            return;
        }


        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivty.this,"Login Successfully!",Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(LoginActivty.this,MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }else{
                    Toast.makeText(LoginActivty.this,"Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}
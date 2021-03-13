package com.example.womensafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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


public class RegisterActivity extends AppCompatActivity {
    private EditText email;
    private  EditText name;
    private EditText mobile;
    private EditText password;
    private Button register;
    private ImageView backBtn;
    private ImageView manu;
    private TextView signinText;


    private  FirebaseAuth firebaseAuth;

    private String emailPattern="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";




    private Button btn; //----------------------------------------------------------------------------------------------------------------this gonna delete

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name_txt);
        email = findViewById(R.id.email_txt);
        mobile = findViewById(R.id.mobile_no);
        password = findViewById(R.id.password_txt);
        register = findViewById(R.id.register_btn);
        signinText = findViewById(R.id.signin_txt);
        firebaseAuth = FirebaseAuth.getInstance();
        //------------------------------------------------------------------------------------------------------------------------------this should be dlelte------------------------------------
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //----------------------------------------------------------------------------------------------------------------------------------------------------------

        if(firebaseAuth.getCurrentUser() != null){
            Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(mainIntent);
            finish();

        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInputs();
            }
        });

        signinText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(RegisterActivity.this,LoginActivty.class);
                startActivity(mainIntent);
                finish();
            }
        });

    }





    private void checkInputs() {

        if(TextUtils.isEmpty(name.getText().toString())){

            name.setError("Email is required!");
            return;

        }

        if(TextUtils.isEmpty(email.getText().toString())){

            email.setError("Email is required!");
            return;

        }
        if(TextUtils.isEmpty(mobile.getText().toString())){

            mobile.setError("Mobile number is required!");
            return;

        }
        if(TextUtils.isEmpty(password.getText().toString())){

            password.setError("Password is required!");
            return;

        }
        if(mobile.getText().length() != 10){
            mobile.setError("Check your Mobile number!");
            return;
        }
        if(password.getText().length() < 8){
            password.setError("Password should at least 8 char");
            return;
        }


        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,"User Cerated!",Toast.LENGTH_SHORT).show();
                            Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(mainIntent);
                            finish();

                        }else{
                            Toast.makeText(RegisterActivity.this,"Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }

                        }
                });


    }
}
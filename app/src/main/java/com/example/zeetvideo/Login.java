package com.example.zeetvideo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
private FirebaseAuth mAuth;
TextInputEditText email,password;
Button login,signIn;
TextView forgetpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailID);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signIn = findViewById(R.id.signIn);
        forgetpassword = findViewById(R.id.forgetpassword);
        AlertBox alertBox = new AlertBox(Login.this);
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(Login.this,resetpassword.class);
                startActivity(n);

            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,SignIn.class);
                startActivity(i);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertBox.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        alertBox.dismissDialog();
                    }
                },2000);
                String Email = email.getText().toString().trim();
                String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
                String Password = password.getText().toString().trim();
                if(TextUtils.isEmpty(Email)){
                    Toast.makeText(Login.this,"please enter email !!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Password)){
                    Toast.makeText(Login.this,"please enter the password !!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Password.length()<6){
                    Toast.makeText(Login.this,"Password length is too short !!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(Password.matches(pattern)){
                    mAuth.signInWithEmailAndPassword(Email,Password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent i = new Intent(Login.this,MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        Toast.makeText(Login.this,"SignIn Failed !!",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });

                }

            }
        });

    }
}
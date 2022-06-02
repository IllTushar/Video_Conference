package com.example.zeetvideo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
TextInputEditText name,email,password,confirmpassword;
Button login,signIn;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        name = findViewById(R.id.name);
        email = findViewById(R.id.emailID);
        password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        confirmpassword= findViewById(R.id.conpassword);
        login = findViewById(R.id.login);
        AlertBox alertBox = new AlertBox(SignIn.this);
        signIn = findViewById(R.id.signIn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(SignIn.this,Login.class);
                startActivity(i);
                finish();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
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
                String Name = name.getText().toString();
                //String Phone = phone.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                String Confirmpassword = confirmpassword.getText().toString();
                String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";

                if (TextUtils.isEmpty(Name)){
                    Toast.makeText(SignIn.this,"Enter your name !!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Password)){
                    Toast.makeText(SignIn.this,"Enter your password !!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Password.length()<6){
                    Toast.makeText(SignIn.this,"Password length is too short !!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!(Password.matches(pattern))){
                    Toast.makeText(SignIn.this,"Enter Valid Password !!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(Confirmpassword)){
                    Toast.makeText(SignIn.this,"Enter your confirm-password !!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (Password.equals(Confirmpassword)){
                    mAuth.createUserWithEmailAndPassword(Email,Password)
                            .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent o = new Intent(SignIn.this,MainActivity.class);
                                        startActivity(o);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SignIn.this,"SignIn Failed !!",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });
                }
            }
        });
    }
}
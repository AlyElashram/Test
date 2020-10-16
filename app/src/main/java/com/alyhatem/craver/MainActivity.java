 package com.alyhatem.craver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button SignInEmail_btn, SignInGuest_btn, signout_btn,Register_btn;
    EditText Email_txt, Password_txt;
    FirebaseAuth Authenticator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Email_txt = findViewById(R.id.Email_txt);
        Password_txt = findViewById(R.id.Password_txt);
        Register_btn=findViewById(R.id.Register_btn);
        Email_txt.setHint("Email To sign in or Sign up");
        Password_txt.setHint("Password");
        SignInEmail_btn = findViewById(R.id.SignInEmail_btn);
        SignInGuest_btn = findViewById(R.id.SignInGuest_btn);
        signout_btn = findViewById(R.id.signout_btn);
        Authenticator = FirebaseAuth.getInstance();
        signout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Authenticator.getCurrentUser() != null) {
                    Authenticator.signOut();
                }
            }
        });
        Register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUp.class));
            }
        });

        SignInEmail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Authenticator.getCurrentUser() == null) {
                    if (!checkValues()) {
                        showDialog("Missing Information", "Please Enter Email and password");
                    } else {
                        Authenticator.signInWithEmailAndPassword(Email_txt.getText().toString(), Password_txt.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //add Loading screen
                                    startActivity(new Intent(MainActivity.this, Profile.class));
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "Email or Password Incorrect or Not Registered", Toast.LENGTH_SHORT).show();
                                }
                            }

                        });


                    }
                }
                else{
                    showDialog("Sign in Not Available","You are Signed In as Guest Please Log out");
                }
            }
        });


        SignInGuest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Authenticator.signInAnonymously();
                startActivity(new Intent(MainActivity.this,Profile.class));
                finish();
                //To Second Activity not profile
            }
        });


    }


    private boolean checkValues(){
        if(Email_txt.getText().toString().isEmpty()||Password_txt.getText().toString().isEmpty())
        {
            return false;
        }
        return true;
    }

    private void showDialog(String Title,String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(Title);
        builder.setCancelable(false);
        builder.setTitle(Message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();

    }
}

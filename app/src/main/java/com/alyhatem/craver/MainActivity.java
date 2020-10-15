 package com.alyhatem.craver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
    Button SignInEmail_btn,SignUpEmail_btn,SignInGuest_btn;
    EditText Email_txt,Password_txt,ConfirmPassword_txt;
    FirebaseAuth Authenticator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Email_txt = findViewById(R.id.Email_txt);
        Password_txt = findViewById(R.id.Password_txt);
        Email_txt.setHint("Email To sign in or Sign up");
        Password_txt.setHint("Password");
        ConfirmPassword_txt = findViewById(R.id.ConfirmPassword_txt);
        ConfirmPassword_txt.setHint("Confirm Password for \n sign up only");
        SignInEmail_btn = findViewById(R.id.SignInEmail_btn);
        SignInGuest_btn = findViewById(R.id.SignInGuest_btn);
        SignUpEmail_btn = findViewById(R.id.SignUpEmail_btn);
        Authenticator = FirebaseAuth.getInstance();
        if(Authenticator.getCurrentUser()==null) {
            SignInEmail_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!checkValues()) {
                        showDialog("Missing Information", "Please Enter Email and password");
                    } else {
                        Authenticator.signInWithEmailAndPassword(Email_txt.getText().toString(), Password_txt.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //add Loading screen
                                    startActivity(new Intent(MainActivity.this,Profile.class));
                                } else {
                                    Toast.makeText(MainActivity.this, "Email or Password Incorrect", Toast.LENGTH_SHORT).show();
                                }
                            }

                        });


                    }

                }
            });

            SignUpEmail_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPasswordAndEmail() == 0) {
                        showDialog("Missing Information", "Missing Password or Password Confirmation");
                    } else if (checkPasswordAndEmail() == -1) {
                        showDialog("Missing Information", "No Information has been added");
                    } else if (checkPasswordAndEmail() == 1) {
                        showDialog("Incorrect Password", "Passwords Do Not Match");
                    } else if (checkPasswordAndEmail() == 2) {
                        showDialog("Missing Email", "Email is Missing");
                    } else if (checkPasswordAndEmail() == 3) {
                        showDialog("Password too short", "Password must be at least 6 characters");
                    } else {
                        Authenticator.createUserWithEmailAndPassword(Email_txt.getText().toString(), Password_txt.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isComplete()) {
                                    startActivity(new Intent(MainActivity.this,Profile.class));
                                    //Add Loading Screen

                                } else {
                                    Toast.makeText(MainActivity.this, task.getResult().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }


                }
            });

            SignInGuest_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Authenticator.signInAnonymously();
                    // startActivity(new Intent(MainActivity.this,));
                    //To Second Activity not profile
                }
            });


        }
        else{
            startActivity(new Intent());
        }
    }
    private boolean checkValues(){
        if(Email_txt.getText().toString().isEmpty()||Password_txt.getText().toString().isEmpty())
        {
            return false;
        }
        return true;
    }
    private int checkPasswordAndEmail(){
        if(Email_txt.getText().toString().isEmpty()&&Password_txt.getText().toString().isEmpty()){
            return -1;
        }
        else if(Password_txt.getText().toString().isEmpty()||ConfirmPassword_txt.getText().toString().isEmpty()){
            return 0;
        }
        else if (!(Password_txt.getText().toString().equals(ConfirmPassword_txt.getText().toString()))){
            return 1;
        }
        else if(Email_txt.getText().toString().isEmpty()){
            return 2;
        }
        else if(Password_txt.getText().toString().length()<6){
            return 3;
        }
        return 4;

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

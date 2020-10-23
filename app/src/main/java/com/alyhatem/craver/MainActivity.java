 package com.alyhatem.craver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
    Button SignInEmail_btn, SignInGuest_btn,Register_btn;
    EditText Email_txt, Password_txt;
    FirebaseAuth Authenticator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Email_txt = findViewById(R.id.Email_txt);
        Password_txt = findViewById(R.id.Password_txt);
        Register_btn = findViewById(R.id.Register_btn);
        SignInEmail_btn = findViewById(R.id.SignInEmail_btn);
        SignInGuest_btn = findViewById(R.id.SignInGuest_btn);
        Authenticator = FirebaseAuth.getInstance();
        if (Authenticator.getCurrentUser() == null) {
            Register_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, SignUp.class));
                }
            });

            SignInEmail_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Authenticator.getCurrentUser() == null) {
                        if (!checkValues()) {
                            showDialog("Missing Information", "Please Enter Email and password");
                        } else {
                           final LoaderDialog dialog = new LoaderDialog(MainActivity.this);
                            dialog.startDialog();
                            Authenticator.signInWithEmailAndPassword(Email_txt.getText().toString(), Password_txt.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        dialog.dismissDialog();
                                        startActivity(new Intent(MainActivity.this, MainApp.class));
                                        finish();
                                    } else {
                                        dialog.dismissDialog();
                                        Toast.makeText(MainActivity.this, "Email or Password Incorrect or Not Registered", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });


                        }
                    }
                }
            });


            SignInGuest_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Authenticator.getCurrentUser() == null) {
                        Authenticator.signInAnonymously();
                        startActivity(new Intent(MainActivity.this, MainApp.class));
                        finish();
                    }
                }
            });

        }
        else{
            startActivity(new Intent(MainActivity.this,MainApp.class));
            finish();
        }
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

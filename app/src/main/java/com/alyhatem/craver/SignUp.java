package com.alyhatem.craver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    EditText Email,Password,ConfirmPassword;
    Button SignUpEmail_btn;
    FirebaseAuth Authenticator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Authenticator=FirebaseAuth.getInstance();
        Email=findViewById(R.id.EmailSignUp_txt);
        Password=findViewById(R.id.PasswordSignup_txt);
        ConfirmPassword=findViewById(R.id.ConfirmPasswordSignup_txt);
        SignUpEmail_btn=findViewById(R.id.signup_btn);
        Email.setHint("Email:");
        Password.setHint("Password:");
        ConfirmPassword.setHint("Confirm Password");


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
                        Authenticator.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(SignUp.this, Profile.class));
                                    finish();
                                }else{
                                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }


                        });
                    }


            }
        });



    }
    private void showDialog(String Title,String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(SignUp.this);
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
        private int checkPasswordAndEmail(){
        if(Email.getText().toString().isEmpty()&&Password.getText().toString().isEmpty()){
            return -1;
        }
        else if(Password.getText().toString().isEmpty()||ConfirmPassword.getText().toString().isEmpty()){
            return 0;
        }
        else if (!(Password.getText().toString().equals(ConfirmPassword.getText().toString()))){
            return 1;
        }
        else if(Email.getText().toString().isEmpty()){
            return 2;
        }
        else if(Password.getText().toString().length()<6){
            return 3;
        }
        return 4;

    }
}
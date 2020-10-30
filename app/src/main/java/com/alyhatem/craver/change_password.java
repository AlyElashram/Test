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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class change_password extends AppCompatActivity {
    Button change_password_btn;
    FirebaseAuth Authenticator;
    EditText old_passowrd, new_password;
     boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        change_password_btn = findViewById(R.id.change_password_btn);
        old_passowrd = findViewById(R.id.old_password);
        new_password = findViewById(R.id.new_password);
        Authenticator = FirebaseAuth.getInstance();
        FirebaseUser user = Authenticator.getCurrentUser();
        change_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(old_passowrd.getText().toString().isEmpty()||new_password.getText().toString().isEmpty()){
                    showDialog("Missing Information","Please Enter Old Password and new Password");
                }
                else if(new_password.getText().toString().length()<6){
                    showDialog("Short Password","Password has to be at least 6 characters");
                }
                else if(new_password.getText().equals(old_passowrd.getText())) {
                    showDialog("No change","Old password and new password can not be the same");
                }
                else{
                    Authenticator.getCurrentUser().updatePassword(new_password.getText().toString());
                    startActivity(new Intent(change_password.this,MainApp.class));
                }
            }
        });


    }


    private void showDialog(String Title,String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(change_password.this);
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
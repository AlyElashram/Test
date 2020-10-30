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
    EditText  new_password;
    private int check=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        change_password_btn = findViewById(R.id.change_password_btn);

        new_password = findViewById(R.id.new_password);
        Authenticator = FirebaseAuth.getInstance();
        FirebaseUser user = Authenticator.getCurrentUser();
        change_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new_password.getText().toString().isEmpty()){
                    showDialog("Missing Information","Please Enter a new Password");
                }
                else if(new_password.getText().toString().length()<6){
                    showDialog("Short Password","Password has to be at least 6 characters");
                }
                else{
                    Authenticator.getCurrentUser().updatePassword(new_password.getText().toString());
                    startActivity(new Intent(change_password.this,MainApp.class));
                    finish();
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
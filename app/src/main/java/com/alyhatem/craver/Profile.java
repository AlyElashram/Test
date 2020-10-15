package com.alyhatem.craver;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Profile extends AppCompatActivity {
    EditText Name_txt,Age_txt,Freq_txt,Fav_txt;
    Button RegisterProfile_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Name_txt=findViewById(R.id.Name_txt);
        Age_txt=findViewById(R.id.Age_txt);
        Freq_txt=findViewById(R.id.Frequency_txt);
        Fav_txt=findViewById(R.id.Fav_txt);
        RegisterProfile_btn=findViewById(R.id.SetProfile_btn);
        RegisterProfile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkValues()){
                    showDialog("Incomplete","Some Information Is Missing");
                }
            }
        });



    }
    private boolean checkValues(){
        if(Name_txt.getText().toString().isEmpty()||
           Age_txt.getText().toString().isEmpty()||
           Freq_txt.getText().toString().isEmpty()||
           Fav_txt.getText().toString().isEmpty())
        {
            return false;
        }
        return true;
    }
    private void showDialog(String Title,String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(Profile.this);
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
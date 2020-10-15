package com.alyhatem.craver;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity {
    EditText Name_txt,Age_txt,Freq_txt,Fav_txt;
    Button RegisterProfile_btn;
    FirebaseAuth Authenticator;
    DatabaseReference users_Ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Authenticator=FirebaseAuth.getInstance();
        users_Ref= FirebaseDatabase.getInstance().getReference("Users");
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
                else{

                    Client A=new Client(Name_txt.getText().toString(),
                            Integer.parseInt(Age_txt.getText().toString()),
                            Fav_txt.getText().toString(),
                            Integer.parseInt(Freq_txt.getText().toString()),
                            Authenticator.getCurrentUser().getUid());
                    addClient(A);
                    //To Craving


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
    private void addClient(Client A){
        String Uid=Authenticator.getUid();
        users_Ref.child(Uid).setValue(A);
    }
}
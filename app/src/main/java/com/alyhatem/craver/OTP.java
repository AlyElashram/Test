package com.alyhatem.craver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {
    FirebaseAuth Authenticator;
    EditText d1,d2,d3,d4,d5,d6;
    DatabaseReference Users;
    String Phone;
    EditText Email_txt,Password_txt;
    Button Verify_btn,send_btn;
    PhoneAuthProvider mprovider;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String Verid;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);
        Authenticator=FirebaseAuth.getInstance();
        Phone=getIntent().getStringExtra("Phone");
        Email_txt=MainActivity.Email_txt;
        Password_txt=MainActivity.Password_txt;
        Verify_btn=findViewById(R.id.Verify_btn);
        send_btn=findViewById(R.id.send_btn);
        d1=findViewById(R.id.d1);
        d2=findViewById(R.id.d2);
        d3=findViewById(R.id.d3);
        d4=findViewById(R.id.d4);
        d5=findViewById(R.id.d5);
        d6=findViewById(R.id.d6);
        d1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    d2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        d2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    d3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        d3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    d4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        d4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    d5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        d5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    d6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d("TAG", "onVerificationCompleted:" + credential);
                startActivity(new Intent(OTP.this,MainApp.class));

    }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("TAG", "onVerificationFailed"+ e.getLocalizedMessage());


                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(OTP.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                    Toast.makeText(OTP.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("TAG", "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                Verid = verificationId;

                // ...
            }
        };



      /*  Users = FirebaseDatabase.getInstance().getReference().child("Users").child(Authenticator.getUid());
        Users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Phone=snapshot.child("phoneNumber").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/



        send_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Log.d("phonenum", "Value is " + Phone);
                                            PhoneAuthOptions options =
                                            PhoneAuthOptions.newBuilder(Authenticator)
                                            .setPhoneNumber("+20"+Phone)       // Phone number to verify
                                             .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                                            .setActivity(OTP.this)                 // Activity (for callback binding)
                                                            .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                                            .build();
                                            PhoneAuthProvider.verifyPhoneNumber(options);
                                        }
                                    });
            Verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkValues()){
                    Toast.makeText(OTP.this,"Please Enter 6 digit code",Toast.LENGTH_LONG).show();
                    return;
                }
               String code=d1.getText().toString()+d2.getText().toString()+
                       d3.getText().toString()+
                       d4.getText().toString()+
                       d5.getText().toString()+
                       d6.getText().toString();
                if(Verid!=null){
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(Verid,code);
                    Authenticator.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Authenticator.signOut();
                                Authenticator.signInWithEmailAndPassword(Email_txt.getText().toString(),Password_txt.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                       // startActivity(new Intent(OTP.this,MainApp.class));
                                    }
                                });
                            }
                            else{
                                Toast.makeText(OTP.this, task.getResult().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });



    }

    private boolean checkValues(){
        if(d1.getText().toString().trim().isEmpty()||
                d2.getText().toString().trim().isEmpty()||
                d3.getText().toString().trim().isEmpty()||
                d4.getText().toString().trim().isEmpty()||
                d5.getText().toString().trim().isEmpty()||
                d6.getText().toString().trim().isEmpty()
        ){
            return false;
        }
        return true;

    }
}
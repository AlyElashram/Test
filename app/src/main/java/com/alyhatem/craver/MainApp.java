package com.alyhatem.craver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.net.URI;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainApp extends AppCompatActivity {
    private TextView nav_username,Email;
    private DrawerLayout drawer;
    private FirebaseAuth Authenticator;
    private DatabaseReference user;
    private NavigationView nav_view;
    private ArrayList<String> profile;
    private ImageView user_Image;
    private Uri imageUri;
    private final static int PICK_IMAGE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Authenticator=FirebaseAuth.getInstance();
        user=FirebaseDatabase.getInstance().getReference().child("Users").child(Authenticator.getUid());
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(MainApp.this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        profile=new ArrayList<>();
        nav_view=findViewById(R.id.nav_view);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        nav_username= (TextView) headerView.findViewById(R.id.nav_username_txt);
        Email=headerView.findViewById(R.id.nav_email_txt);
       user_Image=headerView.findViewById(R.id.nav_view_image);


       user_Image.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               openGallery();
           }
       });

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.getValue()==null)){
                    profile.add(snapshot.child("name").getValue().toString());
                    profile.add(snapshot.child("age").getValue().toString());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                final LoaderDialog dialog=new LoaderDialog(MainApp.this);
                if(item.getTitle().equals("LogOut")){
                    dialog.startDialog();
                    Authenticator.signOut();
                    dialog.dismissDialog();
                    startActivity(new Intent(MainApp.this,MainActivity.class));
                    finish();
                    return false;
                }
              if(item.getTitle().equals("Profile")){
                  if(profile.isEmpty()){
                      Toast.makeText(MainApp.this,"Signed in as Guest.No Profile Available",Toast.LENGTH_LONG).show();
                  }
                  else{
                      //Edit profile
                  }


                }

                return false;
            }
        });

    }

    private void openGallery(){
        Intent openGallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(openGallery,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==PICK_IMAGE){
            imageUri=data.getData();
            user_Image.setImageURI(imageUri);
        }
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
        drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
}
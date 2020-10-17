package com.alyhatem.craver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainApp extends AppCompatActivity {
    private TextView nav_username;
    private DrawerLayout drawer;
    private FirebaseAuth Authenticator;
    private DatabaseReference users_ref;
    FirebaseDatabase database;
    private NavigationView nav_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        database=FirebaseDatabase.getInstance();
        Authenticator=FirebaseAuth.getInstance();
        users_ref= FirebaseDatabase.getInstance().getReference("Users");
        nav_view=findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                final LoaderDialog dialog=new LoaderDialog(MainApp.this);
                if(item.getTitle().equals("LogOut")){
                    dialog.startDialog();
                    Authenticator.signOut();
                    dialog.dismissDialog();
                    startActivity(new Intent(MainApp.this,MainActivity.class));
                    return false;
                }
            /*    if(item.getTitle().equals("Profile")){
                    String uid=Authenticator.getCurrentUser().getUid();
                    if() {
                        Toast.makeText(MainApp.this,"User has no profile",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(MainApp.this,"IDK",Toast.LENGTH_LONG).show();
                    }

                }*/

                return false;
            }
        });
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(MainApp.this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
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
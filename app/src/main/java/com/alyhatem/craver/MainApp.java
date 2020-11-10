package com.alyhatem.craver;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

@SuppressWarnings("ConstantConditions")
public class MainApp extends AppCompatActivity implements OnMapReadyCallback {
    private TextView nav_username, phone;
    private Button elevation_btn;
    private DrawerLayout drawer;
    private FirebaseAuth Authenticator;
    private DatabaseReference user;
    private NavigationView nav_view;
    private ArrayList<String> profile;
    private ImageView user_Image;
    private Uri imageUri;
    private final static int PICK_IMAGE = 100;
    private int click;
    private Toolbar toolbar;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleMap map;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter card_Adapter;
    private RecyclerView.LayoutManager recycler_LayoutManager;
    private int Location_Permission_Code = 1;
    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Authenticator = FirebaseAuth.getInstance();
        user = FirebaseDatabase.getInstance().getReference().child("Users").child(Authenticator.getUid());
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainApp.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        profile = new ArrayList<>();
        nav_view = findViewById(R.id.nav_view);
        View headerView = nav_view.getHeaderView(0);
        nav_username = headerView.findViewById(R.id.nav_username_txt);
        phone = headerView.findViewById(R.id.nav_phone_txt);
        user_Image = headerView.findViewById(R.id.nav_view_image);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ArrayList<CardItem> items = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            CardItem a = new CardItem(R.drawable.ic_email_icon, "Mcdonalds", "KFC");
            items.add(a);
        }

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recycler_LayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        card_Adapter = new RecyclerAdapter(items);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.HORIZONTAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(recycler_LayoutManager);
        recyclerView.setAdapter(card_Adapter);


        user_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        user.addValueEventListener(new ValueEventListener() {
            @Override
            @SuppressWarnings("Constant Conditions")
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.getValue() == null)) {
                    profile.add(snapshot.child("name").getValue().toString());
                    profile.add(snapshot.child("age").getValue().toString());
                    profile.add(snapshot.child("phoneNumber").getValue().toString());
                    profile.add(snapshot.child("favourite_Restaurant").getValue().toString());
                    nav_username.setText(snapshot.child("name").getValue().toString());
                    phone.setText(snapshot.child("phoneNumber").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                final LoaderDialog dialog = new LoaderDialog(MainApp.this);
                if (item.getTitle().equals("LogOut")) {
                    dialog.startDialog();
                    Authenticator.signOut();
                    dialog.dismissDialog();
                    startActivity(new Intent(MainApp.this, MainActivity.class));
                    finish();
                    return false;
                }
                if (item.getTitle().equals("Profile")) {
                    if (profile.isEmpty()) {
                        Toast.makeText(MainApp.this, "Signed in as Guest.No Profile Available", Toast.LENGTH_LONG).show();
                    } else {
                        startActivity(new Intent(MainApp.this, Profile.class));
                    }


                }
                if (item.getTitle().equals("Change Password")) {
                    if (!(profile.isEmpty())) {
                        startActivity(new Intent(MainApp.this, change_password.class));
                    }

                }


                return false;
            }
        });

    }

    private void openGallery() {
        Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(openGallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            user_Image.setImageURI(imageUri);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocation();

            return;
        } else {
            map.setMyLocationEnabled(true);
            getLocation();


        }


    }


    private void requestLocation() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)&&
                ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)) {

            showDialog("Permission Required", "For the App to work properly the Location is required");

        }

        else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, Location_Permission_Code);
        }

    }

    private void showDialog(String Title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainApp.this);
        builder.setMessage(Title);
        builder.setCancelable(false);
        builder.setTitle(Message);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(MainApp.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, Location_Permission_Code);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Location_Permission_Code) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainApp.this, "Permission Granted", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(MainApp.this, "Permission Denied", Toast.LENGTH_LONG).show();
        }
    }

    private void moveCamera(LatLng latLng,float Zoom){
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,Zoom));
    }

    private void getLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainApp.this);
       try {
               if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                       ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
               }
               Task Location = mFusedLocationProviderClient.getLastLocation();
               Location.addOnCompleteListener(new OnCompleteListener() {
                   @Override
                   public void onComplete(@NonNull Task task) {
                       if (task.isSuccessful()) {
                           Location CurrentLocation =(Location)task.getResult();
                           LatLng mylocation=new LatLng(CurrentLocation.getLatitude(),CurrentLocation.getLongitude());
                           moveCamera(mylocation,15f);

                       }
                       else{
                           Toast.makeText(MainApp.this, "Not able to access current location", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
       }catch(SecurityException e){
           Toast.makeText(MainApp.this,"Security exception"+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
       }

    }
}
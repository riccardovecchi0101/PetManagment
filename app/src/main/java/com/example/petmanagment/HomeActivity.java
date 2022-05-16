package com.example.petmanagment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.petmanagment.databinding.ActivityHomeBinding;
import com.example.petmanagment.login.MainActivity;
import com.example.petmanagment.login.PhotoCreator;
import com.example.petmanagment.ui.Customers.CustomersFragment;
import com.example.petmanagment.ui.Customers.CustomersViewModel;
import com.example.petmanagment.ui.Settings.SettingsFragment;
import com.example.petmanagment.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    /* drawerLayout e navigationView servono per rendere il pulsante di logout un semplice pulsante*/
    private NavigationView navigationView;
    private DrawerLayout drawer;
    Bitmap puppet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarHome.toolbar);
        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_customers, R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        /*codice per fare logout schiacciando il pulsante dal menu a tendina*/
        findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawer.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {
                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(HomeActivity.this, MainActivity.class));
                        Toast.makeText(HomeActivity.this, "logout successul", Toast.LENGTH_LONG).show();
                        return true;

                    case R.id.nav_customers:
                        replaceFragment(new CustomersFragment());
                        return true;

                    case R.id.nav_home:
                        replaceFragment(new HomeFragment());
                        return true;

                    case R.id.nav_settings:
                        replaceFragment(new SettingsFragment());
                        return true;

                }
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);  //questa linea di codice mi fa apparire i tre puntini in alto a destra
        TextView username = (TextView) findViewById(R.id.mailView);
        ImageView profileImage = (ImageView) findViewById(R.id.ProfileIcon);
        //profileImage.setVisibility(View.INVISIBLE);
        profileImage.setOnClickListener(view -> {
            PhotoCreator creator = new PhotoCreator(getApplicationContext());
            startActivityForResult(creator.dispatchTakePictureIntent(), PhotoCreator.REQUEST_IMAGE_CAPTURE);
            profileImage.setImageBitmap(puppet);

        });
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            username.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        else
            username.setText("none");
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotoCreator.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            puppet = (Bitmap) extras.get("data");
        }
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.app_bar_home,fragment);
        fragmentTransaction.commit();
    }

}
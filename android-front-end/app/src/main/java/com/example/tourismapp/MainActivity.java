package com.example.tourismapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tourismapp.Fragments.AnalyticsFragment;
import com.example.tourismapp.Fragments.LoginFragment;
import com.example.tourismapp.Fragments.RegistrationFragment;
import com.example.tourismapp.Fragments.SearchFragment;
import com.example.tourismapp.Fragments.TicketHistoryFragment;
import com.example.tourismapp.Helpers.GlobalStorage;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_draw_open, R.string.navigation_draw_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_search);
        }

        String userEmail = ((GlobalStorage) this.getApplication()).getUserEmail();
        if(userEmail == null) {
            // user not logged in
            navigationView = findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_history).setVisible(false);
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            nav_Menu.findItem(R.id.nav_registration).setVisible(true);
        }
        else {
            // user logged in
            // fetch user id
            userId = ((GlobalStorage) this.getApplication()).getUserId();
            //int userId = ((GlobalStorage) this.getApplication()).getUserId();
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();

            nav_Menu.findItem(R.id.nav_history).setVisible(true);
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
            nav_Menu.findItem(R.id.nav_registration).setVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
                break;
            case R.id.nav_login:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
                break;
            case R.id.nav_registration:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegistrationFragment()).commit();
                break;
            case R.id.nav_analytics:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AnalyticsFragment()).commit();
                break;
            case R.id.nav_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TicketHistoryFragment()).commit();
                break;
            case R.id.nav_logout:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
                // change visibilities
                NavigationView navigationView = findViewById(R.id.nav_view);
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_history).setVisible(false);
                nav_Menu.findItem(R.id.nav_logout).setVisible(false);
                nav_Menu.findItem(R.id.nav_login).setVisible(true);
                nav_Menu.findItem(R.id.nav_registration).setVisible(true);
                // set auth status with user id and email to null
                ((GlobalStorage) this.getApplication()).setUserEmail(null);
                ((GlobalStorage) this.getApplication()).setUserId(0);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

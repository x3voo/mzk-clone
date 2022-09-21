package pl.rikwo.mzkclone;


import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import pl.rikwo.mzkclone.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private DrawerLayout drawer;

    DatabaseHelper mDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //DB
        mDatabaseHelper = new DatabaseHelper(this);
        //AddData("COL0-asd","COL1-qwe");

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);



        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_my_tickets, R.id.nav_ticket_control, R.id.nav_buy_ticket, R.id.nav_profile, R.id.nav_change_pin)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        /*
        https://stackoverflow.com/questions/71565073/why-does-navigation-not-work-in-the-navigation-drawer-activity-template-with-ver

        try {
            String frag = null;

            if(getIntent().getStringExtra("frag") == null || getIntent().getStringExtra("frag") == ""){
                frag = null;
            }else {
                frag = getIntent().getStringExtra("frag");

            }

            if(frag != null){
                switch(frag){
                    case "nav_my_tickets":
                        navController.navigate(R.id.nav_my_tickets);
                        break;
                    case "nav_ticket_control":
                        navController.navigate(R.id.nav_my_tickets);
                        navController.navigate(R.id.nav_ticket_control);
                        break;
                    case "nav_buy_ticket":
                        navController.navigate(R.id.nav_my_tickets);
                        navController.navigate(R.id.nav_buy_ticket);
                        break;
                    case "nav_profile":
                        navigationView.setCheckedItem(R.id.nav_buy_ticket);
                        navController.navigate(R.id.nav_profile);
                        break;
                    case "nav_change_pin":
                        navController.navigate(R.id.nav_my_tickets);
                        navController.navigate(R.id.nav_change_pin);
                        break;
                    default:
                        navController.navigate(R.id.nav_my_tickets);
                        break;
                }
                getIntent().removeExtra("frag");
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        */

        /*
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
                int id = item.getItemId();
                if(!handled) {
                    switch (item.getItemId()){
                        case R.id.nav_buy_ticket:
                            Intent intent = new Intent(HomeActivity.this, BuyTicketActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.nav_change_pin:
                            toastMessage("777");
                            break;
                    }
                }
                drawer.closeDrawer(GravityCompat.START);
                return handled;
            }
        });

         */
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
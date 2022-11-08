package com.example.deviceconnector.conditioner;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.deviceconnector.R;
import com.example.deviceconnector.conditioner.adapters.MainTabPagerAdapter;
import com.example.deviceconnector.conditioner.dialogs.SettingsDialog;
import com.example.deviceconnector.conditioner.tabs.main.DataFragment;
import com.example.deviceconnector.conditioner.tabs.main.ScheduleFragment;
import com.example.deviceconnector.conditioner.tabs.main.SliderFragment;
import com.example.deviceconnector.conditioner.util.LayoutUtil;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class ConditionarActivity extends AppCompatActivity {
    public static TabLayout tabLayout;
    ViewPager2 viewPager;
    public static Toolbar toolbar;
    ImageButton settingsButton;
    private int[] tabIcons = {
            R.drawable.ic_baseline_timer_24,
            R.drawable.ic_baseline_ac_unit_24,
            R.drawable.ic_baseline_show_chart_24
    };
    Context context;

    ScheduleFragment scheduleTab;
    SliderFragment dialTab;
    DataFragment dataTab;

    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        context = this;

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);
        viewPager.setAdapter(LayoutUtil.createCardAdapter(this));
        viewPager.setCurrentItem(1);

        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch(tab.getPosition()){
                            case 0: tab.setText("Schedule");
                            case 1: tab.setText("Set");
                            case 2: tab.setText("Data");
                        }
                    }
                }).attach();

        LayoutUtil.setupTabIcons(tabLayout, tabIcons);

        scheduleTab = (ScheduleFragment) ((MainTabPagerAdapter)viewPager.getAdapter()).getFragment(0);
        dialTab = (SliderFragment) ((MainTabPagerAdapter)viewPager.getAdapter()).getFragment(1);
        dataTab = (DataFragment) ((MainTabPagerAdapter)viewPager.getAdapter()).getFragment(2);

        System.out.println(dialTab);
        System.out.println("fragment: " + viewPager.getAdapter().getItemId(0));


        // set up the top toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.home_icon);
        actionBar.setTitle("Air Conditioned");

        settingsButton = findViewById(R.id.settings_button);
        View.OnClickListener settingsListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open the settings dialog
                Dialog dialog = SettingsDialog.onCreateDialog(context);
                dialog.show();
            }
        };
        settingsButton.setOnClickListener(settingsListener);

        navigationView = findViewById(R.id.nav_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_schedule:
                        item.setChecked(true);
                        viewPager.setCurrentItem(0);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_dial:
                        item.setChecked(true);
                        viewPager.setCurrentItem(1);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_data:
                        item.setChecked(true);
                        viewPager.setCurrentItem(2);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_settings:
                        item.setChecked(true);
                        Dialog dialog = SettingsDialog.onCreateDialog(context);
                        dialog.show();
                        drawerLayout.closeDrawers();
                        return true;
                }
                return false;
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

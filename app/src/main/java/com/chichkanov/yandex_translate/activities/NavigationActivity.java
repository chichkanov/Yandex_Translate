package com.chichkanov.yandex_translate.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.chichkanov.yandex_translate.R;
import com.chichkanov.yandex_translate.fragments.SettingsFragment;
import com.chichkanov.yandex_translate.fragments.TranslateFragment;
import com.chichkanov.yandex_translate.fragments.AboutAppFragment;
import com.chichkanov.yandex_translate.fragments.FavFragment;
import com.chichkanov.yandex_translate.fragments.HistoryFragment;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final static int MENU_TRANSLATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                hideKeyboard();
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            navigationView.getMenu().getItem(MENU_TRANSLATE).setChecked(true);
            onNavigationItemSelected(navigationView.getMenu().getItem(MENU_TRANSLATE));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_translate) {
            TranslateFragment translateFragment = TranslateFragment.newInstance("Переводчик");
            addFragment(translateFragment);
        } else if (id == R.id.nav_history) {
            HistoryFragment historyFragment = HistoryFragment.newInstance("История");
            addFragment(historyFragment);
        } else if (id == R.id.nav_fav) {
            FavFragment favFragment = FavFragment.newInstance("Избранное");
            addFragment(favFragment);
        } else if (id == R.id.nav_settings) {
            SettingsFragment settingsFragment = SettingsFragment.newInstance("Настройки");
            addFragment(settingsFragment);
        } else if (id == R.id.nav_about) {
            AboutAppFragment aboutAppFragment = AboutAppFragment.newInstance("О приложении");
            addFragment(aboutAppFragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void addFragment(Fragment fragment) {
        hideKeyboard();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft = ft.replace(R.id.content_navigation, fragment);
        ft.commit();
    }

    private void hideKeyboard(){
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_navigation);
        InputMethodManager imm = (InputMethodManager)this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(frameLayout.getWindowToken(), 0);
    }
}

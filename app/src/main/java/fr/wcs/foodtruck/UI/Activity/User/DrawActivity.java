package fr.wcs.foodtruck.UI.Activity.User;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import fr.wcs.foodtruck.R;

public class DrawActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
     NavigationView mNav;
    private Fragment mFragment;
    private ArrayList<Fragment> mNewFragment = new ArrayList<>();

    public Fragment getCurrentFragment() {
        return this.getSupportFragmentManager().findFragmentById(R.id.container);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawActivity.this.setTitle("Acceuil");
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_main);
        navigationView.setNavigationItemSelectedListener(this);

        this.getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment current = getCurrentFragment();
                if (current instanceof MainFragment) {
                    navigationView.setCheckedItem(R.id.nav_main);
                }else if (current instanceof MenuFragment){
                    navigationView.setCheckedItem(R.id.nav_Menu);
                } else if (current instanceof PresentationFragment){
                    navigationView.setCheckedItem(R.id.nav_histoire);
                }else if (current instanceof EventFragment){
                    navigationView.setCheckedItem(R.id.nav_Event);
                }else if (current instanceof MapListFragment){
                    navigationView.setCheckedItem(R.id.nav_map);
                }else if (current instanceof ContactFragment){
                    navigationView.setCheckedItem(R.id.nav_contact);
                }
            }
        });

        mNewFragment.add(Fragment.instantiate(this, MainFragment.class.getName()));
        mNewFragment.add(Fragment.instantiate(this,MenuFragment.class.getName()));
        mNewFragment.add(Fragment.instantiate(this, PresentationFragment.class.getName()));
        mNewFragment.add(Fragment.instantiate(this,EventFragment.class.getName()));
        mNewFragment.add(Fragment.instantiate(this, MapListFragment.class.getName()));
        mNewFragment.add(Fragment.instantiate(this,ContactFragment.class.getName()));

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragment = mNewFragment.get(0);
        fragmentTransaction.replace(R.id.container, mFragment);
        fragmentTransaction.commit();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch(id) {
            case R.id.nav_main:
                mFragment = mNewFragment.get(0);
                break;
            case R.id.nav_histoire:
                mFragment = mNewFragment.get(2);
                break;
            case R.id.nav_Menu:
                mFragment = mNewFragment.get(1);
                break;
            case R.id.nav_Event:
                mFragment = mNewFragment.get(3);
                break;
            case R.id.nav_map:
                mFragment = mNewFragment.get(4);
                break;
            case R.id.nav_contact:
                mFragment = mNewFragment.get(5);
                break;
            default:
                mFragment = mNewFragment.get(0);
        }
        fragmentTransaction.replace(R.id.container, mFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

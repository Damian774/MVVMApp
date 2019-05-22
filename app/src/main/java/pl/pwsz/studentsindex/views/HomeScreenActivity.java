package pl.pwsz.studentsindex.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileOutputStream;

import pl.pwsz.studentsindex.R;

public class HomeScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences preferences;
    int studyId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("myPreferences", Activity.MODE_PRIVATE);
        studyId = preferences.getInt("activeStudy", 0);




        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_grade) {
            startActivity(new Intent(HomeScreenActivity.this, AddGradeActivity.class));
        } else if (id == R.id.nav_add_category) {
            startActivity(new Intent(HomeScreenActivity.this, AddCategoryActivity.class));
        } else if (id == R.id.nav_add_study) {
            startActivity(new Intent(HomeScreenActivity.this, AddStudyActivity.class));

        } else if (id == R.id.nav_show_studies) {
            startActivity(new Intent(HomeScreenActivity.this, SelectStudiesActivity.class));
        } else if (id == R.id.nav_add_note) {
            startActivity(new Intent(HomeScreenActivity.this, AddNoteActivity.class));
        } else if (id == R.id.nav_show_notes) {
            startActivity(new Intent(HomeScreenActivity.this, ShowNotesActivity.class));
        } else if (id == R.id.nav_add_exam) {
            startActivity(new Intent(HomeScreenActivity.this, AddExamActivity.class));
        } else if (id == R.id.nav_show_exams){
            startActivity(new Intent(HomeScreenActivity.this, ShowExamsActivity.class));
        } else if (id == R.id.nav_show_categories){
            startActivity(new Intent(HomeScreenActivity.this, ShowCategoriesActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

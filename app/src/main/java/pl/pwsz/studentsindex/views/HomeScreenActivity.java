package pl.pwsz.studentsindex.views;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

import pl.pwsz.studentsindex.R;
import pl.pwsz.studentsindex.model.Grade;
import pl.pwsz.studentsindex.model.Study;
import pl.pwsz.studentsindex.viewmodels.AddExamActivityViewModel;
import pl.pwsz.studentsindex.viewmodels.HomeScreenActivityViewModel;
import pl.pwsz.studentsindex.viewmodels.SelectStudiesActivityViewModel;

public class HomeScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences preferences;
    int studyId;
    TextView averageGradeLabelTV;
    TextView averageGradeTV;
    TextView selectedStudyTV;
    TextView categoriesBTN;
    TextView studiesBTN;
    TextView notesBTN;
    TextView examsBTN;
    TextView selectedStudyYearTV;
    HomeScreenActivityViewModel homeScreenActivityViewModel;
    Study selectedStudy;


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
        averageGradeLabelTV = findViewById(R.id.average_grade_label);
        averageGradeTV = findViewById(R.id.average_grade_tv);
        selectedStudyTV = findViewById(R.id.selected_study_tv);
        categoriesBTN = findViewById(R.id.categories_btn);
        studiesBTN = findViewById(R.id.studies_btn);
        notesBTN = findViewById(R.id.notes_btn);
        examsBTN = findViewById(R.id.exams_btn);
        selectedStudyYearTV = findViewById(R.id.selected_study_year_tv);
        homeScreenActivityViewModel = ViewModelProviders.of(this).get(HomeScreenActivityViewModel.class);
        homeScreenActivityViewModel.getAllStudies().observe(this, new Observer<List<Study>>() {
            @Override
            public void onChanged(@Nullable List<Study> studies) {
                for(Study loopStudy : studies){
                    studyId = preferences.getInt("activeStudy", 0);
                    if(loopStudy.getId() == studyId){
                        selectedStudy = loopStudy;
                        break;
                    }
                }
            }
        });
        homeScreenActivityViewModel.getAllGrades().observe(this, new Observer<List<Grade>>() {
            @Override
            public void onChanged(@Nullable List<Grade> grades) {
                if(grades.size()!=0){
                    BigDecimal weightedAverage = BigDecimal.ZERO;
                    weightedAverage.setScale(2,RoundingMode.HALF_UP);
                    BigDecimal dividend = BigDecimal.ZERO;
                    BigDecimal divider = BigDecimal.ZERO;
                    for(Grade loopGrade : grades){
                        BigDecimal value = loopGrade.getValue();
                        BigDecimal weight = loopGrade.getWeight();
                        dividend = dividend.add(value.multiply(weight));
                        divider = divider.add(weight);
                    }
                    if(divider!=null && !divider.equals(new BigDecimal(0))){
                        weightedAverage = dividend.divide(divider,10,BigDecimal.ROUND_HALF_UP).setScale(2,BigDecimal.ROUND_HALF_UP);
                        averageGradeTV.setText(weightedAverage.toPlainString());
                    }

                }

            }
        });
        selectedStudy = homeScreenActivityViewModel.getStudyById(studyId);
        if(selectedStudy == null){
            selectedStudyTV.setText("Create and select active study first");
        }else{
            selectedStudyTV.setText(selectedStudy.getSchoolName());
            selectedStudyYearTV.setText("Year "+selectedStudy.getYear());
        }


        categoriesBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreenActivity.this, ShowCategoriesActivity.class));
            }
        });
        notesBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreenActivity.this, ShowNotesActivity.class));
            }
        });
        studiesBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreenActivity.this, SelectStudiesActivity.class));
            }
        });
        examsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreenActivity.this, ShowExamsActivity.class));
            }
        });

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

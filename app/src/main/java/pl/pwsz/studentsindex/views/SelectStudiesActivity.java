package pl.pwsz.studentsindex.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Collections;
import java.util.List;

import pl.pwsz.studentsindex.R;
import pl.pwsz.studentsindex.model.Study;
import pl.pwsz.studentsindex.adapters.StudyAdapter;
import pl.pwsz.studentsindex.viewmodels.SelectStudiesActivityViewModel;

public class SelectStudiesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    StudyAdapter adapter;
    FloatingActionButton fab;
    SelectStudiesActivityViewModel selectStudiesActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new StudyAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));




        selectStudiesActivityViewModel = ViewModelProviders.of(this).get(SelectStudiesActivityViewModel.class);
        selectStudiesActivityViewModel.getAllStudies().observe(this, new Observer<List<Study>>() {
            @Override
            public void onChanged(@Nullable List<Study> studies) {
                Collections.reverse(studies);
                adapter.setStudies(studies);
            }
        });


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectStudiesActivity.this, AddStudyActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
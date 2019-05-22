package pl.pwsz.studentsindex.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import pl.pwsz.studentsindex.R;
import pl.pwsz.studentsindex.model.Study;
import pl.pwsz.studentsindex.adapters.StudyAdapter;
import pl.pwsz.studentsindex.viewmodels.SelectStudiesActivityViewModel;

public class SelectStudiesActivity extends AppCompatActivity implements View.OnCreateContextMenuListener {

    RecyclerView recyclerView;
    StudyAdapter adapter;
    FloatingActionButton fab;
    SelectStudiesActivityViewModel selectStudiesActivityViewModel;
    private int mCurrentItemPosition;
    private static final String PREFERENCES_NAME = "myPreferences";
    private static final String PREFERENCES_STUDY_ID = "activeStudy";
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);

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
        registerForContextMenu(recyclerView);
        adapter.setOnLongItemClickListener(new StudyAdapter.onLongItemClickListener() {
            @Override
            public void ItemLongClicked(View v, int position) {
                mCurrentItemPosition = position;
                v.showContextMenu();
            }
        });


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


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select_studies_activity, menu);
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final Study selectedStudy = adapter.getStudyAtPosition(mCurrentItemPosition);
        int id = item.getItemId();
        if (id == R.id.edit) {
            Intent intent = new Intent(SelectStudiesActivity.this, AddStudyActivity.class);
            intent.putExtra("Study",selectedStudy);
            startActivity(intent);

        }
        if(id == R.id.setAsDefault){
            SharedPreferences.Editor preferencesEditor = preferences.edit();
            int studyId = selectedStudy.getId();
            preferencesEditor.putInt(PREFERENCES_STUDY_ID, studyId);
            preferencesEditor.commit();
            Toast.makeText(this, "Study set as active", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.delete) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("You are about to delete this entry. You will lose all data connected with this Study. This action is permanent!\nAre you sure?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            selectStudiesActivityViewModel.deleteStudy(selectedStudy);
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

        return true;
    }

}
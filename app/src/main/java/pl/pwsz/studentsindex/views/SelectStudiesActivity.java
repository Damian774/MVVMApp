package pl.pwsz.studentsindex.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.Collections;
import java.util.List;

import pl.pwsz.studentsindex.R;
import pl.pwsz.studentsindex.model.Study;
import pl.pwsz.studentsindex.adapters.StudyAdapter;
import pl.pwsz.studentsindex.viewmodels.SelectStudiesActivityViewModel;
//TODO set as default in menu option - save in file
public class SelectStudiesActivity extends AppCompatActivity implements View.OnCreateContextMenuListener {

    RecyclerView recyclerView;
    StudyAdapter adapter;
    FloatingActionButton fab;
    SelectStudiesActivityViewModel selectStudiesActivityViewModel;
    private int mCurrentItemPosition;


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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select_studies_activity, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final Study selectedStudy = adapter.getStudyAtPosition(mCurrentItemPosition);
        int id = item.getItemId();
        if (id == R.id.edit) {
            Intent intent = new Intent(SelectStudiesActivity.this, AddStudyActivity.class);
            intent.putExtra("Study",selectedStudy);
            startActivity(intent);

        }

        if (id == R.id.delete) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("You are about to delete this entry. You will lose all subentries that are connected with this Study. This action is permanent!\nAre you sure?");
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
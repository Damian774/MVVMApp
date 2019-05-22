package pl.pwsz.studentsindex.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import pl.pwsz.studentsindex.R;
import pl.pwsz.studentsindex.adapters.CategoryAdapter;
import pl.pwsz.studentsindex.adapters.StudyAdapter;
import pl.pwsz.studentsindex.model.Category;
import pl.pwsz.studentsindex.model.Grade;
import pl.pwsz.studentsindex.model.Study;
import pl.pwsz.studentsindex.viewmodels.AddCategoryActivityViewModel;
import pl.pwsz.studentsindex.viewmodels.SelectStudiesActivityViewModel;


public class ShowCategoriesActivity extends AppCompatActivity implements View.OnCreateContextMenuListener {

    RecyclerView recyclerView;
    CategoryAdapter adapter;
    FloatingActionButton fab;
    AddCategoryActivityViewModel addCategoryActivityViewModel;
    private int mCurrentItemPosition;
    private static final String PREFERENCES_NAME = "myPreferences";
    private static final String PREFERENCES_STUDY_ID = "activeStudy";
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_categories);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);



        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        adapter = new CategoryAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));




        addCategoryActivityViewModel = ViewModelProviders.of(this).get(AddCategoryActivityViewModel.class);
        addCategoryActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                adapter.setCategories(categories);
            }
        });


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowCategoriesActivity.this, AddCategoryActivity.class));
            }
        });
        registerForContextMenu(recyclerView);
        adapter.setOnLongItemClickListener(new CategoryAdapter.onLongItemClickListener() {
            @Override
            public void ItemLongClicked(View v, int position) {
                mCurrentItemPosition = position;
                v.showContextMenu();
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));


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

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select_categories_activity, menu);
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final Category selectedCategory = adapter.getCategoryAtPosition(mCurrentItemPosition);
        int id = item.getItemId();
        if (id == R.id.edit) {
            Intent intent = new Intent(ShowCategoriesActivity.this, AddCategoryActivity.class);
            intent.putExtra("Category",selectedCategory);
            startActivity(intent);

        }
        if(id == R.id.showGrades){
            Intent intent = new Intent(ShowCategoriesActivity.this, ShowGradesActivity.class);
            intent.putExtra("Category",selectedCategory);
            startActivity(intent);
        }
        if (id == R.id.delete) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("You are about to delete this category. You will lose all grades connected with this category!\nAre you sure?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            addCategoryActivityViewModel.delete(selectedCategory);
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
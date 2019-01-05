package pl.pwsz.studentsindex.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import pl.pwsz.studentsindex.R;
import pl.pwsz.studentsindex.model.Category;
import pl.pwsz.studentsindex.viewmodels.AddGradeActivityViewModel;

public class AddGradeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    AddGradeActivityViewModel addGradeActivityViewModel;
    static ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grade);

        addGradeActivityViewModel = ViewModelProviders.of(this).get(AddGradeActivityViewModel.class);
        addGradeActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable final List<Category> categories) {
                updateSpinner(adapter,categories);
            }
        });



        Spinner spinner = findViewById(R.id.label_spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }

        ArrayList<String> categoryStrings = new ArrayList<>();
        categoryStrings.add("Add category first");


        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                categoryStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
        adapter.add(categoryStrings.get(0));

    }
    public static void updateSpinner(ArrayAdapter<String> arrayAdapter, List<Category> categories){
        arrayAdapter.clear();
        List<String> categoryStrings = new ArrayList<>();
        if (categories!=null) {
            for(int i = 0; i < categories.size(); i++){
                categoryStrings.add(categories.get(i).getCategoryName());
            }
        }
        for (String categoryName : categoryStrings){
            arrayAdapter.add(categoryName);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String spinnerString = parent.getItemAtPosition(position).toString();
        Log.i("Spinner string: ",spinnerString);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

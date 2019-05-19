package pl.pwsz.studentsindex.views;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import pl.pwsz.studentsindex.R;
import pl.pwsz.studentsindex.model.Category;
import pl.pwsz.studentsindex.model.Grade;
import pl.pwsz.studentsindex.viewmodels.AddGradeActivityViewModel;

public class AddGradeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    AddGradeActivityViewModel addGradeActivityViewModel;
    static ArrayAdapter<String> adapter;
    EditText gradeValueET;
    EditText gradeWeightET;
    EditText gradeNoteET;
    Button saveGradeBTN;
    Spinner spinner;
    String categoryPicked;
    int categoryId;
    Grade pickedGrade;
    TextView categoryListEmptyTV;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grade);
        preferences = getSharedPreferences("myPreferences", Activity.MODE_PRIVATE);
        String studyId = preferences.getString("activeStudy", "");

         spinner = findViewById(R.id.spinner_categories);

        if( getIntent().getExtras() != null){
            Intent myIntent = getIntent();
            pickedGrade = (Grade)myIntent.getSerializableExtra("Grade");
        }


        addGradeActivityViewModel = ViewModelProviders.of(this).get(AddGradeActivityViewModel.class);
        addGradeActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable final List<Category> categories) {
                updateSpinner(adapter,categories);
            }
        });

        categoryListEmptyTV = findViewById(R.id.et_category_list_empty);
        gradeValueET = findViewById(R.id.et_grade_value);
        gradeNoteET = findViewById(R.id.et_grade_note);
        gradeWeightET = findViewById(R.id.et_grade_weight);
        saveGradeBTN = findViewById(R.id.btn_save_grade);
        saveGradeBTN.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                BigDecimal value = BigDecimal.valueOf(Double.parseDouble(gradeValueET.getText().toString()));
                                                BigDecimal weight = BigDecimal.valueOf(Double.parseDouble(gradeWeightET.getText().toString()));
                                                String note = gradeNoteET.getText().toString();
                                                categoryId = addGradeActivityViewModel.getCategoryByName(categoryPicked).getId();

                                                    if(pickedGrade!=null){
                                                        pickedGrade.setValue(value);
                                                        pickedGrade.setWeight(weight);
                                                        pickedGrade.setCategoryId(categoryId);
                                                        pickedGrade.setAdditionalNote(note);
                                                        addGradeActivityViewModel.update(pickedGrade);
                                                    }else{
                                                        Grade grade = new Grade(categoryId, value, weight, note);
                                                        addGradeActivityViewModel.insert(grade);
                                                    }



                                                startActivity(new Intent(AddGradeActivity.this, ShowGradesActivity.class));


                                            }
                                        });


        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }

        ArrayList<String> categoryStrings = new ArrayList<>();
        categoryStrings.add("");

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                categoryStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
        adapter.add(categoryStrings.get(0));
        if(pickedGrade!=null) {
            gradeValueET.setText(pickedGrade.getValue().toString());
            gradeWeightET.setText(pickedGrade.getWeight().toString());
            gradeNoteET.setText(pickedGrade.getAdditionalNote());

        }



    }



    public void updateSpinner(ArrayAdapter<String> arrayAdapter, List<Category> categories){
        arrayAdapter.clear();
        List<String> categoryStrings = new ArrayList<>();

        if (categories!=null && categories.size()>0) {
            spinner.setVisibility(View.VISIBLE);
            saveGradeBTN.setVisibility(View.VISIBLE);
            gradeValueET.setVisibility(View.VISIBLE);
            gradeWeightET.setVisibility(View.VISIBLE);
            gradeNoteET.setVisibility(View.VISIBLE);
            categoryListEmptyTV.setVisibility(View.GONE);

            categoryStrings.add("Select category");
            for(int i = 0; i < categories.size(); i++){
                categoryStrings.add(categories.get(i).getName());
            }
        }else{
            spinner.setVisibility(View.INVISIBLE);
            saveGradeBTN.setVisibility(View.INVISIBLE);
            gradeValueET.setVisibility(View.INVISIBLE);
            gradeWeightET.setVisibility(View.INVISIBLE);
            gradeNoteET.setVisibility(View.INVISIBLE);
            categoryListEmptyTV.setVisibility(View.VISIBLE);
        }
        for (String categoryName : categoryStrings){
            arrayAdapter.add(categoryName);
        }
        adapter.notifyDataSetChanged();
        if (pickedGrade != null) {
            spinner.setSelection(adapter.getPosition(addGradeActivityViewModel.getCategoryById(pickedGrade.getCategoryId()).getName()));
        }


    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        categoryPicked = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}

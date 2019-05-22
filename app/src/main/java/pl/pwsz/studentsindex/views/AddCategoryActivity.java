package pl.pwsz.studentsindex.views;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.Format;
import java.util.Locale;

import pl.pwsz.studentsindex.R;
import pl.pwsz.studentsindex.model.Category;
import pl.pwsz.studentsindex.model.Exam;
import pl.pwsz.studentsindex.viewmodels.AddCategoryActivityViewModel;

public class AddCategoryActivity extends AppCompatActivity {

    EditText categoryNameET;
    Button addCategoryBTN;
    AddCategoryActivityViewModel addCategoryActivityViewModel;
    private SharedPreferences preferences;
    int studyId;
    Category pickedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_category);
        preferences = getSharedPreferences("myPreferences", Activity.MODE_PRIVATE);
        studyId = preferences.getInt("activeStudy", 0);


        categoryNameET = findViewById(R.id.et_category_name);
        addCategoryBTN = findViewById(R.id.btn_save_category);

        if( getIntent().getExtras() != null){
            Intent myIntent = getIntent();
            pickedCategory = (Category) myIntent.getSerializableExtra("Category");
            if(pickedCategory!=null){
                categoryNameET.setText(pickedCategory.getName());
            }
        }
        addCategoryActivityViewModel = ViewModelProviders.of(this).get(AddCategoryActivityViewModel.class);
        addCategoryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pickedCategory !=null){
                    addCategoryActivityViewModel.update(pickedCategory);

                }else{
                    Category category = new Category(studyId,categoryNameET.getText().toString());
                    addCategoryActivityViewModel.insert(category);

                }

                startActivity(new Intent(AddCategoryActivity.this, ShowCategoriesActivity.class));
            }
        });

    }
}

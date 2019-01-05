package pl.pwsz.studentsindex.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import pl.pwsz.studentsindex.R;
import pl.pwsz.studentsindex.model.Category;
import pl.pwsz.studentsindex.viewmodels.AddCategoryActivityViewModel;

public class AddCategoryActivity extends AppCompatActivity {

    EditText categoryNameET;
    Button addCategoryBTN;
    AddCategoryActivityViewModel addCategoryActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        categoryNameET = findViewById(R.id.et_category_name);
        addCategoryBTN = findViewById(R.id.btn_save_category);

        addCategoryActivityViewModel = ViewModelProviders.of(this).get(AddCategoryActivityViewModel.class);
        addCategoryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Category category = new Category(categoryNameET.getText().toString());
                addCategoryActivityViewModel.insert(category);

                startActivity(new Intent(AddCategoryActivity.this, HomeScreenActivity.class));
            }
        });

    }
}

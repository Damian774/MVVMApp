package pl.pwsz.studentsindex.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import pl.pwsz.studentsindex.R;
import pl.pwsz.studentsindex.model.Study;
import pl.pwsz.studentsindex.viewmodels.CreateStudyViewModel;

public class AddStudyActivity extends AppCompatActivity {

    EditText schoolName;
    EditText schoolYear;
    Button button;
    CreateStudyViewModel createStudyViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_study);

        schoolName = findViewById(R.id.et_school_name);
        schoolYear = findViewById(R.id.et_school_year);
        button = findViewById(R.id.button_save_study);

        createStudyViewModel = ViewModelProviders.of(this).get(CreateStudyViewModel.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Study study = new Study(schoolName.getText().toString(), schoolYear.getText().toString());
                createStudyViewModel.insert(study);


                startActivity(new Intent(AddStudyActivity.this, SelectStudiesActivity.class));
            }
        });
    }
}

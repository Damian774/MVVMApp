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

    EditText schoolNameET;
    EditText schoolYearET;
    Button button;
    CreateStudyViewModel createStudyViewModel;
    Study pickedStudy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_study);



        schoolNameET = findViewById(R.id.et_school_name);
        schoolYearET = findViewById(R.id.et_school_year);
        button = findViewById(R.id.button_save_study);

        if( getIntent().getExtras() != null){
            Intent myIntent = getIntent();
            pickedStudy = (Study) myIntent.getSerializableExtra("Study");
            schoolNameET.setText(pickedStudy.getSchoolName());
            schoolYearET.setText(String.valueOf(pickedStudy.getYear()));
        }

        createStudyViewModel = ViewModelProviders.of(this).get(CreateStudyViewModel.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pickedStudy!=null){
                    pickedStudy.setSchoolName(schoolNameET.getText().toString());
                    pickedStudy.setYear(schoolYearET.getText().toString());
                    createStudyViewModel.update(pickedStudy);
                }else {
                    Study study = new Study(schoolNameET.getText().toString(), schoolYearET.getText().toString());
                    createStudyViewModel.insert(study);

                }
                startActivity(new Intent(AddStudyActivity.this, SelectStudiesActivity.class));

            }
        });
    }
}

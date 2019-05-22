package pl.pwsz.studentsindex.views;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;


import pl.pwsz.studentsindex.R;
import pl.pwsz.studentsindex.model.Note;
import pl.pwsz.studentsindex.viewmodels.ApplicationViewModel;


public class AddNoteActivity extends AppCompatActivity {

    ApplicationViewModel applicationViewModel;
    static ArrayAdapter<String> adapter;
    EditText gradeNoteET;
    Button saveGradeBTN;
    int categoryId;
    Note pickedNote;
    private SharedPreferences preferences;
    int studyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        preferences = getSharedPreferences("myPreferences", Activity.MODE_PRIVATE);
        studyId = preferences.getInt("activeStudy", 0);
        gradeNoteET = findViewById(R.id.et_grade_note);

        if (getIntent().getExtras() != null) {
            Intent myIntent = getIntent();
            pickedNote = (Note) myIntent.getSerializableExtra("Note");
            if(pickedNote!=null){
                gradeNoteET.setText(pickedNote.getNote());
            }
        }


        applicationViewModel = ViewModelProviders.of(this).get(ApplicationViewModel.class);

        saveGradeBTN = findViewById(R.id.btn_save_grade);
        saveGradeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String note = gradeNoteET.getText().toString();

                if (pickedNote != null) {
                    pickedNote.setNote(note);
                    applicationViewModel.update(pickedNote);
                } else {
                    Note noteObj = new Note(studyId,note);
                    applicationViewModel.insert(noteObj);
                }


                startActivity(new Intent(AddNoteActivity.this, HomeScreenActivity.class));


            }
        });
    }


}

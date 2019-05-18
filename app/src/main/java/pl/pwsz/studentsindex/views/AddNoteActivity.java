package pl.pwsz.studentsindex.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        if (getIntent().getExtras() != null) {
            Intent myIntent = getIntent();
            pickedNote = (Note) myIntent.getSerializableExtra("Note");
        }


        applicationViewModel = ViewModelProviders.of(this).get(ApplicationViewModel.class);

        gradeNoteET = findViewById(R.id.et_grade_note);
        saveGradeBTN = findViewById(R.id.btn_save_grade);
        saveGradeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String note = gradeNoteET.getText().toString();

                if (pickedNote != null) {
                    pickedNote.setNote(note);
                    applicationViewModel.update(pickedNote);
                } else {
                    Note noteObj = new Note(note);
                    applicationViewModel.insert(noteObj);
                }


                startActivity(new Intent(AddNoteActivity.this, HomeScreenActivity.class));


            }
        });
    }


}

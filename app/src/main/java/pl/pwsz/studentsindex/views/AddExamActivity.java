package pl.pwsz.studentsindex.views;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.pwsz.studentsindex.R;
import pl.pwsz.studentsindex.model.Category;
import pl.pwsz.studentsindex.model.Exam;
import pl.pwsz.studentsindex.model.Grade;
import pl.pwsz.studentsindex.model.Study;
import pl.pwsz.studentsindex.viewmodels.AddExamActivityViewModel;
import pl.pwsz.studentsindex.viewmodels.AddGradeActivityViewModel;
import pl.pwsz.studentsindex.viewmodels.CreateStudyViewModel;

public class AddExamActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText categoryET;
    EditText typeET;
    EditText dateET;
    Button button;
    AddExamActivityViewModel addExamActivityViewModel;
    static ArrayAdapter<String> adapter;
    Spinner spinner;
    int categoryId;
    String categoryPicked;
    Calendar myCalendar;
    TextView categoryListEmptyTV;
    private SharedPreferences preferences;
    int studyId;
    Exam pickedExam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);
        preferences = getSharedPreferences("myPreferences", Activity.MODE_PRIVATE);
        studyId = preferences.getInt("activeStudy", 0);
        spinner = findViewById(R.id.spinner_categories);
        typeET = findViewById(R.id.et_type);
        dateET = findViewById(R.id.date_examDate);
        button = findViewById(R.id.btn_save_exam);
        categoryListEmptyTV = findViewById(R.id.et_category_list_empty);



        addExamActivityViewModel = ViewModelProviders.of(this).get(AddExamActivityViewModel.class);
        addExamActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable final List<Category> categories) {
                updateSpinner(adapter,categories);
            }
        });

        //date
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        dateET.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddExamActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //date

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryId = addExamActivityViewModel.getCategoryByName(categoryPicked).getId();

                if(pickedExam!=null){
                    pickedExam.setCategoryId(categoryId);
                    DateFormat format = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
                    Date date = null;
                    try {
                        date = format.parse(dateET.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    pickedExam.setDate(date);
                    pickedExam.setType(typeET.getText().toString());
                    addExamActivityViewModel.update(pickedExam);
                }else {
                    DateFormat format = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
                    Date date = null;
                    try {
                        date = format.parse(dateET.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Exam exam = new Exam(studyId,categoryId,date, typeET.getText().toString());
                    addExamActivityViewModel.insert(exam);

                }
                startActivity(new Intent(AddExamActivity.this, ShowExamsActivity.class));

            }

        });
        //date

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
        if(pickedExam!=null) {
            pickedExam.setCategoryId(categoryId);
            DateFormat format = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
            Date date1 = null;
            try {
                date1 = format.parse(dateET.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            pickedExam.setDate(date1);
            pickedExam.setType(typeET.getText().toString());
            addExamActivityViewModel.update(pickedExam);

        }
        if( getIntent().getExtras() != null){
            Intent myIntent = getIntent();
            pickedExam = (Exam) myIntent.getSerializableExtra("Exam");
            if(pickedExam!=null){
                typeET.setText(pickedExam.getType());
                Format formatter = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
                String dateString = formatter.format(pickedExam.getDate());
                dateET.setText(dateString);
                spinner.setSelection(adapter.getPosition(addExamActivityViewModel.getCategoryById(pickedExam.getCategoryId()).getName()));
            }
        }
    }

    public void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        dateET.setText(sdf.format(myCalendar.getTime()));
    }
    //date

    public void updateSpinner(ArrayAdapter<String> arrayAdapter, List<Category> categories){
        arrayAdapter.clear();
        List<String> categoryStrings = new ArrayList<>();

        if (categories!=null && categories.size()>0) {
            spinner.setVisibility(View.VISIBLE);
            typeET.setVisibility(View.VISIBLE);
            dateET.setVisibility(View.VISIBLE);
            categoryListEmptyTV.setVisibility(View.GONE);

            categoryStrings.add("Select category");
            for(int i = 0; i < categories.size(); i++){
                categoryStrings.add(categories.get(i).getName());
            }
        }else{
            spinner.setVisibility(View.INVISIBLE);
            typeET.setVisibility(View.INVISIBLE);
            dateET.setVisibility(View.INVISIBLE);
            categoryListEmptyTV.setVisibility(View.VISIBLE);
        }
        for (String categoryName : categoryStrings){
            arrayAdapter.add(categoryName);
        }
        adapter.notifyDataSetChanged();
        if (pickedExam != null) {
            spinner.setSelection(adapter.getPosition(addExamActivityViewModel.getCategoryById(pickedExam.getCategoryId()).getName()));
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

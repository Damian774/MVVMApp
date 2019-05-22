package pl.pwsz.studentsindex.views;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import pl.pwsz.studentsindex.R;
import pl.pwsz.studentsindex.model.Category;
import pl.pwsz.studentsindex.model.Exam;
import pl.pwsz.studentsindex.model.Grade;
import pl.pwsz.studentsindex.viewmodels.ShowGradesActivityViewModel;

/**
 * An activity representing a list of Grades. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link GradeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */

public class ShowGradesActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    ShowGradesActivityViewModel showGradesActivityViewModel;
    RecyclerView recyclerView;
    SimpleItemRecyclerViewAdapter simpleItemRecyclerViewAdapter;
    List<Grade> gradeList;
    int pickedGradeId;
    ListView listView;
    private SharedPreferences preferences;
    int studyId;
    Category pickedCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_list);
        preferences = getSharedPreferences("myPreferences", Activity.MODE_PRIVATE);
        studyId = preferences.getInt("activeStudy", 0);
        listView = findViewById(R.id.list_view);
        showGradesActivityViewModel = ViewModelProviders.of(this).get(ShowGradesActivityViewModel.class);

        if( getIntent().getExtras() != null){
            Intent myIntent = getIntent();
            pickedCategory = (Category) myIntent.getSerializableExtra("Category");
            showGradesActivityViewModel.getAllGradesByCategory(pickedCategory.getId()).observe(this, new Observer<List<Grade>>() {
                @Override
                public void onChanged(@Nullable List<Grade> grades) {
                    Collections.reverse(grades);
                    gradeList = grades;
                    updateRecyclerView();
                    listView.invalidateViews();
                }
            });

        }else{
            showGradesActivityViewModel.getAllGrades().observe(this, new Observer<List<Grade>>() {
                @Override
                public void onChanged(@Nullable List<Grade> grades) {
                    Collections.reverse(grades);
                    gradeList = grades;
                    updateRecyclerView();
                    listView.invalidateViews();
                }
            });
        }


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
      //  toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowGradesActivity.this, AddGradeActivity.class));
            }
        });

        if (findViewById(R.id.grade_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        recyclerView = findViewById(R.id.grade_list);
        if (gradeList != null)
            simpleItemRecyclerViewAdapter = new SimpleItemRecyclerViewAdapter(this, gradeList, mTwoPane);
        if (recyclerView != null) recyclerView.setAdapter(simpleItemRecyclerViewAdapter);

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();

                        Grade grade = simpleItemRecyclerViewAdapter.getGradeAtPosition(position);
                        Toast.makeText(ShowGradesActivity.this, "Grade deleted", Toast.LENGTH_LONG).show();


                        showGradesActivityViewModel.deleteGrade(grade);
                        simpleItemRecyclerViewAdapter.notifyDataSetChanged();
                        updateRecyclerView();
                        listView.invalidateViews();
                    }
                });

        helper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));


    }


    public void updateRecyclerView() {
        simpleItemRecyclerViewAdapter = new SimpleItemRecyclerViewAdapter(this, gradeList, mTwoPane);
        if (recyclerView != null) recyclerView.setAdapter(simpleItemRecyclerViewAdapter);
    }


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {


        private final ShowGradesActivity mParentActivity;
        private final List<Grade> grades;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Grade item = (Grade) view.getTag();
                pickedGradeId = item.getId();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(GradeDetailFragment.ARG_ITEM_ID, pickedGradeId);
                    GradeDetailFragment fragment = new GradeDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.grade_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, GradeDetailActivity.class);
                    intent.putExtra(GradeDetailFragment.ARG_ITEM_ID, pickedGradeId);

                    context.startActivity(intent);
                }
            }
        };


        SimpleItemRecyclerViewAdapter(ShowGradesActivity parent,
                                      List<Grade> gradeList,
                                      boolean twoPane) {
            grades = gradeList;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        public Grade getGradeAtPosition(int position) {
            return grades.get(position);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grade_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.gradeValue.setText(grades.get(position).getValue().toString());
            holder.gradeWeight.setText(grades.get(position).getWeight().toString());
            String category = showGradesActivityViewModel.getCategoryById(grades.get(position).getCategoryId()).getName();
            holder.gradeCategory.setText(category);

            holder.itemView.setTag(grades.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return grades == null ? 0 : grades.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView gradeValue;
            final TextView gradeWeight;
            final TextView gradeCategory;

            ViewHolder(View view) {
                super(view);
                gradeValue = (TextView) view.findViewById(R.id.tv_value_grade);
                gradeWeight = (TextView) view.findViewById(R.id.tv_weight_grade);
                gradeCategory = (TextView) view.findViewById(R.id.tv_category_grade);

            }
        }
    }


}

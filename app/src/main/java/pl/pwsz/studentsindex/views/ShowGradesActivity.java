package pl.pwsz.studentsindex.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import pl.pwsz.studentsindex.R;

import pl.pwsz.studentsindex.model.Grade;
import pl.pwsz.studentsindex.viewmodels.ShowGradesActivityViewModel;

import java.util.Collections;
import java.util.List;

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
    int choosenGradeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_list);

        showGradesActivityViewModel = ViewModelProviders.of(this).get(ShowGradesActivityViewModel.class);
        showGradesActivityViewModel.getAllGrades().observe(this, new Observer<List<Grade>>() {
            @Override
            public void onChanged(@Nullable List<Grade> grades) {
                Collections.reverse(grades);
                gradeList = grades;
                updateRecyclerView();

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        if(gradeList!=null)simpleItemRecyclerViewAdapter = new SimpleItemRecyclerViewAdapter(this,gradeList,mTwoPane);
        if(recyclerView != null) recyclerView.setAdapter(simpleItemRecyclerViewAdapter);
    }


    public void updateRecyclerView() {
        simpleItemRecyclerViewAdapter = new SimpleItemRecyclerViewAdapter(this,gradeList,mTwoPane);
        if(recyclerView != null) recyclerView.setAdapter(simpleItemRecyclerViewAdapter);
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
                choosenGradeId = item.getId();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(GradeDetailFragment.ARG_ITEM_ID,choosenGradeId);
                    GradeDetailFragment fragment = new GradeDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.grade_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, GradeDetailActivity.class);
                    intent.putExtra(GradeDetailFragment.ARG_ITEM_ID, choosenGradeId);

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

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grade_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(grades.get(position).getValue().toString());
            holder.mContentView.setText( grades.get(position).getWeight().toString());

            holder.itemView.setTag(grades.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return grades==null ? 0 : grades.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}

package pl.pwsz.studentsindex.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;


import pl.pwsz.studentsindex.R;
import pl.pwsz.studentsindex.model.Note;
import pl.pwsz.studentsindex.viewmodels.ApplicationViewModel;

public class ShowNotesActivity extends AppCompatActivity {


    private boolean mTwoPane;
    ApplicationViewModel applicationViewModel;
    RecyclerView recyclerView;
    SimpleItemRecyclerViewAdapter simpleItemRecyclerViewAdapter;
    List<Note> noteList;
    int pickedNoteId;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        listView = findViewById(R.id.list_view);
        applicationViewModel = ViewModelProviders.of(this).get(ApplicationViewModel.class);
        applicationViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                Collections.reverse(notes);
                noteList = notes;
                updateRecyclerView();
                listView.invalidateViews();

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());



        if (findViewById(R.id.grade_detail_container) != null) {
            mTwoPane = true;
        }

        recyclerView = findViewById(R.id.grade_list);
        if (noteList != null)
            simpleItemRecyclerViewAdapter = new SimpleItemRecyclerViewAdapter(this, noteList, mTwoPane);
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

                        Note note = simpleItemRecyclerViewAdapter.getNoteAtPosition(position);
                        Toast.makeText(ShowNotesActivity.this, "Note deleted", Toast.LENGTH_LONG).show();

                        applicationViewModel.deleteNote(note);
                        simpleItemRecyclerViewAdapter.notifyDataSetChanged();
                        updateRecyclerView();
                        listView.invalidateViews();
                    }
                });

        helper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));


    }

    public void updateRecyclerView() {
        simpleItemRecyclerViewAdapter = new SimpleItemRecyclerViewAdapter(this, noteList, mTwoPane);
        if (recyclerView != null) recyclerView.setAdapter(simpleItemRecyclerViewAdapter);
    }


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ShowNotesActivity mParentActivity;
        private final List<Note> notes;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note item = (Note) view.getTag();
                pickedNoteId = item.getId();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(NoteDetailFragment.ARG_ITEM_ID, pickedNoteId);
                    NoteDetailFragment fragment = new NoteDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.grade_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, NoteDetailActivity.class);
                    intent.putExtra(NoteDetailFragment.ARG_ITEM_ID, pickedNoteId);

                    context.startActivity(intent);
                }
            }
        };


        SimpleItemRecyclerViewAdapter(ShowNotesActivity parent,
                                      List<Note> noteList,
                                      boolean twoPane) {
            notes = noteList;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        public Note getNoteAtPosition(int position) {
            return notes.get(position);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.note_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            holder.mIdView.setText(String.valueOf(position));
            holder.mContentView.setText(notes.get(position).getNote());

            holder.itemView.setTag(notes.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return notes == null ? 0 : notes.size();
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

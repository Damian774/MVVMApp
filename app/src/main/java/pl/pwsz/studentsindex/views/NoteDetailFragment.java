package pl.pwsz.studentsindex.views;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import pl.pwsz.studentsindex.R;
import pl.pwsz.studentsindex.model.Note;
import pl.pwsz.studentsindex.viewmodels.ApplicationViewModel;


public class NoteDetailFragment extends Fragment {

    ApplicationViewModel applicationViewModel;
    public static final String ARG_ITEM_ID = "item_id";
    int noteId;

    private Note note;


    public NoteDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(getString(R.string.grade_details_bar_title));
            }
        }
    }


    private void loadData(){
        applicationViewModel = ViewModelProviders.of(getActivity()).get(ApplicationViewModel.class);
        noteId = getArguments().getInt(ARG_ITEM_ID);
        note = applicationViewModel.getNoteById(noteId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.note_detail, container, false);
        loadData();
        if (note != null) {
            applicationViewModel.setPickedNote(note);
            String detailInfo = note.getNote();
            ((TextView) rootView.findViewById(R.id.grade_detail)).setText(detailInfo);
        }

        return rootView;
    }
}

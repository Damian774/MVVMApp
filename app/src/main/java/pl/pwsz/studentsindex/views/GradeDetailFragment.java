package pl.pwsz.studentsindex.views;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.pwsz.studentsindex.R;
import pl.pwsz.studentsindex.model.Grade;
import pl.pwsz.studentsindex.viewmodels.GradeDetailActivityViewModel;

/**
 * A fragment representing a single Grade detail screen.
 * This fragment is either contained in a {@link ShowGradesActivity}
 * in two-pane mode (on tablets) or a {@link GradeDetailActivity}
 * on handsets.
 */
public class GradeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    GradeDetailActivityViewModel gradeDetailActivityViewModel;
    public static final String ARG_ITEM_ID = "item_id";
    int gradeId;
    /**
     * The dummy content this fragment is presenting.
     */
    private Grade grade;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GradeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(getString(R.string.grade_details_bar_title));
            }
        }
    }


    private void loadData(){
        gradeDetailActivityViewModel = ViewModelProviders.of(getActivity()).get(GradeDetailActivityViewModel.class);
        gradeId = getArguments().getInt(ARG_ITEM_ID);
        grade = gradeDetailActivityViewModel.getGradeById(gradeId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grade_detail, container, false);
        // Show the dummy content as text in a TextView.
        loadData();
        if (grade != null) {
            gradeDetailActivityViewModel.setPickedGrade(grade);
            StringBuilder detailInfo = new StringBuilder();
            detailInfo.append(grade.getValue().toString())
                    .append(", weight: ")
                    .append(grade.getWeight())
                    .append(", category: ")
                    .append(gradeDetailActivityViewModel.getCategoryById(grade.getCategoryId()).getName());
            ((TextView) rootView.findViewById(R.id.grade_detail)).setText(detailInfo);
        }

        return rootView;
    }
}

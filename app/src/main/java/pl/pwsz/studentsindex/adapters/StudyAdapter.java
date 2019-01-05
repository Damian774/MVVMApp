package pl.pwsz.studentsindex.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.pwsz.studentsindex.R;
import pl.pwsz.studentsindex.model.Study;

public class StudyAdapter extends RecyclerView.Adapter<StudyAdapter.ViewHolder> {

    public List<Study> studies;
    private final LayoutInflater inflater;

    @Override
    public StudyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.study_row, parent, false);
        return new ViewHolder(view);
    }

    public StudyAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(StudyAdapter.ViewHolder holder, int position) {
        if (studies !=null) {
            holder.firstName.setText(studies.get(position).getSchoolName());
            holder.lastName.setText(studies.get(position).getYear());
        } else {
            holder.firstName.setText("No studies added");
        }
    }

    @Override
    public int getItemCount() {
        if (studies != null)
            return studies.size();
        else return 0;
    }

    public void setStudies(List<Study> studies){
        this.studies = studies;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView firstName;
        public TextView lastName;

        public ViewHolder(View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.et_school_name);
            lastName = itemView.findViewById(R.id.et_school_year);
        }
    }
}

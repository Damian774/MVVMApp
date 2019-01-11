package pl.pwsz.studentsindex.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.pwsz.studentsindex.R;
import pl.pwsz.studentsindex.model.Study;
import pl.pwsz.studentsindex.views.SelectStudiesActivity;

public class StudyAdapter extends RecyclerView.Adapter<StudyAdapter.ViewHolder> {

    public List<Study> studies;
    private final LayoutInflater inflater;
    onLongItemClickListener mOnLongItemClickListener;

    public void setOnLongItemClickListener(onLongItemClickListener onLongItemClickListener) {
        mOnLongItemClickListener = onLongItemClickListener;
    }

    public interface onLongItemClickListener {
        void ItemLongClicked(View v, int position);
    }

    public Study getStudyAtPosition(int position) {
        return studies.get(position);
    }

    @Override
    public StudyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.study_row, parent, false);
        return new ViewHolder(view);
    }

    public StudyAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(StudyAdapter.ViewHolder holder, final int position) {
        if (studies != null) {
            holder.firstName.setText(studies.get(position).getSchoolName());
            holder.lastName.setText(studies.get(position).getYear());
        } else {
            holder.firstName.setText("No studies added");
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnLongItemClickListener != null) {
                    mOnLongItemClickListener.ItemLongClicked(v, position);
                }

                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        if (studies != null)
            return studies.size();
        else return 0;
    }

    public void setStudies(List<Study> studies) {
        this.studies = studies;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder// implements View.OnCreateContextMenuListener {
    {public TextView firstName;
        public TextView lastName;

        public ViewHolder(View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.et_school_name);
            lastName = itemView.findViewById(R.id.et_school_year);
         //   itemView.setOnCreateContextMenuListener(this);
        }
/*
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select The Action");
            menu.add(0, v.getId(), 0, "Call");//groupId, itemId, order, title
            menu.add(0, v.getId(), 0, "SMS");
        }*/
    }
}

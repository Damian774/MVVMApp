package pl.pwsz.studentsindex.adapters;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import pl.pwsz.studentsindex.R;
import pl.pwsz.studentsindex.model.Exam;
import pl.pwsz.studentsindex.viewmodels.AddExamActivityViewModel;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ViewHolder> {

    public AddExamActivityViewModel addExamActivityViewModel;
    public List<Exam> exams;
    private LayoutInflater inflater;
    ExamAdapter.onLongItemClickListener mOnLongItemClickListener;

    public ExamAdapter(Context context){
        inflater = LayoutInflater.from(context);
         addExamActivityViewModel = ViewModelProviders.of((FragmentActivity) context).get(AddExamActivityViewModel.class);;
    }
    public void setOnLongItemClickListener(ExamAdapter.onLongItemClickListener onLongItemClickListener) {
        mOnLongItemClickListener = onLongItemClickListener;
    }

    public interface onLongItemClickListener {
        void ItemLongClicked(View v, int position);
    }

    public Exam getExamAtPosition(int position) {
        return exams.get(position);
    }

    @Override
    public ExamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.exam_row, parent, false);
        return new ExamAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ExamAdapter.ViewHolder holder, final int position) {
        if (exams != null) {
            String category = addExamActivityViewModel.getCategoryById(exams.get(position).getCategoryId()).getName();
            holder.category.setText(category);
            holder.type.setText(exams.get(position).getType());
            Format formatter = new SimpleDateFormat("MM/dd/yy",Locale.getDefault());
            String dateString = formatter.format(exams.get(position).getDate());
            holder.date.setText(dateString);
        } else {
            holder.category.setText("No exams added");
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
        if (exams != null)
            return exams.size();
        else return 0;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder// implements View.OnCreateContextMenuListener {
    {
        public TextView category;
        public TextView date;
        public TextView type;

        public ViewHolder(View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.tv_category);
            type = itemView.findViewById(R.id.tv_type);
            date = itemView.findViewById(R.id.tv_date);
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

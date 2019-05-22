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
import pl.pwsz.studentsindex.model.Category;
import pl.pwsz.studentsindex.model.Study;
import pl.pwsz.studentsindex.views.SelectStudiesActivity;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    public List<Category> categories;
    private final LayoutInflater inflater;
    onLongItemClickListener mOnLongItemClickListener;

    public void setOnLongItemClickListener(onLongItemClickListener onLongItemClickListener) {
        mOnLongItemClickListener = onLongItemClickListener;
    }

    public interface onLongItemClickListener {
        void ItemLongClicked(View v, int position);
    }

    public Category getCategoryAtPosition(int position) {
        return categories.get(position);
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_row, parent, false);
        return new ViewHolder(view);
    }

    public CategoryAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, final int position) {
        if (categories != null) {
            holder.categoryName.setText(categories.get(position).getName());
        } else {
            holder.categoryName.setText("No categories added");
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
        if (categories != null)
            return categories.size();
        else return 0;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder// implements View.OnCreateContextMenuListener {
    {
        public TextView categoryName;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.et_category_name);
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

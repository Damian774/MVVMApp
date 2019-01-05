package pl.pwsz.studentsindex.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Category {

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    @PrimaryKey(autoGenerate = true)
    private int category_id;


    @ColumnInfo(name = "category_name")
    private String categoryName;


    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

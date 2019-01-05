package pl.pwsz.studentsindex.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    LiveData<List<Category>> getAllCategories();

    @Insert
    void insert(Category category);

    @Query("DELETE FROM Category")
    void deleteAll();
}


package pl.pwsz.studentsindex.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getAllCategories();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category category);

    @Query("SELECT * FROM categories WHERE id LIKE :categoryId LIMIT 1")
    Category getCategoryById(int categoryId);

    @Query("SELECT * FROM categories WHERE name LIKE :categoryName LIMIT 1")
    Category getCategoryByName(String categoryName);

    @Query("DELETE FROM categories")
    void deleteAll();
}


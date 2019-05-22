package pl.pwsz.studentsindex.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categories where study_id = :studyId")
    LiveData<List<Category>> getAllCategories(int studyId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category category);

    @Query("SELECT * FROM categories WHERE id LIKE :categoryId AND study_id = :studyId LIMIT 1")
    Category getCategoryById(int studyId, int categoryId);

    @Query("SELECT * FROM categories WHERE name LIKE :categoryName AND study_id = :studyId LIMIT 1")
    Category getCategoryByName(int studyId,String categoryName);

    @Query("DELETE FROM categories WHERE study_id = :studyId")
    void deleteAll(int studyId);

    @Query("UPDATE categories SET  study_id=:studyId,name=:name WHERE id = :id")
    void update(int id, int studyId, String name);

    @Delete
    void deleteCategory(Category category);
}


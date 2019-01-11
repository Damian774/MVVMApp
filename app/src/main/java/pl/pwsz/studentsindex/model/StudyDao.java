package pl.pwsz.studentsindex.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface StudyDao {
    @Query("SELECT * FROM studies")
    LiveData<List<Study>> getAllStudies();

    @Insert
    void insert(Study study);

    @Query("DELETE FROM studies")
    void deleteAll();

    @Update
    void update(Study study);

    @Delete
    void deleteStudy(Study study);
}

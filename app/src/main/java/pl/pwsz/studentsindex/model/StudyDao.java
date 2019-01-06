package pl.pwsz.studentsindex.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface StudyDao {
    @Query("SELECT * FROM studies")
    LiveData<List<Study>> getAllStudies();

    @Insert
    void insert(Study study);

    @Query("DELETE FROM studies")
    void deleteAll();
}

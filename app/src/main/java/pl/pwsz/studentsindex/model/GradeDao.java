package pl.pwsz.studentsindex.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface GradeDao {

    @Query("SELECT * FROM grades")
    LiveData<List<Grade>> getAllGrades();

    @Insert
    void insert(Grade grade);

    @Query("SELECT * FROM grades WHERE id LIKE :gradeId LIMIT 1")
    Grade getGradeById(int gradeId);

    @Query("DELETE FROM grades")
    void deleteAll();
}

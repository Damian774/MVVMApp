package pl.pwsz.studentsindex.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.math.BigDecimal;
import java.util.List;

import pl.pwsz.studentsindex.utils.BigDecimalConverter;

@Dao
@TypeConverters({BigDecimalConverter.class})
public interface GradeDao {

    @Query("SELECT * FROM grades WHERE study_id = :studyId")
    LiveData<List<Grade>> getAllGrades(int studyId);

    @Insert
    void insert(Grade grade);

    @Query("SELECT * FROM grades WHERE id = :gradeId AND study_id = :studyId LIMIT 1")
    Grade getGradeById(int studyId,int gradeId);

    @Query("DELETE FROM grades WHERE study_id = :studyId")
    void deleteAll(int studyId);

    @Delete
    void deleteGrade(Grade grade);

    @Query("SELECT * FROM grades WHERE category_id = :categoryId AND study_id = :studyId")
    LiveData<List<Grade>> getGradesByCategoryId(int studyId,int categoryId);

    @Query("UPDATE grades SET  category_id=:category_id,value=:value,weight=:weight,additional_note=:additional_note WHERE id = :id")
    void update(int id, int category_id, BigDecimal value,BigDecimal weight,String additional_note);
}

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

    @Query("SELECT * FROM grades")
    LiveData<List<Grade>> getAllGrades();

    @Insert
    void insert(Grade grade);

    @Query("SELECT * FROM grades WHERE id = :gradeId LIMIT 1")
    Grade getGradeById(int gradeId);

    @Query("DELETE FROM grades")
    void deleteAll();

    @Delete
    void deleteGrade(Grade grade);



    @Query("UPDATE grades SET  category_id=:category_id,value=:value,weight=:weight,additional_note=:additional_note WHERE id = :id")
    void update(int id, int category_id, BigDecimal value,BigDecimal weight,String additional_note);
}

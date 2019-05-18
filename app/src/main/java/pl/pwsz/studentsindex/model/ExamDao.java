package pl.pwsz.studentsindex.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import pl.pwsz.studentsindex.utils.DateConverter;

@Dao
@TypeConverters(DateConverter.class)
public interface ExamDao {
    @Query("SELECT * FROM exams")
    LiveData<List<Exam>> getAllExams();

    @Insert
    void insert(Exam exam);

    @Query("SELECT * FROM exams WHERE id = :gradeId LIMIT 1")
    Exam getExamById(int gradeId);

    @Query("DELETE FROM exams")
    void deleteAll();

    @Delete
    void deleteExam(Exam exam);

    @Query("UPDATE exams SET  category_id=:category_id,date=:date,type=:type,additional_note=:additional_note WHERE id = :id")
    void update(int id, int category_id, Date date, String type, String additional_note);
}

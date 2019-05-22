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
    @Query("SELECT * FROM exams WHERE study_id = :studyId")
    LiveData<List<Exam>> getAllExams(int studyId);

    @Insert
    void insert(Exam exam);

    @Query("SELECT * FROM exams WHERE id = :gradeId AND study_id = :studyId LIMIT 1")
    Exam getExamById(int studyId,int gradeId);

    @Query("DELETE FROM exams WHERE study_id = :studyId")
    void deleteAll(int studyId);

    @Delete
    void deleteExam(Exam exam);

    @Query("UPDATE exams SET  category_id=:category_id,date=:date,type=:type WHERE id = :id")
    void update(int id, int category_id, Date date, String type);
}

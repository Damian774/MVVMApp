package pl.pwsz.studentsindex.model.repositories;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.TypeConverters;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.math.BigDecimal;
import java.util.List;

import pl.pwsz.studentsindex.model.AppDatabase;
import pl.pwsz.studentsindex.model.Category;
import pl.pwsz.studentsindex.model.Grade;
import pl.pwsz.studentsindex.model.GradeDao;
import pl.pwsz.studentsindex.utils.BigDecimalConverter;


public class GradeRepository {

    private GradeDao gradeDao;

    private LiveData<List<Grade>> allGrades;

    private SharedPreferences preferences;
    int studyId;

    public GradeRepository(Application application) {
        preferences = application.getApplicationContext().getSharedPreferences("myPreferences", Activity.MODE_PRIVATE);
        studyId = preferences.getInt("activeStudy", 0);
        AppDatabase db = AppDatabase.getDatabase(application);
        gradeDao = db.gradeDao();
        allGrades = gradeDao.getAllGrades(studyId);
    }

    public LiveData<List<Grade>> getAllGradesByCategory(int categoryId) {
        return gradeDao.getGradesByCategoryId(studyId,categoryId);
    }

    public LiveData<List<Grade>> getAllGrades() {
        return allGrades;
    }

    public Grade getGradeById(int gradeId){ return gradeDao.getGradeById(studyId,gradeId);} //TODO in another thread

    public void insert(Grade grade) {
        new GradeRepository.insertGradeAsyncTask(gradeDao).execute(grade);
    }

    public void update(Grade grade){
        new GradeRepository.updateGradeAsyncTask(gradeDao).execute(grade);

    }

    public void deleteGrade(Grade grade){
        new GradeRepository.deleteGradeAsyncTask(gradeDao).execute(grade);

    }

    private static class deleteGradeAsyncTask extends AsyncTask<Grade, Void, Void> {

        private GradeDao asyncTaskGradeDao;

        deleteGradeAsyncTask(GradeDao dao) {
            asyncTaskGradeDao = dao;
        }

        @Override
        protected Void doInBackground(final Grade... grades) {
            asyncTaskGradeDao.deleteGrade(grades[0]);

            return null;
        }
    }

    private static class insertGradeAsyncTask extends AsyncTask<Grade, Void, Void> {

        private GradeDao asyncTaskGradeDao;

        insertGradeAsyncTask(GradeDao dao) {
            asyncTaskGradeDao = dao;
        }

        @Override
        protected Void doInBackground(final Grade... grades) {
            asyncTaskGradeDao.insert(grades[0]);

            return null;
        }
    }

    private static class updateGradeAsyncTask extends AsyncTask<Grade, Void, Void> {

        private GradeDao asyncTaskGradeDao;

        updateGradeAsyncTask(GradeDao dao) {
            asyncTaskGradeDao = dao;
        }

        @Override
        protected Void doInBackground(final Grade... grades) {
            asyncTaskGradeDao.update(grades[0].getId(),grades[0].getCategoryId(),grades[0].getValue(),grades[0].getWeight(),grades[0].getAdditionalNote());

            return null;
        }
    }
}

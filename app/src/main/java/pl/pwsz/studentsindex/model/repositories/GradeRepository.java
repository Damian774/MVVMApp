package pl.pwsz.studentsindex.model.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import pl.pwsz.studentsindex.model.AppDatabase;
import pl.pwsz.studentsindex.model.Grade;
import pl.pwsz.studentsindex.model.GradeDao;

public class GradeRepository {

    private GradeDao gradeDao;

    private LiveData<List<Grade>> allGrades;

    public GradeRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        gradeDao = db.gradeDao();
        allGrades = gradeDao.getAllGrades();
    }


    public LiveData<List<Grade>> getAllGrades() {
        return allGrades;
    }


    public void insert(Grade grade) {
        new GradeRepository.insertGradeAsyncTask(gradeDao).execute(grade);
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
}

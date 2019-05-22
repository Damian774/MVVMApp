package pl.pwsz.studentsindex.model.repositories;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.List;

import pl.pwsz.studentsindex.model.AppDatabase;
import pl.pwsz.studentsindex.model.Exam;
import pl.pwsz.studentsindex.model.ExamDao;

public class ExamRepository {

    private ExamDao examDao;

    private LiveData<List<Exam>> allExams;
    private SharedPreferences preferences;
    int studyId;
    public ExamRepository(Application application) {
        preferences = application.getApplicationContext().getSharedPreferences("myPreferences", Activity.MODE_PRIVATE);
        studyId = preferences.getInt("activeStudy", 0);
        AppDatabase db = AppDatabase.getDatabase(application);
        examDao = db.examDao();
        allExams = examDao.getAllExams(studyId);
    }


    public LiveData<List<Exam>> getAllExams() {
        return allExams;
    }

    public Exam getExamById(int examId){ return examDao.getExamById(studyId,examId);} //TODO in another thread

    public void insert(Exam exam) {
        new ExamRepository.insertExamAsyncTask(examDao).execute(exam);
    }

    public void update(Exam exam){
        new ExamRepository.updateExamAsyncTask(examDao).execute(exam);

    }

    public void deleteExam(Exam exam){
        new ExamRepository.deleteExamAsyncTask(examDao).execute(exam);

    }

    private static class deleteExamAsyncTask extends AsyncTask<Exam, Void, Void> {

        private ExamDao asyncTaskExamDao;

        deleteExamAsyncTask(ExamDao dao) {
            asyncTaskExamDao = dao;
        }

        @Override
        protected Void doInBackground(final Exam... exams) {
            asyncTaskExamDao.deleteExam(exams[0]);

            return null;
        }
    }

    private static class insertExamAsyncTask extends AsyncTask<Exam, Void, Void> {

        private ExamDao asyncTaskExamDao;

        insertExamAsyncTask(ExamDao dao) {
            asyncTaskExamDao = dao;
        }

        @Override
        protected Void doInBackground(final Exam... exams) {
            asyncTaskExamDao.insert(exams[0]);

            return null;
        }
    }

    private static class updateExamAsyncTask extends AsyncTask<Exam, Void, Void> {

        private ExamDao asyncTaskExamDao;

        updateExamAsyncTask(ExamDao dao) {
            asyncTaskExamDao = dao;
        }

        @Override
        protected Void doInBackground(final Exam... exams) {
            asyncTaskExamDao.update(exams[0].getId(),exams[0].getCategoryId(),exams[0].getDate(),exams[0].getType());

            return null;
        }
    }
}

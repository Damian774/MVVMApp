package pl.pwsz.studentsindex.model.repositories;

import android.app.Application;

import pl.pwsz.studentsindex.model.AppDatabase;

public class ExamRepository {

    private ExamDao examDao;

    private LiveData<List<Exam>> allExams;

    public ExamRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        examDao = db.examDao();
        allExams = examDao.getAllExams();
    }


    public LiveData<List<Exam>> getAllExams() {
        return allExams;
    }

    public Exam getExamById(int examId){ return examDao.getExamById(examId);} //TODO in another thread

    public void insert(Exam exam) {
        new ExamRepository.insertExamAsyncTask(examDao).execute(exam);
    }

    public void update(Exam exam){
        new ExamRepository.updateExamAsyncTask(examDao).execute(exam);

    }

    public void deleteGrade(Exam exam){
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
            asyncTaskExamDao.update(exams[0].getId(),exams[0].getCategoryId(),grades[0].getValue(),grades[0].getWeight(),grades[0].getAdditionalNote());

            return null;
        }
    }
}

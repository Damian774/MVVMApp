package pl.pwsz.studentsindex.model.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import android.os.AsyncTask;

import java.util.List;

import pl.pwsz.studentsindex.model.AppDatabase;
import pl.pwsz.studentsindex.model.Study;
import pl.pwsz.studentsindex.model.StudyDao;

public class StudyRepository {

        private StudyDao studyDao;

        private LiveData<List<Study>> allStudies;


        public StudyRepository(Application application) {
            AppDatabase db = AppDatabase.getDatabase(application);
            studyDao = db.studyDao();
            allStudies = studyDao.getAllStudies();
        }

        public LiveData<List<Study>> getAllStudies() {
            return allStudies;
        }

        public void insert(Study study) {
            new insertStudyAsyncTask(studyDao).execute(study);
        }

        public void update(Study study){new updateStudyAsyncTask(studyDao).execute(study);}

    public void deleteStudy(Study study){studyDao.deleteStudy(study);}


    private static class updateStudyAsyncTask extends AsyncTask<Study, Void, Void> {

        private StudyDao asyncTaskStudyDao;

        updateStudyAsyncTask(StudyDao dao) {
            asyncTaskStudyDao = dao;
        }

        @Override
        protected Void doInBackground(final Study... studies) {
            asyncTaskStudyDao.update(studies[0]);
            return null;
        }
    }

        private static class insertStudyAsyncTask extends AsyncTask<Study, Void, Void> {

            private StudyDao asyncTaskStudyDao;

            insertStudyAsyncTask(StudyDao dao) {
                asyncTaskStudyDao = dao;
            }

            @Override
            protected Void doInBackground(final Study... studies) {
                asyncTaskStudyDao.insert(studies[0]);
                return null;
            }
        }


        }



package pl.pwsz.studentsindex.model.repositories;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.List;

import pl.pwsz.studentsindex.model.AppDatabase;
import pl.pwsz.studentsindex.model.Category;
import pl.pwsz.studentsindex.model.CategoryDao;
import pl.pwsz.studentsindex.model.Study;

public class CategoryRepository {
    private CategoryDao categoryDao;

    private LiveData<List<Category>> allCategories;
    private SharedPreferences preferences;
    int studyId;

    public CategoryRepository(Application application) {
        preferences = application.getApplicationContext().getSharedPreferences("myPreferences", Activity.MODE_PRIVATE);
        studyId = preferences.getInt("activeStudy", 0);
        AppDatabase db = AppDatabase.getDatabase(application);
        categoryDao = db.categoryDao();
        allCategories = categoryDao.getAllCategories(studyId);

    }


    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }
    public Category getCategoryByName(String categoryName){ return categoryDao.getCategoryByName(studyId,categoryName);} //TODO in another thread
    public Category getCategoryById(int categoryId){ return categoryDao.getCategoryById(studyId,categoryId);} //TODO in another thread

    public void update(Category category){new updateCategoryAsyncTask(categoryDao).execute(category);}

    public void delete(Category category){categoryDao.deleteCategory(category);}

    public void insert(Category category) {
        new CategoryRepository.insertCategoryAsyncTask(categoryDao).execute(category);
    }


    private static class insertCategoryAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDao asyncTaskCategoryDao;

        insertCategoryAsyncTask(CategoryDao dao) {
            asyncTaskCategoryDao = dao;
        }

        @Override
        protected Void doInBackground(final Category... categories) {
            asyncTaskCategoryDao.insert(categories[0]);

            return null;
        }
    }

    private static class updateCategoryAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDao asyncTaskCategoryDao;

        updateCategoryAsyncTask(CategoryDao dao) {
            asyncTaskCategoryDao = dao;
        }

        @Override
        protected Void doInBackground(final Category... categories) {
            asyncTaskCategoryDao.update(categories[0].getId(),categories[0].getStudyId(),categories[0].getName());

            return null;
        }
    }

}

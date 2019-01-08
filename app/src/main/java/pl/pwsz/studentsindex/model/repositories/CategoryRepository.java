package pl.pwsz.studentsindex.model.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import pl.pwsz.studentsindex.model.AppDatabase;
import pl.pwsz.studentsindex.model.Category;
import pl.pwsz.studentsindex.model.CategoryDao;

public class CategoryRepository {
    private CategoryDao categoryDao;

    private LiveData<List<Category>> allCategories;

    public CategoryRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        categoryDao = db.categoryDao();
        allCategories = categoryDao.getAllCategories();
    }


    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }
    public Category getCategoryByName(String categoryName){ return categoryDao.getCategoryByName(categoryName);} //TODO in another thread
    public Category getCategoryById(int categoryId){ return categoryDao.getCategoryById(categoryId);} //TODO in another thread


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

}

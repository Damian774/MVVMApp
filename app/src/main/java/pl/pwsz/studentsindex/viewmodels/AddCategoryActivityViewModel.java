package pl.pwsz.studentsindex.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import pl.pwsz.studentsindex.model.Category;
import pl.pwsz.studentsindex.model.repositories.CategoryRepository;

public class AddCategoryActivityViewModel extends AndroidViewModel {
    private CategoryRepository categoryRepository;

    private LiveData<List<Category>> allCategories;

    public AddCategoryActivityViewModel(Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);

        allCategories = categoryRepository.getAllCategories();

    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public void insert(Category category) { categoryRepository.insert(category);}

        public void delete(Category category) { categoryRepository.delete(category); }

        public void update(Category category){categoryRepository.update(category);}
}

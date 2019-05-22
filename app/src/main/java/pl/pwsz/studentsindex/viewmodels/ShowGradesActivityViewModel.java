package pl.pwsz.studentsindex.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import pl.pwsz.studentsindex.model.Category;
import pl.pwsz.studentsindex.model.Grade;
import pl.pwsz.studentsindex.model.repositories.CategoryRepository;
import pl.pwsz.studentsindex.model.repositories.GradeRepository;

public class ShowGradesActivityViewModel extends AndroidViewModel {

    private GradeRepository gradeRepository;

    private LiveData<List<Grade>> allGrades;

    private LiveData<List<Grade>> allGradesByCategory;

    private CategoryRepository categoryRepository;

    private LiveData<List<Category>> allCategories;

    public ShowGradesActivityViewModel(Application application) {
        super(application);
        gradeRepository = new GradeRepository(application);

        allGrades = gradeRepository.getAllGrades();

        categoryRepository = new CategoryRepository(application);

        allCategories = categoryRepository.getAllCategories();

    }

    public Grade getGradeById(int gradeId){ return gradeRepository.getGradeById(gradeId);}

    public LiveData<List<Grade>> getAllGradesByCategory(int categoryId){ return gradeRepository.getAllGradesByCategory(categoryId);}
    public LiveData<List<Grade>> getAllGrades() {
        return allGrades;
    }

    public void insert(Grade grade) { gradeRepository.insert(grade);}

    public void deleteGrade(Grade grade) { gradeRepository.deleteGrade(grade);}

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public void insert(Category category) { categoryRepository.insert(category);}

    public Category getCategoryById(int categoryId){return categoryRepository.getCategoryById(categoryId);}

    public Category getCategoryByName(String categoryName){return categoryRepository.getCategoryByName(categoryName);}

}

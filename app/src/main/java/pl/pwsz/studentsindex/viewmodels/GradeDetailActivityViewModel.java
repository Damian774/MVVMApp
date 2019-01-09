package pl.pwsz.studentsindex.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import pl.pwsz.studentsindex.model.Category;
import pl.pwsz.studentsindex.model.Grade;
import pl.pwsz.studentsindex.model.repositories.CategoryRepository;
import pl.pwsz.studentsindex.model.repositories.GradeRepository;

public class GradeDetailActivityViewModel extends AndroidViewModel {

    private GradeRepository gradeRepository;

    private LiveData<List<Grade>> allGrades;

    private CategoryRepository categoryRepository;

    private LiveData<List<Category>> allCategories;

    private Grade pickedGrade;

    public Grade getPickedGrade() {
        return pickedGrade;
    }

    public void setPickedGrade(Grade pickedGrade) {
        this.pickedGrade = pickedGrade;
    }

    public GradeDetailActivityViewModel(Application application) {
        super(application);
        gradeRepository = new GradeRepository(application);

        allGrades = gradeRepository.getAllGrades();

        categoryRepository = new CategoryRepository(application);

        allCategories = categoryRepository.getAllCategories();

    }

    public Grade getGradeById(int gradeId){ return gradeRepository.getGradeById(gradeId);}

    public Category getCategoryById(int categoryId){return categoryRepository.getCategoryById(categoryId);}

    public LiveData<List<Grade>> getAllGrades() {
        return allGrades;
    }

    public void insert(Grade grade) { gradeRepository.insert(grade);}

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public void insert(Category category) { categoryRepository.insert(category);}

    public Category getCategoryByName(String categoryName){return categoryRepository.getCategoryByName(categoryName);}
}

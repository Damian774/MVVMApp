package pl.pwsz.studentsindex.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import pl.pwsz.studentsindex.model.Category;
import pl.pwsz.studentsindex.model.Exam;
import pl.pwsz.studentsindex.model.Note;
import pl.pwsz.studentsindex.model.repositories.CategoryRepository;
import pl.pwsz.studentsindex.model.repositories.ExamRepository;
import pl.pwsz.studentsindex.model.repositories.NoteRepository;

public class AddExamActivityViewModel extends AndroidViewModel {

    private ExamRepository examRepository;

    private LiveData<List<Exam>> allExams;

    public Exam getPickedExam() {
        return pickedExam;
    }

    public void setPickedNote(Exam pickedExam) {
        this.pickedExam = pickedExam;
    }

    private Exam pickedExam;

    private CategoryRepository categoryRepository;

    private LiveData<List<Category>> allCategories;


    public AddExamActivityViewModel(Application application) {
        super(application);
        examRepository = new ExamRepository(application);

        allExams = examRepository.getAllExams();

        categoryRepository = new CategoryRepository(application);

        allCategories = categoryRepository.getAllCategories();

    }

    public LiveData<List<Exam>> getAllExams() { return allExams; }

    public Exam getExamById(int examId){return examRepository.getExamById(examId);}

    public void insert(Exam exam) { examRepository.insert(exam);}

    public void update(Exam exam) { examRepository.update(exam);}

    public void deleteExam(Exam exam){examRepository.deleteExam(exam);}

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public Category getCategoryById(int categoryId){return categoryRepository.getCategoryById(categoryId);}

    public void insert(Category category) { categoryRepository.insert(category);}

    public Category getCategoryByName(String categoryName){return categoryRepository.getCategoryByName(categoryName);}
}

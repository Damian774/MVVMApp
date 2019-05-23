package pl.pwsz.studentsindex.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import pl.pwsz.studentsindex.model.Grade;
import pl.pwsz.studentsindex.model.Study;
import pl.pwsz.studentsindex.model.repositories.GradeRepository;
import pl.pwsz.studentsindex.model.repositories.StudyRepository;

public class HomeScreenActivityViewModel extends AndroidViewModel {
    private StudyRepository studyRepository;

    private LiveData<List<Study>> allStudies;

    private GradeRepository gradeRepository;

    private LiveData<List<Grade>> allGrades;

    public HomeScreenActivityViewModel(@NonNull Application application) {
        super(application);
        studyRepository = new StudyRepository(application);

        allStudies = studyRepository.getAllStudies();
        gradeRepository = new GradeRepository(application);

        allGrades = gradeRepository.getAllGrades();
    }

    public LiveData<List<Grade>> getAllGrades() {
        return allGrades;
    }

    public LiveData<List<Study>> getAllStudies() {
        return allStudies;
    }

    public Study getStudyById(int studyId){
        return studyRepository.getStudyById(studyId);
    }

}

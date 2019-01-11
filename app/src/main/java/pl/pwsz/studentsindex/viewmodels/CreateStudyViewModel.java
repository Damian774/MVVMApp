package pl.pwsz.studentsindex.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import pl.pwsz.studentsindex.model.Study;
import pl.pwsz.studentsindex.model.repositories.StudyRepository;

public class CreateStudyViewModel extends AndroidViewModel {
    private StudyRepository studyRepository;

    private LiveData<List<Study>> allStudies;

    public CreateStudyViewModel(Application application) {
        super(application);
        studyRepository = new StudyRepository(application);

        allStudies = studyRepository.getAllStudies();
    }

    public LiveData<List<Study>> getAllStudies() {
        return allStudies;
    }

    public void insert(Study study) { studyRepository.insert(study); }

    public void update(Study study){studyRepository.update(study);}
}

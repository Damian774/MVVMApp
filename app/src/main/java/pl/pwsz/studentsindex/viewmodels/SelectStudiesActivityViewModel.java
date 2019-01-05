package pl.pwsz.studentsindex.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import pl.pwsz.studentsindex.model.Study;
import pl.pwsz.studentsindex.model.repositories.StudyRepository;

public class SelectStudiesActivityViewModel extends AndroidViewModel {

    private StudyRepository studyRepository;

    private LiveData<List<Study>> allStudies;

    public SelectStudiesActivityViewModel(@NonNull Application application) {
        super(application);
        studyRepository = new StudyRepository(application);

        allStudies = studyRepository.getAllStudies();
    }

    public LiveData<List<Study>> getAllStudies() {
        return allStudies;
    }

    public void insert(Study study) { studyRepository.insert(study); }
}

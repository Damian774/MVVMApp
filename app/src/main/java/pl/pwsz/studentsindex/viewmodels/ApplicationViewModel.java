package pl.pwsz.studentsindex.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;


import pl.pwsz.studentsindex.model.Note;
import pl.pwsz.studentsindex.model.repositories.NoteRepository;


public class ApplicationViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;

    private LiveData<List<Note>> allNotes;

    public Note getPickedNote() {
        return pickedNote;
    }

    public void setPickedNote(Note pickedNote) {
        this.pickedNote = pickedNote;
    }

    private Note pickedNote;


    public ApplicationViewModel(Application application) {
        super(application);
        noteRepository = new NoteRepository(application);

        allNotes = noteRepository.getAllNotes();

    }

    public LiveData<List<Note>> getAllNotes() { return allNotes; }

    public Note getNoteById(int noteId){return noteRepository.getNoteById(noteId);}

    public void insert(Note note) { noteRepository.insert(note);}

    public void update(Note note) { noteRepository.update(note);}

    public void deleteNote(Note note){noteRepository.deleteNote(note);}

}

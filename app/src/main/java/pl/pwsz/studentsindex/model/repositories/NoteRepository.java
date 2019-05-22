package pl.pwsz.studentsindex.model.repositories;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;

import java.util.List;


import pl.pwsz.studentsindex.model.AppDatabase;
import pl.pwsz.studentsindex.model.Note;
import pl.pwsz.studentsindex.model.NoteDao;


public class NoteRepository {

    private NoteDao noteDao;

    private LiveData<List<Note>> allNotes;
    private SharedPreferences preferences;
    int studyId;
    public NoteRepository(Application application) {
        preferences = application.getApplicationContext().getSharedPreferences("myPreferences", Activity.MODE_PRIVATE);
        studyId = preferences.getInt("activeStudy", 0);
        AppDatabase db = AppDatabase.getDatabase(application);
        noteDao = db.noteDao();
        allNotes = noteDao.getAllNotes(studyId);
    }


    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public Note getNoteById(int noteId) {
        return noteDao.getNoteById(studyId,noteId);
    }

    public void insert(Note note) {
        noteDao.insert(note);
    }

    public void update(Note note) {
        noteDao.update(note.getId(),note.getNote());


    }

    public void deleteNote(Note note) {
        noteDao.deleteNote(note);
    }

}

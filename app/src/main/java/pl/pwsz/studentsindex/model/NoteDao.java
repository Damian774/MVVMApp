package pl.pwsz.studentsindex.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getAllNotes();

    @Insert
    void insert(Note note);

    @Query("SELECT * FROM notes WHERE id = :noteId LIMIT 1")
    Note getNoteById(int noteId);

    @Query("DELETE FROM notes")
    void deleteAll();

    @Delete
    void deleteNote(Note note);

    @Query("UPDATE notes SET  note=:note WHERE id = :id")
    void update(int id, String note);
}

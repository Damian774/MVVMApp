package pl.pwsz.studentsindex.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes WHERE study_id = :studyId")
    LiveData<List<Note>> getAllNotes(int studyId);

    @Insert
    void insert(Note note);

    @Query("SELECT * FROM notes WHERE id = :noteId AND study_id = :studyId LIMIT 1")
    Note getNoteById(int studyId,int noteId);

    @Query("DELETE FROM notes WHERE study_id = :studyId")
    void deleteAll(int studyId);

    @Delete
    void deleteNote(Note note);

    @Query("UPDATE notes SET  note=:note WHERE id = :id")
    void update(int id, String note);
}

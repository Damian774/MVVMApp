package pl.pwsz.studentsindex.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "notes",foreignKeys =
        @ForeignKey( entity = Study.class,
                parentColumns = "id",
                childColumns = "study_id",
                onDelete = CASCADE), indices = {
        @Index( name = "study_id_note_index",value = {"study_id"})})
public class Note implements Serializable {

    public Note(int studyId,String note) {
        this.studyId = studyId;
        this.note = note;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "study_id")
    private int studyId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public int getStudyId() {
        return studyId;
    }

    public void setStudyId(int studyId) {
        this.studyId = studyId;
    }
}

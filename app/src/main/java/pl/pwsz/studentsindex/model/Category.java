package pl.pwsz.studentsindex.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "categories",foreignKeys =
@ForeignKey( entity = Study.class,
        parentColumns = "id",
        childColumns = "study_id",
        onDelete = CASCADE),
        indices = {@Index(value = {"name"}),@Index( name = "study_id_category_index",value = {"study_id"})})
public class Category implements Serializable {

    public Category(int studyId,String name) {
        this.studyId = studyId;
        this.name = name;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;


    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "study_id")
    private int studyId;

    public int getStudyId() {
        return studyId;
    }

    public void setStudyId(int studyId) {
        this.studyId = studyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String categoryName) {
        this.name = categoryName;
    }
}

package pl.pwsz.studentsindex.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Study {

    public Study(String schoolName, String year) {
        this.schoolName = schoolName;
        this.year = year;

    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "school_name")
    private String schoolName;

    @ColumnInfo(name = "year")
    private String year;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


}

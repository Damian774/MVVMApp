package pl.pwsz.studentsindex.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.math.BigDecimal;

import pl.pwsz.studentsindex.utils.BigDecimalConverter;

import static android.arch.persistence.room.ForeignKey.CASCADE;

//category_id column references a foreign key but it is not part of an index.
// This may trigger full table scans whenever parent table is modified so you are highly advised to create an index that covers this column.
@Entity(tableName = "grades", foreignKeys = {
        @ForeignKey(entity = Category.class,
                parentColumns = "id",
                childColumns = "category_id",
                onDelete = CASCADE),
        @ForeignKey(
                entity = Study.class,
                parentColumns = "id",
                childColumns = "study_id"
        )
}, indices = {
        @Index(name = "category_id_grade_index", value = {"category_id"}),
        @Index( name = "study_id_grade_index",value = {"study_id"})
})
@TypeConverters(BigDecimalConverter.class)
public class Grade implements Serializable {

    public Grade(int studyId,int categoryId, BigDecimal value, BigDecimal weight, String additionalNote) {
        this.studyId = studyId;
        this.categoryId = categoryId;
        this.value = value;
        this.weight = weight;
        this.additionalNote = additionalNote;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getAdditionalNote() {
        return additionalNote;
    }

    public void setAdditionalNote(String additionalNote) {
        this.additionalNote = additionalNote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudyId() {
        return studyId;
    }

    public void setStudyId(int studyId) {
        this.studyId = studyId;
    }
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "category_id")
    private int categoryId;

    @ColumnInfo(name = "value")
    private BigDecimal value;

    @ColumnInfo(name = "weight")
    private BigDecimal weight;

    @ColumnInfo(name = "additional_note")
    private String additionalNote;

    @ColumnInfo(name = "study_id")
    private int studyId;
}

package pl.pwsz.studentsindex.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import pl.pwsz.studentsindex.utils.BigDecimalConverter;
import pl.pwsz.studentsindex.utils.DateConverter;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "exams", foreignKeys = {@ForeignKey(entity = Category.class,
        parentColumns = "id",
        childColumns = "category_id",
        onDelete = CASCADE),
        @ForeignKey( entity = Study.class,
                parentColumns = "id",
                childColumns = "study_id",
                onDelete = CASCADE)}, indices = {
        @Index(name = "category_id_exam_index", value = {"category_id"}),
                @Index( name = "study_id_exam_index",value = {"study_id"})})
@TypeConverters(DateConverter.class)
public class Exam implements Serializable{

        public Exam(int studyId, int categoryId, Date date, String type) {
            this.studyId = studyId;
            this.categoryId = categoryId;
            this.date = date;
            this.type = type;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date value) {
            this.date = date;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        @ColumnInfo(name = "date")
        private Date date;

        @ColumnInfo(name = "type")
        private String type;

    @ColumnInfo(name = "study_id")
    private int studyId;

    }


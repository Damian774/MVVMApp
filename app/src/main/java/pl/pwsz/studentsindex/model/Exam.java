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

@Entity(tableName = "exams", foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "id",
        childColumns = "category_id",
        onDelete = CASCADE), indices = {
        @Index(name = "category_id_index", value = {"category_id"})
})
public class Exam {

        public Exam(int categoryId, BigDecimal value, BigDecimal weight, String additionalNote) {
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

        @PrimaryKey(autoGenerate = true)
        private int id;

        @ColumnInfo(name = "category_id")
        private int categoryId;

        @ColumnInfo(name = "date")
        private DateTime value;

        @ColumnInfo(name = "type")
        private BigDecimal weight;

        @ColumnInfo(name = "additional_note")
        private String additionalNote;

    }

}

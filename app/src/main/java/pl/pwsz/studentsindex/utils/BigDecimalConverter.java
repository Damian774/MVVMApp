package pl.pwsz.studentsindex.utils;

import android.arch.persistence.room.TypeConverter;

import java.math.BigDecimal;

public class BigDecimalConverter {

        @TypeConverter
        public static BigDecimal fromInt(int value) {
            return value == 0 ? new BigDecimal(0) : new BigDecimal(value).divide(new BigDecimal(10));
        }

        @TypeConverter
        public static int toInt(BigDecimal value) {
            return value == null? 0 : value.multiply(new BigDecimal(10)).intValue();
        }

    }




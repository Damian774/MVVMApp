package pl.pwsz.studentsindex.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Study.class,Category.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    private StudyDao StudyDao;
    private CategoryDao categoryDao;

    public abstract StudyDao studyDao();
    public abstract CategoryDao categoryDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "student_database")
                            .allowMainThreadQueries() //TODO access DB in another thread
                            .fallbackToDestructiveMigration()
                            // java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
                            .build();

                }
            }
        }
        return INSTANCE;
    }


}

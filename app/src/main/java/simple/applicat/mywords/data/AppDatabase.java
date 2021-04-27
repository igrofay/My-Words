package simple.applicat.mywords.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Dictionary.class , Word.class} , version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoDictionary getDaoDictionary();
    public abstract DaoWord getDaoWord();
    private static AppDatabase INSTANCE = null;
    public static AppDatabase getDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(
                    context, AppDatabase.class , "database-App"
            ).build();
        }
        return INSTANCE ;
    }
}

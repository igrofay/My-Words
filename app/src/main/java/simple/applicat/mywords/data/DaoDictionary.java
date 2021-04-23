package simple.applicat.mywords.data;

import androidx.room.*;


@Dao
public interface DaoDictionary {
    @Insert
    long insertDictionary_db(Dictionary dictionary);
    @Delete
    void delete(Dictionary dictionary);
}

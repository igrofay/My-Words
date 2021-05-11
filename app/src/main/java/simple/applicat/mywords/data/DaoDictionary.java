package simple.applicat.mywords.data;

import androidx.room.*;

import java.util.List;


@Dao
public interface DaoDictionary {
    @Insert
    long insertDictionary_db(Dictionary dictionary);
    @Update
    void updateDictionary_db(Dictionary dictionary);
    @Delete
    void deleteDictionary_db(Dictionary dictionary);

    @Query("SELECT * FROM T_DICTIONARIES")
    List<Dictionary> getAllDictionaries_db();

}

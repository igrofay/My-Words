package simple.applicat.mywords.data;

import androidx.room.*;

import java.util.List;


@Dao
public interface DaoWord {

    @Insert
    long insertWord_db(Word word);

    @Delete
    void deleteWord_db(Word word);

    @Delete
    void deleteWords_db(Word... word);

    @Update
    void updateWord_db(Word word);

    @Update
    void updateWords_db(Word... word);

    @Query("SELECT * FROM t_Words WHERE idParent IN (:idDictionary)")
    List<Word> getAllWordsFromDictionary_db(long idDictionary);

}

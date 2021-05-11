package simple.applicat.mywords.data;

import androidx.room.*;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface DaoWord {

    @Insert
    long insertWord_db(Word word);

    @Delete
    void deleteWord_db(Word word);

    @Delete
    void deleteWords_db(ArrayList<Word> word);

    @Update
    void updateWord_db(Word word);

    @Update
    void updateWords_db(ArrayList<Word> word);

    @Query("SELECT * FROM t_Words WHERE idParent IN (:idDictionary)")
    List<Word> getAllWordsFromDictionary_db(long idDictionary);

    @Query("SELECT * FROM t_Words WHERE idParent IN (:idDictionary) ORDER BY levelOfKnowledge ASC LIMIT (:count)")
    List<Word> getWordsForLearning(long idDictionary , int count);
}

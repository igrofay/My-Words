package simple.applicat.mywords.data;

import androidx.room.Entity;

@Entity(tableName = "t_Words")
public class Word {
    private long idWord;
    private String nativeWord ;
    private String foreignWord;
    private String transcription;
    private String string_URI_photo;
    private long idParent;
}

package simple.applicat.mywords.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "t_Words")
public class Word {
    @PrimaryKey(autoGenerate = true)
    private long idWord;
    private String nativeWord ;
    private String foreignWord;
    private String transcription;
    private String string_URI_photo;
    private long idParent;

    public long getIdWord() {
        return idWord;
    }

    public void setIdWord(long idWord) {
        this.idWord = idWord;
    }

    public String getNativeWord() {
        return nativeWord;
    }

    public void setNativeWord(String nativeWord) {
        this.nativeWord = nativeWord;
    }

    public String getForeignWord() {
        return foreignWord;
    }

    public void setForeignWord(String foreignWord) {
        this.foreignWord = foreignWord;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public String getString_URI_photo() {
        return string_URI_photo;
    }

    public void setString_URI_photo(String string_URI_photo) {
        this.string_URI_photo = string_URI_photo;
    }

    public long getIdParent() {
        return idParent;
    }

    public void setIdParent(long idParent) {
        this.idParent = idParent;
    }
}

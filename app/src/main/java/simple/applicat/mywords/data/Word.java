package simple.applicat.mywords.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "t_Words")
public class Word implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private long idWord;
    private String nativeWord ;
    private String foreignWord;
    private String transcription;
    private String string_URI_photo;
    private int levelOfKnowledge;
    private long idParent;

    public Word(){}

    public Word( String foreignWord ,String nativeWord, String transcription, String string_URI_photo, long idParent) {
        this.foreignWord = foreignWord;
        this.nativeWord = nativeWord;
        this.transcription = transcription;
        this.string_URI_photo = string_URI_photo;
        this.idParent = idParent;
        this.levelOfKnowledge = 0;
    }

    protected Word(Parcel in) {
        idWord = in.readLong();
        nativeWord = in.readString();
        foreignWord = in.readString();
        transcription = in.readString();
        string_URI_photo = in.readString();
        levelOfKnowledge = in.readInt();
        idParent = in.readLong();
    }

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

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
    public int getLevelOfKnowledge() {
        return levelOfKnowledge;
    }
    public void setLevelOfKnowledge(int levelOfKnowledge) {
        this.levelOfKnowledge = levelOfKnowledge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(idWord);
        dest.writeString(nativeWord);
        dest.writeString(foreignWord);
        dest.writeString(transcription);
        dest.writeString(string_URI_photo);
        dest.writeInt(levelOfKnowledge);
        dest.writeLong(idParent);
    }
}

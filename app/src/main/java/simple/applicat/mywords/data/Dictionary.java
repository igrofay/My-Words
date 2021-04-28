package simple.applicat.mywords.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.*;

import java.util.ArrayList;

@Entity(tableName = "t_Dictionaries")
public class Dictionary implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private long idDictionary;
    private String nameDictionary;
    private String string_URI_photo;
    @Ignore
    private ArrayList<Word> listWords ;



    public Dictionary(String nameDictionary , String string_URI_photo) {
        this.nameDictionary = nameDictionary; this.string_URI_photo = string_URI_photo ;
    }


    public String getString_URI_photo() { return string_URI_photo; }
    public void setString_URI_photo(String string_URI_photo) { this.string_URI_photo = string_URI_photo; }
    public String getNameDictionary() { return nameDictionary; }
    public void setNameDictionary(String nameDictionary) {
        this.nameDictionary = nameDictionary;
    }
    public long getIdDictionary() { return idDictionary; }
    public void setIdDictionary(long idDictionary) { this.idDictionary = idDictionary; }
    public ArrayList<Word> getListWords() {return listWords; }
    public void setListWords(ArrayList<Word> listWords) {this.listWords = listWords; }

    protected Dictionary(Parcel in) {
        idDictionary = in.readLong();
        nameDictionary = in.readString();
        string_URI_photo = in.readString();
    }
    public static final Creator<Dictionary> CREATOR = new Creator<Dictionary>() {
        @Override
        public Dictionary createFromParcel(Parcel in) {
            return new Dictionary(in);
        }

        @Override
        public Dictionary[] newArray(int size) {
            return new Dictionary[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(idDictionary);
        dest.writeString(nameDictionary);
        dest.writeString(string_URI_photo);
    }
}
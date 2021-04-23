package simple.applicat.mywords.data;

import androidx.room.*;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "t_Dictionaries")
public class Dictionary {
    @PrimaryKey
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
}

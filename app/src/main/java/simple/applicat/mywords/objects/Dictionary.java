package simple.applicat.mywords.objects;

import android.graphics.Bitmap;

public class Dictionary {
    private String nameDictionary;
    Bitmap photoDictionary;
    String string_URI_photo;

    public Dictionary(String nameDictionary) {
        this.nameDictionary = nameDictionary;
    }

    public String getNameDictionary() {
        return nameDictionary;
    }

    public void setNameDictionary(String nameDictionary) {
        this.nameDictionary = nameDictionary;
    }
}

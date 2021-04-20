package simple.applicat.mywords.data;


public class Dictionary {
 
    private long id ;
    private String nameDictionary;
    private String string_URI_photo;

    public Dictionary(String nameDictionary , String string_URI_photo) { this.nameDictionary = nameDictionary; this.string_URI_photo = string_URI_photo ; }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getString_URI_photo() { return string_URI_photo; }
    public void setString_URI_photo(String string_URI_photo) { this.string_URI_photo = string_URI_photo; }
    public String getNameDictionary() { return nameDictionary; }
    public void setNameDictionary(String nameDictionary) {
        this.nameDictionary = nameDictionary;
    }
}

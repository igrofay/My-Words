package simple.applicat.mywords.helper;

import java.util.ArrayList;


import simple.applicat.mywords.data.Dictionary;

public class IntentHelper {
    private ArrayList<Dictionary> dictionaries ;
    private IntentHelper() {
    }
    private static IntentHelper INSTANCE = null;
    public static IntentHelper getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new IntentHelper();
        }
        return INSTANCE ;
    }
    public void putDictionaris(ArrayList<Dictionary> list){
        dictionaries = list ;
    }
    public ArrayList<Dictionary> getDictionaries(){
        return dictionaries;
    }
}

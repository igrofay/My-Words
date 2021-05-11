package simple.applicat.mywords.mainfragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import simple.applicat.mywords.managers.DialogManager;
import simple.applicat.mywords.MainActivity;
import simple.applicat.mywords.R;
import simple.applicat.mywords.data.AppDatabase;
import simple.applicat.mywords.data.Dictionary;
import simple.applicat.mywords.data.Word;

import static android.app.Activity.RESULT_OK;
import static simple.applicat.mywords.mainfragments.WordsFragment.OPEN_DICTIONARY;


public class CreateOrEditWordFragment extends Fragment {
    static final int GALLERY_REQUEST = 1;
    public static String EDIT_WORD = "EDIT_WORD";
    public static String ITSELF_WORD = "ITSELF_WORD";
    Uri URI_SELECTED_IMAGE;
    Button saveNewWord;
    ImageView addImageWord;
    TextView addWordInForeign;
    TextView addWordInNative;
    TextView addTranscription;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_or_edit_word, container, false);
        addImageWord = view.findViewById(R.id.add_image_word);
        addWordInForeign = view.findViewById(R.id.add_word_in_foreign);
        addWordInNative = view.findViewById(R.id.add_word_in_native);
        addTranscription = view.findViewById(R.id.add_transcription);
        saveNewWord = view.findViewById(R.id.save_new_word);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean edit_word = getArguments().getBoolean(EDIT_WORD , false);
        addImageWord.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        });
        addWordInForeign.setOnClickListener(v -> {
            DialogManager.startInputDialog(v, R.string.input_word_in_foreign);
        });
        addWordInNative.setOnClickListener(v -> {
            DialogManager.startInputDialog(v, R.string.input_word_in_native);
        });
        addTranscription.setOnClickListener(v -> {
            DialogManager.startInputDialog(v, R.string.input_transcription);
        });
        saveNewWord.setOnClickListener(v -> {
            if(edit_word)saveEditWord();
             else saveNewWord();
        });
        if(edit_word && savedInstanceState == null){
            Word word = getArguments().getParcelable(ITSELF_WORD);
            Glide.with(this).load(word.getString_URI_photo()).error(R.drawable.ic_error_image).into(addImageWord);
            addWordInForeign.setText(word.getForeignWord());
            addWordInNative.setText(word.getNativeWord());
            addTranscription.setText(word.getTranscription());
        }else if(savedInstanceState != null ){
            addWordInForeign.setText(savedInstanceState.getString(wordInForeign));
            addWordInNative.setText(savedInstanceState.getString(wordInNative));
            addTranscription.setText(savedInstanceState.getString(wordTranscription));
            if(savedInstanceState.getString(STR_URI) != null){
                URI_SELECTED_IMAGE = Uri.parse(savedInstanceState.getString(STR_URI));
                Glide.with(this).load(URI_SELECTED_IMAGE).error(R.drawable.ic_error_image).into(addImageWord);
            }
        }
    }


    private void saveEditWord() {
        Word word = getArguments().getParcelable(ITSELF_WORD);
        word.setForeignWord(addWordInForeign.getText().toString());
        word.setNativeWord(addWordInNative.getText().toString());
        word.setTranscription(addTranscription.getText().toString());
        word.setString_URI_photo( URI_SELECTED_IMAGE != null ? URI_SELECTED_IMAGE.toString() : word.getString_URI_photo());
        new Thread(()->{
            AppDatabase.getDatabase(requireContext()).getDaoWord().updateWord_db(word);
        }).start();
        NavHostFragment.findNavController(this).popBackStack();
    }

    private void saveNewWord(){
        int position = getArguments().getInt(OPEN_DICTIONARY);
        Dictionary dictionary = ((MainActivity) requireActivity()).dictionaries.get(position);
        Word word = new Word(addWordInForeign.getText().toString() , addWordInNative.getText().toString(),
                addTranscription.getText().toString() , URI_SELECTED_IMAGE!=null ? URI_SELECTED_IMAGE.toString() : "" ,
                dictionary.getIdDictionary()
                );
        new Thread(()->{
            long id = AppDatabase.getDatabase(requireContext()).
                    getDaoWord().insertWord_db(word);
            word.setIdWord(id);
        }).start();
        dictionary.getListWords().add(word);
        NavHostFragment.findNavController(this).popBackStack();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(wordInForeign , addWordInForeign.getText().toString() );
        outState.putString(wordInNative , addWordInNative.getText().toString());
        outState.putString(wordTranscription , addTranscription.getText().toString());
        outState.putString(STR_URI , URI_SELECTED_IMAGE != null ? URI_SELECTED_IMAGE.toString() : null );
    }
    String wordInForeign = "WordInForeign";
    String wordInNative = "WordInNative";
    String wordTranscription = "WordTranscription" ;
    String STR_URI = "STR_URI";

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == GALLERY_REQUEST){
            URI_SELECTED_IMAGE = data.getData();
            Glide.with(this).
                    load(URI_SELECTED_IMAGE).
                    error(R.drawable.ic_error_image).
                    into(addImageWord);
        }
    }

}
package simple.applicat.mywords.mainfragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

import simple.applicat.mywords.managers.DialogManager;
import simple.applicat.mywords.MainActivity;
import simple.applicat.mywords.R;
import simple.applicat.mywords.TeachWordsActivity;
import simple.applicat.mywords.adapters.WordsAdapter;
import simple.applicat.mywords.data.AppDatabase;
import simple.applicat.mywords.data.Dictionary;
import simple.applicat.mywords.data.Word;

import static android.app.Activity.RESULT_OK;
import static simple.applicat.mywords.TeachWordsActivity.LEARNED_WORDS;
import static simple.applicat.mywords.TeachWordsActivity.WORDS_FOR_LEARNING;
import static simple.applicat.mywords.mainfragments.CreateOrEditWordFragment.EDIT_WORD;

public class WordsFragment extends Fragment {
    public static String OPEN_DICTIONARY = "OPEN_DICTIONARY";
    public static int WORDS = 36 ;
    Dictionary dictionary;
    RecyclerView RC_Words;
    AppCompatImageButton createNewWord;
    int position;
    Toolbar toolbar ;
    ImageView imageOpenDictionary;
    AppCompatButton teachWords;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        position = getArguments().getInt(OPEN_DICTIONARY );
        dictionary = ((MainActivity) requireActivity()).dictionaries.get(position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_words, container, false);
        RC_Words = v.findViewById(R.id.recycle_view_words);
        createNewWord = v.findViewById(R.id.create_new_word);
        toolbar = v.findViewById(R.id.toolbar_words); imageOpenDictionary = v.findViewById(R.id.image_open_dictionary);
        collapsingToolbarLayout = v.findViewById(R.id.CollapsingToolbarLayoutWords);
        teachWords = v.findViewById(R.id.teach_words);
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RC_Words.setAdapter(new WordsAdapter(this , dictionary.getListWords() , true ));
        RC_Words.setLayoutManager(new LinearLayoutManager(getContext()));
        createNewWord.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putBoolean(EDIT_WORD ,false);
            args.putInt(OPEN_DICTIONARY , position);
           NavHostFragment.findNavController(this).navigate(R.id.action_wordsFragment_to_createWordFragment , args);
       });
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.edit_dictionary:
                    DialogManager.startEditDictionaryBottomDialog(this, position);
                    return true;
                case  R.id.delete_dictionary:
                    DialogManager.startDeleteDialog(
                            ((MainActivity) requireActivity()).dictionaries , position , this);
                    return true;
                default:
                    return false;
            }
        });
        setValuesToolbar();
        teachWords.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
            int count = Integer.parseInt(sharedPreferences.getString("countWords" , "5"));
            if(dictionary.getListWords().size() < count){
                Toast.makeText(requireContext() , R.string.few_words , Toast.LENGTH_SHORT).show();

            }else {
                DialogManager dialog = new DialogManager(requireContext());
                dialog.startLoadingDialog();
                new Thread(()->{
                    ArrayList<Word> wordArrayList = (ArrayList<Word>) AppDatabase
                            .getDatabase(requireContext()).getDaoWord()
                            .getWordsForLearning(dictionary.getIdDictionary() ,count);
                    Intent intent = new Intent(requireContext() , TeachWordsActivity.class);
                    intent.putParcelableArrayListExtra(WORDS_FOR_LEARNING , wordArrayList);
                    startActivityForResult(intent , WORDS);
                    dialog.dismissDialog();
                }).start();
            }
        });
    }

    CollapsingToolbarLayout collapsingToolbarLayout ;
    public void setValuesToolbar(){
        Glide.with(this).load(dictionary.getString_URI_photo()).error(R.drawable.ic_dictionaries_toolbar).into(imageOpenDictionary);
        collapsingToolbarLayout.setTitle(dictionary.getNameDictionary());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== RESULT_OK ){
            ArrayList<Word> resultWords = data.getParcelableArrayListExtra(LEARNED_WORDS);
            for (int i = 0; i < resultWords.size(); i++) {
                for (Word word : dictionary.getListWords()) {
                    if(word.getIdWord() == resultWords.get(i).getIdWord()){
                        word.setLevelOfKnowledge(
                                resultWords.get(i).getLevelOfKnowledge()
                        );
                        break;
                    }
                }
            }
            new Thread(()->{
                AppDatabase.getDatabase(requireContext())
                        .getDaoWord().updateWords_db(resultWords);
            }).start();
        }
    }
}
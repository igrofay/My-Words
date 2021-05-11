package simple.applicat.mywords.adapters;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import simple.applicat.mywords.R;

import simple.applicat.mywords.data.AppDatabase;
import simple.applicat.mywords.data.Dictionary;

import simple.applicat.mywords.data.Word;
import simple.applicat.mywords.mainfragments.DictionariesFragment;
import simple.applicat.mywords.managers.DialogManager;


import static simple.applicat.mywords.mainfragments.WordsFragment.OPEN_DICTIONARY;


public class DictionariesAdapter extends RecyclerView.Adapter<DictionariesAdapter.ViewCell>{
    private final ArrayList<Dictionary> dictionaryArrayList;
    private final DictionariesFragment dictionariesFragment ;
    public DictionariesAdapter(ArrayList<Dictionary> dictionaryArrayList, DictionariesFragment dictionariesFragment) {
        this.dictionaryArrayList = dictionaryArrayList;
        this.dictionariesFragment = dictionariesFragment ;
    }


    @NonNull
    @Override
    public ViewCell onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.style_view_cell_dictionary , parent , false);
        return new ViewCell(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewCell holder, int position) {
        Dictionary dictionary = dictionaryArrayList.get(position);
        holder.setValues(dictionary);
        holder.startAnimation();
    }

    @Override
    public int getItemCount() {
        return dictionaryArrayList.size();
    }

    class ViewCell extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView nameDictionary ;
        final ImageView imageDictionary;
        final Animation animationItem ;
        Dictionary dictionary ;
        public ViewCell(@NonNull View itemView) {
            super(itemView);
            nameDictionary = itemView.findViewById(R.id.name_dictionary);
            imageDictionary = itemView.findViewById(R.id.image_dictionary);
            animationItem = AnimationUtils.loadAnimation( dictionariesFragment.getContext() , R.anim.anim_view_cell_dictionary);
            itemView.setOnClickListener(this);
        }

        public void startAnimation(){
            itemView.startAnimation(animationItem);
        }
        public void setValues(Dictionary dictionary){
            this.dictionary = dictionary ;
            nameDictionary.setText(dictionary.getNameDictionary());
            Glide.with(dictionariesFragment).
                    load(dictionary.getString_URI_photo()).
                    error(R.drawable.ic_error_image).
                    into(imageDictionary);
        }

        @Override
        public void onClick(View v) {
            Bundle args = new Bundle();
            args.putInt(OPEN_DICTIONARY , dictionaryArrayList.indexOf(dictionary));
            if(dictionary.getListWords()==null){
                DialogManager dialogManager = new DialogManager(dictionariesFragment.getContext());
                dialogManager.startLoadingDialog();
                new Thread(()->{
                    AppDatabase db = AppDatabase.getDatabase(dictionariesFragment.getContext());
                    dictionary.setListWords(
                            (ArrayList<Word>) db.getDaoWord().getAllWordsFromDictionary_db(dictionary.getIdDictionary())
                    );
                    NavHostFragment.findNavController(dictionariesFragment).navigate(R.id.action_dictionariesFragment_to_wordsFragment,args);
                    dialogManager.dismissDialog();
                }).start();
            }else {
                NavHostFragment.findNavController(dictionariesFragment).navigate(R.id.action_dictionariesFragment_to_wordsFragment,args);
            }
        }
    }
}

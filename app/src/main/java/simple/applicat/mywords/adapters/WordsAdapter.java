package simple.applicat.mywords.adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import simple.applicat.mywords.R;
import simple.applicat.mywords.data.AppDatabase;
import simple.applicat.mywords.data.Word;

import static simple.applicat.mywords.mainfragments.CreateOrEditWordFragment.EDIT_WORD;
import static simple.applicat.mywords.mainfragments.CreateOrEditWordFragment.ITSELF_WORD;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ViewCellNorm> {
    private final Fragment fragment;
    private final ArrayList<Word> wordArrayList ;
    private boolean expansion ;
    private int[] result ;
    public WordsAdapter(Fragment fragment, ArrayList<Word> wordArrayList , boolean expansion){
        this.fragment = fragment;
        this.wordArrayList = wordArrayList;
        this.expansion = expansion;
    }
    public WordsAdapter(Fragment fragment, ArrayList<Word> wordArrayList , int[] result ){
        this.fragment = fragment;
        this.wordArrayList = wordArrayList;
        this.result = result;
    }


    @NonNull
    @Override
    public ViewCellNorm onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(result == null){
            View v = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.style_view_cell_word , parent , false);
            return expansion ?  new ViewCellExpansion(v) : new  ViewCellNorm(v) ;
        }else {
            return new
                    ViewCellResult(LayoutInflater.from(parent.getContext()).inflate(R.layout.style_view_cell_word_result, parent ,false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewCellNorm holder, int position) {
        holder.setValues(position);
    }

    @Override
    public int getItemCount() {
        return wordArrayList.size();
    }

    class ViewCellNorm extends RecyclerView.ViewHolder {
        final TextView foreignWord ;
        final TextView nativeWord;
        final TextView transcription;
        final ImageView imageWord ;
        Word word ;
        public ViewCellNorm(@NonNull View itemView) {
            super(itemView);
            foreignWord = itemView.findViewById(R.id.foreign_word);
            nativeWord = itemView.findViewById(R.id.native_word);
            transcription = itemView.findViewById(R.id.transcription);
            imageWord = itemView.findViewById(R.id.image_word);

        }
        void setValues(int position){
            word = wordArrayList.get(position);
            Glide.with(fragment).load(word.getString_URI_photo()).error(R.drawable.ic_error_image).into(imageWord);
            foreignWord.setText(word.getForeignWord());
            nativeWord.setText(word.getNativeWord());
            transcription.setText(word.getTranscription());
        }

    }
    class ViewCellExpansion extends ViewCellNorm implements View.OnClickListener {
        public ViewCellExpansion(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            PopupMenu popupMenu = new PopupMenu(fragment.requireContext() , v);
            popupMenu.getMenuInflater().inflate(R.menu.menu_word, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.knowledge_level:
                            int strResource = word.getLevelOfKnowledge() <25 ? R.string.badly :
                                    word.getLevelOfKnowledge() <75 ? R.string.fine : R.string.excellent ;
                            Toast.makeText(fragment.requireContext() , strResource , Toast.LENGTH_SHORT).show();
                            return true ;
                        case R.id.edit_word:
                            Bundle args = new Bundle();
                            args.putBoolean(EDIT_WORD , true);
                            args.putParcelable(ITSELF_WORD, word);
                            NavHostFragment.findNavController(fragment).navigate(R.id.action_wordsFragment_to_createWordFragment , args);
                            return true;
                        case R.id.delete_word:
                            notifyItemRemoved(wordArrayList.indexOf(word));
                            wordArrayList.remove(word);
                            new Thread(()->{
                                AppDatabase.getDatabase(fragment.requireContext()).getDaoWord().deleteWord_db(word);
                            }).start();
                            return true;
                        default:
                            return false;
                    }
                }
            });
            popupMenu.show();
        }
    }

    class ViewCellResult extends ViewCellNorm{
        TextView numberOfPoints;
        public ViewCellResult(@NonNull View itemView) {
            super(itemView);
            numberOfPoints = itemView.findViewById(R.id.number_of_points);
        }

        @SuppressLint("SetTextI18n")
        @Override
        void setValues(int position) {
            super.setValues(position);
            numberOfPoints.setText(
                    fragment.getResources().getText(R.string.scores)+": " + result[position]);
        }
    }
}

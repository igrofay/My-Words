package simple.applicat.mywords.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import simple.applicat.mywords.R;
import simple.applicat.mywords.data.Dictionary;

public class DictionariesAdapter extends RecyclerView.Adapter<DictionariesAdapter.ViewCell>{
    private final ArrayList<Dictionary> dictionaries ;
    private final Context context ;
    public DictionariesAdapter(ArrayList<Dictionary> dictionaries , Context context) {
        this.dictionaries = dictionaries;
        this.context = context ;
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
        Dictionary dictionary = dictionaries.get(position);
        holder.setValues(dictionary);
        holder.startAnimation();
    }

    @Override
    public int getItemCount() {
        return dictionaries.size();
    }
    class ViewCell extends RecyclerView.ViewHolder{
        final TextView nameDictionary ;
        final ImageView imageDictionary;
        final Animation animationItem ;
        public ViewCell(@NonNull View itemView) {
            super(itemView);
            nameDictionary = itemView.findViewById(R.id.name_dictionary);
            imageDictionary = itemView.findViewById(R.id.image_dictionary);
            animationItem = AnimationUtils.loadAnimation( context , R.anim.anim_view_cell_dictionary);
        }
        public void startAnimation(){
            itemView.startAnimation(animationItem);
        }
        public void setValues(Dictionary dictionary){
            nameDictionary.setText(dictionary.getNameDictionary());
            Glide.with(context).
                    load(dictionary.getString_URI_photo()).
                    error(R.drawable.ic_error_image).
                    into(imageDictionary);
        }
    }
}

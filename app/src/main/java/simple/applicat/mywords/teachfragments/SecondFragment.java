package simple.applicat.mywords.teachfragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import simple.applicat.mywords.R;
import simple.applicat.mywords.TeachWordsActivity;
import simple.applicat.mywords.data.Word;
import simple.applicat.mywords.managers.DialogManager;
import simple.applicat.mywords.managers.DistributorManager;

public class SecondFragment extends Fragment {
    DistributorManager distributorManager ;
    Word question;
    TextView answer ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        distributorManager = ((TeachWordsActivity)requireActivity()).distributorManager ;
        question = distributorManager.getQuestion();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        setValues(view , distributorManager.getTypeOfQuestion());
        return view ;
    }
    void setValues(View view , boolean typeOfQuestion){
        TextView[] answerOptionsTextView = { view.findViewById(R.id.answer_one_fs) , view.findViewById(R.id.answer_two_fs) ,
                view.findViewById(R.id.answer_three_fs) ,view.findViewById(R.id.answer_four_fs)};
        @SuppressLint("UseCompatLoadingForDrawables")
        Drawable outline = getResources().getDrawable(R.drawable.background_view_outline);
        View.OnClickListener onClickListener = selectedTextView -> {
            if(answer!=null) answer.setForeground(null);
            answer = (TextView) selectedTextView;
            answer.setForeground(outline);
        };
        List<Word> answerOptions = distributorManager.getAnswerOptions() ;
        for (int i = 0; i < 4 ; i++) {
            TextView textView = answerOptionsTextView[i];
            textView.setOnClickListener(onClickListener);
            textView.setText(
                    typeOfQuestion ? answerOptions.get(i).getNativeWord() : answerOptions.get(i).getForeignWord()
            );
        }
        answerOptionsTextView[(int) Math.round(Math.random()*3)].setText(
                typeOfQuestion ? question.getNativeWord() : question.getForeignWord()
        );
        ImageView translatableImage = view.findViewById(R.id.translatable_image_word_fs);
        Glide.with(this).load(question.getString_URI_photo()).error(R.drawable.ic_error_image).into(translatableImage);
        Button changeData = view.findViewById(R.id.change_data_fs);
        changeData.setOnClickListener(v12 -> {
            TextView translatableText = view.findViewById(R.id.translatable_word_fs);
            translatableText.setText(
                    typeOfQuestion ? question.getForeignWord() : question.getNativeWord()
            );
            translatableText.setVisibility(View.VISIBLE);
            v12.setVisibility(View.GONE);
            translatableImage.setVisibility(View.INVISIBLE);
        });
        Button  reply = view.findViewById(R.id.reply_fs);
        reply.setOnClickListener(v1 -> {
            if(answer == null) Toast.makeText(requireContext() , R.string.select_answer_option , Toast.LENGTH_SHORT).show();
            else {
                boolean result = answer.getText().toString().equals(
                        typeOfQuestion ? question.getNativeWord() : question.getForeignWord()
                );
                distributorManager.saveResult(result);
                DialogManager.startResultBottomSheetDialog(this , distributorManager.getFragment() , result ,
                        typeOfQuestion ? question.getNativeWord() : question.getForeignWord()
                );
            }
        });
    }
}
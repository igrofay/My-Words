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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import simple.applicat.mywords.R;
import simple.applicat.mywords.TeachWordsActivity;
import simple.applicat.mywords.data.Word;
import simple.applicat.mywords.managers.DialogManager;
import simple.applicat.mywords.managers.DistributorManager;


public class FirstFragment extends Fragment {

    TextView answer ;
    DistributorManager distributorManager ;
    Word question;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        distributorManager = ((TeachWordsActivity)requireActivity()).distributorManager ;
        question = distributorManager.getQuestion();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first, container, false);
        setValues(v , distributorManager.getTypeOfQuestion());
        return v;
    }

    void setValues(View v , boolean typeOfQuestion){
        TextView[] answerOptionsTextView = { v.findViewById(R.id.answer_one_ff) , v.findViewById(R.id.answer_two_ff) ,
                v.findViewById(R.id.answer_three_ff) ,v.findViewById(R.id.answer_four_ff)};
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
        TextView  translatableText = v.findViewById(R.id.translatable_word_ff);
        translatableText.setText(
                typeOfQuestion ? question.getForeignWord() : question.getNativeWord()
        );
        answerOptionsTextView[(int) Math.round(Math.random()*3)].setText(
                typeOfQuestion ? question.getNativeWord() : question.getForeignWord()
        );
        Button  reply = v.findViewById(R.id.reply_ff);
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
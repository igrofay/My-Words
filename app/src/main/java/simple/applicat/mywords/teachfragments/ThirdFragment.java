package simple.applicat.mywords.teachfragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import simple.applicat.mywords.R;
import simple.applicat.mywords.TeachWordsActivity;
import simple.applicat.mywords.data.Word;
import simple.applicat.mywords.managers.DialogManager;
import simple.applicat.mywords.managers.DistributorManager;


public class ThirdFragment extends Fragment {
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
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        setValues(view , distributorManager.getTypeOfQuestion());
        return view ;
    }
    void setValues(View view , boolean typeOfQuestion){
        ImageView translatableImage = view.findViewById(R.id.translatable_image_word_ft);
        TextView translatableText = view.findViewById(R.id.translatable_word_ft);
        EditText answerInput = view.findViewById(R.id.answer_input_ft);
        Glide.with(this).load(question.getString_URI_photo()).error(R.drawable.ic_error_image).into(translatableImage);
        translatableText.setText(
                typeOfQuestion ? question.getForeignWord() : question.getNativeWord()
        );
        Button  reply = view.findViewById(R.id.reply_ft);
        reply.setOnClickListener(v -> {
            String text = answerInput.getText().toString();// Получаем ответ
            String[] strList = text.split(" "); //Парсим его в массив, убирая все пробелы
            if(strList.length != 0 && !text.equals("")) { // Проверяем на пустой ввод
                ArrayList<String> correctTextArrayList = new ArrayList<>();
                for (String str : strList) {
                    if (!str.equals("")) correctTextArrayList.add(str);
                }
                boolean result = String.join(" ", correctTextArrayList).toLowerCase().equals(
                        (typeOfQuestion ? question.getNativeWord() : question.getForeignWord()).toLowerCase()
                );
                distributorManager.saveResult(result);
                DialogManager.startResultBottomSheetDialog(this , distributorManager.getFragment() , result ,
                        typeOfQuestion ? question.getNativeWord() : question.getForeignWord()
                );
            }else Toast.makeText(requireContext() , R.string.text_cannot_be_empty , Toast.LENGTH_SHORT).show();
        });
    }
}
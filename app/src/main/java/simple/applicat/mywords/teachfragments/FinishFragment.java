package simple.applicat.mywords.teachfragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;

import simple.applicat.mywords.R;
import simple.applicat.mywords.TeachWordsActivity;
import simple.applicat.mywords.adapters.WordsAdapter;
import simple.applicat.mywords.data.Word;
import simple.applicat.mywords.managers.DistributorManager;

import static android.app.Activity.RESULT_OK;


public class FinishFragment extends Fragment {

    RecyclerView RC_Words ;
    int[] result ;
    ArrayList<Word> wordArrayList ;
    Button saveResult;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DistributorManager distributorManager = ((TeachWordsActivity)requireActivity()).distributorManager ;
        result = distributorManager.getResult();
        wordArrayList = distributorManager.getWordArrayList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_finish, container, false);
        RC_Words = v.findViewById(R.id.rc_result_word_view);
        saveResult = v.findViewById(R.id.save_result);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RC_Words.setAdapter(new WordsAdapter(this , wordArrayList , result));
        RC_Words.setLayoutManager(new LinearLayoutManager(requireContext()));
        saveResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < wordArrayList.size(); i++) {
                    wordArrayList.get(i).setLevelOfKnowledge(
                            wordArrayList.get(i).getLevelOfKnowledge()+result[i]
                    );
                }
                Intent result = new Intent();
                result.putParcelableArrayListExtra(TeachWordsActivity.LEARNED_WORDS , wordArrayList);
                requireActivity().setResult(RESULT_OK, result);
                requireActivity().finish();
            }
        });
    }
}
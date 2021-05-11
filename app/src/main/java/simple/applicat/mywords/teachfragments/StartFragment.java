package simple.applicat.mywords.teachfragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import simple.applicat.mywords.R;
import simple.applicat.mywords.TeachWordsActivity;
import simple.applicat.mywords.adapters.WordsAdapter;
import simple.applicat.mywords.data.Word;
import simple.applicat.mywords.managers.DistributorManager;

public class StartFragment extends Fragment {
    RecyclerView RC_Words ;
    Button startTeach ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_start, container, false);
        RC_Words = v.findViewById(R.id.rc_word_view);
        startTeach = v.findViewById(R.id.start_teach);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RC_Words.setAdapter(new WordsAdapter(this , ((TeachWordsActivity) requireActivity()).wordArrayList , false));
        RC_Words.setLayoutManager(new LinearLayoutManager(requireContext()));
        startTeach.setOnClickListener(v -> {
            int fragment = ((TeachWordsActivity)requireActivity()).distributorManager.getFragment();
            NavHostFragment.findNavController(this).navigate(fragment);
        });
    }
}
package simple.applicat.mywords.mainfragments;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import simple.applicat.mywords.R;
import simple.applicat.mywords.adapters.DictionariesAdapter;
import simple.applicat.mywords.objects.Dictionary;

public class DictionariesFragment extends Fragment {
    FloatingActionButton addNewDictionary;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dictionaries, container, false);
        addNewDictionary = view.findViewById(R.id.button_for_add_new_dictionaty);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Dictionary> dictionaries = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            dictionaries.add(new Dictionary(String.valueOf(i)));
        }
        RecyclerView rv = view.findViewById(R.id.recycle_view_dictionaries);
        rv.setAdapter(new DictionariesAdapter(dictionaries , getContext()));
        rv.setLayoutManager(new StaggeredGridLayoutManager(
                getScreenOrientation()
                ,StaggeredGridLayoutManager.VERTICAL));
        addNewDictionary.setOnClickListener(v -> {
            AddNewDictionaryBottomSheetDialogFragment addNewDictionaryBottomSheetDialogFragment =
                    new AddNewDictionaryBottomSheetDialogFragment();

            addNewDictionaryBottomSheetDialogFragment.show(getChildFragmentManager() , "");

        });
    }

    private int getScreenOrientation(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return 1;
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return 2;
        else
            return 1;
    }

}
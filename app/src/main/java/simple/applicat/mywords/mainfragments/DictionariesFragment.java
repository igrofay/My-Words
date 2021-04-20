package simple.applicat.mywords.mainfragments;


import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import simple.applicat.mywords.R;
import simple.applicat.mywords.adapters.DictionariesAdapter;
import simple.applicat.mywords.data.Dictionary;

public class DictionariesFragment extends Fragment {
    FloatingActionButton addNewDictionary;
    static ArrayList<Dictionary> dictionaries = new ArrayList<>();
    RecyclerView RC_Dictionaries;
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
        RC_Dictionaries = view.findViewById(R.id.recycle_view_dictionaries);
        RC_Dictionaries.setAdapter(new DictionariesAdapter(dictionaries , getContext()));
        RC_Dictionaries.setLayoutManager(new StaggeredGridLayoutManager(
                getScreenOrientation()
                ,StaggeredGridLayoutManager.VERTICAL));
        addNewDictionary.setOnClickListener(v -> {
           createDialogForAddNewDictionary(getChildFragmentManager());
        });

    }


    private void createDialogForAddNewDictionary(FragmentManager fragmentManager){
        AddNewDictionaryBottomSheetDialogFragment addNewDictionaryBSDF =
                new AddNewDictionaryBottomSheetDialogFragment();
        addNewDictionaryBSDF.show(fragmentManager , "");
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
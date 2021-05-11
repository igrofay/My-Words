package simple.applicat.mywords.mainfragments;


import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import simple.applicat.mywords.managers.DialogManager;
import simple.applicat.mywords.MainActivity;
import simple.applicat.mywords.R;
import simple.applicat.mywords.adapters.DictionariesAdapter;
import simple.applicat.mywords.data.Dictionary;


public class DictionariesFragment extends Fragment {
    FloatingActionButton addNewDictionary;
    public ArrayList<Dictionary> dictionaries;
    RecyclerView RC_Dictionaries;
    public DictionariesAdapter dictionariesAdapter ;
    Toolbar toolbar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         dictionaries = ((MainActivity) requireActivity()).dictionaries;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.
                inflate(R.layout.fragment_dictionaries, container, false);
        toolbar = view.findViewById(R.id.toolbar_dictionaries);
        addNewDictionary = view.findViewById(R.id.add_new_dictionaty);
        RC_Dictionaries = view.findViewById(R.id.recycle_view_dictionaries);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dictionariesAdapter = new DictionariesAdapter(dictionaries ,
                DictionariesFragment.this);
        RC_Dictionaries.setAdapter(dictionariesAdapter);
        RC_Dictionaries.setLayoutManager(new StaggeredGridLayoutManager(
                getScreenOrientation()
                ,StaggeredGridLayoutManager.VERTICAL));
        addNewDictionary.setOnClickListener(v -> {
           DialogManager.startAddDictionaryBottomDialog(this);
        });
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.settings_app:
                    NavHostFragment.findNavController(this).navigate(R.id.action_dictionariesFragment_to_settingsFragment);
                    return true;
                default:
                    return false;
            }
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
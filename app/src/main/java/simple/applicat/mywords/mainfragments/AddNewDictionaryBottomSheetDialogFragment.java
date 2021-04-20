package simple.applicat.mywords.mainfragments;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import simple.applicat.mywords.R;
import simple.applicat.mywords.data.Dictionary;

import static android.app.Activity.RESULT_OK;

public class AddNewDictionaryBottomSheetDialogFragment extends BottomSheetDialogFragment {
    static final int GALLERY_REQUEST = 1;
    Uri URI_SELECTED_IMAGE;
    ImageView viewNewImageDictionary;
    TextInputEditText viewNewNameDictionary;
    Button viewAddNewDictionary;
    ArrayList<Dictionary> dictionaries;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        return bottomSheetDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.bottom_sheet_add_new_dictionary,container,false);
        viewNewImageDictionary = v.findViewById(R.id.new_image_new_dictionary);
        viewNewNameDictionary = v.findViewById(R.id.new_name_new_dictionary);
        viewAddNewDictionary = v.findViewById(R.id.b_add_new_dictionary);
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewNewImageDictionary.setOnClickListener(v->{
            Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        });
        viewAddNewDictionary.setOnClickListener(v->{
            if(viewNewNameDictionary.getText().toString().equals("")){
                Toast.makeText(getContext() , R.string.input_name_dictionary, Toast.LENGTH_SHORT).show();
            }else {
                addNewDictionary();
                dismiss();
            }
        });
    }
    public void addNewDictionary(){
        Dictionary dictionary = new Dictionary(
                viewNewNameDictionary.getText().toString(),
                URI_SELECTED_IMAGE!=null ? URI_SELECTED_IMAGE.toString() : ""
        );
        ((DictionariesFragment) getParentFragment()).dictionaries.add(dictionary);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == GALLERY_REQUEST){
            URI_SELECTED_IMAGE = data.getData();
            Glide.with(this).
                    load(URI_SELECTED_IMAGE).
                    error(R.drawable.ic_error_image).
                    into(viewNewImageDictionary);
        }
    }

}

package simple.applicat.mywords.managers;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import simple.applicat.mywords.MainActivity;
import simple.applicat.mywords.R;
import simple.applicat.mywords.data.AppDatabase;
import simple.applicat.mywords.data.Dictionary;
import simple.applicat.mywords.mainfragments.DictionariesFragment;
import simple.applicat.mywords.mainfragments.WordsFragment;

import static android.app.Activity.RESULT_OK;
import static simple.applicat.mywords.mainfragments.WordsFragment.OPEN_DICTIONARY;

public class DialogManager {
    private final Context contextDM;
    private AlertDialog dialog;

    public DialogManager(Context context) {
        this.contextDM = context;
    }

    public static void startExitDialog(AppCompatActivity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.you_want_exit);
        builder.setMessage(R.string.explanation_of_exit);
        builder.setPositiveButton(R.string.go_out, (dialog, which) -> activity.finish());
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());
        builder.setCancelable(false);
        builder.show();
    }
    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(contextDM);
        builder.setView(R.layout.dialog_loading_words);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }

    public static void startInputDialog(View v , int resource ){
        Context context = v.getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_input_text , null , false);
        builder.setView(view);
        TextView title = view.findViewById(R.id.title_dialog); title.setText(resource);
        EditText input = view.findViewById(R.id.input_text_dialog);
        builder.setPositiveButton(R.string.save, (dialog, which) -> {
            String text = input.getText().toString();
            String[] strList = text.split(" ");
            if(strList.length != 0 && !text.equals("")){
                ArrayList<String> string = new ArrayList<>();
                for (String str:  strList) {
                    if(!str.equals("")) string.add(str);
                }
                ((TextView) v).setText( String.join(" ", string) );
                dialog.dismiss();
            }else Toast.makeText(context , R.string.text_cannot_be_empty , Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog .dismiss() );
        builder.setCancelable(false);
        builder.show();
    }

    public static void startDeleteDialog(ArrayList<Dictionary> dictionaries , int position , Fragment fragment){
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
        builder.setCancelable(false)
                .setTitle(R.string.delete_dictionary).setMessage(R.string.explanation_of_delete)
                .setPositiveButton(R.string.delete, (dialog, which) -> {
                    Dictionary dictionary = dictionaries.remove(position);
                    new Thread(()->{
                        AppDatabase db = AppDatabase.getDatabase(fragment.getContext());
                        db.getDaoWord().deleteWords_db(dictionary.getListWords());
                        db.getDaoDictionary().deleteDictionary_db(dictionary);
                    }).start();
                    NavHostFragment.findNavController(fragment).popBackStack();
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog .dismiss());
        builder.show();
    }

    public static void startAddDictionaryBottomDialog(DictionariesFragment fragment){
        CustomBottomSheetDialogMain customBottomSheetDialog = new CustomBottomSheetDialogMain();
        customBottomSheetDialog.show(fragment.getChildFragmentManager() , "TAG");
    }

    public static void startEditDictionaryBottomDialog(WordsFragment fragment , int position){
        CustomBottomSheetDialogMain customBottomSheetDialog = new CustomBottomSheetDialogMain();
        Bundle bundle = new Bundle();
        bundle.putInt(OPEN_DICTIONARY , position);
        customBottomSheetDialog.setArguments(bundle);
        customBottomSheetDialog.show(fragment.getChildFragmentManager() , "TAG");
    }

    public static class CustomBottomSheetDialogMain extends BottomSheetDialogFragment {
        static final int GALLERY_REQUEST = 1;
        Uri URI_SELECTED_IMAGE;
        ImageView imageDictionary;
        TextInputEditText nameDictionary;
        Button save;
        Dictionary dictionary ;
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if(getArguments()!=null){
                dictionary = ((MainActivity)requireActivity()).dictionaries.get(getArguments().getInt(OPEN_DICTIONARY));
            }
        }
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
            bottomSheetDialog.setDismissWithAnimation(true);
            bottomSheetDialog.setCanceledOnTouchOutside(true);
            bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
            return bottomSheetDialog;
        }
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v =  inflater.inflate(R.layout.bottom_sheet_add_or_edit_dictionary,container,false);
            imageDictionary = v.findViewById(R.id.new_image_new_dictionary);
            nameDictionary = v.findViewById(R.id.new_name_new_dictionary);
            save = v.findViewById(R.id.b_add_new_dictionary);
            return v ;
        }
        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            imageDictionary.setOnClickListener(v->{
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            });
            if(dictionary !=null){
                save.setText(R.string.save);
                save.setOnClickListener(v-> onChickEdit());
                nameDictionary.setText(dictionary.getNameDictionary());
                Glide.with(this).
                        load(dictionary.getString_URI_photo()).
                        error(R.drawable.ic_error_image).
                        into(imageDictionary);
            }else {
                save.setOnClickListener(v-> onChickAdd());
            }
        }
        void onChickAdd(){
            String text = nameDictionary.getText().toString();
            String[] strList = text.split(" ");
            if(text.equals("") || strList.length == 0){
                Toast.makeText(getContext() , R.string.input_name_dictionary, Toast.LENGTH_SHORT).show();
            }else {
                ArrayList<String> string = new ArrayList<>();
                for (String str:  strList) {
                    if(!str.equals("")) string.add(str);
                }
                Dictionary newDictionary = new Dictionary(
                        String.join(" ", string),
                        URI_SELECTED_IMAGE!=null ? URI_SELECTED_IMAGE.toString() : ""
                );
                new Thread(()->{
                    long id = AppDatabase.
                            getDatabase(getContext()).
                            getDaoDictionary().
                            insertDictionary_db(newDictionary);
                    newDictionary.setIdDictionary(id);
                }).start();
                DictionariesFragment df = (DictionariesFragment) requireParentFragment();
                df.dictionaries.add(newDictionary);
                df.dictionariesAdapter.notifyItemInserted(df.dictionaries.size());
                dismiss();
            }
        }
        void onChickEdit(){
            String text = nameDictionary.getText().toString();
            String[] strList = text.split(" ");
            if(text.equals("") || strList.length == 0){
                Toast.makeText(getContext() , R.string.input_name_dictionary, Toast.LENGTH_SHORT).show();
            }else {
                ArrayList<String> string = new ArrayList<>();
                for (String str:  strList) {
                    if(!str.equals("")) string.add(str);
                }
                dictionary.setNameDictionary(String.join(" ", string));
                dictionary.setString_URI_photo(
                        URI_SELECTED_IMAGE != null ? URI_SELECTED_IMAGE.toString() : dictionary.getString_URI_photo()
                );
                new Thread(()->{
                    AppDatabase.getDatabase(getContext()).
                            getDaoDictionary().updateDictionary_db(dictionary);
                }).start();
               ((WordsFragment)getParentFragment()).setValuesToolbar();
                dismiss();
            }
        }
        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == RESULT_OK && requestCode == GALLERY_REQUEST){
                URI_SELECTED_IMAGE = data.getData();
                Glide.with(this).
                        load(URI_SELECTED_IMAGE).
                        error(R.drawable.ic_error_image).
                        into(imageDictionary);
            }
        }
    }


    public static void startResultBottomSheetDialog(Fragment fragment , int nexFragment , boolean result , String correctAnswer){
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.requireContext());
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        View view = LayoutInflater.from(fragment.requireContext()).
                inflate(R.layout.dialog_result , null , false);
        TextView resultText = view.findViewById(R.id.result);
        resultText.setText( result ? R.string.right : R.string.wrong);
        TextView correctAnswerText = view.findViewById(R.id.correct_answer);
        correctAnswerText.setText(correctAnswer);
        view.setBackgroundColor(
                fragment.requireContext().getColor(
                        result ? R.color.green : R.color.red
                )
        );
        view.setOnClickListener(v -> {
            NavHostFragment.findNavController(fragment).navigate(nexFragment);
            alertDialog.dismiss();
        });
        alertDialog.setView(view);
        alertDialog.show();
    }
}

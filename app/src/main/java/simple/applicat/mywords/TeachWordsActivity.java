package simple.applicat.mywords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;

import java.util.ArrayList;

import simple.applicat.mywords.data.Word;
import simple.applicat.mywords.managers.DialogManager;
import simple.applicat.mywords.managers.DistributorManager;


public class TeachWordsActivity extends AppCompatActivity {
    public static String WORDS_FOR_LEARNING = "WORDS_FOR_LEARNING";
    public static String LEARNED_WORDS = "LEARNED_WORDS" ;
    public ArrayList<Word> wordArrayList;
    public ProgressBar progress;
    public DistributorManager distributorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        wordArrayList = getIntent().getParcelableArrayListExtra(WORDS_FOR_LEARNING);
        setContentView(R.layout.activity_teach_words);
        progress = findViewById(R.id.learning_progress);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int repetitions = Integer.parseInt(sharedPreferences.getString("numberOfRepetitions", "2"));
        distributorManager = new DistributorManager( wordArrayList , repetitions  , progress);
        distributorManager.startDistributionData();
    }

    @Override
    public void onBackPressed() {
        DialogManager.startExitDialog(this);
    }
}
package simple.applicat.mywords.managers;

import android.util.Log;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import simple.applicat.mywords.R;
import simple.applicat.mywords.data.Word;

public class DistributorManager {
    private ArrayList<Word> wordArrayList;
    private int repetitions ;
    private Random random ;
    private int[] fragments;
    private Word[] questions;
    private int[] result ;
    private int position ;
    private int sizeTest ;
    private ProgressBar progressBar ;
    public DistributorManager(ArrayList<Word> wordArrayList , int repetitions , ProgressBar progressBar) {
        this.wordArrayList =wordArrayList;
        this.repetitions = repetitions;
        random = new Random();
        this.progressBar = progressBar ;
    }
    public void  startDistributionData(){
        sizeTest = repetitions*wordArrayList.size() ;
        fragments = new int[sizeTest];
        questions = new Word[sizeTest];
        result = new int[sizeTest];
        int positionArray = 0 ;
        for (int i = 1; i <= repetitions ; i++) {
            Collections.shuffle(wordArrayList);
            for (int j =0; j < wordArrayList.size(); j++ , positionArray++ ) {
                questions[positionArray] = wordArrayList.get(j);
                int x = random.nextInt(3);
                fragments [positionArray] =( x==0 ? R.id.firstFragment : x==1 ? R.id.thirdFragment : R.id.secondFragment );
            }
        }

    }
    public int getFragment(){
        if(position == sizeTest) return R.id.finishFragment;
        return fragments[position];
    }
    public Word getQuestion(){
        return questions[position];
    }
    void enlarge(){
        position++;
        progressBar.setProgress(Math.round(100.0f/sizeTest)*position);
    }
    public void saveResult(boolean ok){
        result[position] = ok ? 1 : -1 ;
        enlarge();
    }

    public List<Word> getAnswerOptions(){
        Collections.shuffle(wordArrayList);
        List<Word> answerOptions = new ArrayList<>(wordArrayList);
        answerOptions.remove( getQuestion() );
        Collections.shuffle(answerOptions);
        return answerOptions ;
    }
    public boolean getTypeOfQuestion(){
        return random.nextBoolean();
        //true -> иностранный на родной
        //false -> родной на иностранный
    }
    public int[] getResult(){
        int[] calculatedResult = new int[wordArrayList.size()];
        for (int i = 0; i < sizeTest; i++) {
            int x = wordArrayList.indexOf(questions[i]);
            calculatedResult[x] = result[i]+calculatedResult[x];
        }
        return calculatedResult ;
    }
    public ArrayList<Word> getWordArrayList(){
        return  this.wordArrayList ;
    }
}

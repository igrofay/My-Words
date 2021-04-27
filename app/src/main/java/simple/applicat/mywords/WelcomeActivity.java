package simple.applicat.mywords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import simple.applicat.mywords.data.AppDatabase;
import simple.applicat.mywords.data.Dictionary;
import simple.applicat.mywords.helper.IntentHelper;

public class WelcomeActivity extends AppCompatActivity{
    ArrayList<Dictionary> dictionaries;
    boolean finishActivity = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getAllDictionary(getBaseContext());
        new Handler().postDelayed(() -> {
            if(finishActivity){
                finishActivity();
            }else {
                finishActivity = true;
            }
        }, 3000);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setAnimations();
    }
    void setAnimations(){
        ImageView loginIcon = findViewById(R.id.login_icon);
        TextView loginText = findViewById(R.id.login_text);
        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);
        Animation animationImView2 = AnimationUtils.loadAnimation(this, R.anim.anim_login_view_2);
        Animation animationImView1 = AnimationUtils.loadAnimation(this, R.anim.anim_login_view_1);
        Animation animationIcon = AnimationUtils.loadAnimation(this, R.anim.anim_login_icon);
        Animation animationText = AnimationUtils.loadAnimation(this, R.anim.anim_login_text);
        imageView2.startAnimation(animationImView2);
        imageView1.startAnimation(animationImView1);
        loginText.startAnimation(animationText);
        loginIcon.startAnimation(animationIcon);
    }


    void getAllDictionary(Context context){
        new Thread(()->{
            AppDatabase db = AppDatabase.getDatabase(context);
            dictionaries = (ArrayList<Dictionary>) db.getDaoDictionary().getAllDictionaries_db();
            if(finishActivity){
                finishActivity();
            }else {
                finishActivity = true ;
            }
        }).start();
    }
    void finishActivity(){
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        IntentHelper helper = IntentHelper.getINSTANCE();
        helper.putDictionaris(dictionaries);
        startActivity(intent);
        finish();
    }
}
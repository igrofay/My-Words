package simple.applicat.mywords;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;


import simple.applicat.mywords.data.Dictionary;


public class MainActivity extends AppCompatActivity {
    public ArrayList<Dictionary> dictionaries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dictionaries = getIntent().getParcelableArrayListExtra(WelcomeActivity.DICTIONARIES);
        setContentView(R.layout.activity_main);
    }
}
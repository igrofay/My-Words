package simple.applicat.mywords;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

import simple.applicat.mywords.data.Dictionary;
import simple.applicat.mywords.helper.IntentHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
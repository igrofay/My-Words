package simple.applicat.mywords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this , MainActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
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
}
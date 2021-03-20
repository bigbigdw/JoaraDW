package Bigbigdw.JoaraDW.Etc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import Bigbigdw.JoaraDW.Main.Main;
import Bigbigdw.JoaraDW.R;

public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new android.os.Handler().postDelayed(
                () -> {
                    Intent intent = new Intent(this, Main.class);
                    startActivity(intent);
                },
                2000);

    }
}

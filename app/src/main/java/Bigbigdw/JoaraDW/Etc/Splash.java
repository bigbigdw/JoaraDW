package Bigbigdw.JoaraDW.Etc;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import Bigbigdw.JoaraDW.R;

public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        startLoading();
    }
    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(this::finish, 2000);
    }
}

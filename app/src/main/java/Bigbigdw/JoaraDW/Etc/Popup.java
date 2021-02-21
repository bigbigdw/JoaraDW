package Bigbigdw.JoaraDW.Etc;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;

import java.util.Objects;

import Bigbigdw.JoaraDW.R;


public class Popup extends Dialog {

    private View.OnClickListener mBtnLeftListener;
    private View.OnClickListener mBtnRightListener;

    public Popup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        Objects.requireNonNull(getWindow()).setAttributes(layoutParams);

        setContentView(R.layout.popup);

        //셋팅
        Button BtnLeft = findViewById(R.id.BtnLeft);
        Button BtnRight = findViewById(R.id.BtnRight);

        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)
        BtnLeft.setOnClickListener(mBtnLeftListener);
        BtnRight.setOnClickListener(mBtnRightListener);
    }

    //생성자 생성
    public Popup(@NonNull Context context, View.OnClickListener BtnLeftListener, View.OnClickListener BtnRightListener) {
        super(context);
        this.mBtnLeftListener = BtnLeftListener;
        this.mBtnRightListener = BtnRightListener;
    }
}

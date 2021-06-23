package bigbigdw.joaradw.book_viewer;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;

import java.util.Objects;

import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.R;

public class ViewerSetting extends AppCompatActivity {

    Intent intent;
    ColorPicker picker;
    ColorPicker pickerText;
    LinearLayout bgApply;
    TextView textEditBg;
    TextView textEditText;
    TextView themeEdit;
    TextView fontType;
    TextView exText;
    TextView indentText;
    TextView textContinue;
    TextView textVolume;
    ImageView btnFontLeft;
    ImageView btnFontRight;
    LinearLayout textApply;
    LinearLayout colorPickerBG;
    LinearLayout colorPickerText;
    LinearLayout themeApply;
    LinearLayout viewerSettingTheme;
    LinearLayout exBg;
    SeekBar textSizeSeekBar;
    SeekBar textSpaceSeekBar;
    SwitchCompat switchIndent;
    SwitchCompat swtichConitnue;
    SwitchCompat switchVolume;
    String normal = "기본";
    String bold = "굵게";
    String italic = "이탤릭";
    String boldItalic = "굵은 이탤릭";
    Button btnDefault;
    Button btnApply;
    RadioButton scrollScroll;
    RadioButton scrollUpDown;
    RadioButton scrollLeftRight;
    JOARADW myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer_setting);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        intent = getIntent();

        bgApply = findViewById(R.id.BgApply);
        textEditBg = findViewById(R.id.TextEditBg);
        colorPickerBG = findViewById(R.id.ColorPickerBG);
        textEditText = findViewById(R.id.TextEditText);
        colorPickerText = findViewById(R.id.ColorPickerText);
        textApply = findViewById(R.id.TextApply);
        themeEdit = findViewById(R.id.ThemeEdit);
        themeApply = findViewById(R.id.ThemeApply);
        viewerSettingTheme = findViewById(R.id.ViewerSettingTheme);
        exBg = findViewById(R.id.ExBg);
        exText = findViewById(R.id.ExText);
        btnFontLeft = findViewById(R.id.Btn_FontLeft);
        btnFontRight = findViewById(R.id.Btn_FontRight);
        fontType = findViewById(R.id.FontType);
        textSizeSeekBar = findViewById(R.id.TextSizeSeekBar);
        textSpaceSeekBar = findViewById(R.id.TextSpaceSeekBar);
        switchIndent = findViewById(R.id.SwitchIndent);
        indentText = findViewById(R.id.IndentText);
        textContinue = findViewById(R.id.TextContinue);
        swtichConitnue = findViewById(R.id.SwtichConitnue);
        switchVolume = findViewById(R.id.SwitchVolume);
        textVolume = findViewById(R.id.TextVolume);
        btnDefault = findViewById(R.id.BtnDefault);
        btnApply = findViewById(R.id.BtnApply);
        scrollScroll = findViewById(R.id.ScrollScroll);
        scrollUpDown = findViewById(R.id.ScrollUpDown);
        scrollLeftRight = findViewById(R.id.ScrollLeftRight);

        picker = findViewById(R.id.picker);
        SVBar svBar = findViewById(R.id.svbar);
        OpacityBar opacityBar = findViewById(R.id.opacitybar);
        SaturationBar saturationBar = findViewById(R.id.saturationbar);
        ValueBar valueBar = findViewById(R.id.valuebar);
        picker.addSVBar(svBar);
        picker.addOpacityBar(opacityBar);
        picker.addSaturationBar(saturationBar);
        picker.addValueBar(valueBar);

        pickerText = findViewById(R.id.pickerText);
        SVBar svBarText = findViewById(R.id.svbarText);
        pickerText.addSVBar(svBarText);

        myApp = (JOARADW) getApplicationContext();

        setLayout();
    }

    public void setLayout() {

        exText.setTextColor(myApp.getTextColor());
        textApply.setBackgroundColor(myApp.getTextColor());
        pickerText.setOldCenterColor(myApp.getTextColor());
        pickerText.setNewCenterColor(myApp.getTextColor());
        if(myApp.getViewerBGType().equals("BG")){
            exBg.setBackgroundColor(myApp.getViewerBG());
            bgApply.setBackgroundColor(myApp.getViewerBG());
            picker.setOldCenterColor(myApp.getViewerBG());
            picker.setNewCenterColor(myApp.getViewerBG());
        } else {
            exBg.setBackgroundResource(myApp.getViewerBGTheme());
        }
        fontType.setText(myApp.getTextType());
        changeFontType();
        textSizeSeekBar.setProgress(myApp.getTextSize());
        textSizeOption(myApp.getTextSize());
        textSpaceOption(myApp.getTextLineSpace());
        textSpaceSeekBar.setProgress(myApp.getTextLineSpace());


        switchVolume.setOnClickListener(v -> {
            if (switchVolume.isChecked()) {
                textVolume.setText("볼륨버튼으로 스크롤을 제어합니다.");
            } else {
                textVolume.setText("볼륨버튼으로 스크롤을 제어하지 않습니다.");
            }
        });

        swtichConitnue.setOnClickListener(v -> {
            if (swtichConitnue.isChecked()) {
                textContinue.setText("연속보기를 적용합니다.");
            } else {
                textContinue.setText("연속보기를 적용하지 않습니다.");
            }
        });

        switchIndent.setOnClickListener(v -> {
            if (switchIndent.isChecked()) {
                indentText.setText("들여쓰기를 적용합니다.");
            } else {
                indentText.setText("들여쓰기를 적용하지 않습니다.");
            }
        });


        textSpaceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSpaceOption(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("TextSpaceSeekBar", "Touch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("TextSpaceSeekBar", "Change");
            }
        });

        textSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSizeOption(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("TextSizeSeekBar", "Touch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("TextSizeSeekBar", "Change");
            }
        });

        btnFontLeft.setOnClickListener(v -> changeFontTypeBtn("LEFT"));
        btnFontRight.setOnClickListener(v -> changeFontTypeBtn("RIGHT"));

        picker.setOldCenterColor(-1);
        pickerText.setOldCenterColor(-16777216);

        btnApply.setOnClickListener(v -> {
            myApp.setTextType(fontType.getText().toString());
            myApp.setViewerBG(picker.getColor());
            myApp.setTextColor(pickerText.getColor());
            myApp.setTextLineSpace(textSpaceSeekBar.getProgress());
            myApp.setTextSize(textSizeSeekBar.getProgress());
            finish();
        });

        btnDefault.setOnClickListener(v -> {
            exBg.setBackgroundColor(-1);
            exText.setTextColor(-16777216);
            exText.setTypeface(null, Typeface.NORMAL);
            picker.setOldCenterColor(-1);
            pickerText.setOldCenterColor(-16777216);
            picker.setNewCenterColor(-1);
            pickerText.setNewCenterColor(-16777216);
            bgApply.setBackgroundColor(-1);
            textApply.setBackgroundColor(-16777216);
            switchVolume.setChecked(false);
            swtichConitnue.setChecked(false);
            switchIndent.setChecked(false);
            textSizeSeekBar.setProgress(0);
            textSpaceSeekBar.setProgress(0);
            fontType.setText(normal);
            indentText.setText("들여쓰기를 적용하지 않습니다.");
            textContinue.setText("연속보기를 적용하지 않습니다.");
            textVolume.setText("볼륨버튼으로 스크롤을 제어하지 않습니다.");
            scrollScroll.setChecked(true);
            scrollUpDown.setChecked(false);
            scrollLeftRight.setChecked(false);
            myApp.setViewerBGTheme(R.drawable.viewer_full_bg01);
            myApp.setTextType("기본");
            myApp.setViewerBG(-1);
            myApp.setTextColor(-16777216);
            myApp.setViewerBGType("BG");
        });

        textEditBg.setOnClickListener(v -> {
            if (textEditBg.getText().equals("적용")) {
                int color = picker.getColor();
                bgApply.setBackgroundColor(color);
                exBg.setBackgroundColor(color);
                textEditBg.setText("변경");
                colorPickerBG.setVisibility(View.GONE);
                myApp.setViewerBGType("BG");
                picker.setOldCenterColor(picker.getColor());
            } else {
                textEditBg.setText("적용");
                colorPickerBG.setVisibility(View.VISIBLE);
            }
        });

        textEditText.setOnClickListener(v -> {
            if (textEditText.getText().equals("적용")) {
                int color = pickerText.getColor();
                textApply.setBackgroundColor(color);
                exText.setTextColor(color);
                textEditText.setText("변경");
                colorPickerText.setVisibility(View.GONE);
                pickerText.setOldCenterColor(picker.getColor());
            } else {
                textEditText.setText("적용");
                colorPickerText.setVisibility(View.VISIBLE);
            }
        });

        themeEdit.setOnClickListener(v -> {
            if (themeEdit.getText().equals("적용")) {
                themeEdit.setText("변경");
                myApp.setViewerBGType("BG_THEME");
                viewerSettingTheme.setVisibility(View.GONE);
            } else {
                themeEdit.setText("적용");
                viewerSettingTheme.setVisibility(View.VISIBLE);
            }
        });
    }

    public void checkScroll(View view) {
        if (view.getId() == R.id.ScrollScroll) {
            scrollUpDown.setChecked(false);
            scrollLeftRight.setChecked(false);
        } else if (view.getId() == R.id.ScrollUpDown) {
            scrollScroll.setChecked(false);
            scrollLeftRight.setChecked(false);
        } else if (view.getId() == R.id.ScrollLeftRight) {
            scrollScroll.setChecked(false);
            scrollUpDown.setChecked(false);
        }
    }

    public void onClickBgTheme(View view) {
        if (view.getId() == R.id.Viewer_Theme1) {
            changeTheme(R.drawable.viewer_full_bg01, R.drawable.viewer_full_bg01);
        } else if (view.getId() == R.id.Viewer_Theme2) {
            changeTheme(R.drawable.viewer_full_bg02, R.drawable.viewer_full_bg02);
        } else if (view.getId() == R.id.Viewer_Theme3) {
            changeTheme(R.drawable.viewer_full_bg03, R.drawable.viewer_full_bg03);
        } else if (view.getId() == R.id.Viewer_Theme4) {
            changeTheme(R.drawable.viewer_full_bg04, R.drawable.viewer_full_bg04);
        } else if (view.getId() == R.id.Viewer_Theme5) {
            changeTheme(R.drawable.viewer_full_bg05, R.drawable.viewer_full_bg05);
        } else if (view.getId() == R.id.Viewer_Theme6) {
            changeTheme(R.drawable.viewer_full_bg06, R.drawable.viewer_full_bg06);
        } else if (view.getId() == R.id.Viewer_Theme7) {
            changeTheme(R.drawable.viewer_full_bg07, R.drawable.viewer_full_bg07);
        } else if (view.getId() == R.id.Viewer_Theme8) {
            changeTheme(R.drawable.viewer_full_bg08, R.drawable.viewer_full_bg08);
        } else if (view.getId() == R.id.Viewer_Theme9) {
            changeTheme(R.drawable.viewer_full_bg09, R.drawable.viewer_full_bg09);
        } else if (view.getId() == R.id.Viewer_Theme10) {
            changeTheme(R.drawable.viewer_full_bg10, R.drawable.viewer_full_bg10);
        } else if (view.getId() == R.id.Viewer_Theme11) {
            changeTheme(R.drawable.viewer_full_bg11, R.drawable.viewer_full_bg11);
        } else if (view.getId() == R.id.Viewer_Theme12) {
            changeTheme(R.drawable.viewer_full_bg12, R.drawable.viewer_full_bg12);
        } else if (view.getId() == R.id.Viewer_Theme13) {
            changeTheme(R.drawable.viewer_full_bg13, R.drawable.viewer_full_bg13);
        } else if (view.getId() == R.id.Viewer_Theme14) {
            changeTheme(R.drawable.viewer_full_bg14, R.drawable.viewer_full_bg14);
        } else if (view.getId() == R.id.Viewer_Theme15) {
            changeTheme(R.drawable.viewer_full_bg15, R.drawable.viewer_full_bg15);
        } else if (view.getId() == R.id.Viewer_Theme16) {
            changeTheme(R.drawable.viewer_full_bg16, R.drawable.viewer_full_bg16);
        } else if (view.getId() == R.id.Viewer_Theme17) {
            changeTheme(R.drawable.viewer_full_bg17, R.drawable.viewer_full_bg17);
        } else if (view.getId() == R.id.Viewer_Theme18) {
            changeTheme(R.drawable.viewer_full_bg18, R.drawable.viewer_full_bg18);
        }
    }

    public void textSpaceOption(int progress) {
        if (progress == 0) {
            exText.setLineSpacing(0, 1.0f);
        } else if (progress == 1) {
            exText.setLineSpacing(0, 1.2f);
        } else if (progress == 2) {
            exText.setLineSpacing(0, 1.4f);
        } else if (progress == 3) {
            exText.setLineSpacing(0, 1.6f);
        } else if (progress == 4) {
            exText.setLineSpacing(0, 1.8f);
        } else if (progress == 5) {
            exText.setLineSpacing(0, 2.0f);
        }
    }

    public void textSizeOption(int progress) {
        if (progress == 0) {
            exText.setTextSize(14);
        } else if (progress == 1) {
            exText.setTextSize(16);
        } else if (progress == 2) {
            exText.setTextSize(18);
        } else if (progress == 3) {
            exText.setTextSize(20);
        } else if (progress == 4) {
            exText.setTextSize(22);
        } else if (progress == 5) {
            exText.setTextSize(24);
        }
    }


    public void changeFontTypeBtn(String direction) {
        if (fontType.getText().equals(normal)) {
            changeFontBtnDir(direction, boldItalic, bold);
            changeFontType();
        } else if (fontType.getText().equals(bold)) {
            changeFontBtnDir(direction, normal, italic);
            changeFontType();
        } else if (fontType.getText().equals(italic)) {
            changeFontBtnDir(direction, bold, boldItalic);
            changeFontType();
        } else if (fontType.getText().equals(boldItalic)) {
            changeFontBtnDir(direction, italic, normal);
            changeFontType();
        }
    }

    public void changeFontBtnDir(String direction, String left, String right) {
        if (direction.equals("LEFT")) {
            fontType.setText(left);
        } else {
            fontType.setText(right);
        }
    }

    public void changeFontType() {
        if (fontType.getText().equals(normal)) {
            exText.setTypeface(null, Typeface.NORMAL);
        } else if (fontType.getText().equals(bold)) {
            exText.setTypeface(exText.getTypeface(), Typeface.BOLD);
        } else if (fontType.getText().equals(italic)) {
            exText.setTypeface(exText.getTypeface(), Typeface.ITALIC);
        } else if (fontType.getText().equals(boldItalic)) {
            exText.setTypeface(exText.getTypeface(), Typeface.BOLD_ITALIC);
        }
    }

    public void changeTheme(int apply, int ex) {
        themeApply.setBackgroundResource(apply);
        exBg.setBackgroundResource(ex);
        myApp.setViewerBGTheme(ex);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            myApp.setViewerBGTheme(myApp.getViewerBGTheme());
            myApp.setTextType(myApp.getTextType());
            myApp.setViewerBG(myApp.getViewerBG());
            myApp.setTextColor(myApp.getTextColor());
            myApp.setViewerBGType(myApp.getViewerBGType());
            myApp.setTextSize(myApp.getTextSize());
            myApp.setTextLineSpace(myApp.getTextLineSpace());
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

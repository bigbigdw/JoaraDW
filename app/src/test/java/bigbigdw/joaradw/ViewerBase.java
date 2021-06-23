package bigbigdw.joaradw;

import android.graphics.Typeface;
import android.widget.TextView;

public class ViewerBase {
    String normal = "기본";
    String bold = "굵게";
    String italic = "이탤릭";
    String boldItalic = "굵은 이탤릭";

    public void textSpaceOption(int progress, TextView exText) {
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

    public void textSizeOption(int progress, TextView exText) {
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

    public void changeFontType(TextView fontType , TextView exText) {
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
}

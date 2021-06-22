package bigbigdw.joaradw.test;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import androidx.appcompat.app.AppCompatActivity;

import bigbigdw.joaradw.R;


public class ActivityTest extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        editText = findViewById(R.id.edit_text);
        textView = findViewById(R.id.text_view);
        button = findViewById(R.id.btn);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textView.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        button.setOnClickListener(v -> Toast.makeText(getApplicationContext(), textView.getText(), Toast.LENGTH_SHORT).show());
    }

}
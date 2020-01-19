package com.example.aiub.playwithnumberexample2;

import android.os.Bundle;
import android.support.p003v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText editText;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0273R.layout.activity_main);
        final int appNumbr = new Random().nextInt(10) + 1;
        this.editText = (EditText) findViewById(C0273R.C0275id.eidtTextId);
        this.button = (Button) findViewById(C0273R.C0275id.buttonId);
        this.button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int editTextNumber = Integer.parseInt(MainActivity.this.editText.getText().toString().trim());
                int i = appNumbr;
                if (editTextNumber < i) {
                    Toast.makeText(MainActivity.this, "Enter higer Number ", 0).show();
                } else if (editTextNumber > i) {
                    Toast.makeText(MainActivity.this, "Enter Lower Number ", 0).show();
                } else {
                    Toast.makeText(MainActivity.this, "Congrats ! ", 0).show();
                }
            }
        });
    }
}

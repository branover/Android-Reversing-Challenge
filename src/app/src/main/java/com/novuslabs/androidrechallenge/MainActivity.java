package com.novuslabs.androidrechallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public boolean CheckFlagOne(String flag) {
        String compareString = getString(R.string.s3cr3t_fl4g);
        return flag.equals(compareString);
    }

    public boolean CheckFlagTwo(String flag) {
        final TextView hiddenTextView = findViewById(R.id.textViewJustIgnoreMe);
        String compareString = hiddenTextView.getText().toString();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < flag.length(); i++) {
            char c = flag.charAt(i);
            if       (c >= 'a' && c <= 'm') c += 13;
            else if  (c >= 'A' && c <= 'M') c += 13;
            else if  (c >= 'n' && c <= 'z') c -= 13;
            else if  (c >= 'N' && c <= 'Z') c -= 13;
            sb.append(c);
        }

        byte[] byteArray = new byte[0];
        try {
            byteArray = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String finalFlag = Base64.encodeToString(byteArray, Base64.NO_WRAP);

        return finalFlag.equals(compareString);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button checkFlagOneButton = findViewById(R.id.checkFlagOneButton);
        checkFlagOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView flagOneTextView = findViewById(R.id.textViewFlagOne);
                final TextView flagOneGuess = findViewById(R.id.editTextFirstFlag);
                if (CheckFlagOne(flagOneGuess.getText().toString())) {
                    flagOneTextView.setText("Correct!");
                    flagOneTextView.setTextColor(Color.GREEN);
                }
                else {
                    flagOneTextView.setText("Incorrect");
                    flagOneTextView.setTextColor(Color.RED);
                }
            }
        });

        final Button checkFlagTwoButton = findViewById(R.id.checkFlagTwoButton);
        checkFlagTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView flagTwoTextView = findViewById(R.id.textViewFlagTwo);
                final TextView flagTwoGuess = findViewById(R.id.editTextSecondFlag);
                if (CheckFlagTwo(flagTwoGuess.getText().toString())) {
                    flagTwoTextView.setText("Correct!");
                    flagTwoTextView.setTextColor(Color.GREEN);
                }
                else {
                    flagTwoTextView.setText("Incorrect");
                    flagTwoTextView.setTextColor(Color.RED);
                }
            }
        });

        final Button checkFlagThreeButton = findViewById(R.id.checkFlagThreeButton);
        checkFlagThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView flagThreeTextView = findViewById(R.id.textViewFlagThree);
                final TextView flagThreeGuess = findViewById(R.id.editTextThirdFlag);
                new HttpRequestor(flagThreeTextView,flagThreeGuess).execute("http://www.impossibledomainthatdoesntexist.novuslabs");

            }
        });
    }
}
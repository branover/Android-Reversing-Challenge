package com.novuslabs.androidrechallenge;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequestor extends AsyncTask<String, Void, Boolean> {
    //private final Context mContext;
    private final TextView flagThreeTextView;
    private final TextView flagThreeGuess;

    public HttpRequestor(final TextView textView, final TextView editText) {
        //mContext = context;
        flagThreeTextView = textView;
        flagThreeGuess = editText;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        URL url;
        try {
            url = new URL(strings[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean response) {
        if (response) {
            flagThreeTextView.setText("Correct!");
            flagThreeTextView.setTextColor(Color.GREEN);
            flagThreeGuess.setText("flag{you_fixed_the_internet}");
        }
        else {
            flagThreeTextView.setText("Incorrect");
            flagThreeTextView.setTextColor(Color.RED);
        }
    }

}

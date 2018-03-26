package com.derohimat.kamusiden;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.derohimat.kamusiden.db.DictionaryHelper;
import com.derohimat.kamusiden.model.WordDao;
import com.derohimat.kamusiden.pref.AppPreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.derohimat.kamusiden.db.DbContract.TABLE_EN;
import static com.derohimat.kamusiden.db.DbContract.TABLE_ID;

/**
 * Created by denirohimat on 27/03/18.
 */

public class PreLoadAct extends AppCompatActivity {

    private ProgressBar progressBar;
    private Context context;
    private AppPreference appPreference;
    private boolean firstRun;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_load);
        context = this;

        progressBar = findViewById(R.id.progressBar);

        appPreference = new AppPreference(context);
        firstRun = appPreference.getFirstRun();

        if (firstRun) {
            new LoadData().execute();
        } else {
            Intent i = new Intent(context, DictionaryActivity.class);
            startActivity(i);
            finish();
        }
        appPreference.setFirstRun(false);
    }

    public ArrayList<WordDao> preLoadRaw(@RawRes int rawResources) {
        ArrayList<WordDao> wordDaos = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(rawResources);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                WordDao wordDao;

                wordDao = new WordDao(splitstr[0], splitstr[1]);
                wordDaos.add(wordDao);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordDaos;
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        DictionaryHelper dictionaryHelper;
        double progress;
        double maxProgress = 100;

        @Override
        protected void onPreExecute() {
            dictionaryHelper = new DictionaryHelper(context);
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (firstRun) {
                ArrayList<WordDao> enData = preLoadRaw(R.raw.english_indonesia);
                ArrayList<WordDao> idData = preLoadRaw(R.raw.indonesia_english);

                dictionaryHelper.open();

                progress = 0;
                publishProgress((int) progress);
                double progressMaxInsert = 100.0;
                double progressDiff = (progressMaxInsert - progress) / (enData.size() + idData.size() * 1000);

                dictionaryHelper.beginTransaction();

                try {
                    for (WordDao model : enData) {
                        dictionaryHelper.insertTransaction(TABLE_EN, model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    for (WordDao model : idData) {
                        dictionaryHelper.insertTransaction(TABLE_ID, model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    dictionaryHelper.setTransactionSuccess();
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground: Exception");
                }
                dictionaryHelper.endTransaction();
                dictionaryHelper.close();

                publishProgress((int) maxProgress);
            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);

                        publishProgress(50);

                        this.wait(2000);
                        publishProgress((int) maxProgress);
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(context, DictionaryActivity.class);
            startActivity(i);
            finish();
        }
    }
}

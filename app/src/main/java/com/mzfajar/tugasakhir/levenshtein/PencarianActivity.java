package com.mzfajar.tugasakhir.levenshtein;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mzfajar.tugasakhir.R;
import com.mzfajar.tugasakhir.database.DatabaseOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PencarianActivity extends AppCompatActivity {

    public static Integer jarak;
    public static String sumber;
    public static String target;

    SQLiteDatabase db;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarian);
        db = new DatabaseOpenHelper(PencarianActivity.this, "quran.db").openDatabase();

        TextView data = findViewById(R.id.data);
        //TextView dataHasil = findViewById(R.id.datahasil);
        TextView dataHasil2 = findViewById(R.id.datahasil2);
        data.setTypeface(Typeface.createFromAsset(getAssets(), "me_quran.ttf"));
        Bundle bundle = getIntent().getExtras();
        sumber = (bundle.getString("DataSaya"));
        data.setText(sumber);

        Cursor cur = db.rawQuery("SELECT text FROM quran" , null);
        cur.moveToFirst();

        //ini
        for (int i = 1; i < cur.getCount(); i++) {
            cur.moveToPosition(i);
            //target = (cur.getString(cur.getColumnIndex("text")));
            //jarak = distance(sumber, target);
       //     if (jarak == 0) {
                dataHasil2.setText(sumber);
                        //+ ", " + cur.getString(cur.getColumnIndex("text")) + ") = " + jarak);
        //    }
        }
    }

    //Levenshtein Distance
    public static int distance(String a, String b) {
        a = a.toLowerCase(); // String sumber
        b = b.toLowerCase(); // String target
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

}
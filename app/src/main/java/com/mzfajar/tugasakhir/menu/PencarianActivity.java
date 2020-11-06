package com.mzfajar.tugasakhir.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import com.mzfajar.tugasakhir.R;
import com.mzfajar.tugasakhir.adapter.QuranAdapter;
import com.mzfajar.tugasakhir.database.DatabaseOpenHelper;
import com.mzfajar.tugasakhir.model.QuranModel;
import com.mzfajar.tugasakhir.algoritma.LevenshteinDistance;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PencarianActivity extends AppCompatActivity {

    private String sumber;
    private SQLiteDatabase db;
    private RecyclerView rvQuran;
    public long panjangSum;

    private TextView teks;
    private TextView tvNamaSurat;
    private TextView tvAyat;
    private TextView tvTeks;
    private TextView tvTerjemahan;
    private TextView tvDistance;
    private TextView tvTarget;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarian);
        db = new DatabaseOpenHelper(PencarianActivity.this, "quran.db").openDatabase();

        teks = findViewById(R.id.data);
        tvNamaSurat = findViewById(R.id.tvNamaSura);
        tvAyat = findViewById(R.id.tvAyat);
        tvTeks = findViewById(R.id.tvTeks);
        tvDistance = findViewById(R.id.tvDistance);
        tvTerjemahan = findViewById(R.id.tvTerjemahan);
        tvTarget = findViewById(R.id.tvTarget);
        rvQuran = findViewById(R.id.rvQuran);
        teks.setTypeface(Typeface.createFromAsset(getAssets(), "me_quran.ttf"));
        tvTeks.setTypeface(Typeface.createFromAsset(getAssets(), "me_quran.ttf"));

        Bundle bundle = getIntent().getExtras();
        sumber = (bundle.getString("DataSaya"));
        getListDataQuran();

       // panjang sumber
        int [] sum = new int[sumber.length()];
        panjangSum = 0;
        if(sum != null) {
            for (int p = 1; p < sum.length; p++) {
                panjangSum++;
            }
            teks.setText(panjangSum + "  :  " + sumber);
        }
    }

    private void getListDataQuran(){
        Cursor cursor = db.rawQuery("SELECT * FROM quran" , null);
        cursor.moveToFirst();
        List<QuranModel> quranList = new ArrayList<>();
        QuranModel quranModel;
        if(cursor != null){
            if(cursor.getCount() > 0){
                do{
                    quranModel = new QuranModel();
                    quranModel.setSurat(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseOpenHelper.SURAT)));
                    quranModel.setNamaSura(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseOpenHelper.NAMA_SURA)));
                    quranModel.setAyat(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseOpenHelper.AYAT)));
                    quranModel.setTerjemah(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseOpenHelper.TERJEMAH)));
                    quranModel.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseOpenHelper.TEXT)));
                    quranModel.setTarget(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseOpenHelper.TARGET)));
                    if(quranModel.getTarget() != null){
                        // nilai distance
                        long distance = LevenshteinDistance.distance(sumber, quranModel.getTarget());
                        quranModel.setDistance(distance);
                        
                        if(distance <= 1){ // yang sama
                            quranModel.setDistance(distance);
                            target(quranModel);
                            showResult(quranModel);
                        }else if(distance > 1 && distance < 10){ // yang hampir sama

                            quranModel.setDistance(distance);
                            target(quranModel);
                            quranList.add(quranModel);
                        }
                    }
                    cursor.moveToNext();
                }while (!cursor.isAfterLast());

                if(quranList.size() > 0){
                    showRecyclerView(quranList);
                }else{
//                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_SHORT).show();
                }
            }
        }
        cursor.close();
    }

    private void target(QuranModel quranModel) {
        long panjtar = 0;
        if(quranModel.getTarget() != null) {
            for (int j = 1; j < quranModel.getTarget().length(); j++) {
                panjtar++;
            }
            quranModel.setPanjTar(panjtar);
        }
    }

    private void showResult(QuranModel quranModel){
        tvTerjemahan.setText("Artinya : " + quranModel.getTerjemah());
        tvTeks.setText(quranModel.getText());
        tvTarget.setText(quranModel.getTarget() + "  :  " + quranModel.getPanjTar());
        tvAyat.setText(", Ayat : " + String.valueOf(quranModel.getAyat()) + ")");
        tvNamaSurat.setText("(" + quranModel.getNamaSura());
        tvDistance.setText("(Distance : " + quranModel.getDistance() + ")");
    }

    private void showRecyclerView(List<QuranModel> quranList){
        QuranAdapter quranAdapter = new QuranAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvQuran.setLayoutManager(layoutManager);
        rvQuran.setHasFixedSize(true);
        rvQuran.setAdapter(quranAdapter);
        rvQuran.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
        quranAdapter.setData(quranList);
    }


}
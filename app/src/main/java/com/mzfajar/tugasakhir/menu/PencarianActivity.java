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
import java.util.List;

public class PencarianActivity extends AppCompatActivity {

    private String sumber;
    private SQLiteDatabase db;
    private RecyclerView rvQuran;

    private TextView teks;
    private TextView tvNamaSurat;
    private TextView tvAyat;
    private TextView tvTeks;
    private TextView tvTerjemahan;
    private TextView tvDistance;
   // public Integer distance;

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
        rvQuran = findViewById(R.id.rvQuran);
        teks.setTypeface(Typeface.createFromAsset(getAssets(), "me_quran.ttf"));
        tvTeks.setTypeface(Typeface.createFromAsset(getAssets(), "me_quran.ttf"));

        Bundle bundle = getIntent().getExtras();
        sumber = (bundle.getString("DataSaya"));
        getListDataQuran();

        teks.setText(sumber);
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
                        long distance = LevenshteinDistance.distance(sumber, quranModel.getTarget());
                        quranModel.setDistance(distance);
                        if(distance <= 1){
                            //tvDistance.setText(distance);
                            showResult(quranModel);
                        }else if(distance > 1 && distance <= 5){
                           // tvNamaSurat.setText("Ayat Tidak Ditemukan");
                            quranList.add(quranModel);
                        } //if(distance > 5)
                           //noResult();
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

    private void showResult(QuranModel quranModel){
        tvTerjemahan.setText("Artinya : " + quranModel.getTerjemah());
        tvTeks.setText(quranModel.getText());
        tvAyat.setText(", Ayat : " + String.valueOf(quranModel.getAyat()) + ")");
        tvNamaSurat.setText("(" + quranModel.getNamaSura());
        tvDistance.setText("(Distance : " + quranModel.getDistance() + ")");
    }

    private void noResult(){
        tvNamaSurat.setText("Ayat Tidak Ditemukan");
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
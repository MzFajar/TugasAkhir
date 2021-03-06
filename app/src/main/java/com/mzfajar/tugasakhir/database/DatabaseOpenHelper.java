package com.mzfajar.tugasakhir.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseOpenHelper {

    private String DB_NAME = "";
    private Context context;

    public static String SURAT = "surat";
    public static String AYAT = "ayat";
    public static String NAMA_SURA = "namaSura";
    public static String TARGET = "target";
    public static String TEXT = "text";
    public static String TERJEMAH = "terjemah";


    public DatabaseOpenHelper(Context context, String dbname) {
        this.context = context;
        this.DB_NAME = dbname;
    }

    public SQLiteDatabase openDatabase() {
        File dbFile = context.getDatabasePath(DB_NAME);
        File par = dbFile.getParentFile();
        if (!par.exists()) par.mkdirs();
        if (!dbFile.exists()) {
            try {
                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READONLY);
    }

    private void copyDatabase(File dbFile) throws IOException {
        InputStream is = context.getAssets().open(DB_NAME);
        OutputStream os = new FileOutputStream(dbFile);

        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }

        os.flush();
        os.close();
        is.close();
    }

}

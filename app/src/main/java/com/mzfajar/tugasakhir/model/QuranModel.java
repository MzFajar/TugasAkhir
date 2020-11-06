package com.mzfajar.tugasakhir.model;

import java.util.Collection;
import java.util.Collections;

public class QuranModel {

    private long surat;
    private String namaSura;
    private long ayat;
    private String target;
    private String text;
    private String terjemah;
    private long distance;
    private long panjTar;

    public QuranModel() {
        this.surat = surat;
        this.namaSura = namaSura;
        this.target = target;
        this.ayat = ayat;
        this.text = text;
        this.terjemah = terjemah;
        this.distance = distance;
        this.panjTar = panjTar;
    }

    public long getSurat() {
        return surat;
    }

    public void setSurat(long surat) {
        this.surat = surat;
    }

    public String getNamaSura() {
        return namaSura;
    }

    public void setNamaSura(String namaSura) {
        this.namaSura = namaSura;
    }

    public long getAyat() {
        return ayat;
    }

    public void setAyat(long ayat) {
        this.ayat = ayat;
    }

    public String getTarget() { return target; }

    public void setTarget(String target) { this.target = target; }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTerjemah() {
        return terjemah;
    }

    public void setTerjemah(String terjemah) {
        this.terjemah = terjemah;
    }

    public long getDistance() { return distance; }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public long getPanjTar() { return  panjTar; }

    public void setPanjTar(long panjTar) { this.panjTar = panjTar; }

}

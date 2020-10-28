package com.mzfajar.tugasakhir.model;

public class QuranModel {

    private long surat;
    private String namaSura;
    private long ayat;
    private String target;
    private String text;
    private String terjemah;
    private String latin;

    public QuranModel() {
    }

    public QuranModel(long surat, String namaSura, long ayat, String target, String text, String terjemah, String latin) {
        this.surat = surat;
        this.namaSura = namaSura;
        this.target = target;
        this.ayat = ayat;
        this.text = text;
        this.terjemah = terjemah;
        this.latin = latin;
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

    public String getLatin() {
        return latin;
    }

    public void setLatin(String latin) {
        this.latin = latin;
    }
}

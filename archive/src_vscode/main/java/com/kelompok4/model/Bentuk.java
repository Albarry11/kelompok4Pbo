package com.kelompok4.model;

public abstract class Bentuk {
    private String nama;
    private String warna;
    protected Bentuk(String nama, String warna) { this.nama = nama; this.warna = warna; }
    protected Bentuk() { this.nama = "Tidak diketahui"; this.warna = "Tidak diketahui"; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getWarna() { return warna; }
    public void setWarna(String warna) { this.warna = warna; }
    public abstract double hitungLuas();
    public abstract String info();
    public String info(boolean detail) { if (detail) return "Bentuk: " + nama + " | Warna: " + warna; return nama; }
    @Override public String toString() { return nama + " (Warna: " + warna + ")"; }
}

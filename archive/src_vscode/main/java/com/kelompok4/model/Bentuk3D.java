package com.kelompok4.model;

public abstract class Bentuk3D extends Bentuk {
    protected Bentuk3D(String nama, String warna) { super(nama, warna); }
    protected Bentuk3D() { super(); }
    public abstract double hitungVolume();
    public abstract double hitungLuasPermukaan();
    @Override public String info(boolean detail) { if (detail) return super.info(detail) + " | Tipe: 3 Dimensi"; return super.info(false); }
}

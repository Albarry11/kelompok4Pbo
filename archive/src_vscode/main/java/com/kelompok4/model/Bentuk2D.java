package com.kelompok4.model;

public abstract class Bentuk2D extends Bentuk {
    protected Bentuk2D(String nama, String warna) { super(nama, warna); }
    protected Bentuk2D() { super(); }
    public abstract double hitungKeliling();
    @Override public String info(boolean detail) { if (detail) return super.info(detail) + " | Tipe: 2 Dimensi"; return super.info(false); }
}

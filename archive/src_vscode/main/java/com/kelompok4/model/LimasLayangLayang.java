package com.kelompok4.model;

import com.kelompok4.interfaces.Calculatable;
import com.kelompok4.exception.InvalidInputException;

public class LimasLayangLayang extends Bentuk3D implements Calculatable {
    private LayangLayang alas; private double tinggi;
    public LimasLayangLayang(LayangLayang alas, double tinggi) { super("Limas Layang-Layang", "Biru"); this.alas = alas; this.tinggi = tinggi; }
    public LimasLayangLayang(double d1, double d2, double sisiA, double sisiB, double tinggi) { super("Limas Layang-Layang", "Biru"); this.alas = new LayangLayang(d1, d2, sisiA, sisiB); this.tinggi = tinggi; }
    public LayangLayang getAlas() { return alas; } public void setAlas(LayangLayang alas) { this.alas = alas; }
    public double getTinggi() { return tinggi; } public void setTinggi(double tinggi) { this.tinggi = tinggi; }
    @Override public double hitungLuas() { return alas.hitungLuas(); }
    @Override public double hitungVolume() { return (1.0/3.0)*alas.hitungLuas()*tinggi; }
    @Override public double hitungLuasPermukaan() { double luasAlas = alas.hitungLuas(); double halfD1 = alas.getDiagonal1()/2.0; double halfD2 = alas.getDiagonal2()/2.0; double slantHeightA = Math.sqrt(tinggi*tinggi + halfD2*halfD2); double slantHeightB = Math.sqrt(tinggi*tinggi + halfD1*halfD1); double luasSisiA = 2*(0.5*alas.getSisiA()*slantHeightA); double luasSisiB = 2*(0.5*alas.getSisiB()*slantHeightB); return luasAlas + luasSisiA + luasSisiB; }
    @Override public String info() { return String.format("=== LIMAS LAYANG-LAYANG (3D) ===\n--- Alas (Layang-Layang) ---\nDiagonal 1     : %.2f\nDiagonal 2     : %.2f\nSisi A         : %.2f\nSisi B         : %.2f\nLuas Alas      : %.2f\n--- Limas ---\nTinggi         : %.2f\nVolume         : %.2f\nLuas Permukaan : %.2f", alas.getDiagonal1(), alas.getDiagonal2(), alas.getSisiA(), alas.getSisiB(), alas.hitungLuas(), tinggi, hitungVolume(), hitungLuasPermukaan()); }
    @Override public String calculate() { return String.format("Luas Alas = ½ × %.2f × %.2f = %.2f\nVolume = ⅓ × %.2f × %.2f = %.2f\nLuas Permukaan = %.2f", alas.getDiagonal1(), alas.getDiagonal2(), alas.hitungLuas(), alas.hitungLuas(), tinggi, hitungVolume(), hitungLuasPermukaan()); }
    @Override public String getTipeBentuk() { return "3D - Limas"; }
    public void validasiInput() throws InvalidInputException { alas.validasiInput(); if (tinggi <= 0) throw new InvalidInputException("Tinggi limas harus bernilai positif! (tinggi=" + tinggi + ")"); }
}

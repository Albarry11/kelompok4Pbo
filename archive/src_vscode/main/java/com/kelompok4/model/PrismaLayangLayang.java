package com.kelompok4.model;

import com.kelompok4.interfaces.Calculatable;
import com.kelompok4.exception.InvalidInputException;

public class PrismaLayangLayang extends Bentuk3D implements Calculatable {
    private LayangLayang alas; private double tinggi;
    public PrismaLayangLayang(LayangLayang alas, double tinggi) { super("Prisma Layang-Layang", "Hijau"); this.alas = alas; this.tinggi = tinggi; }
    public PrismaLayangLayang(double d1, double d2, double sisiA, double sisiB, double tinggi) { super("Prisma Layang-Layang", "Hijau"); this.alas = new LayangLayang(d1, d2, sisiA, sisiB); this.tinggi = tinggi; }
    public LayangLayang getAlas() { return alas; } public void setAlas(LayangLayang alas) { this.alas = alas; }
    public double getTinggi() { return tinggi; } public void setTinggi(double tinggi) { this.tinggi = tinggi; }
    @Override public double hitungLuas() { return alas.hitungLuas(); }
    @Override public double hitungVolume() { return alas.hitungLuas() * tinggi; }
    @Override public double hitungLuasPermukaan() { double luasDuaAlas = 2 * alas.hitungLuas(); double luasSelimut = alas.hitungKeliling() * tinggi; return luasDuaAlas + luasSelimut; }
    @Override public String info() { return String.format("=== PRISMA LAYANG-LAYANG (3D) ===\n--- Alas (Layang-Layang) ---\nDiagonal 1     : %.2f\nDiagonal 2     : %.2f\nSisi A         : %.2f\nSisi B         : %.2f\nLuas Alas      : %.2f\nKeliling Alas  : %.2f\n--- Prisma ---\nTinggi         : %.2f\nVolume         : %.2f\nLuas Permukaan : %.2f", alas.getDiagonal1(), alas.getDiagonal2(), alas.getSisiA(), alas.getSisiB(), alas.hitungLuas(), alas.hitungKeliling(), tinggi, hitungVolume(), hitungLuasPermukaan()); }
    @Override public String calculate() { return String.format("Luas Alas = ½ × %.2f × %.2f = %.2f\nKeliling Alas = 2 × (%.2f + %.2f) = %.2f\nVolume = %.2f × %.2f = %.2f\nLuas Permukaan = 2×%.2f + %.2f×%.2f = %.2f", alas.getDiagonal1(), alas.getDiagonal2(), alas.hitungLuas(), alas.getSisiA(), alas.getSisiB(), alas.hitungKeliling(), alas.hitungLuas(), tinggi, hitungVolume(), alas.hitungLuas(), alas.hitungKeliling(), tinggi, hitungLuasPermukaan()); }
    @Override public String getTipeBentuk() { return "3D - Prisma"; }
    public void validasiInput() throws InvalidInputException { alas.validasiInput(); if (tinggi <= 0) throw new InvalidInputException("Tinggi prisma harus bernilai positif! (tinggi=" + tinggi + ")"); }
}

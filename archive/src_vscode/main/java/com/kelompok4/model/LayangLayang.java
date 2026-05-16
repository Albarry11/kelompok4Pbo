package com.kelompok4.model;

import com.kelompok4.interfaces.Calculatable;
import com.kelompok4.exception.InvalidInputException;

public class LayangLayang extends Bentuk2D implements Calculatable {
    private double diagonal1; private double diagonal2; private double sisiA; private double sisiB;
    public LayangLayang(double diagonal1, double diagonal2, double sisiA, double sisiB) {
        super("Layang-Layang", "Merah"); this.diagonal1 = diagonal1; this.diagonal2 = diagonal2; this.sisiA = sisiA; this.sisiB = sisiB;
    }
    public LayangLayang(double diagonal1, double diagonal2) {
        super("Layang-Layang", "Merah"); this.diagonal1 = diagonal1; this.diagonal2 = diagonal2; double halfD2 = diagonal2/2.0; double topD1 = diagonal1*0.4; double bottomD1 = diagonal1*0.6; this.sisiA = Math.sqrt(topD1*topD1 + halfD2*halfD2); this.sisiB = Math.sqrt(bottomD1*bottomD1 + halfD2*halfD2);
    }
    public double getDiagonal1() { return diagonal1; }
    public void setDiagonal1(double diagonal1) { this.diagonal1 = diagonal1; }
    public double getDiagonal2() { return diagonal2; }
    public void setDiagonal2(double diagonal2) { this.diagonal2 = diagonal2; }
    public double getSisiA() { return sisiA; }
    public void setSisiA(double sisiA) { this.sisiA = sisiA; }
    public double getSisiB() { return sisiB; }
    public void setSisiB(double sisiB) { this.sisiB = sisiB; }
    @Override public double hitungLuas() { return 0.5 * diagonal1 * diagonal2; }
    @Override public double hitungKeliling() { return 2 * (sisiA + sisiB); }
    @Override public String info() { return String.format("=== LAYANG-LAYANG (2D) ===\nDiagonal 1 : %.2f\nDiagonal 2 : %.2f\nSisi A     : %.2f\nSisi B     : %.2f\nLuas       : %.2f\nKeliling   : %.2f", diagonal1, diagonal2, sisiA, sisiB, hitungLuas(), hitungKeliling()); }
    @Override public String calculate() { return String.format("Luas Layang-Layang = ½ × %.2f × %.2f = %.2f\nKeliling Layang-Layang = 2 × (%.2f + %.2f) = %.2f", diagonal1, diagonal2, hitungLuas(), sisiA, sisiB, hitungKeliling()); }
    @Override public String getTipeBentuk() { return "2D - Bangun Datar"; }
    public void validasiInput() throws InvalidInputException { if (diagonal1 <= 0 || diagonal2 <= 0) throw new InvalidInputException("Diagonal harus bernilai positif! (d1=" + diagonal1 + ", d2=" + diagonal2 + ")"); if (sisiA <= 0 || sisiB <= 0) throw new InvalidInputException("Sisi harus bernilai positif! (a=" + sisiA + ", b=" + sisiB + ")"); }
}

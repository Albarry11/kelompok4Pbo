package model;

import interfaces.Calculatable;
import exception.InvalidInputException;

/**
 * Class LayangLayang (Benda 2 Dimensi)
 * =====================================
 * Concrete class yang merepresentasikan bangun datar layang-layang.
 * 
 * Hierarki: Bentuk → Bentuk2D → LayangLayang
 * 
 * PILAR OOP yang diterapkan:
 * 1. ENCAPSULATION  : Semua atribut private, akses via getter/setter
 * 2. INHERITANCE    : extends Bentuk2D (→ Bentuk)
 * 3. OVERLOADING    : Constructor overloading (2 constructor berbeda parameter)
 * 4. OVERRIDING     : Override hitungLuas(), hitungKeliling(), info()
 * 5. POLYMORPHISM   : Implements Calculatable interface
 * 
 * Rumus:
 * - Luas      = ½ × d1 × d2
 * - Keliling  = 2 × (sisiA + sisiB)
 */
public class LayangLayang extends Bentuk2D implements Calculatable {
    
    // ===== ENCAPSULATION: Atribut PRIVATE =====
    private double diagonal1;   // diagonal panjang
    private double diagonal2;   // diagonal pendek
    private double sisiA;       // pasangan sisi panjang
    private double sisiB;       // pasangan sisi pendek
    
    /**
     * Constructor LENGKAP dengan semua parameter
     * PILAR OOP: Overloading (constructor 1 dari 2)
     * 
     * @param diagonal1 diagonal pertama (panjang)
     * @param diagonal2 diagonal kedua (pendek)
     * @param sisiA pasangan sisi panjang
     * @param sisiB pasangan sisi pendek
     */
    public LayangLayang(double diagonal1, double diagonal2, double sisiA, double sisiB) {
        super("Layang-Layang", "Merah");  // super() memanggil constructor parent
        this.diagonal1 = diagonal1;        // keyword 'this'
        this.diagonal2 = diagonal2;
        this.sisiA = sisiA;
        this.sisiB = sisiB;
    }
    
    /**
     * Constructor SINGKAT hanya dengan diagonal (sisi dihitung otomatis)
     * PILAR OOP: Overloading (constructor 2 dari 2)
     * 
     * Asumsi: layang-layang simetris, diagonal berpotongan di 
     * titik yang membagi d2 menjadi 2 bagian sama panjang,
     * dan d1 dibagi tidak sama (misal 60:40 dari panjang d1)
     * 
     * @param diagonal1 diagonal pertama
     * @param diagonal2 diagonal kedua
     */
    public LayangLayang(double diagonal1, double diagonal2) {
        super("Layang-Layang", "Merah");
        this.diagonal1 = diagonal1;
        this.diagonal2 = diagonal2;
        // Hitung sisi menggunakan teorema Pythagoras
        // Asumsi diagonal1 dipotong 60:40, diagonal2 dipotong 50:50
        double halfD2 = diagonal2 / 2.0;
        double topD1 = diagonal1 * 0.4;
        double bottomD1 = diagonal1 * 0.6;
        this.sisiA = Math.sqrt(topD1 * topD1 + halfD2 * halfD2);
        this.sisiB = Math.sqrt(bottomD1 * bottomD1 + halfD2 * halfD2);
    }
    
    // ===== GETTER & SETTER (Encapsulation) =====
    
    public double getDiagonal1() {
        return diagonal1;
    }
    
    public void setDiagonal1(double diagonal1) {
        this.diagonal1 = diagonal1;
    }
    
    public double getDiagonal2() {
        return diagonal2;
    }
    
    public void setDiagonal2(double diagonal2) {
        this.diagonal2 = diagonal2;
    }
    
    public double getSisiA() {
        return sisiA;
    }
    
    public void setSisiA(double sisiA) {
        this.sisiA = sisiA;
    }
    
    public double getSisiB() {
        return sisiB;
    }
    
    public void setSisiB(double sisiB) {
        this.sisiB = sisiB;
    }
    
    // ===== OVERRIDING abstract methods dari parent =====
    
    /**
     * OVERRIDING hitungLuas() dari Bentuk
     * Rumus Luas Layang-Layang = ½ × d1 × d2
     * PILAR OOP: Overriding
     */
    @Override
    public double hitungLuas() {
        return 0.5 * diagonal1 * diagonal2;
    }
    
    /**
     * OVERRIDING hitungKeliling() dari Bentuk2D
     * Rumus Keliling = 2 × (sisiA + sisiB)
     * PILAR OOP: Overriding
     */
    @Override
    public double hitungKeliling() {
        return 2 * (sisiA + sisiB);
    }
    
    /**
     * OVERRIDING info() dari Bentuk
     * PILAR OOP: Overriding
     */
    @Override
    public String info() {
        return String.format(
            "=== LAYANG-LAYANG (2D) ===\n" +
            "Diagonal 1 : %.2f\n" +
            "Diagonal 2 : %.2f\n" +
            "Sisi A     : %.2f\n" +
            "Sisi B     : %.2f\n" +
            "Luas       : %.2f\n" +
            "Keliling   : %.2f",
            diagonal1, diagonal2, sisiA, sisiB,
            hitungLuas(), hitungKeliling()
        );
    }
    
    // ===== IMPLEMENTATION dari Interface Calculatable =====
    
    /**
     * Implementasi method calculate() dari interface Calculatable
     * Sesuai materi slide 5: semua method interface wajib diimplementasikan
     */
    @Override
    public String calculate() {
        return String.format(
            "Luas Layang-Layang = ½ × %.2f × %.2f = %.2f\n" +
            "Keliling Layang-Layang = 2 × (%.2f + %.2f) = %.2f",
            diagonal1, diagonal2, hitungLuas(),
            sisiA, sisiB, hitungKeliling()
        );
    }
    
    @Override
    public String getTipeBentuk() {
        return "2D - Bangun Datar";
    }
    
    /**
     * Validasi input
     * Menggunakan custom InvalidInputException (Exception Handling)
     * @throws InvalidInputException jika input tidak valid
     */
    public void validasiInput() throws InvalidInputException {
        if (diagonal1 <= 0 || diagonal2 <= 0) {
            throw new InvalidInputException("Diagonal harus bernilai positif! (d1=" + diagonal1 + ", d2=" + diagonal2 + ")");
        }
        if (sisiA <= 0 || sisiB <= 0) {
            throw new InvalidInputException("Sisi harus bernilai positif! (a=" + sisiA + ", b=" + sisiB + ")");
        }
    }
}

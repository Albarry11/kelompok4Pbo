package model;

import interfaces.Calculatable;
import exception.InvalidInputException;

/**
 * Class PrismaLayangLayang (Benda 3 Dimensi - Prisma)
 * ====================================================
 * Prisma dengan alas dan tutup berbentuk layang-layang.
 * 
 * Hierarki: Bentuk → Bentuk3D → PrismaLayangLayang
 * Komposisi: memiliki objek LayangLayang sebagai alas
 * 
 * PILAR OOP yang diterapkan:
 * 1. ENCAPSULATION  : Atribut private + getter/setter
 * 2. INHERITANCE    : extends Bentuk3D (→ Bentuk)
 * 3. OVERLOADING    : Constructor overloading
 * 4. OVERRIDING     : Override hitungLuas(), hitungVolume(), hitungLuasPermukaan(), info()
 * 5. POLYMORPHISM   : Implements Calculatable, bisa dipanggil sebagai tipe Bentuk
 * 
 * Rumus:
 * - Volume         = Luas_alas × tinggi
 * - Luas Permukaan = 2 × Luas_alas + Keliling_alas × tinggi
 */
public class PrismaLayangLayang extends Bentuk3D implements Calculatable {
    
    // ===== ENCAPSULATION: Atribut PRIVATE =====
    private LayangLayang alas;   // komposisi: objek LayangLayang sebagai alas
    private double tinggi;       // tinggi prisma
    
    /**
     * Constructor LENGKAP
     * PILAR OOP: Overloading (constructor 1 dari 2)
     * 
     * @param alas objek LayangLayang sebagai alas prisma
     * @param tinggi tinggi prisma
     */
    public PrismaLayangLayang(LayangLayang alas, double tinggi) {
        super("Prisma Layang-Layang", "Hijau");  // super() ke Bentuk3D
        this.alas = alas;
        this.tinggi = tinggi;
    }
    
    /**
     * Constructor dengan parameter langsung
     * PILAR OOP: Overloading (constructor 2 dari 2)
     * 
     * @param d1 diagonal 1 alas
     * @param d2 diagonal 2 alas
     * @param sisiA sisi A alas
     * @param sisiB sisi B alas
     * @param tinggi tinggi prisma
     */
    public PrismaLayangLayang(double d1, double d2, double sisiA, double sisiB, double tinggi) {
        super("Prisma Layang-Layang", "Hijau");
        this.alas = new LayangLayang(d1, d2, sisiA, sisiB);
        this.tinggi = tinggi;
    }
    
    // ===== GETTER & SETTER (Encapsulation) =====
    
    public LayangLayang getAlas() {
        return alas;
    }
    
    public void setAlas(LayangLayang alas) {
        this.alas = alas;
    }
    
    public double getTinggi() {
        return tinggi;
    }
    
    public void setTinggi(double tinggi) {
        this.tinggi = tinggi;
    }
    
    // ===== OVERRIDING abstract methods =====
    
    /**
     * OVERRIDING hitungLuas() dari Bentuk
     * Untuk prisma, hitungLuas() mengembalikan luas alas
     * PILAR OOP: Overriding
     */
    @Override
    public double hitungLuas() {
        return alas.hitungLuas();
    }
    
    /**
     * OVERRIDING hitungVolume() dari Bentuk3D
     * Rumus Volume Prisma = Luas_alas × tinggi
     * PILAR OOP: Overriding
     */
    @Override
    public double hitungVolume() {
        return alas.hitungLuas() * tinggi;
    }
    
    /**
     * OVERRIDING hitungLuasPermukaan() dari Bentuk3D
     * Rumus = 2 × Luas_alas + Keliling_alas × tinggi
     * 
     * Prisma punya:
     * - 2 alas (atas dan bawah) → 2 × Luas_layang-layang
     * - 4 sisi tegak persegi panjang → Keliling_alas × tinggi
     * 
     * PILAR OOP: Overriding
     */
    @Override
    public double hitungLuasPermukaan() {
        double luasDuaAlas = 2 * alas.hitungLuas();
        double luasSelimut = alas.hitungKeliling() * tinggi;
        return luasDuaAlas + luasSelimut;
    }
    
    /**
     * OVERRIDING info() dari Bentuk
     * PILAR OOP: Overriding
     */
    @Override
    public String info() {
        return String.format(
            "=== PRISMA LAYANG-LAYANG (3D) ===\n" +
            "--- Alas (Layang-Layang) ---\n" +
            "Diagonal 1     : %.2f\n" +
            "Diagonal 2     : %.2f\n" +
            "Sisi A         : %.2f\n" +
            "Sisi B         : %.2f\n" +
            "Luas Alas      : %.2f\n" +
            "Keliling Alas  : %.2f\n" +
            "--- Prisma ---\n" +
            "Tinggi         : %.2f\n" +
            "Volume         : %.2f\n" +
            "Luas Permukaan : %.2f",
            alas.getDiagonal1(), alas.getDiagonal2(),
            alas.getSisiA(), alas.getSisiB(),
            alas.hitungLuas(), alas.hitungKeliling(),
            tinggi, hitungVolume(), hitungLuasPermukaan()
        );
    }
    
    // ===== IMPLEMENTATION dari Interface Calculatable =====
    
    @Override
    public String calculate() {
        return String.format(
            "Luas Alas = ½ × %.2f × %.2f = %.2f\n" +
            "Keliling Alas = 2 × (%.2f + %.2f) = %.2f\n" +
            "Volume = %.2f × %.2f = %.2f\n" +
            "Luas Permukaan = 2×%.2f + %.2f×%.2f = %.2f",
            alas.getDiagonal1(), alas.getDiagonal2(), alas.hitungLuas(),
            alas.getSisiA(), alas.getSisiB(), alas.hitungKeliling(),
            alas.hitungLuas(), tinggi, hitungVolume(),
            alas.hitungLuas(), alas.hitungKeliling(), tinggi, hitungLuasPermukaan()
        );
    }
    
    @Override
    public String getTipeBentuk() {
        return "3D - Prisma";
    }
    
    /**
     * Validasi input
     * @throws InvalidInputException jika input tidak valid
     */
    public void validasiInput() throws InvalidInputException {
        alas.validasiInput();
        if (tinggi <= 0) {
            throw new InvalidInputException("Tinggi prisma harus bernilai positif! (tinggi=" + tinggi + ")");
        }
    }
}

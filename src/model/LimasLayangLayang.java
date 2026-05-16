package model;

import interfaces.Calculatable;
import exception.InvalidInputException;

/**
 * Class LimasLayangLayang (Benda 3 Dimensi - Limas)
 * ==================================================
 * Limas dengan alas berbentuk layang-layang.
 * 
 * Hierarki: Bentuk → Bentuk3D → LimasLayangLayang
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
 * - Volume         = ⅓ × Luas_alas × tinggi
 * - Luas Permukaan = Luas_alas + Σ(luas 4 segitiga sisi tegak)
 */
public class LimasLayangLayang extends Bentuk3D implements Calculatable {
    
    // ===== ENCAPSULATION: Atribut PRIVATE =====
    private LayangLayang alas;   // komposisi: objek LayangLayang sebagai alas
    private double tinggi;       // tinggi limas
    
    /**
     * Constructor LENGKAP
     * PILAR OOP: Overloading (constructor 1 dari 2)
     * 
     * @param alas objek LayangLayang sebagai alas limas
     * @param tinggi tinggi limas
     */
    public LimasLayangLayang(LayangLayang alas, double tinggi) {
        super("Limas Layang-Layang", "Biru");  // super() ke Bentuk3D
        this.alas = alas;
        this.tinggi = tinggi;
    }
    
    /**
     * Constructor dengan parameter langsung (tanpa objek LayangLayang)
     * PILAR OOP: Overloading (constructor 2 dari 2)
     * 
     * @param d1 diagonal 1 alas
     * @param d2 diagonal 2 alas
     * @param sisiA sisi A alas
     * @param sisiB sisi B alas
     * @param tinggi tinggi limas
     */
    public LimasLayangLayang(double d1, double d2, double sisiA, double sisiB, double tinggi) {
        super("Limas Layang-Layang", "Biru");
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
     * Untuk limas, hitungLuas() mengembalikan luas alas
     * PILAR OOP: Overriding
     */
    @Override
    public double hitungLuas() {
        return alas.hitungLuas();
    }
    
    /**
     * OVERRIDING hitungVolume() dari Bentuk3D
     * Rumus Volume Limas = ⅓ × Luas_alas × tinggi
     * PILAR OOP: Overriding
     */
    @Override
    public double hitungVolume() {
        return (1.0 / 3.0) * alas.hitungLuas() * tinggi;
    }
    
    /**
     * OVERRIDING hitungLuasPermukaan() dari Bentuk3D
     * Rumus = Luas_alas + Luas 4 sisi tegak (segitiga)
     * 
     * Limas layang-layang punya 4 sisi tegak segitiga:
     * - 2 segitiga dengan alas = sisiA
     * - 2 segitiga dengan alas = sisiB
     * 
     * Tinggi miring (slant height) dihitung dengan Pythagoras
     * PILAR OOP: Overriding
     */
    @Override
    public double hitungLuasPermukaan() {
        double luasAlas = alas.hitungLuas();
        
        // Hitung tinggi miring (slant height) untuk masing-masing sisi
        // Tinggi miring dari puncak limas ke tengah sisi alas
        double halfD1 = alas.getDiagonal1() / 2.0;
        double halfD2 = alas.getDiagonal2() / 2.0;
        
        // Tinggi miring ke sisi yang sejajar dengan d1
        double slantHeightA = Math.sqrt(tinggi * tinggi + halfD2 * halfD2);
        // Tinggi miring ke sisi yang sejajar dengan d2
        double slantHeightB = Math.sqrt(tinggi * tinggi + halfD1 * halfD1);
        
        // Luas 2 segitiga dengan alas sisiA
        double luasSisiA = 2 * (0.5 * alas.getSisiA() * slantHeightA);
        // Luas 2 segitiga dengan alas sisiB
        double luasSisiB = 2 * (0.5 * alas.getSisiB() * slantHeightB);
        
        return luasAlas + luasSisiA + luasSisiB;
    }
    
    /**
     * OVERRIDING info() dari Bentuk
     * PILAR OOP: Overriding
     */
    @Override
    public String info() {
        return String.format(
            "=== LIMAS LAYANG-LAYANG (3D) ===\n" +
            "--- Alas (Layang-Layang) ---\n" +
            "Diagonal 1     : %.2f\n" +
            "Diagonal 2     : %.2f\n" +
            "Sisi A         : %.2f\n" +
            "Sisi B         : %.2f\n" +
            "Luas Alas      : %.2f\n" +
            "--- Limas ---\n" +
            "Tinggi         : %.2f\n" +
            "Volume         : %.2f\n" +
            "Luas Permukaan : %.2f",
            alas.getDiagonal1(), alas.getDiagonal2(),
            alas.getSisiA(), alas.getSisiB(),
            alas.hitungLuas(),
            tinggi, hitungVolume(), hitungLuasPermukaan()
        );
    }
    
    // ===== IMPLEMENTATION dari Interface Calculatable =====
    
    @Override
    public String calculate() {
        return String.format(
            "Luas Alas = ½ × %.2f × %.2f = %.2f\n" +
            "Volume = ⅓ × %.2f × %.2f = %.2f\n" +
            "Luas Permukaan = %.2f",
            alas.getDiagonal1(), alas.getDiagonal2(), alas.hitungLuas(),
            alas.hitungLuas(), tinggi, hitungVolume(),
            hitungLuasPermukaan()
        );
    }
    
    @Override
    public String getTipeBentuk() {
        return "3D - Limas";
    }
    
    /**
     * Validasi input
     * @throws InvalidInputException jika input tidak valid
     */
    public void validasiInput() throws InvalidInputException {
        alas.validasiInput();
        if (tinggi <= 0) {
            throw new InvalidInputException("Tinggi limas harus bernilai positif! (tinggi=" + tinggi + ")");
        }
    }
}

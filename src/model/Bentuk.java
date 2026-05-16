package model;

/**
 * Abstract Class Bentuk
 * =====================
 * ROOT dari hierarki taksonomi class.
 * 
 * Sesuai materi slide 5 (Abstract Class):
 * - Class yang terlalu umum sehingga sulit diinstansiasi
 * - Memerlukan subclass yang lebih spesifik
 * - Method abstract WAJIB di-override oleh subclass
 * 
 * Sesuai materi slide 2 (Struktur OOP):
 * - Modifier private → Encapsulation (information hiding)
 * - Modifier protected → bisa diakses oleh subclass
 * 
 * PILAR OOP: Encapsulation (private attributes + getter/setter)
 *            Inheritance (menjadi superclass untuk Bentuk2D & Bentuk3D)
 */
public abstract class Bentuk {
    
    // ===== ENCAPSULATION: Atribut private =====
    private String nama;
    private String warna;
    
    /**
     * Constructor dengan parameter
     * @param nama nama bentuk geometri
     * @param warna warna bentuk
     */
    protected Bentuk(String nama, String warna) {
        this.nama = nama;       // keyword 'this' untuk membedakan atribut dan parameter
        this.warna = warna;
    }
    
    /**
     * Constructor tanpa parameter (OVERLOADING constructor)
     * PILAR OOP: Overloading
     */
    protected Bentuk() {
        this.nama = "Tidak diketahui";
        this.warna = "Tidak diketahui";
    }
    
    // ===== GETTER & SETTER (Encapsulation) =====
    
    public String getNama() {
        return nama;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }
    
    public String getWarna() {
        return warna;
    }
    
    public void setWarna(String warna) {
        this.warna = warna;
    }
    
    // ===== ABSTRACT METHODS =====
    // Wajib di-override oleh setiap subclass (Overriding)
    
    /**
     * Menghitung luas bentuk geometri.
     * Abstract karena setiap bentuk punya rumus berbeda.
     * PILAR OOP: Overriding & Polymorphism
     * @return luas bentuk
     */
    public abstract double hitungLuas();
    
    /**
     * Mengembalikan informasi lengkap tentang bentuk.
     * @return string informasi bentuk
     */
    public abstract String info();
    
    /**
     * Method info dengan parameter detail (OVERLOADING)
     * PILAR OOP: Overloading - nama method sama, parameter berbeda
     * @param detail jika true, tampilkan info lengkap
     * @return string informasi bentuk
     */
    public String info(boolean detail) {
        if (detail) {
            return "Bentuk: " + nama + " | Warna: " + warna;
        }
        return nama;
    }
    
    /**
     * Override toString() dari Object class
     */
    @Override
    public String toString() {
        return nama + " (Warna: " + warna + ")";
    }
}

package model;

/**
 * Abstract Class Bentuk2D
 * =======================
 * Turunan dari Bentuk, khusus untuk bentuk 2 dimensi.
 * 
 * PILAR OOP: Inheritance (extends Bentuk)
 *            Overriding (menambah abstract method hitungKeliling)
 */
public abstract class Bentuk2D extends Bentuk {
    
    /**
     * Constructor dengan parameter
     * Menggunakan super() untuk memanggil constructor parent
     * Sesuai materi slide 4: super(arguments)
     */
    protected Bentuk2D(String nama, String warna) {
        super(nama, warna); // memanggil constructor Bentuk
    }
    
    /**
     * Constructor tanpa parameter (OVERLOADING)
     */
    protected Bentuk2D() {
        super();
    }
    
    /**
     * Menghitung keliling bentuk 2D
     * Abstract karena setiap bentuk 2D punya rumus keliling berbeda
     * @return keliling bentuk 2D
     */
    public abstract double hitungKeliling();
    
    /**
     * Override info(boolean) dari parent
     * Memperluas informasi dengan menambahkan tipe "2D"
     * PILAR OOP: Overriding
     */
    @Override
    public String info(boolean detail) {
        if (detail) {
            return super.info(detail) + " | Tipe: 2 Dimensi";
        }
        return super.info(false);
    }
}

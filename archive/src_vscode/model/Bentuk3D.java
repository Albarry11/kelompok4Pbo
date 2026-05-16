package model;

/**
 * Abstract Class Bentuk3D
 * =======================
 * Turunan dari Bentuk, khusus untuk bentuk 3 dimensi.
 * 
 * PILAR OOP: Inheritance (extends Bentuk)
 *            Overriding (menambah abstract method hitungVolume dan hitungLuasPermukaan)
 */
public abstract class Bentuk3D extends Bentuk {
    
    /**
     * Constructor dengan parameter
     * Menggunakan super() untuk memanggil constructor parent
     */
    protected Bentuk3D(String nama, String warna) {
        super(nama, warna);
    }
    
    /**
     * Constructor tanpa parameter (OVERLOADING)
     */
    protected Bentuk3D() {
        super();
    }
    
    /**
     * Menghitung volume bentuk 3D
     * Abstract karena setiap bentuk 3D punya rumus volume berbeda
     * @return volume bentuk 3D
     */
    public abstract double hitungVolume();
    
    /**
     * Menghitung luas permukaan bentuk 3D
     * Abstract karena setiap bentuk 3D punya rumus luas permukaan berbeda
     * @return luas permukaan bentuk 3D
     */
    public abstract double hitungLuasPermukaan();
    
    /**
     * Override info(boolean) dari parent
     * PILAR OOP: Overriding
     */
    @Override
    public String info(boolean detail) {
        if (detail) {
            return super.info(detail) + " | Tipe: 3 Dimensi";
        }
        return super.info(false);
    }
}

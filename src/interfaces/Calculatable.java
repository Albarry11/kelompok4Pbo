package interfaces;

/**
 * Interface Calculatable
 * =====================
 * Sesuai materi slide 5 (Abstract Class dan Interface):
 * - Interface adalah class yang paling abstract
 * - Semua method() pada interface harus abstract
 * - Nama interface biasanya berupa verb+able (Calculatable)
 * 
 * Interface ini mendefinisikan kontrak bahwa setiap bentuk geometri
 * harus bisa melakukan kalkulasi dan mengembalikan hasilnya.
 * 
 * PILAR OOP: Interface mendukung Polymorphism dan abstraksi
 */
public interface Calculatable {
    
    /**
     * Melakukan semua kalkulasi untuk bentuk geometri
     * dan mengembalikan string hasil perhitungan.
     * @return String berisi hasil semua perhitungan
     */
    String calculate();
    
    /**
     * Mengembalikan tipe bentuk geometri
     * @return String tipe bentuk (2D atau 3D)
     */
    String getTipeBentuk();
}

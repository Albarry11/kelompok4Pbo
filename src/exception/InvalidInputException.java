package exception;

/**
 * InvalidInputException
 * =====================
 * Sesuai materi slide 6 (Exception Handling):
 * - Custom exception class yang extends Exception
 * - Digunakan untuk validasi input pengguna
 * - Mekanisme: throw → throws → try-catch-finally
 * 
 * PILAR OOP: Inheritance (extends Exception)
 */
public class InvalidInputException extends Exception {
    
    /**
     * Constructor dengan pesan error
     * @param pesan pesan error yang akan ditampilkan
     */
    public InvalidInputException(String pesan) {
        super(pesan); // memanggil constructor parent (Exception)
    }
    
    /**
     * Constructor tanpa parameter (overloading constructor)
     * PILAR OOP: Overloading
     */
    public InvalidInputException() {
        super("Input tidak valid! Pastikan semua nilai positif.");
    }
}

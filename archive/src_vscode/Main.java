import gui.MainFrame;
import model.*;
import interfaces.Calculatable;

/**
 * Main Class - Entry Point Program
 * =================================
 * Kelas utama yang menjalankan aplikasi.
 * 
 * Mendemonstrasikan POLYMORPHISM:
 * - Super Class yang sama (Bentuk) dapat mewujudkan banyak objek
 *   yang memiliki perilaku berbeda-beda (sesuai materi slide 4)
 * 
 * Contoh Polymorphism (sesuai slide):
 *   Bentuk b1 = new LayangLayang(...);
 *   Bentuk b2 = new LimasLayangLayang(...);
 *   Bentuk b3 = new PrismaLayangLayang(...);
 */
public class Main {
    
    public static void main(String[] args) {
        
        System.out.println("========================================");
        System.out.println("  KALKULATOR BANGUN GEOMETRI");
        System.out.println("  Layang-Layang | Limas | Prisma");
        System.out.println("  PBO - IF C");
        System.out.println("========================================\n");
        
        // === DEMONSTRASI POLYMORPHISM di Console ===
        System.out.println("--- DEMO POLYMORPHISM ---");
        
        // Buat objek LayangLayang
        LayangLayang layang = new LayangLayang(14, 10, 8, 6);
        
        // POLYMORPHISM: tipe Bentuk, objek berbeda-beda
        Bentuk b1 = layang;
        Bentuk b2 = new LimasLayangLayang(new LayangLayang(14, 10, 8, 6), 12);
        Bentuk b3 = new PrismaLayangLayang(new LayangLayang(14, 10, 8, 6), 15);
        
        // Array polymorphic
        Bentuk[] semuaBentuk = { b1, b2, b3 };
        
        for (Bentuk b : semuaBentuk) {
            System.out.println("\nObjek: " + b.getClass().getSimpleName());
            System.out.println("hitungLuas() = " + String.format("%.2f", b.hitungLuas()));
            System.out.println(b.info());
            System.out.println("---");
        }
        
        // === JALANKAN GUI ===
        System.out.println("\nMembuka GUI...\n");
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }
}

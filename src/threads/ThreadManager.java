package threads;

import model.*;
import interfaces.Calculatable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Class ThreadManager
 * ===================
 * Mengelola pembuatan dan eksekusi thread-thread perhitungan.
 * 
 * Sesuai materi slide 7 (Multithreading):
 * - Mengatur thread pool sederhana
 * - Setting prioritas thread
 * - Mengatur interrupt antar thread
 * - Menggunakan join() untuk menunggu thread selesai
 * 
 * PILAR OOP: Multithreading, Encapsulation
 */
public class ThreadManager {
    
    // ===== ENCAPSULATION =====
    private JTextArea logArea;
    private Thread threadLayang;
    private Thread threadLimas;
    private Thread threadPrisma;
    private HitungThread runnableLayang;
    private HitungThread runnableLimas;
    private HitungThread runnablePrisma;
    
    /**
     * Constructor
     * @param logArea JTextArea untuk menampilkan log thread
     */
    public ThreadManager(JTextArea logArea) {
        this.logArea = logArea;
    }
    
    /**
     * Jalankan perhitungan untuk semua bentuk secara paralel dengan saling interrupt
     * 
     * Mekanisme:
     * 1. Thread 1 (LayangLayang) berjalan → selesai → interrupt Thread 2
     * 2. Thread 2 (Limas) berjalan → terinterrupt → handle → selesai → interrupt Thread 3
     * 3. Thread 3 (Prisma) berjalan → terinterrupt → handle → selesai
     * 
     * @param layang objek LayangLayang
     * @param limas objek LimasLayangLayang
     * @param prisma objek PrismaLayangLayang
     */
    public void jalankanSemuaThread(LayangLayang layang, LimasLayangLayang limas, PrismaLayangLayang prisma) {
        
        // Bersihkan log
        SwingUtilities.invokeLater(() -> logArea.setText(""));
        appendLog("════════════════════════════════════════");
        appendLog("   MULTITHREADING - PERHITUNGAN PARALEL");
        appendLog("════════════════════════════════════════\n");
        
        // Buat Runnable objects (POLYMORPHISM: Calculatable interface)
        runnableLayang = new HitungThread(layang, "Layang-Layang", logArea, 1500);
        runnableLimas = new HitungThread(limas, "Limas", logArea, 2000);
        runnablePrisma = new HitungThread(prisma, "Prisma", logArea, 2500);
        
        // Buat Thread objects
        threadLayang = new Thread(runnableLayang, "Thread-LayangLayang");
        threadLimas = new Thread(runnableLimas, "Thread-Limas");
        threadPrisma = new Thread(runnablePrisma, "Thread-Prisma");
        
        // Set prioritas thread (sesuai materi: prioritas 1-10)
        threadLayang.setPriority(Thread.MAX_PRIORITY);  // prioritas 10
        threadLimas.setPriority(Thread.NORM_PRIORITY);   // prioritas 5
        threadPrisma.setPriority(Thread.MIN_PRIORITY);   // prioritas 1
        
        appendLog("📋 Konfigurasi Thread:");
        appendLog("   Thread-LayangLayang : Prioritas " + threadLayang.getPriority() + " (MAX)");
        appendLog("   Thread-Limas        : Prioritas " + threadLimas.getPriority() + " (NORMAL)");
        appendLog("   Thread-Prisma       : Prioritas " + threadPrisma.getPriority() + " (MIN)");
        appendLog("");
        
        // Set interrupt chain: Layang → interrupt → Limas → interrupt → Prisma
        runnableLayang.setNextThread(threadLimas);
        runnableLimas.setNextThread(threadPrisma);
        
        appendLog("🔗 Rantai Interrupt:");
        appendLog("   LayangLayang → [interrupt] → Limas → [interrupt] → Prisma\n");
        
        // Jalankan semua thread secara bersamaan
        appendLog("🚀 Memulai semua thread secara paralel...\n");
        threadLayang.start();
        threadLimas.start();
        threadPrisma.start();
        
        // Monitor thread di thread terpisah (agar GUI tidak freeze)
        new Thread(() -> {
            try {
                // Tunggu semua thread selesai menggunakan join()
                // Sesuai materi slide 7: join() menunggu thread selesai
                threadLayang.join();
                threadLimas.join();
                threadPrisma.join();
                
                appendLog("\n════════════════════════════════════════");
                appendLog("   SEMUA THREAD TELAH SELESAI");
                appendLog("════════════════════════════════════════");
                appendLog("   Status Layang-Layang : " + (runnableLayang.isSelesai() ? "✓ Selesai" : "✗ Belum"));
                appendLog("   Status Limas         : " + (runnableLimas.isSelesai() ? "✓ Selesai" : "✗ Belum"));
                appendLog("   Status Prisma        : " + (runnablePrisma.isSelesai() ? "✓ Selesai" : "✗ Belum"));
                
            } catch (InterruptedException e) {
                appendLog("⚠ Monitor thread terinterrupt!");
            }
        }, "Thread-Monitor").start();
    }
    
    /**
     * Hentikan semua thread yang sedang berjalan
     */
    public void hentikanSemua() {
        if (threadLayang != null && threadLayang.isAlive()) {
            threadLayang.interrupt();
        }
        if (threadLimas != null && threadLimas.isAlive()) {
            threadLimas.interrupt();
        }
        if (threadPrisma != null && threadPrisma.isAlive()) {
            threadPrisma.interrupt();
        }
        appendLog("🛑 Semua thread dihentikan!");
    }
    
    /**
     * Append log ke JTextArea secara thread-safe
     */
    private void appendLog(String message) {
        if (logArea != null) {
            SwingUtilities.invokeLater(() -> {
                logArea.append(message + "\n");
                logArea.setCaretPosition(logArea.getDocument().getLength());
            });
        }
        System.out.println(message);
    }
    
    // Getter untuk status
    public boolean isSemuaSelesai() {
        return (runnableLayang != null && runnableLayang.isSelesai()) &&
               (runnableLimas != null && runnableLimas.isSelesai()) &&
               (runnablePrisma != null && runnablePrisma.isSelesai());
    }
}

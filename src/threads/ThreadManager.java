package threads;

import model.*;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Class ThreadManager
 * ===================
 * Mengelola 3 thread yang memproses data random > 99.000 secara paralel.
 * 
 * Sesuai materi slide 7: priority, join(), interrupt, synchronized
 * Sesuai ketentuan dosen: data banyak, > 99.000, Math.random()
 */
public class ThreadManager {
    
    private JTextArea logArea;
    private Thread threadLayang;
    private Thread threadLimas;
    private Thread threadPrisma;
    private HitungThread runnableLayang;
    private HitungThread runnableLimas;
    private HitungThread runnablePrisma;
    
    public ThreadManager(JTextArea logArea) {
        this.logArea = logArea;
    }
    
    /**
     * Jalankan 3 thread paralel, masing-masing memproses data random > 99.000
     * @param jumlahData jumlah data random per thread
     */
    public void jalankanSemuaThread(int jumlahData) {
        
        SwingUtilities.invokeLater(() -> logArea.setText(""));
        appendLog("════════════════════════════════════════════════════");
        appendLog("   MULTITHREADING — PERHITUNGAN PARALEL");
        appendLog("   Data random per thread: " + jumlahData);
        appendLog("   Semua nilai > 99.000 (via Math.random())");
        appendLog("════════════════════════════════════════════════════\n");
        
        // Buat Runnable objects — setiap thread proses tipe bentuk berbeda
        runnableLayang = new HitungThread("LAYANG", "Layang-Layang", logArea, jumlahData);
        runnableLimas = new HitungThread("LIMAS", "Limas", logArea, jumlahData);
        runnablePrisma = new HitungThread("PRISMA", "Prisma", logArea, jumlahData);
        
        // Buat Thread
        threadLayang = new Thread(runnableLayang, "Thread-LayangLayang");
        threadLimas = new Thread(runnableLimas, "Thread-Limas");
        threadPrisma = new Thread(runnablePrisma, "Thread-Prisma");
        
        // Set prioritas (sesuai materi: 1-10)
        threadLayang.setPriority(Thread.MAX_PRIORITY);   // 10
        threadLimas.setPriority(Thread.NORM_PRIORITY);   // 5
        threadPrisma.setPriority(Thread.MIN_PRIORITY);   // 1
        
        appendLog("📋 Konfigurasi Thread:");
        appendLog("   Thread-LayangLayang : Prioritas " + threadLayang.getPriority() + " (MAX)");
        appendLog("   Thread-Limas        : Prioritas " + threadLimas.getPriority() + " (NORMAL)");
        appendLog("   Thread-Prisma       : Prioritas " + threadPrisma.getPriority() + " (MIN)");
        appendLog("");
        
        // Set rantai interrupt
        runnableLayang.setNextThread(threadLimas);
        runnableLimas.setNextThread(threadPrisma);
        
        appendLog("🔗 Rantai Interrupt:");
        appendLog("   LayangLayang → [interrupt] → Limas → [interrupt] → Prisma\n");
        
        // Jalankan semua BERSAMAAN
        appendLog("🚀 Memulai semua thread secara paralel...\n");
        long startTime = System.currentTimeMillis();
        
        threadLayang.start();
        threadLimas.start();
        threadPrisma.start();
        
        // Monitor di thread terpisah
        new Thread(() -> {
            try {
                threadLayang.join();
                threadLimas.join();
                threadPrisma.join();
                
                long elapsed = System.currentTimeMillis() - startTime;
                
                appendLog("════════════════════════════════════════════════════");
                appendLog("   SEMUA THREAD TELAH SELESAI");
                appendLog("   Waktu eksekusi total: " + elapsed + " ms");
                appendLog("════════════════════════════════════════════════════");
                appendLog("   Layang-Layang : " + (runnableLayang.isSelesai() ? "✓" : "✗") + " (" + runnableLayang.getDataSelesai() + " data)");
                appendLog("   Limas         : " + (runnableLimas.isSelesai() ? "✓" : "✗") + " (" + runnableLimas.getDataSelesai() + " data)");
                appendLog("   Prisma        : " + (runnablePrisma.isSelesai() ? "✓" : "✗") + " (" + runnablePrisma.getDataSelesai() + " data)");
                appendLog("   Total data    : " + (runnableLayang.getDataSelesai() + runnableLimas.getDataSelesai() + runnablePrisma.getDataSelesai()));
                
            } catch (InterruptedException e) {
                appendLog("⚠ Monitor thread terinterrupt!");
            }
        }, "Thread-Monitor").start();
    }
    
    public void hentikanSemua() {
        if (threadLayang != null && threadLayang.isAlive()) threadLayang.interrupt();
        if (threadLimas != null && threadLimas.isAlive()) threadLimas.interrupt();
        if (threadPrisma != null && threadPrisma.isAlive()) threadPrisma.interrupt();
        appendLog("🛑 Semua thread dihentikan!");
    }
    
    private void appendLog(String message) {
        if (logArea != null) {
            SwingUtilities.invokeLater(() -> {
                logArea.append(message + "\n");
                logArea.setCaretPosition(logArea.getDocument().getLength());
            });
        }
        System.out.println(message);
    }
}

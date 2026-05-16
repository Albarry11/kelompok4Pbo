package threads;

import interfaces.Calculatable;
import javax.swing.SwingUtilities;
import javax.swing.JTextArea;

/**
 * Class HitungThread
 * ==================
 * Thread untuk melakukan perhitungan geometri secara paralel.
 * 
 * Sesuai materi slide 7 (Multithreading):
 * - Implements Runnable interface
 * - Siklus hidup thread: new → runnable → running → blocked → dead
 * - Thread bisa di-interrupt oleh thread lain
 * - Menggunakan synchronized untuk sinkronisasi
 * 
 * PILAR OOP: Multithreading
 *            Encapsulation (atribut private)
 *            Polymorphism (Calculatable interface)
 */
public class HitungThread implements Runnable {
    
    // ===== ENCAPSULATION =====
    private Calculatable bentuk;          // objek yang akan dihitung (Polymorphism!)
    private String namaBentuk;            // nama thread/bentuk
    private JTextArea logArea;            // area untuk menampilkan log di GUI
    private volatile boolean selesai;     // flag apakah thread sudah selesai (volatile sesuai slide 2)
    private String hasil;                 // hasil perhitungan
    private Thread nextThread;            // thread yang akan di-interrupt setelah selesai
    private long delayMs;                 // delay simulasi proses
    
    /**
     * Constructor
     * @param bentuk objek Calculatable yang akan dihitung
     * @param namaBentuk nama untuk identifikasi thread
     * @param logArea JTextArea untuk log output
     * @param delayMs delay dalam milidetik untuk simulasi proses
     */
    public HitungThread(Calculatable bentuk, String namaBentuk, JTextArea logArea, long delayMs) {
        this.bentuk = bentuk;
        this.namaBentuk = namaBentuk;
        this.logArea = logArea;
        this.selesai = false;
        this.hasil = "";
        this.nextThread = null;
        this.delayMs = delayMs;
    }
    
    /**
     * Set thread yang akan di-interrupt ketika thread ini selesai
     * Sesuai ketentuan: "thread bisa saling menginterrupt"
     * @param nextThread thread yang akan di-interrupt
     */
    public void setNextThread(Thread nextThread) {
        this.nextThread = nextThread;
    }
    
    /**
     * Method run() - inti eksekusi thread
     * Sesuai materi slide 7: method run() dieksekusi saat thread.start()
     * 
     * PILAR OOP: Multithreading
     */
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        
        appendLog("▶ [" + threadName + "] Thread " + namaBentuk + " DIMULAI");
        appendLog("  [" + threadName + "] Prioritas: " + Thread.currentThread().getPriority());
        
        try {
            // Fase 1: Memulai perhitungan
            appendLog("  [" + threadName + "] Memproses perhitungan " + namaBentuk + "...");
            Thread.sleep(delayMs); // Simulasi proses perhitungan
            
            // Cek apakah thread sudah di-interrupt
            if (Thread.currentThread().isInterrupted()) {
                appendLog("⚠ [" + threadName + "] " + namaBentuk + " DIINTERRUPT sebelum selesai!");
                throw new InterruptedException();
            }
            
            // Fase 2: Lakukan perhitungan (synchronized untuk thread-safety)
            synchronized (this) {
                hasil = bentuk.calculate();
                appendLog("  [" + threadName + "] Hasil perhitungan:\n" + hasil);
            }
            
            Thread.sleep(delayMs / 2); // Sedikit delay lagi
            
            // Fase 3: Selesai
            selesai = true;
            appendLog("✓ [" + threadName + "] Thread " + namaBentuk + " SELESAI");
            
            // Interrupt thread berikutnya (jika ada)
            if (nextThread != null && nextThread.isAlive()) {
                appendLog("⚡ [" + threadName + "] Mengirim INTERRUPT ke thread " + nextThread.getName());
                nextThread.interrupt();
            }
            
        } catch (InterruptedException e) {
            // Thread di-interrupt oleh thread lain
            appendLog("⚠ [" + threadName + "] " + namaBentuk + " menerima INTERRUPT!");
            appendLog("  [" + threadName + "] Menangani interrupt... melanjutkan perhitungan");
            
            // Tetap lakukan perhitungan meskipun di-interrupt
            synchronized (this) {
                hasil = bentuk.calculate();
                appendLog("  [" + threadName + "] Hasil setelah interrupt:\n" + hasil);
            }
            
            selesai = true;
            appendLog("✓ [" + threadName + "] " + namaBentuk + " SELESAI (setelah interrupt)");
            
            // Reset interrupt status
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Append log ke JTextArea secara thread-safe
     * Menggunakan SwingUtilities.invokeLater untuk update GUI dari thread lain
     * @param message pesan yang akan ditampilkan
     */
    private void appendLog(String message) {
        if (logArea != null) {
            SwingUtilities.invokeLater(() -> {
                logArea.append(message + "\n");
                // Auto-scroll ke bawah
                logArea.setCaretPosition(logArea.getDocument().getLength());
            });
        }
        System.out.println(message); // juga print ke console
    }
    
    // ===== GETTER (Encapsulation) =====
    
    public boolean isSelesai() {
        return selesai;
    }
    
    public String getHasil() {
        return hasil;
    }
    
    public String getNamaBentuk() {
        return namaBentuk;
    }
}

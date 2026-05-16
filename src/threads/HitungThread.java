package threads;

import model.*;
import interfaces.Calculatable;
import javax.swing.SwingUtilities;
import javax.swing.JTextArea;

/**
 * Class HitungThread
 * ==================
 * Thread untuk melakukan perhitungan geometri secara paralel
 * dengan data random dalam jumlah banyak (> 99.000).
 * 
 * Sesuai materi slide 7 (Multithreading):
 * - Implements Runnable interface
 * - Thread bisa di-interrupt oleh thread lain
 * - Menggunakan synchronized untuk sinkronisasi
 * 
 * Sesuai ketentuan dosen:
 * - Data input banyak, > 99.000, pakai Math.random()
 * 
 * PILAR OOP: Multithreading, Encapsulation, Polymorphism
 */
public class HitungThread implements Runnable {
    
    // ===== ENCAPSULATION =====
    private String namaBentuk;
    private String tipeBentuk;           // "LAYANG", "LIMAS", "PRISMA"
    private JTextArea logArea;
    private volatile boolean selesai;    // volatile sesuai materi slide 2
    private Thread nextThread;
    private int jumlahData;              // jumlah data random yang diproses
    
    // Hasil agregat
    private double totalLuas;
    private double totalKeliling;
    private double totalVolume;
    private double totalLuasPermukaan;
    private int dataSelesai;
    
    /**
     * Constructor
     * @param tipeBentuk "LAYANG", "LIMAS", atau "PRISMA"
     * @param namaBentuk nama untuk identifikasi thread
     * @param logArea JTextArea untuk log output
     * @param jumlahData jumlah data random yang akan diproses
     */
    public HitungThread(String tipeBentuk, String namaBentuk, JTextArea logArea, int jumlahData) {
        this.tipeBentuk = tipeBentuk;
        this.namaBentuk = namaBentuk;
        this.logArea = logArea;
        this.jumlahData = jumlahData;
        this.selesai = false;
        this.totalLuas = 0;
        this.totalKeliling = 0;
        this.totalVolume = 0;
        this.totalLuasPermukaan = 0;
        this.dataSelesai = 0;
    }
    
    /**
     * Set thread yang akan di-interrupt ketika thread ini selesai
     * @param nextThread thread yang akan di-interrupt
     */
    public void setNextThread(Thread nextThread) {
        this.nextThread = nextThread;
    }
    
    /**
     * Method run() - inti eksekusi thread
     * Memproses banyak data random dengan nilai > 99.000
     * menggunakan Math.random()
     */
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        
        appendLog("▶ [" + threadName + "] Thread " + namaBentuk + " DIMULAI");
        appendLog("  [" + threadName + "] Prioritas: " + Thread.currentThread().getPriority());
        appendLog("  [" + threadName + "] Jumlah data random: " + jumlahData);
        appendLog("  [" + threadName + "] Semua nilai di-generate > 99.000 via Math.random()\n");
        
        try {
            for (int i = 0; i < jumlahData; i++) {
                
                // Cek interrupt
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }
                
                // Generate nilai random > 99.000 pakai Math.random()
                double d1 = Math.random() * 900000 + 99000;   // 99.000 - 999.000
                double d2 = Math.random() * 900000 + 99000;
                double sisiA = Math.random() * 900000 + 99000;
                double sisiB = Math.random() * 900000 + 99000;
                double tinggi = Math.random() * 900000 + 99000;
                
                // Hitung berdasarkan tipe bentuk (POLYMORPHISM via switch)
                switch (tipeBentuk) {
                    case "LAYANG":
                        LayangLayang ll = new LayangLayang(d1, d2, sisiA, sisiB);
                        // synchronized untuk akses shared data
                        synchronized (this) {
                            totalLuas += ll.hitungLuas();
                            totalKeliling += ll.hitungKeliling();
                        }
                        break;
                        
                    case "LIMAS":
                        LayangLayang alasLimas = new LayangLayang(d1, d2, sisiA, sisiB);
                        LimasLayangLayang limas = new LimasLayangLayang(alasLimas, tinggi);
                        synchronized (this) {
                            totalLuas += limas.hitungLuas();
                            totalVolume += limas.hitungVolume();
                            totalLuasPermukaan += limas.hitungLuasPermukaan();
                        }
                        break;
                        
                    case "PRISMA":
                        LayangLayang alasPrisma = new LayangLayang(d1, d2, sisiA, sisiB);
                        PrismaLayangLayang prisma = new PrismaLayangLayang(alasPrisma, tinggi);
                        synchronized (this) {
                            totalLuas += prisma.hitungLuas();
                            totalVolume += prisma.hitungVolume();
                            totalLuasPermukaan += prisma.hitungLuasPermukaan();
                        }
                        break;
                }
                
                dataSelesai = i + 1;
                
                // Log progres setiap 25%
                if (dataSelesai == jumlahData / 4 || dataSelesai == jumlahData / 2 
                    || dataSelesai == (jumlahData * 3) / 4) {
                    int persen = (dataSelesai * 100) / jumlahData;
                    appendLog("  [" + threadName + "] Progres: " + persen + "% (" + dataSelesai + "/" + jumlahData + ")");
                }
            }
            
            // Selesai
            selesai = true;
            appendLog("✓ [" + threadName + "] " + namaBentuk + " SELESAI (" + dataSelesai + " data diproses)");
            logHasil(threadName);
            
            // Interrupt thread berikutnya
            if (nextThread != null && nextThread.isAlive()) {
                appendLog("⚡ [" + threadName + "] Mengirim INTERRUPT ke " + nextThread.getName());
                nextThread.interrupt();
            }
            
        } catch (InterruptedException e) {
            appendLog("⚠ [" + threadName + "] " + namaBentuk + " menerima INTERRUPT! (" + dataSelesai + "/" + jumlahData + " selesai)");
            appendLog("  [" + threadName + "] Menangani interrupt... melanjutkan sisa data");
            
            // Lanjutkan sisa data setelah interrupt
            for (int i = dataSelesai; i < jumlahData; i++) {
                double d1 = Math.random() * 900000 + 99000;
                double d2 = Math.random() * 900000 + 99000;
                double sisiA = Math.random() * 900000 + 99000;
                double sisiB = Math.random() * 900000 + 99000;
                double tinggi = Math.random() * 900000 + 99000;
                
                switch (tipeBentuk) {
                    case "LAYANG":
                        LayangLayang ll = new LayangLayang(d1, d2, sisiA, sisiB);
                        synchronized (this) {
                            totalLuas += ll.hitungLuas();
                            totalKeliling += ll.hitungKeliling();
                        }
                        break;
                    case "LIMAS":
                        LimasLayangLayang limas = new LimasLayangLayang(d1, d2, sisiA, sisiB, tinggi);
                        synchronized (this) {
                            totalLuas += limas.hitungLuas();
                            totalVolume += limas.hitungVolume();
                            totalLuasPermukaan += limas.hitungLuasPermukaan();
                        }
                        break;
                    case "PRISMA":
                        PrismaLayangLayang prisma = new PrismaLayangLayang(d1, d2, sisiA, sisiB, tinggi);
                        synchronized (this) {
                            totalLuas += prisma.hitungLuas();
                            totalVolume += prisma.hitungVolume();
                            totalLuasPermukaan += prisma.hitungLuasPermukaan();
                        }
                        break;
                }
                dataSelesai = i + 1;
            }
            
            selesai = true;
            appendLog("✓ [" + threadName + "] " + namaBentuk + " SELESAI setelah interrupt (" + dataSelesai + " data)");
            logHasil(threadName);
        }
    }
    
    /**
     * Log hasil agregat perhitungan
     */
    private void logHasil(String threadName) {
        appendLog("  [" + threadName + "] ─── HASIL AGREGAT ───");
        appendLog("  [" + threadName + "] Total Luas          : " + String.format("%,.2f", totalLuas));
        if (tipeBentuk.equals("LAYANG")) {
            appendLog("  [" + threadName + "] Total Keliling       : " + String.format("%,.2f", totalKeliling));
        }
        if (tipeBentuk.equals("LIMAS") || tipeBentuk.equals("PRISMA")) {
            appendLog("  [" + threadName + "] Total Volume         : " + String.format("%,.2f", totalVolume));
            appendLog("  [" + threadName + "] Total Luas Permukaan : " + String.format("%,.2f", totalLuasPermukaan));
        }
        appendLog("  [" + threadName + "] Rata-rata Luas       : " + String.format("%,.2f", totalLuas / jumlahData));
        appendLog("");
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
    
    // ===== GETTER =====
    public boolean isSelesai() { return selesai; }
    public String getNamaBentuk() { return namaBentuk; }
    public double getTotalLuas() { return totalLuas; }
    public double getTotalVolume() { return totalVolume; }
    public int getDataSelesai() { return dataSelesai; }
}

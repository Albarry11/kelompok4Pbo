package threads;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Class ThreadManager - Mengatur eksekusi 3 thread paralel
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
    
    public void jalankanSemuaThread(int dataLayang, int dataLimas, int dataPrisma) {
        
        SwingUtilities.invokeLater(() -> logArea.setText(""));
        
        int total = dataLayang + dataLimas + dataPrisma;
        
        appendLog("=========================================================");
        appendLog("  MULTITHREADING - PERHITUNGAN PARALEL");
        appendLog("  Nilai random     : > 99.000 (Math.random())");
        appendLog("=========================================================");
        appendLog("");
        
        runnableLayang = new HitungThread("LAYANG", "Layang-Layang", logArea, dataLayang);
        runnableLimas = new HitungThread("LIMAS", "Limas", logArea, dataLimas);
        runnablePrisma = new HitungThread("PRISMA", "Prisma", logArea, dataPrisma);
        
        threadLayang = new Thread(runnableLayang, "Thread-Layang");
        threadLimas = new Thread(runnableLimas, "Thread-Limas");
        threadPrisma = new Thread(runnablePrisma, "Thread-Prisma");
        
        threadLayang.setPriority(Thread.MAX_PRIORITY);
        threadLimas.setPriority(Thread.NORM_PRIORITY);
        threadPrisma.setPriority(Thread.MIN_PRIORITY);
        
        appendLog("  KONFIGURASI");
        appendLog("  ---------------------------------------------------------");
        appendLog("  Thread           | Prioritas | Data        | Target");
        appendLog("  ---------------------------------------------------------");
        appendLog("  Thread-Layang    | 10 (MAX)  | " + pad(fmt(dataLayang)) + " | LayangLayang");
        appendLog("  Thread-Limas     |  5 (NORM) | " + pad(fmt(dataLimas)) + " | LimasLayangLayang");
        appendLog("  Thread-Prisma    |  1 (MIN)  | " + pad(fmt(dataPrisma)) + " | PrismaLayangLayang");
        appendLog("  ---------------------------------------------------------");
        appendLog("  Total            |           | " + pad(fmt(total)) + " |");
        appendLog("  ---------------------------------------------------------");
        appendLog("");
        appendLog("  RANTAI INTERRUPT");
        appendLog("  Layang -->> Limas -->> Prisma");
        appendLog("");
        
        runnableLayang.setNextThread(threadLimas);
        runnableLimas.setNextThread(threadPrisma);
        
        appendLog("=========================================================");
        appendLog("  EKSEKUSI DIMULAI");
        appendLog("=========================================================");
        appendLog("");
        
        long startTime = System.currentTimeMillis();
        
        threadLayang.start();
        threadLimas.start();
        threadPrisma.start();
        
        new Thread(() -> {
            try {
                threadLayang.join();
                threadLimas.join();
                threadPrisma.join();
                
                long elapsed = System.currentTimeMillis() - startTime;
                int totalDone = runnableLayang.getDataSelesai() + runnableLimas.getDataSelesai() + runnablePrisma.getDataSelesai();
                
                appendLog("");
                appendLog("=========================================================");
                appendLog("  REKAP");
                appendLog("=========================================================");
                appendLog("  Waktu eksekusi   : " + fmt(elapsed) + " ms");
                appendLog("  ---------------------------------------------------------");
                appendLog("  Thread           | Status  | Data Diproses");
                appendLog("  ---------------------------------------------------------");
                appendLog("  Thread-Layang    | " + status(runnableLayang) + " | " + fmt(runnableLayang.getDataSelesai()));
                appendLog("  Thread-Limas     | " + status(runnableLimas) + " | " + fmt(runnableLimas.getDataSelesai()));
                appendLog("  Thread-Prisma    | " + status(runnablePrisma) + " | " + fmt(runnablePrisma.getDataSelesai()));
                appendLog("  ---------------------------------------------------------");
                appendLog("  Total            |         | " + fmt(totalDone));
                appendLog("=========================================================");
                
            } catch (InterruptedException e) {
                appendLog("  [Monitor] Thread monitor terinterrupt.");
            }
        }, "Thread-Monitor").start();
    }
    
    private String status(HitungThread t) {
        return t.isSelesai() ? "Selesai" : "Gagal  ";
    }
    
    /** Format angka dengan titik per 3 digit */
    private String fmt(long angka) {
        java.text.DecimalFormatSymbols sym = new java.text.DecimalFormatSymbols(new java.util.Locale("id", "ID"));
        sym.setGroupingSeparator('.');
        return new java.text.DecimalFormat("#,###", sym).format(angka);
    }
    
    /** Padding kanan agar kolom rata */
    private String pad(String s) {
        return String.format("%-11s", s);
    }
    
    public void hentikanSemua() {
        if (threadLayang != null && threadLayang.isAlive()) threadLayang.interrupt();
        if (threadLimas != null && threadLimas.isAlive()) threadLimas.interrupt();
        if (threadPrisma != null && threadPrisma.isAlive()) threadPrisma.interrupt();
        appendLog("  [System] Semua thread dihentikan.");
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

package com.kelompok4.threads;

import com.kelompok4.model.*;
import javax.swing.SwingUtilities;
import javax.swing.JTextArea;

public class HitungThread implements Runnable {
    private String namaBentuk; private String tipeBentuk; private JTextArea logArea; private volatile boolean selesai; private Thread nextThread; private int jumlahData; private double totalLuas; private double totalKeliling; private double totalVolume; private double totalLuasPermukaan; private int dataSelesai;
    public HitungThread(String tipeBentuk, String namaBentuk, JTextArea logArea, int jumlahData) { this.tipeBentuk = tipeBentuk; this.namaBentuk = namaBentuk; this.logArea = logArea; this.jumlahData = jumlahData; this.selesai = false; this.totalLuas = 0; this.totalKeliling = 0; this.totalVolume = 0; this.totalLuasPermukaan = 0; this.dataSelesai = 0; }
    public void setNextThread(Thread nextThread) { this.nextThread = nextThread; }
    @Override public void run() {
        String tn = Thread.currentThread().getName();
        appendLog("[" + tn + "]  MULAI  |  Prioritas: " + Thread.currentThread().getPriority() + "  |  Data: " + jumlahData);
        try {
            for (int i = 0; i < jumlahData; i++) {
                if (Thread.currentThread().isInterrupted()) { throw new InterruptedException(); }
                double d1 = Math.random() * 900000 + 99000;
                double d2 = Math.random() * 900000 + 99000;
                double sisiA = Math.random() * 900000 + 99000;
                double sisiB = Math.random() * 900000 + 99000;
                double tinggi = Math.random() * 900000 + 99000;
                switch (tipeBentuk) {
                    case "LAYANG": LayangLayang ll = new LayangLayang(d1, d2, sisiA, sisiB); synchronized (this) { totalLuas += ll.hitungLuas(); totalKeliling += ll.hitungKeliling(); } break;
                    case "LIMAS": LimasLayangLayang limas = new LimasLayangLayang(d1, d2, sisiA, sisiB, tinggi); synchronized (this) { totalLuas += limas.hitungLuas(); totalVolume += limas.hitungVolume(); totalLuasPermukaan += limas.hitungLuasPermukaan(); } break;
                    case "PRISMA": PrismaLayangLayang prisma = new PrismaLayangLayang(d1, d2, sisiA, sisiB, tinggi); synchronized (this) { totalLuas += prisma.hitungLuas(); totalVolume += prisma.hitungVolume(); totalLuasPermukaan += prisma.hitungLuasPermukaan(); } break;
                }
                dataSelesai = i + 1;
                if (dataSelesai == jumlahData / 4 || dataSelesai == jumlahData / 2 || dataSelesai == (jumlahData * 3) / 4) {
                    int persen = (dataSelesai * 100) / jumlahData;
                    appendLog("[" + tn + "]  PROSES |  " + persen + "%  (" + dataSelesai + "/" + jumlahData + ")");
                }
            }
            selesai = true;
            appendLog("[" + tn + "]  SELESAI|  " + dataSelesai + " data diproses");
            logHasil(tn);
            if (nextThread != null && nextThread.isAlive()) { appendLog("[" + tn + "]  >> INTERRUPT dikirim ke " + nextThread.getName()); nextThread.interrupt(); }
        } catch (InterruptedException e) {
            appendLog("[" + tn + "]  << INTERRUPT diterima  (" + dataSelesai + "/" + jumlahData + " selesai)");
            for (int i = dataSelesai; i < jumlahData; i++) {
                double d1 = Math.random() * 900000 + 99000;
                double d2 = Math.random() * 900000 + 99000;
                double sisiA = Math.random() * 900000 + 99000;
                double sisiB = Math.random() * 900000 + 99000;
                double tinggi = Math.random() * 900000 + 99000;
                switch (tipeBentuk) {
                    case "LAYANG": LayangLayang ll = new LayangLayang(d1, d2, sisiA, sisiB); synchronized (this) { totalLuas += ll.hitungLuas(); totalKeliling += ll.hitungKeliling(); } break;
                    case "LIMAS": LimasLayangLayang limas = new LimasLayangLayang(d1, d2, sisiA, sisiB, tinggi); synchronized (this) { totalLuas += limas.hitungLuas(); totalVolume += limas.hitungVolume(); totalLuasPermukaan += limas.hitungLuasPermukaan(); } break;
                    case "PRISMA": PrismaLayangLayang prisma = new PrismaLayangLayang(d1, d2, sisiA, sisiB, tinggi); synchronized (this) { totalLuas += prisma.hitungLuas(); totalVolume += prisma.hitungVolume(); totalLuasPermukaan += prisma.hitungLuasPermukaan(); } break;
                }
                dataSelesai = i + 1;
            }
            selesai = true;
            appendLog("[" + tn + "]  SELESAI|  " + dataSelesai + " data (setelah interrupt)");
            logHasil(tn);
        }
    }
    private void logHasil(String tn) { appendLog("[" + tn + "]  HASIL  |  Total Luas           = " + String.format("%,.2f", totalLuas)); if (tipeBentuk.equals("LAYANG")) { appendLog("[" + tn + "]         |  Total Keliling       = " + String.format("%,.2f", totalKeliling)); appendLog("[" + tn + "]         |  Rata-rata Luas      = " + String.format("%,.2f", totalLuas / jumlahData)); appendLog("[" + tn + "]         |  Rata-rata Keliling  = " + String.format("%,.2f", totalKeliling / jumlahData)); } if (tipeBentuk.equals("LIMAS") || tipeBentuk.equals("PRISMA")) { appendLog("[" + tn + "]         |  Total Volume        = " + String.format("%,.2f", totalVolume)); appendLog("[" + tn + "]         |  Total LP            = " + String.format("%,.2f", totalLuasPermukaan)); appendLog("[" + tn + "]         |  Rata-rata Luas      = " + String.format("%,.2f", totalLuas / jumlahData)); appendLog("[" + tn + "]         |  Rata-rata Volume    = " + String.format("%,.2f", totalVolume / jumlahData)); } }
    private void appendLog(String message) { if (logArea != null) { SwingUtilities.invokeLater(() -> { logArea.append(message + "\n"); logArea.setCaretPosition(logArea.getDocument().getLength()); }); } System.out.println(message); }
    public boolean isSelesai() { return selesai; } public String getNamaBentuk() { return namaBentuk; } public double getTotalLuas() { return totalLuas; } public double getTotalVolume() { return totalVolume; } public int getDataSelesai() { return dataSelesai; }
    public double getTotalKeliling() { return totalKeliling; } public double getTotalLuasPermukaan() { return totalLuasPermukaan; }
}

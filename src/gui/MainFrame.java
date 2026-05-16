package gui;

import model.*;
import threads.*;
import exception.InvalidInputException;
import interfaces.Calculatable;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * MainFrame - GUI utama aplikasi
 * Menggunakan Java Swing (sesuai materi slide 9 & 10)
 * Komponen: JFrame, JPanel, JTextField, JRadioButton, JButton, JTextArea, JMenuBar
 * Event Handling: ActionListener (slide 10)
 */
public class MainFrame extends JFrame {

    // ===== ENCAPSULATION: Komponen GUI private =====
    private JRadioButton rbLayang, rbLimas, rbPrisma;
    private JTextField tfD1, tfD2, tfSisiA, tfSisiB, tfTinggi;
    private JLabel lblTinggi;
    private JTextArea taHasil, taLog;
    private JButton btnHitung, btnReset, btnHitungSemua, btnStop;
    private JPanel panelTinggi;
    private ThreadManager threadManager;

    public MainFrame() {
        super("Kalkulator Bangun Geometri - Layang-Layang | PBO IF C");
        initComponents();
        initMenuBar();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 750);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);
        JMenuItem miReset = new JMenuItem("Reset");
        miReset.addActionListener(e -> resetForm());
        JMenuItem miExit = new JMenuItem("Exit");
        miExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        miExit.addActionListener(e -> {
            int opt = JOptionPane.showConfirmDialog(this, "Yakin ingin keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) System.exit(0);
        });
        menuFile.add(miReset);
        menuFile.addSeparator();
        menuFile.add(miExit);

        JMenu menuBantuan = new JMenu("Bantuan");
        menuBantuan.setMnemonic(KeyEvent.VK_B);
        JMenuItem miAbout = new JMenuItem("Tentang");
        miAbout.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "Kalkulator Bangun Geometri\nLayang-Layang, Limas, Prisma\n\n" +
            "Mata Kuliah: PBO - Kelas IF C\n\n" +
            "Kelompok 4 :\n" +
            "- 123240008 Muhammad Wingga Tribaya\n" +
            "- 123240009 Muhammad Faaiz\n" +
            "- 123240232 Azarya Shalom Widura\n" +
            "- 123220209 Albarry Hirzi Iskandar\n\n" +
            "5 Pilar OOP:\n" +
            "1. Encapsulation\n2. Inheritance\n3. Overloading\n" +
            "4. Overriding & Polymorphism\n5. Multithreading",
            "Tentang Aplikasi", JOptionPane.INFORMATION_MESSAGE));
        JMenuItem miRumus = new JMenuItem("Rumus");
        miRumus.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "RUMUS BANGUN GEOMETRI:\n\n" +
            "Layang-Layang (2D):\n  Luas = ½ × d1 × d2\n  Keliling = 2 × (a + b)\n\n" +
            "Limas Layang-Layang (3D):\n  Volume = ⅓ × Luas alas × tinggi\n  LP = Luas alas + Σ sisi tegak\n\n" +
            "Prisma Layang-Layang (3D):\n  Volume = Luas alas × tinggi\n  LP = 2 × Luas alas + Keliling × tinggi",
            "Rumus", JOptionPane.INFORMATION_MESSAGE));
        menuBantuan.add(miAbout);
        menuBantuan.add(miRumus);

        menuBar.add(menuFile);
        menuBar.add(menuBantuan);
        setJMenuBar(menuBar);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        ((JPanel)getContentPane()).setBorder(new EmptyBorder(10,10,10,10));

        // === PANEL KIRI: Input ===
        JPanel panelKiri = new JPanel();
        panelKiri.setLayout(new BoxLayout(panelKiri, BoxLayout.Y_AXIS));
        panelKiri.setPreferredSize(new Dimension(380, 0));

        // Pilih Bentuk
        JPanel panelBentuk = new JPanel(new GridLayout(3, 1, 5, 5));
        panelBentuk.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Pilih Bentuk Geometri",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 13)));
        ButtonGroup bg = new ButtonGroup();
        rbLayang = new JRadioButton("Layang-Layang (2D)", true);
        rbLimas = new JRadioButton("Limas Layang-Layang (3D)");
        rbPrisma = new JRadioButton("Prisma Layang-Layang (3D)");
        bg.add(rbLayang); bg.add(rbLimas); bg.add(rbPrisma);
        panelBentuk.add(rbLayang); panelBentuk.add(rbLimas); panelBentuk.add(rbPrisma);

        ActionListener rbListener = e -> updateInputFields();
        rbLayang.addActionListener(rbListener);
        rbLimas.addActionListener(rbListener);
        rbPrisma.addActionListener(rbListener);

        // Input Data
        JPanel panelInput = new JPanel(new GridLayout(5, 2, 8, 8));
        panelInput.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Input Data",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 13)));
        tfD1 = new JTextField(10); tfD2 = new JTextField(10);
        tfSisiA = new JTextField(10); tfSisiB = new JTextField(10);
        tfTinggi = new JTextField(10);
        lblTinggi = new JLabel("Tinggi:");

        panelInput.add(new JLabel("Diagonal 1:")); panelInput.add(tfD1);
        panelInput.add(new JLabel("Diagonal 2:")); panelInput.add(tfD2);
        panelInput.add(new JLabel("Sisi A:")); panelInput.add(tfSisiA);
        panelInput.add(new JLabel("Sisi B:")); panelInput.add(tfSisiB);
        panelTinggi = new JPanel(new BorderLayout());
        panelInput.add(lblTinggi); panelInput.add(tfTinggi);

        // Tombol
        JPanel panelTombol = new JPanel(new GridLayout(2, 2, 8, 8));
        panelTombol.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Aksi",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 13)));
        btnHitung = new JButton("Hitung");
        btnReset = new JButton("Reset");
        btnHitungSemua = new JButton("Hitung Semua (Thread)");
        btnStop = new JButton("Stop Thread");
        btnStop.setEnabled(false);
        panelTombol.add(btnHitung); panelTombol.add(btnReset);
        panelTombol.add(btnHitungSemua); panelTombol.add(btnStop);

        // Styling tombol
        btnHitung.setBackground(new Color(46, 139, 87));
        btnHitung.setForeground(Color.WHITE);
        btnHitung.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnReset.setBackground(new Color(178, 34, 34));
        btnReset.setForeground(Color.WHITE);
        btnHitungSemua.setBackground(new Color(70, 130, 180));
        btnHitungSemua.setForeground(Color.WHITE);
        btnHitungSemua.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnStop.setBackground(new Color(220, 20, 60));
        btnStop.setForeground(Color.WHITE);

        panelKiri.add(panelBentuk);
        panelKiri.add(Box.createVerticalStrut(8));
        panelKiri.add(panelInput);
        panelKiri.add(Box.createVerticalStrut(8));
        panelKiri.add(panelTombol);

        // === PANEL KANAN: Hasil ===
        JPanel panelKanan = new JPanel(new BorderLayout(5, 5));

        taHasil = new JTextArea(10, 30);
        taHasil.setEditable(false);
        taHasil.setFont(new Font("Monospaced", Font.PLAIN, 13));
        taHasil.setBorder(new EmptyBorder(8,8,8,8));
        JScrollPane scrollHasil = new JScrollPane(taHasil);
        scrollHasil.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Hasil Perhitungan",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 13)));

        taLog = new JTextArea(12, 30);
        taLog.setEditable(false);
        taLog.setFont(new Font("Monospaced", Font.PLAIN, 11));
        taLog.setBackground(new Color(30, 30, 30));
        taLog.setForeground(new Color(0, 255, 0));
        taLog.setBorder(new EmptyBorder(8,8,8,8));
        JScrollPane scrollLog = new JScrollPane(taLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Log Thread (Multithreading)",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 13)));

        panelKanan.add(scrollHasil, BorderLayout.NORTH);
        panelKanan.add(scrollLog, BorderLayout.CENTER);

        add(panelKiri, BorderLayout.WEST);
        add(panelKanan, BorderLayout.CENTER);

        // === EVENT HANDLING (slide 10) ===
        btnHitung.addActionListener(e -> hitungSingle());
        btnReset.addActionListener(e -> resetForm());
        btnHitungSemua.addActionListener(e -> hitungSemuaThread());
        btnStop.addActionListener(e -> {
            if (threadManager != null) threadManager.hentikanSemua();
            btnStop.setEnabled(false);
        });

        updateInputFields();
    }

    private void updateInputFields() {
        boolean is3D = rbLimas.isSelected() || rbPrisma.isSelected();
        lblTinggi.setVisible(is3D);
        tfTinggi.setVisible(is3D);
    }

    /**
     * Hitung single (tanpa thread)
     * Mendemonstrasikan POLYMORPHISM: variabel tipe Bentuk bisa berisi berbagai subclass
     */
    private void hitungSingle() {
        try {
            double d1 = Double.parseDouble(tfD1.getText().trim());
            double d2 = Double.parseDouble(tfD2.getText().trim());
            double sA = Double.parseDouble(tfSisiA.getText().trim());
            double sB = Double.parseDouble(tfSisiB.getText().trim());

            // POLYMORPHISM: satu variabel tipe Bentuk, banyak bentuk objek
            Bentuk bentuk;

            if (rbLayang.isSelected()) {
                LayangLayang ll = new LayangLayang(d1, d2, sA, sB);
                ll.validasiInput();
                bentuk = ll;
            } else if (rbLimas.isSelected()) {
                double t = Double.parseDouble(tfTinggi.getText().trim());
                LimasLayangLayang limas = new LimasLayangLayang(d1, d2, sA, sB, t);
                limas.validasiInput();
                bentuk = limas;
            } else {
                double t = Double.parseDouble(tfTinggi.getText().trim());
                PrismaLayangLayang prisma = new PrismaLayangLayang(d1, d2, sA, sB, t);
                prisma.validasiInput();
                bentuk = prisma;
            }

            // Polymorphism: method info() berbeda tergantung objek asli
            taHasil.setText("POLYMORPHISM Demo:\n");
            taHasil.append("Tipe variabel : Bentuk\n");
            taHasil.append("Objek asli    : " + bentuk.getClass().getSimpleName() + "\n\n");
            taHasil.append(bentuk.info() + "\n\n");
            taHasil.append("--- Detail (Overloading info(boolean)) ---\n");
            taHasil.append(bentuk.info(true) + "\n\n");

            if (bentuk instanceof Calculatable) {
                taHasil.append("--- Langkah Perhitungan ---\n");
                taHasil.append(((Calculatable) bentuk).calculate() + "\n");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Input harus berupa angka!", "Error Input", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validasi Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Hitung semua bentuk menggunakan Multithreading
     * Sesuai ketentuan dosen: data > 99.000 pakai Math.random()
     * PILAR OOP: Multithreading + Polymorphism
     */
    private void hitungSemuaThread() {
        // Tanya jumlah data
        String input = JOptionPane.showInputDialog(this,
            "Masukkan jumlah data random per thread:\n" +
            "(Setiap data akan di-generate > 99.000 via Math.random())\n" +
            "Rekomendasi: 100000",
            "Konfigurasi Multithreading", JOptionPane.QUESTION_MESSAGE);
        
        if (input == null || input.trim().isEmpty()) return;
        
        try {
            int jumlahData = Integer.parseInt(input.trim());
            if (jumlahData <= 0) {
                JOptionPane.showMessageDialog(this, "Jumlah data harus > 0!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Tampilkan info di panel hasil
            taHasil.setText("=== MULTITHREADING MODE ===\n\n");
            taHasil.append("Jumlah data per thread : " + jumlahData + "\n");
            taHasil.append("Total data (3 thread)  : " + (jumlahData * 3) + "\n");
            taHasil.append("Nilai random           : > 99.000 (Math.random())\n\n");
            taHasil.append("POLYMORPHISM:\n");
            taHasil.append("  Thread 1 → LayangLayang.hitungLuas()\n");
            taHasil.append("  Thread 2 → LimasLayangLayang.hitungVolume()\n");
            taHasil.append("  Thread 3 → PrismaLayangLayang.hitungVolume()\n");
            taHasil.append("  Nama method sama, perilaku beda!\n\n");
            taHasil.append("Lihat log thread di bawah ↓\n");
            
            // Jalankan thread
            threadManager = new ThreadManager(taLog);
            threadManager.jalankanSemuaThread(jumlahData);
            btnStop.setEnabled(true);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Input harus angka bulat!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        tfD1.setText(""); tfD2.setText(""); tfSisiA.setText("");
        tfSisiB.setText(""); tfTinggi.setText("");
        taHasil.setText(""); taLog.setText("");
        rbLayang.setSelected(true);
        updateInputFields();
    }
}

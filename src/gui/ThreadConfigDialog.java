package gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Dialog konfigurasi multithreading.
 * 3 kolom input — ketik di kolom pertama, sisanya ikut otomatis.
 * Klik kolom lain untuk custom manual.
 */
public class ThreadConfigDialog extends JDialog {
    
    private JTextField tfLayang, tfLimas, tfPrisma;
    private boolean limasCustom = false;
    private boolean prismaCustom = false;
    private boolean updating = false;
    private int[] result = null; // null = cancelled
    
    private static final DecimalFormat FORMAT;
    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("id", "ID"));
        symbols.setGroupingSeparator('.');
        FORMAT = new DecimalFormat("#,###", symbols);
    }
    
    public ThreadConfigDialog(JFrame parent) {
        super(parent, "Konfigurasi Multithreading", true);
        initComponents();
        setSize(420, 320);
        setLocationRelativeTo(parent);
        setResizable(false);
    }
    
    private void initComponents() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Header
        JLabel header = new JLabel("Jumlah data random per thread (nilai > 99.000)");
        header.setFont(new Font("SansSerif", Font.PLAIN, 12));
        
        JLabel hint = new JLabel("<html><i>Isi kolom pertama, sisanya ikut otomatis.<br>"
            + "Klik kolom lain untuk input manual.</i></html>");
        hint.setFont(new Font("SansSerif", Font.PLAIN, 11));
        hint.setForeground(Color.GRAY);
        
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        headerPanel.add(header);
        headerPanel.add(hint);
        
        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        tfLayang = createField();
        tfLimas = createField();
        tfPrisma = createField();
        
        // Placeholder
        tfLayang.setText("100.000");
        tfLimas.setText("100.000");
        tfPrisma.setText("100.000");
        tfLimas.setForeground(Color.GRAY);
        tfPrisma.setForeground(Color.GRAY);
        
        inputPanel.add(new JLabel("Thread Layang-Layang :"));
        inputPanel.add(tfLayang);
        inputPanel.add(new JLabel("Thread Limas :"));
        inputPanel.add(tfLimas);
        inputPanel.add(new JLabel("Thread Prisma :"));
        inputPanel.add(tfPrisma);
        
        // Auto-sync: ketik di Layang → Limas & Prisma ikut
        tfLayang.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { syncFields(); }
            public void removeUpdate(DocumentEvent e) { syncFields(); }
            public void changedUpdate(DocumentEvent e) { syncFields(); }
        });
        
        // Klik Limas → mode manual
        tfLimas.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                limasCustom = true;
                tfLimas.setForeground(Color.BLACK);
                tfLimas.selectAll();
            }
        });
        
        // Klik Prisma → mode manual
        tfPrisma.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                prismaCustom = true;
                tfPrisma.setForeground(Color.BLACK);
                tfPrisma.selectAll();
            }
        });
        
        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton btnOk = new JButton("Jalankan");
        JButton btnCancel = new JButton("Batal");
        btnOk.setBackground(new Color(46, 139, 87));
        btnOk.setForeground(Color.WHITE);
        btnOk.setFont(new Font("SansSerif", Font.BOLD, 12));
        
        btnOk.addActionListener(e -> {
            try {
                int v1 = parseFormatted(tfLayang.getText());
                int v2 = parseFormatted(tfLimas.getText());
                int v3 = parseFormatted(tfPrisma.getText());
                
                if (v1 <= 0 || v2 <= 0 || v3 <= 0) {
                    JOptionPane.showMessageDialog(this, "Semua nilai harus > 0", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                result = new int[]{ v1, v2, v3 };
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Input tidak valid. Masukkan angka bulat.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnCancel.addActionListener(e -> dispose());
        
        btnPanel.add(btnCancel);
        btnPanel.add(btnOk);
        
        main.add(headerPanel, BorderLayout.NORTH);
        main.add(inputPanel, BorderLayout.CENTER);
        main.add(btnPanel, BorderLayout.SOUTH);
        
        setContentPane(main);
    }
    
    private JTextField createField() {
        JTextField tf = new JTextField(16);
        tf.setFont(new Font("Monospaced", Font.PLAIN, 14));
        tf.setHorizontalAlignment(JTextField.RIGHT);
        
        // Format otomatis: tambah "." per 3 digit saat ketik
        tf.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (updating) return;
                formatField(tf);
            }
        });
        
        return tf;
    }
    
    private void formatField(JTextField tf) {
        updating = true;
        try {
            String raw = tf.getText().replace(".", "").trim();
            if (!raw.isEmpty()) {
                long val = Long.parseLong(raw);
                tf.setText(FORMAT.format(val));
            }
        } catch (NumberFormatException ex) {
            // biarkan, user mungkin masih mengetik
        }
        updating = false;
    }
    
    private void syncFields() {
        if (updating) return;
        String val = tfLayang.getText();
        if (!limasCustom) {
            tfLimas.setText(val);
            tfLimas.setForeground(Color.GRAY);
        }
        if (!prismaCustom) {
            tfPrisma.setText(val);
            tfPrisma.setForeground(Color.GRAY);
        }
    }
    
    private int parseFormatted(String text) {
        return Integer.parseInt(text.replace(".", "").trim());
    }
    
    /**
     * Tampilkan dialog dan kembalikan hasil.
     * @return int[3] jumlah data per thread, atau null jika dibatalkan
     */
    public int[] showDialog() {
        setVisible(true);
        return result;
    }
    
    /**
     * Utility: format angka dengan titik per 3 digit (format Indonesia)
     */
    public static String formatAngka(long angka) {
        return FORMAT.format(angka);
    }
    
    public static String formatAngka(double angka) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("id", "ID"));
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return df.format(angka);
    }
}

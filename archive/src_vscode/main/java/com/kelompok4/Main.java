package com.kelompok4;

import com.kelompok4.gui.MainFrame;
import com.kelompok4.model.*;
import com.kelompok4.interfaces.Calculatable;

public class Main {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  KALKULATOR BANGUN GEOMETRI");
        System.out.println("  Layang-Layang | Limas | Prisma");
        System.out.println("  PBO - IF C");
        System.out.println("========================================\n");

        // Demo polymorphism (console)
        LayangLayang layang = new LayangLayang(14, 10, 8, 6);
        Bentuk b1 = layang;
        Bentuk b2 = new LimasLayangLayang(new LayangLayang(14, 10, 8, 6), 12);
        Bentuk b3 = new PrismaLayangLayang(new LayangLayang(14, 10, 8, 6), 15);

        Bentuk[] semuaBentuk = { b1, b2, b3 };
        for (Bentuk b : semuaBentuk) {
            System.out.println("\nObjek: " + b.getClass().getSimpleName());
            System.out.println("hitungLuas() = " + String.format("%.2f", b.hitungLuas()));
            System.out.println(b.info());
            System.out.println("---");
        }

        // Jalankan GUI
        javax.swing.SwingUtilities.invokeLater(() -> new MainFrame());
    }
}

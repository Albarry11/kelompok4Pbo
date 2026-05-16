Project Kelompok 4 - PBO
=========================

Ringkasan
--------
Proyek ini adalah Kalkulator Bangun Geometri (Layang-Layang, Limas Layang-Layang, Prisma Layang-Layang) dengan GUI Swing dan fitur multithreading seperti tugas mata kuliah PBO.

Tujuan perubahan
----------------
- Biar mudah dibuka di Apache NetBeans, saya tambahkan `pom.xml` (Maven) sehingga NetBeans mengenali proyek secara otomatis.
- Tidak memindahkan kode sekarang — Maven dikonfigurasi untuk menggunakan `src/` sebagai source directory.

Cara buka di NetBeans
---------------------
1. Buka NetBeans → `File` → `Open Project...` → pilih folder proyek (`kelompok4Pbo`) yang berisi `pom.xml`.
2. NetBeans akan mengenali proyek Maven dan mengimpor dependensi.
3. Klik kanan project → `Clean and Build`.
4. Jalankan dari NetBeans atau jalankan jar di `target/` setelah build:

```bash
mvn clean package
java -cp target/kelompok4-pbo-1.0-SNAPSHOT.jar Main
```

Catatan penting
---------------
- Saat ini kode sumber tetap di `src/` (tidak dipindahkan ke `src/main/java`) untuk menghindari perubahan besar. Jika ingin, saya bisa memigrasikan ke struktur Maven standar.
- `Main` berada di paket default — sebaiknya pindahkan ke paket bernama (mis. `app`), tapi NetBeans dapat menjalankan juga.

Daftar tugas (untuk laporan)
----------------------------
- Cover: judul, kelas, anggota (nama & NIM)
- Topik: judul proyek dan deskripsi singkat
- Flowchart: buat diagram per kelas (masing-masing class harus punya flowchart atau bagian) — perbaiki sampai notasi benar
- Kode program: tampilkan per class/inner class (struktur taksonomi terlihat)
- Input/Output: tangkapan layar GUI, contoh run multithreading (data > 99.000)

Mapping ke 5 Pilar PBO (untuk laporan)
--------------------------------------
- Encapsulation: field private di kelas model (`Bentuk`, `Bentuk2D`, `Bentuk3D`), gunakan getter/setter.
- Inheritance: `Bentuk2D`/`Bentuk3D` turunkan ke `LayangLayang`, `LimasLayangLayang`, `PrismaLayangLayang`.
- Overloading: tunjukkan constructor overloaded di satu atau dua kelas model.
- Overriding & Polymorphism: `calculate()` atau `info()` override di subclass.
- Multithreading: `HitungThread`, `ThreadManager` — tunjukkan bagaimana thread dibuat, prioritas, interrupt.

Langkah lanjutan yang saya rekomendasikan
----------------------------------------
- (Opsional) Migrasi ke struktur Maven standar `src/main/java` + `src/test/java` untuk kompatibilitas lebih baik.
- Perapihan `Main` ke paket bernama, mis. `app.Main`.
- Tambah file `nbproject/` jika tim benar-benar ingin format NetBeans Ant project (saya bisa bantu buatkan).

Butuh bantuan lanjutan?
----------------------
Saya bisa:
- Migrasikan semua kode ke `src/main/java` dan perbarui package names.
- Membuat project NetBeans native (`nbproject/`) dan form `.form` jika tim ingin GUI editable di Matisse.
- Bantu susun flowchart dan laporan (cover + flowchart per class + screenshot hasil) berdasarkan kode.



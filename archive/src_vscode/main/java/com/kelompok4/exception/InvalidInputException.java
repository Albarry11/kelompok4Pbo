package com.kelompok4.exception;

public class InvalidInputException extends Exception {
    public InvalidInputException(String pesan) { super(pesan); }
    public InvalidInputException() { super("Input tidak valid! Pastikan semua nilai positif."); }
}

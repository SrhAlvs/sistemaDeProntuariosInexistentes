package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogWriter {
    private static boolean append = false;
    public static void write(String message) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("log.txt", append))){
            bw.write(message);
            bw.newLine();
            append = true;
        } catch (IOException e) {
            IO.println("[ERRO | LOG] Deu o carai memo."); // tomara que não dê o carai, tmj :D
            throw new RuntimeException(e);
        }
    }
}

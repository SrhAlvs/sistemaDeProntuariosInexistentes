package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogWriter {
    private static boolean append = false;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public static void write(String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("log.txt", append))){
            bw.write("[" + timestamp + "] " + message);
            bw.newLine();
            append = true;
        } catch (IOException e) {
            IO.println("[ERRO | LOG] Deu o carai memo."); // tomara que não dê o carai, tmj :D
            throw new RuntimeException(e);
        }
    }
}

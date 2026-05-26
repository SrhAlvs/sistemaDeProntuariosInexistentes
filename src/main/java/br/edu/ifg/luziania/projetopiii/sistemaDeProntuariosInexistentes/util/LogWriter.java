package br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.util;

import java.io.FileWriter;
import java.io.IOException;

public class LogWriter {
    public static void write(String message) {
        try (FileWriter fw = new FileWriter("log.txt")){
            fw.write(message);
        } catch (IOException e) {
            IO.println("[ERRO | LOG] Deu o carai memo."); // tomara que não dê o carai, tmj :D
            throw new RuntimeException(e);
        }
    }
}

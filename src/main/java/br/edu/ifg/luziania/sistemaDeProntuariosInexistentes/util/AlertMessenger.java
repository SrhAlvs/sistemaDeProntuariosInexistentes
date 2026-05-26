package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util;

import javafx.scene.control.Alert;

public class AlertMessenger {

    /**
     * Exibe um alerta pop-up na tela do usuário.
     * @param type O tipo do alerta (Alert.AlertType.ERROR, INFORMATION, WARNING, etc.)
     * @param title O título da janela do alerta.
     * @param message A mensagem principal a ser exibida.
     */
    public static void show(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);

        // "deixa o cabeçalho nulo para um design mais limpo e direto"
        alert.setHeaderText(null);
        alert.setContentText(message);

        // bloqueia a interação com a tela de trás até que o usuário feche o alerta
        alert.showAndWait();
    }
}
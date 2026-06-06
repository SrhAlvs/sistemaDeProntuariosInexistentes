package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.controller;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.LogWriter;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.ScreenNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ZHomePatientController {

    // trata o clique no botão de voltar, redirecionando para a tela de Login do Paciente
    @FXML
    private void handleBackButton(ActionEvent event) {
        // registra a ação no Log
        LogWriter.write("[SUCESSO | NAVEGAÇÃO] Usuário paciente navegando para a tela de Login.");

        // realiza a troca da cena
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginPatientPage.fxml");
    }

    // trata o clique no botão de 'marcar consulta', redirecionando para a tela de 'marcar consulta' (quem diria né?)
    @FXML
    private void handleScheduleAppointmentButton(ActionEvent event) {
        // registra a ação no Log
        LogWriter.write("[SUCESSO | NAVEGAÇÃO] Usuário paciente navegando para a tela de 'Marcar consulta'.");

        // realiza a troca da cena
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/ScheduleAppointmentPatientPage.fxml");
    }
}

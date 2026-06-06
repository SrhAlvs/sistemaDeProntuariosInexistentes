package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.controller;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.LogWriter;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.ScreenNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SPIInitialController {
    // trata o clique no botão "DOCTOR", redirecionando para a tela 'LoginDoctorPage'
    @FXML
    private void handleDoctorNavigation(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário anônimo escolheu a opção: MÉDICO.");
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginDoctorPage.fxml");
    }

    // trata o clique no botão "PATIENT", redirecionando para a tela 'LoginPatientPage'
    @FXML
    private void handlePatientNavigation(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário anônimo escolheu a opção: PACIENTE.");
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginPatientPage.fxml");
    }

}

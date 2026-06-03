package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.controller;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.LogWriter;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.ScreenNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MyDetailsDoctorController {
    // trata o clique no botão de voltar, redirecionando para a tela de Login do Doutor
    @FXML
    private void handleBackButton(ActionEvent event) {
        // registra a ação no Log
        LogWriter.write("[SUCESSO | NAVEGAÇÃO] Usuário médico navegando para a tela de Login.");

        // realiza a troca da cena
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginDoctorPage.fxml");
    }

    // trata o clique no botão de voltar, redirecionando para a tela inicial (home)
    @FXML
    private void handleHomeButton(ActionEvent event) {
        // registra a ação no Log
        LogWriter.write("[SUCESSO | NAVEGAÇÃO] Usuário médico navegando para a tela inicial (home).");

        // realiza a troca da cena
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/HomeDoctorPage.fxml");
    }

    // trata o clique no botão de Prontuários, redirecionando para a tela de Prontuários
    @FXML
    private void handleMedicalRecordsButton(ActionEvent event) {
        // registra a ação no Log
        LogWriter.write("[SUCESSO | NAVEGAÇÃO] Usuário médico navegando para a tela de Prontuários.");

        // realiza a troca da cena
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/MedicalRecordsDoctorPage.fxml");
    }
}

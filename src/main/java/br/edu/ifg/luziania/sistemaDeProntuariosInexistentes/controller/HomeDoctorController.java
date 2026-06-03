package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.controller;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.LogWriter;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.ScreenNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HomeDoctorController {

    // trata o clique no botão de voltar, redirecionando para a tela de Login do Doutor
    @FXML
    private void handleBackButton(ActionEvent event) {
        // registra a ação no Log
        LogWriter.write("[SUCESSO | NAVEGAÇÃO] Usuário médico navegando para a tela de Login.");

        // realiza a troca da cena
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginDoctorPage.fxml");
    }

    // trata o clique no botão de Prontuários, redirecionando para a tela de Prontuários
    @FXML
    private void handleMedicalRecordsButton(ActionEvent event) {
        // registra a ação no Log
        LogWriter.write("[SUCESSO | NAVEGAÇÃO] Usuário médico navegando para a tela de Prontuários.");

        // realiza a troca da cena
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/MedicalRecordsDoctorPage.fxml");
    }

    // trata o clique no botão de 'Meus dados', redirecionando para a tela de 'Meus dados'
    @FXML
    private void handleMyDetailsButton(ActionEvent event) {
        // registra a ação no Log
        LogWriter.write("[SUCESSO | NAVEGAÇÃO] Usuário médico navegando para a tela de Meus dados.");

        // realiza a troca da cena
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/MyDetailsDoctorPage.fxml");
    }
}

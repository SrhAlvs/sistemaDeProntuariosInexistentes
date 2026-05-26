package br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.controller;

import br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.util.ScreenNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SPIInitialController {
   // configura botão de troca de cena
    @FXML
    private void handleDoctorNavigation(ActionEvent event) {
        // registra a ação no console (aqui futuramente chamará o LoggerService de Auditoria)
        System.out.println("[AUDITORIA] Usuário anônimo escolheu a opção: MÉDICO.");

        // realiza a troca da cena
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/projetopiii/sistemaDeProntuariosInexistentes/view/LoginDoctorPage.fxml");
    }

    @FXML
    private void handlePatientNavigation(ActionEvent event) {
        // registra a ação no console (aqui futuramente chamará o LoggerService de Auditoria)
        System.out.println("[AUDITORIA] Usuário anônimo escolheu a opção: PACIENTE.");

        // realiza a troca da cena
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/projetopiii/sistemaDeProntuariosInexistentes/view/LoginPatientPage.fxml");
    }

}

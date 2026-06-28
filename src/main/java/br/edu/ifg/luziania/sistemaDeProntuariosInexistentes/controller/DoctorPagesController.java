package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.controller;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.LogWriter;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.ScreenNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class DoctorPagesController {

    // --------------- AUTENTICAÇÃO CADASTRO ---------------
    @FXML private TextField dcaFullNameTextField;
    @FXML private TextField dcaCRMTextField;
    @FXML private TextField dcaEmailTextField;
    @FXML private PasswordField dcaPasswordField;

    // configura botão 'Enter' (recolhe dados, passa pelo Validator e salva)
    @FXML
    private void handleCreateAccount(ActionEvent event) {
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginDoctorPage.fxml");
        String fullName = dcaFullNameTextField.getText().trim();
        String crm = dcaCRMTextField.getText().trim();
        String email = dcaEmailTextField.getText().trim();
        String password = dcaPasswordField.getText();

        try {
            // disparando a bateria de validações
            UserValidator.validateName(fullName);
            UserValidator.validateCrm(crm);
            UserValidator.validateEmail(email);
            UserValidator.validatePassword(password);

            // se chegou aqui, passou em todos os 'testes'
            AlertMessenger.show(Alert.AlertType.INFORMATION, "Sucesso", "Conta médica criada com sucesso!");

            /*FALTA SALVAR NO BANCO DE DADOS*/

            // após o cadastro, joga o médico de volta para a tela de login
            ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginDoctorPage.fxml");

        } catch (ValidationException e) {
            // // captura o erro lógico e avisa o usuário
            AlertMessenger.show(Alert.AlertType.ERROR, "Erro no Cadastro", e.getMessage());
            // Aqui chamaremos o LoggerService.logException(...) para gravar no arquivo txt
            System.err.println("[LOG EXCEÇÃO] Falha ao tentar cadastrar médico: " + e.getMessage());
        }
    }

    // --------------- AUTENTICAÇÃO LOGIN ---------------
    @FXML private TextField dlEmailCRMTextField;
    @FXML private PasswordField dlPasswordField;

    //configura o botão 'Enter' (passa pelo Validator e tenta autenticar o médico)
    @FXML
    private void handleLogin(ActionEvent event) {
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/HomeDoctorPage.fxml");
//        String inputLogin = dlEmailCRMTextField.getText().trim();
//        String password = dlPasswordField.getText();
//
//        try {
//            // validação lógica de formatos
//            UserValidator.validatePassword(password);
//
//            // verifica se o usuário tentou logar por e-mail ou por CRM
//            if (inputLogin.contains("@")) {
//                UserValidator.validateEmail(inputLogin);
//                System.out.println("[AUDITORIA] Tentativa de login via E-mail: " + inputLogin);
//            } else {
//                UserValidator.validateCrm(inputLogin);
//                System.out.println("[AUDITORIA] Tentativa de login via CRM: " + inputLogin);
//            }
//
//            ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/HomeDoctorPage.fxml");
//        } catch (ValidationException e) {
//            // captura o erro lógico e avisa o usuário
//            AlertMessenger.show(Alert.AlertType.ERROR, "Erro de Autenticação", e.getMessage());
//            // Aqui chamaremos o LoggerService.logException(...) para gravar no arquivo txt
//            System.err.println("[LOG EXCEÇÃO] Falha no login do médico: " + e.getMessage());
//
//        }
    }

    // --------------- NAVEGAÇÃO ---------------

    // trata o clique no botão 'Voltar', redirecionando para a tela de escolha, 'SPIInitialPage'
    @FXML
    private void handleBackToChoiceButton(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário navegando para a tela de Escolha da Forma de Login.");

        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/SPIInitialPage.fxml");
    }

    // trata o clique no link 'Create account', redirecionando para a tela 'CreateAccountDoctorPage'
    @FXML
    private void handleCreateAccountHyperLink(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário navegando para a tela de Cadastro de Médico.");

        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/CreateAccountDoctorPage.fxml");
    }

    // trata o clique no link "Login", redirecionando para a tela 'LoginDoctorPage'
    @FXML
    private void handleBackToLoginHyperLink(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário cancelou o cadastro e está voltando para a tela de Login de Médico.");

        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginDoctorPage.fxml");
    }

    // trata o clique no botão 'Voltar', redirecionando para a tela 'LoginDoctorPage'
    @FXML
    private void handleBackLoginButton(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário (médico) navegando para a tela de Login de Médico.");

        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginDoctorPage.fxml");
    }

    // trata o clique no botão de voltar, redirecionando para a tela 'HomeDoctorPage'
    @FXML
    private void handleBackHomeButton(ActionEvent event) {
        // registra a ação no Log
        LogWriter.write("[NAVEGAÇÃO] Usuário (médico) navegando para a Tela Inicial (Home).");

        // realiza a troca da cena
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/HomeDoctorPage.fxml");
    }

    // trata o clique no botão 'Prontuários', redirecionando para a tela 'MedicalRecordsDoctorPage'
    @FXML
    private void handleMedicalRecordsButton(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário (médico) navegando para a tela de Prontuários.");

        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/MedicalRecordsDoctorPage.fxml");
    }

    // trata o clique no botão 'Meus dados', redirecionando para a tela 'MyDetailsDoctorPage'
    @FXML
    private void handleMyDetailsButton(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário (médico) navegando para a tela de visualização dos Próprios Dados.");

        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/MyDetailsDoctorPage.fxml");
    }
}

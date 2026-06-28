package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.controller;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO.PatientDAO;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Patient;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.AlertMessenger;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.LogWriter;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.ScreenNavigator;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.UserValidator;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.exceptions.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class PatientPagesController {

    PatientDAO patient = new PatientDAO();

    // --------------- AUTENTICAÇÃO CADASTRO ---------------
    @FXML private TextField pcaFullNameTextField;
    @FXML private TextField pcaCPFTextField;
    @FXML private TextField pcaEmailTextField;
    @FXML private PasswordField pcaPasswordField;

    // configura botão 'Enter' (recolhe dados, passa pelo Validator e salva)
    @FXML
    private void handleCreateAccount(ActionEvent event) {

        String fullName = pcaFullNameTextField.getText();
        String fullNameTrimmed = fullName.trim();
        String cpf = pcaCPFTextField.getText().trim();
        String email = pcaEmailTextField.getText().trim();
        String password = pcaPasswordField.getText();

        try {
            // disparando a bateria de validações
            UserValidator.validateName(fullNameTrimmed);
            UserValidator.validateCpf(cpf);
            UserValidator.validateEmail(email);
            UserValidator.validatePassword(password);

            try {
                patient.insert(new Patient(fullName, email, password, "PATIENT", cpf));
            } catch (SQLException e) {
                LogWriter.write("[ERRO | BANCO DE DADOS] Falha ao inserir usuário (paciente).");
                throw new ValidationException("Falha ao tentar cadastrar conta de paciente.");
            }

            // se chegou aqui, passou em todos os 'testes'
            AlertMessenger.show(Alert.AlertType.INFORMATION, "Sucesso", "Conta de paciente criada com sucesso!");
            LogWriter.write("[CONTA] Conta de paciente criada com sucesso!");

            // após o cadastro, joga o paciente de volta para a tela de login
            ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginPatientPage.fxml");

        } catch (ValidationException e) {
            AlertMessenger.show(Alert.AlertType.ERROR, "Erro no Cadastro", e.getMessage());
            LogWriter.write("[CONTA] Falha ao tentar cadastrar conta de paciente.");
        }
    }

    // --------------- AUTENTICAÇÃO LOGIN ---------------
    @FXML private TextField plEmailCPFTextField;
    @FXML private PasswordField plPasswordField;

    // configura o botão 'Enter' (realiza a validação e tenta autenticar o paciente)
    @FXML
    private void handleLogin(ActionEvent event) {
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/HomePatientPage.fxml");
//        String inputLogin = plEmailCPFTextField.getText().trim();
//        String password = plPasswordField.getText();
//
//        try {
//            // validação lógica de formatos
//            UserValidator.validatePassword(password);
//
//            // verifica se o usuário tentou logar por e-mail ou por CPF
//            if (inputLogin.contains("@")) {
//                UserValidator.validateEmail(inputLogin);
//                System.out.println("[AUDITORIA] Tentativa de login via E-mail: " + inputLogin);
//            } else {
//                UserValidator.validateCpf(inputLogin);
//                System.out.println("[AUDITORIA] Tentativa de login via CPF: " + inputLogin);
//            }
//
//            ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/HomePatientPage.fxml");
//        } catch (ValidationException e) {
//            // captura o erro lógico e avisa o usuário
//            AlertMessenger.show(Alert.AlertType.ERROR, "Erro de Autenticação", e.getMessage());
//            // Aqui chamaremos o LoggerService.logException(...) para gravar no arquivo txt
//            System.err.println("[LOG EXCEÇÃO] Falha no login do paciente: " + e.getMessage());
//        }
    }

    // --------------- NAVEGAÇÃO ---------------

    // trata o clique no botão 'Voltar', redirecionando para a tela de escolha, 'SPIInitialPage'
    @FXML
    private void handleBackToChoiceButton(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário navegando para a tela de Escolha da Forma de Login.");

        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/SPIInitialPage.fxml");
    }

    // trata o clique no link 'Create account', redirecionando para a tela 'CreateAccountPatientPage'
    @FXML
    private void handleCreateAccountHyperLink(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário navegando para a tela de Cadastro de Paciente.");

        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/CreateAccountPatientPage.fxml");
    }

    // trata o clique no link "Login", redirecionando para a tela 'LoginPatientPage'
    @FXML
    private void handleBackToLoginHyperLink(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário cancelou o cadastro e está voltando para a tela de Login de Paciente.");

        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginPatientPage.fxml");
    }

    // trata o clique no botão 'Voltar', redirecionando para a tela 'LoginPatientPage'
    @FXML
    private void handleBackLoginButton(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário (paciente) navegando para a tela de Login de Paciente.");

        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginPatientPage.fxml");
    }

    // trata o clique no botão de voltar, redirecionando para a tela 'HomePatientPage'
    @FXML
    private void handleBackHomeButton(ActionEvent event) {
        // registra a ação no Log
        LogWriter.write("[NAVEGAÇÃO] Usuário (médico) navegando para a Tela Inicial (Home).");

        // realiza a troca da cena
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/HomePatientPage.fxml");
    }

    // trata o clique no botão de 'Marcar consulta', redirecionando para a tela "ScheduleAppointmentPatientPage"
    @FXML
    private void handleScheduleAppointmentButton(ActionEvent event) {
        // registra a ação no Log
        LogWriter.write("[NAVEGAÇÃO] Usuário (paciente) navegando para a tela de Marcar Consulta.");

        // realiza a troca da cena
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/ScheduleAppointmentPatientPage.fxml");
    }

    // trata o clique no botão 'Resultados', redirecionando para a tela 'ResultsPatientPage'
    @FXML
    private void handleResultsButton(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário (paciente) navegando para a tela de Resultados.");

        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/ResultsPatientPage.fxml");
    }

    // trata o clique no botão 'Meus dados', redirecionando para a tela 'MyDetailsPatientPage'
    @FXML
    private void handleMyDetailsButton(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário (médico) navegando para a tela de visualização dos Próprios Dados.");

        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/MyDetailsPatientPage.fxml");
    }
}

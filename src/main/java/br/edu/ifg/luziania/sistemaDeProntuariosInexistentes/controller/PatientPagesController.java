package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.controller;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO.PatientDAO;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Doctor;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.DoctorSpecialty;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Patient;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.*;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.exceptions.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PatientPagesController implements Initializable {

    PatientDAO patient = new PatientDAO();

    // =========================================================
    // ----------------- AUTENTICAÇÃO CADASTRO -----------------
    // =========================================================

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

    // ========================================================
    // ------------------ AUTENTICAÇÃO LOGIN ------------------
    // ========================================================

    @FXML private TextField plEmailCPFTextField;
    @FXML private PasswordField plPasswordField;

    // configura o botão 'Enter' (realiza a validação e tenta autenticar o paciente)
    @FXML
    private void handleLogin(ActionEvent event) {
        String inputLogin = plEmailCPFTextField.getText().trim();
        String password = plPasswordField.getText();

        try {
            // validação lógica de formatos
            UserValidator.validatePassword(password);

            // verifica se o usuário tentou logar por e-mail ou por CPF
            if (inputLogin.contains("@")) {
                LogWriter.write("[LOGIN] Tentativa de login de paciente via E-mail: " + inputLogin);

                UserValidator.validateEmail(inputLogin);

                Patient patient = this.patient.findByEmail(inputLogin);

                if (patient != null) {
                    if (patient.getPassword().equals(password) && patient.getEmail().equals(inputLogin) && patient.getType().equals("PATIENT")) {
                        LogWriter.write("[LOGIN] Sucesso no login de paciente via E-mail.");
                        Session.loginPatient(patient);
                    } else {
                        throw new ValidationException("E-mail ou senha inválidos.");
                    }
                } else {
                    throw new ValidationException("E-mail não cadastrado.");
                }

            } else {
                LogWriter.write("[LOGIN] Tentativa de login via CPF: " + inputLogin);

                UserValidator.validateCpf(inputLogin);

                Patient patient = this.patient.findByCpf(inputLogin);

                if (patient != null) {
                    if (patient.getPassword().equals(password) && patient.getCpf().equals(inputLogin) && patient.getType().equals("PATIENT")) {
                        LogWriter.write("[LOGIN] Sucesso no login de paciente via CPF.");
                        Session.loginPatient(patient);
                    } else {
                        throw new ValidationException("CPF ou senha inválidos.");
                    }
                } else {
                    throw new ValidationException("CPF não cadastrado.");
                }
            }

            ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/HomePatientPage.fxml");
        } catch (ValidationException e) {
            // captura o erro lógico e avisa o usuário
            AlertMessenger.show(Alert.AlertType.ERROR, "Erro de Autenticação", e.getMessage());
            // Aqui chamaremos o LoggerService.logException(...) para gravar no arquivo txt
            System.err.println("[LOG EXCEÇÃO] Falha no login do paciente: " + e.getMessage());
        }
    }

    // =========================================================
    // ----------------------- NAVEGAÇÃO -----------------------
    // =========================================================

    // trata o clique no botão 'Voltar', redirecionando para a tela de escolha, 'SPIInitialPage'
    @FXML
    private void handleBackToChoiceButton(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário navegando para a tela de Escolha da Forma de Login.");
        Session.logout();

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

    // ========================================================
    // --------------- PREENCHIMENTO AUTOMÁTICO ---------------
    // ========================================================

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (pmdCpfTextField != null) {
            changeMyDetailsTextField();
        }

        if(phpPatientNameLabel != null) {
            writeLoggedPatientName(phpPatientNameLabel);
        }

        if(psaPatientNameLabel != null) {
            writeLoggedPatientName(psaPatientNameLabel);
        }

        if(prPatientNameLabel != null) {
            writeLoggedPatientName(prPatientNameLabel);
        }

        if(psapPatientNameLabel != null) {
            writeLoggedPatientName(psapPatientNameLabel);
        }
    }

    // nome do paciente no menu da VBox das telas para paciente logado
    @FXML private Label phpPatientNameLabel;
    @FXML private Label psaPatientNameLabel;
    @FXML private Label prPatientNameLabel;
    @FXML private Label psapPatientNameLabel;

    public void writeLoggedPatientName(Label label) {
        Patient patient = Session.getCurrentPatient();

        if (patient != null){
            label.setText(patient.getName());
        }
    }

    // informações do médico do 'MyDetailsPatientPage'
    @FXML private TextField pmdFullNameTextField;
    @FXML private TextField pmdEmailTextField;
    @FXML private TextField pmdCpfTextField;

    public void changeMyDetailsTextField() {
        Patient patient = Session.getCurrentPatient();

        if (patient != null) {
            pmdFullNameTextField.setText(patient.getName());
            pmdEmailTextField.setText(patient.getEmail());
            pmdCpfTextField.setText(patient.getCpf());
        }
    }
}

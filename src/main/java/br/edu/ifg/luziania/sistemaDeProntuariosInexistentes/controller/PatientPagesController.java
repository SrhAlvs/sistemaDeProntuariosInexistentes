package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.controller;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO.AppointmentDAO;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO.DoctorDAO;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO.PatientDAO;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Appointment;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Doctor;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.DoctorSpecialty;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Patient;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.*;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.exceptions.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PatientPagesController implements Initializable {

    PatientDAO patient = new PatientDAO();
    AppointmentDAO appointment = new AppointmentDAO();
    DoctorDAO doctor = new DoctorDAO();

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
            LogWriter.write("[SUCESSO | CONTA] Conta de paciente criada com sucesso!");

            // após o cadastro, joga o paciente de volta para a tela de login
            ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginPatientPage.fxml");

        } catch (ValidationException e) {
            AlertMessenger.show(Alert.AlertType.ERROR, "Erro no Cadastro", e.getMessage());
            LogWriter.write("[ERRO | CONTA] Falha ao tentar cadastrar conta de paciente.");
        }
    }

    // ========================================================
    // ------------------ AUTENTICAÇÃO LOGIN ------------------
    // ========================================================

    @FXML
    private TextField plEmailCPFTextField;
    @FXML
    private PasswordField plPasswordField;

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
                LogWriter.write("[INFORMAÇÃO | LOGIN] Tentativa de login de paciente via E-mail: " + inputLogin);

                UserValidator.validateEmail(inputLogin);

                Patient patient = this.patient.findByEmail(inputLogin);

                if (patient != null) {
                    if (patient.getPassword().equals(password) && patient.getEmail().equals(inputLogin) && patient.getType().equals("PATIENT")) {
                        LogWriter.write("[SUCESSO | LOGIN] Sucesso no login de paciente via E-mail.");
                        Session.loginPatient(patient);
                    } else {
                        LogWriter.write("[ERRO | LOGIN] Falha no login de paciente. E-mail ou senha inválidos.");
                        throw new ValidationException("E-mail ou senha inválidos.");
                    }
                } else {
                    LogWriter.write("[ERRO | LOGIN] Falha no login de paciente. E-mail não cadastrado.");
                    throw new ValidationException("E-mail não cadastrado.");
                }

            } else {
                LogWriter.write("[INFORMAÇÃO | LOGIN] Tentativa de login via CPF: " + inputLogin);

                UserValidator.validateCpf(inputLogin);

                Patient patient = this.patient.findByCpf(inputLogin);

                if (patient != null) {
                    if (patient.getPassword().equals(password) && patient.getCpf().equals(inputLogin) && patient.getType().equals("PATIENT")) {
                        LogWriter.write("[SUCESSO | LOGIN] Sucesso no login de paciente via CPF.");
                        Session.loginPatient(patient);
                    } else {
                        LogWriter.write("[ERRO | LOGIN] Falha no login de paciente. CPF ou senha inválidos.");
                        throw new ValidationException("CPF ou senha inválidos.");
                    }
                } else {
                    LogWriter.write("[ERRO | LOGIN] Falha no login de paciente. CPF não cadastrado.");
                    throw new ValidationException("CPF não cadastrado.");
                }
            }

            ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/HomePatientPage.fxml");
        } catch (ValidationException e) {
            // captura o erro lógico e avisa o usuário
            AlertMessenger.show(Alert.AlertType.ERROR, "Erro de Autenticação", e.getMessage());
            // Aqui chamaremos o LoggerService.logException(...) para gravar no arquivo txt
            LogWriter.write("[ERRO | LOGIN] Falha no login do paciente: " +  e.getMessage());
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

        if (phpPatientNameLabel != null) {
            writeLoggedPatientName(phpPatientNameLabel);
        }

        if (psaPatientNameLabel != null) {
            writeLoggedPatientName(psaPatientNameLabel);
        }

        if (prPatientNameLabel != null) {
            writeLoggedPatientName(prPatientNameLabel);
        }

        if (psapPatientNameLabel != null) {
            writeLoggedPatientName(psapPatientNameLabel);
        }

        if (psaSpecialtyMenuButton != null) {
            synchronizeAppointmentSpecialty();
        }
    }

    // nome do paciente no menu da VBox das telas para paciente logado
    @FXML private Label phpPatientNameLabel;
    @FXML private Label psaPatientNameLabel;
    @FXML private Label prPatientNameLabel;
    @FXML private Label psapPatientNameLabel;

    public void writeLoggedPatientName(Label label) {
        Patient patient = Session.getCurrentPatient();

        if (patient != null) {
            label.setText(patient.getName());
        }
    }

    // especialidades da tela 'ScheduleAppointmentPatientPage'
    @FXML private MenuButton psaSpecialtyMenuButton;
    private DoctorSpecialty selectedAppointmentSpecialty;

    private void synchronizeAppointmentSpecialty() {
        // limpa limpa limpa
        psaSpecialtyMenuButton.getItems().clear();

        for (DoctorSpecialty specialty : DoctorSpecialty.values()) {
            // cria o item usando o nome bonito da especialidade
            MenuItem item = new MenuItem(specialty.getSpecialtyName());

            item.setOnAction(event -> {
                selectedAppointmentSpecialty = specialty;

                //muda o texto do botão para o médico ver o que escolheu
                psaSpecialtyMenuButton.setText(specialty.getSpecialtyName());
            });

            //adiciona o item ao MenuButton
            psaSpecialtyMenuButton.getItems().add(item);
        }
    }

    // informações do paciente do 'MyDetailsPatientPage'
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

    // habilita botões de horário livres da página 'ScheduleAppointment'
    @FXML private Button
            psa0900Button, psa0930Button, psa1000Button, psa1030Button, psa1100Button,
            psa1130Button, psa1200Button, psa1230Button, psa1300Button, psa1330Button,
            psa1400Button, psa1430Button, psa1500Button, psa1530Button, psa1600Button,
            psa1630Button, psa1700Button, psa1730Button, psa1800Button, psa1830Button;


    @FXML private DatePicker psaScheduleDatePicker;

    @FXML
    public void activateButtons(ActionEvent event) {

        try {

            LocalDate date = psaScheduleDatePicker.getValue();

            if (date == null || selectedAppointmentSpecialty == null) {
                LogWriter.write("[ERRO | CONSULTA] Paciente não preencheu todos os campos necessários para procurar horário disponível para o agendamento da consulta.");
                AlertMessenger.show(
                        Alert.AlertType.WARNING,
                        "Ausência de informações",
                        "Por favor, preencha todos os campos");
                return;
            }

            Doctor doctor = this.doctor.findBySpecialty(selectedAppointmentSpecialty);

            if (doctor == null) {
                LogWriter.write("[ERRO | CONSULTA] Nenhum médico foi encontrado para a especialidade selecionada pelo paciente.");
                AlertMessenger.show(
                        Alert.AlertType.WARNING,
                        "Especialidade indisponível",
                        "Nenhum médico encontrado para a especialidade selecionada.");
                return;
            }

            ArrayList<Appointment> appointments =
                    appointment.findAppointment(doctor.getCrm(), date);

            List<Button> buttons = List.of(
                    psa0900Button, psa0930Button, psa1000Button, psa1030Button,
                    psa1100Button, psa1130Button, psa1200Button, psa1230Button,
                    psa1300Button, psa1330Button, psa1400Button, psa1430Button,
                    psa1500Button, psa1530Button, psa1600Button, psa1630Button,
                    psa1700Button, psa1730Button, psa1800Button, psa1830Button
            );

            // Primeiro libera todos os horários
            for (Button b : buttons) {
                b.setDisable(false);
                b.setUserData("FREE");
            }

            // Depois bloqueia apenas os horários ocupados
            for (Appointment a : appointments) {
                for (Button b : buttons) {

                    if (b.getText().equals(a.getAppointmentTime())) {
                        // b.setDisable(true);
                        b.setUserData("OCCUPIED");
                        b.setOpacity(0.5);
                        break;
                    }

                }
            }

        } catch (NullPointerException e) {
            LogWriter.write("[ERRO | CONSULTA] Paciente tentou marcar consulta com algum campo nulo.");
            AlertMessenger.show(
                    Alert.AlertType.ERROR,
                    "Erro",
                    e.getMessage()
            );
        }
    }

    // ==========================================================
    // ------------------ MARCAÇÃO DE CONSULTA ------------------
    // ==========================================================

    public void scheduleAppointmentReal(ActionEvent event) {
        try {
            LocalDate appointmentDate = psaScheduleDatePicker.getValue();

            if (appointmentDate == null || selectedAppointmentSpecialty == null) {
                throw new NullPointerException("Por favor, preencha todos os campos");
            }

            Doctor doctor = this.doctor.findBySpecialty(selectedAppointmentSpecialty);

            if (doctor == null) {
                LogWriter.write("[ERRO | CONSULTA] Nenhum médico foi encontrado para a especialidade selecionada pelo paciente.");
                AlertMessenger.show(
                        Alert.AlertType.WARNING,
                        "Especialidade indisponível",
                        "Nenhum médico encontrado para a especialidade selecionada."
                );
                return;
            }

            Button buttonClicked = (Button) event.getSource();

            if ("OCCUPIED".equals(buttonClicked.getUserData())) {
                LogWriter.write("[ERRO | CONSULTA] Paciente tentou marcar consulta em um horário já ocupado.");
                AlertMessenger.show(
                        Alert.AlertType.WARNING,
                        "Horário indisponível",
                        "Este horário já foi reservado. Escolha outro horário."
                );
                return;
            }

            Appointment appointment = new Appointment(
                    doctor.getCrm(),
                    Session.getCurrentPatient().getCpf(),
                    appointmentDate,
                    buttonClicked.getText()
            );

            if(
                    AlertMessenger.confirm(
                            "Marcar consulta",
                            "Você realmente deseja marcar a consulta?"
                            + "\nMédico: " + doctor.getName()
                            + "\nEspecialidade: " + doctor.getSpecialty().getSpecialtyName()
                            + "\nData: " + appointment.getAppointmentDate()
                            + "\nHorário: " + appointment.getAppointmentTime())
            ) {
                this.appointment.insert(appointment); // Insere a consulta no banco de dados
                LogWriter.write("[SUCESSO | CONSULTA] Consulta marcada para o paciente " + Session.getCurrentPatient().getName() + ".");
                AlertMessenger.show(Alert.AlertType.INFORMATION, "Consulta marcada", "Consulta marcada com sucesso!");
            } else {
                LogWriter.write("[INFORMAÇÃO | CONSULTA] Agendamento de consulta cancelado pelo paciente.");
                AlertMessenger.show(Alert.AlertType.INFORMATION, "Agendamento cancelado", "Agendamento de consulta cancelado!");
            }


            activateButtons(null);

        } catch (NullPointerException e) {
            LogWriter.write("[ERRO | CONSULTA] Erro ao marcar uma consulta. " + e.getMessage());
            AlertMessenger.show(Alert.AlertType.ERROR, "Erro de Autenticação", e.getMessage());
        }

    }

}

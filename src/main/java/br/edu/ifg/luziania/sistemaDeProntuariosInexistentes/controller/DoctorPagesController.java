package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.controller;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO.AppointmentDAO;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO.DoctorDAO;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO.MedicalRecordDAO;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO.PatientDAO;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.*;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.*;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.exceptions.ValidationException;
import com.sun.javafx.scene.shape.ArcHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class DoctorPagesController implements Initializable {

    // =========================================================
    // ----------------- AUTENTICAÇÃO CADASTRO -----------------
    // =========================================================

    @FXML private TextField dcaFullNameTextField;
    @FXML private TextField dcaCRMTextField;
    @FXML private TextField dcaEmailTextField;
    @FXML private PasswordField dcaPasswordField;
    @FXML private MenuButton dcaSpecialtyMenuButton;

    DoctorDAO doctor = new DoctorDAO();
    PatientDAO patient = new PatientDAO();
    MedicalRecordDAO medicalRecord = new MedicalRecordDAO();
    AppointmentDAO appointment = new AppointmentDAO();

    // variável para guardar a especialidade do Médico
    private DoctorSpecialty selectedSpecialty;

    // configura botão 'Enter' (recolhe dados, passa pelo Validator e salva)
    @FXML
    private void handleCreateAccount(ActionEvent event) {
        String fullName = dcaFullNameTextField.getText();
        String fullNameTrimmed = fullName.trim();
        String crm = dcaCRMTextField.getText().trim();
        String email = dcaEmailTextField.getText().trim();
        String password = dcaPasswordField.getText();

        try {
            // disparando a bateria de validações
            UserValidator.validateName(fullNameTrimmed);
            UserValidator.validateCrm(crm);
            UserValidator.validateEmail(email);
            UserValidator.validatePassword(password);
            UserValidator.validateSpecialty(selectedSpecialty);

            try {
                doctor.insert(new Doctor(fullName, email, password, "DOCTOR", crm, selectedSpecialty));
            } catch (SQLException e) {
                LogWriter.write("[ERRO | BANCO DE DADOS] Falha ao inserir usuário (médico).");
                throw new ValidationException("Falha ao tentar cadastrar conta de médico.");
            }

            // se chegou aqui, passou em todos os 'testes'
            AlertMessenger.show(Alert.AlertType.INFORMATION, "Sucesso", "Conta médica criada com sucesso!");
            LogWriter.write("[SUCESSO | CONTA] Conta de médico criada com sucesso!");

            // após o cadastro, joga o médico de volta para a tela de login
            ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginDoctorPage.fxml");

        } catch (ValidationException e) {
            AlertMessenger.show(Alert.AlertType.ERROR, "Erro no Cadastro", e.getMessage());
            LogWriter.write("[ERRO | CONTA] Falha ao tentar cadastrar conta de médico.");
        }
    }

    // ========================================================
    // ------------------ AUTENTICAÇÃO LOGIN ------------------
    // ========================================================

    @FXML private TextField dlEmailCRMTextField;
    @FXML private PasswordField dlPasswordField;

    //configura o botão 'Enter' (passa pelo Validator e tenta autenticar o médico)
    @FXML
    private void handleLogin(ActionEvent event) {
        String inputLogin = dlEmailCRMTextField.getText().trim();
        String password = dlPasswordField.getText();

        try {
            // validação lógica de formatos
            UserValidator.validatePassword(password);

            // verifica se o usuário tentou logar por e-mail ou por CRM
            if (inputLogin.contains("@")) {
                LogWriter.write("[INFORMAÇÃO | LOGIN] Tentativa de login de médico via E-mail: " + inputLogin);

                UserValidator.validateEmail(inputLogin);

                Doctor doctor = this.doctor.findByEmail(inputLogin);

                if (doctor != null) {
                    if (doctor.getPassword().equals(password) && doctor.getEmail().equals(inputLogin) && doctor.getType().equals("DOCTOR")) {
                        LogWriter.write("[SUCESSO | LOGIN] Sucesso no login de médico via E-mail.");
                        Session.loginDoctor(doctor);
                    } else {
                        LogWriter.write("[ERRO | LOGIN] Falha no login de médico. E-mail ou senha inválidos.");
                        throw new ValidationException("E-mail ou senha inválidos.");
                    }
                } else {
                    LogWriter.write("[ERRO | LOGIN] Falha no login de médico. E-mail não cadastrado.");
                    throw new ValidationException("E-mail não cadastrado.");
                }

            } else {
                LogWriter.write("[INFORMAÇÃO | LOGIN] Tentativa de login de médico via CRM: " + inputLogin);

                UserValidator.validateCrm(inputLogin);

                Doctor doctor = this.doctor.findByCrm(inputLogin);

                if (doctor != null) {
                    if (doctor.getPassword().equals(password) && doctor.getCrm().equals(inputLogin) && doctor.getType().equals("DOCTOR")) {
                        LogWriter.write("[SUCESSO | LOGIN] Sucesso no login de médico via CRM.");
                        Session.loginDoctor(doctor);
                    } else {
                        LogWriter.write("[ERRO | LOGIN] Falha no login de médico. CRM ou senha inválidos.");
                        throw new ValidationException("CRM ou senha inválidos.");
                    }
                } else {
                    LogWriter.write("[ERRO | LOGIN] Falha no login de médico. CRM não cadastrado.");
                    throw new ValidationException("CRM não cadastrado.");
                }
            }

            ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/HomeDoctorPage.fxml");
        } catch (ValidationException e) {
            AlertMessenger.show(Alert.AlertType.ERROR, "Erro de Autenticação", e.getMessage());
            LogWriter.write("[ERRO | LOGIN] Falha no login do médico: " + e.getMessage());

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

    // ========================================================
    // --------------- PREENCHIMENTO AUTOMÁTICO ---------------
    // ========================================================

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (dcaSpecialtyMenuButton != null) {
            synchronizeSpecialties();
        }

        if (dmdCrmTextField != null) {
            changeMyDetailsTextField();
        }

        if(dhpDoctorNameLabel != null) {
            writeLoggedDoctorName(dhpDoctorNameLabel);
        }

        if(dmrDoctorNameLabel != null) {
            writeLoggedDoctorName(dmrDoctorNameLabel);
        }

        if(dmdDoctorNameLabel != null) {
            writeLoggedDoctorName(dmdDoctorNameLabel);
        }

        if(dmrCPFTableColumn != null) {
            changeMyMedicalRecordsTable();
        }

        if(dhpDateTableColumn != null) {
            changeMyAgendaTable();
        }
    }

    // menu de especialidades do 'CreateAccountDoctorPage'
    private void synchronizeSpecialties() {
        // limpa limpa limpa
        dcaSpecialtyMenuButton.getItems().clear();

        for (DoctorSpecialty specialty : DoctorSpecialty.values()) {
            // cria o item usando o nome bonito da especialidade
            MenuItem item = new MenuItem(specialty.getSpecialtyName());

            item.setOnAction(event -> {
                selectedSpecialty = specialty;

                // muda o texto do botão para o médico ver o que escolheu
                dcaSpecialtyMenuButton.setText(specialty.getSpecialtyName());
            });

            // adiciona o item ao MenuButton
            dcaSpecialtyMenuButton.getItems().add(item);
        }
    }

    // informações do médico do 'MyDetailsDoctorPage'
    @FXML private TextField dmdFullNameTextField;
    @FXML private TextField dmdEmailTextField;
    @FXML private TextField dmdCrmTextField;
    @FXML private TextField dmdSpecialtyTextField;

    public void changeMyDetailsTextField() {
        Doctor doctor = Session.getCurrentDoctor();

        if (doctor != null) {
            dmdFullNameTextField.setText(doctor.getName());
            dmdEmailTextField.setText(doctor.getEmail());
            dmdCrmTextField.setText(doctor.getCrm());
            dmdSpecialtyTextField.setText(doctor.getSpecialty().getSpecialtyName());
        }
    }

    // nome do médico no menu da VBox das telas para médico logado
    @FXML private Label dhpDoctorNameLabel;
    @FXML private Label dmrDoctorNameLabel;
    @FXML private Label dmdDoctorNameLabel;

    public void writeLoggedDoctorName(Label label) {
        Doctor doctor = Session.getCurrentDoctor();

        if (doctor != null) {
            label.setText(doctor.getName());
        }
    }

    // =========================================================
    // ------------------------ TABELAS ------------------------
    // =========================================================

    //preenche a tabela de agenda da página 'HomeDoctorPage'
    @FXML private TableView<Agenda> dhpAgendaTable;
    @FXML private TableColumn<Appointment, Date> dhpDateTableColumn;
    @FXML private TableColumn<Appointment, String> dhpTimeTableColumn;
    @FXML private TableColumn<Appointment, String> dhpPatientNameTableColumn;
    @FXML private TableColumn<Agenda, Void> dhpMedicalRecordsTableColumn;

    public void changeMyAgendaTable() {
        System.out.println("CHEGOU 1");

        dhpDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dhpTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        dhpPatientNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));

        System.out.println("CHEGOU 2");

        ArrayList<Appointment> appointments = this.appointment.findAppointmentByCRM(Session.getCurrentDoctor().getCrm());
        ArrayList<Patient> patients = this.doctor.findAllPatientsByDoctor(Session.getCurrentDoctor());
        ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();

        System.out.println("CHEGOU 2,5");

        for (Appointment appointment : appointments) {
            System.out.println("CHEGOU 2,6");
            medicalRecords.add(
                    new MedicalRecord(
                            appointment.getIdAppointment(),
                            this.medicalRecord.findByIdAppointment(appointment.getIdAppointment()).getPath()
                    )
            );
            System.out.println("CHEGOU 2,7");
        }

        System.out.println("CHEGOU 3");

        ObservableList<Agenda> rows =
                FXCollections.observableArrayList();

        System.out.println("CHEGOU 4");

        for (int i = 0; i < appointments.size(); i++) {

            Appointment appointment = appointments.get(i);
            Patient patient = patients.get(i);

            rows.add(
                    new Agenda(
                            appointment.getAppointmentDate(),
                            appointment.getAppointmentTime(),
                            patient.getName(),
                            patient
                    )
            );
        }

        System.out.println("CHEGOU 5");

        dhpAgendaTable.setItems(rows);

        dhpMedicalRecordsTableColumn.setCellFactory(param -> new TableCell<>() {

            private final Button button = new Button("Abrir");

            {
                button.setOnAction(event -> {

                    Agenda agenda =
                            getTableView().getItems().get(getIndex());

                    MedicalRecordHandler generator =
                            new MedicalRecordHandler(agenda.getPatient());

                    String path = generator.getPath();
                    File pdf = new File(path);

                    if (!pdf.exists()) {
                        AlertMessenger.show(Alert.AlertType.ERROR, "Prontuário não encontrado", "Por favor, preencha o prontuário na aba 'Prontuários' antes de tentar acessá-lo.");
                        LogWriter.write("[ERRO | PRONTUÁRIOS] Usuário médico tentou acessar um prontuário antes de preenchê-lo.");
                    } else {
                        generator.OpenPDF(path);
                    }

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {

                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });

    }

    // preenche e possibilita criação de prontuários da tabela de prontuários da página 'MedicalRecordsDoctorPage'
    @FXML private TableView<Patient> dmrMedicalRecordsTable;
    @FXML private TableColumn<Patient, String> dmrCPFTableColumn;
    @FXML private TableColumn<Patient, String> dmrPatientNameTableColumn;
    @FXML private TableColumn<Patient, Void> dmrResumeTableColumn;

    public void changeMyMedicalRecordsTable() {
        ArrayList<Patient> patients = this.doctor.findAllPatientsByDoctor(Session.getCurrentDoctor());

        dmrCPFTableColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        dmrPatientNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        ObservableList<Patient> observablePatients = FXCollections.observableArrayList();
        observablePatients.addAll(patients);

        dmrMedicalRecordsTable.setItems(observablePatients);

        dmrResumeTableColumn.setCellFactory(param -> new TableCell<>() {

            private final Button button = new Button("Abrir");

            {
                button.setOnAction(event -> {

                    Patient patient =
                            getTableView().getItems().get(getIndex());

                    MedicalRecordHandler generator =
                            new MedicalRecordHandler(patient);

                    String path = generator.getPath();
                    File pdf = new File(path);

                    if (!pdf.exists()) {
                        String patientExamType = AlertMessenger.inputMessenger("Insira as informações do paciente", "Tipo do exame: ");
                        String patientResult = AlertMessenger.inputMessenger("Insira as informações do paciente", "Resultado do exame: ");

                        generator.GeneratePDF(patientExamType, patientResult);
                        generator.OpenPDF(path);
                    } else {
                        generator.OpenPDF(path);
                    }

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {

                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
    }

}
